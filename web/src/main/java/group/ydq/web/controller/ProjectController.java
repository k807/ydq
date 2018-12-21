package group.ydq.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import group.ydq.authority.SubjectUtils;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.ExpertReview;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.*;
import group.ydq.utils.DateUtil;
import group.ydq.utils.FileUtil;
import group.ydq.utils.RetResponse;
import group.ydq.utils.UpdateUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Daylight
 * @date 2018/11/29 0:41
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @Resource
    private CheckStageService stageService;

    @Resource
    private DeclareService declareService;

    @Resource
    private RBACService rbacService;

    @Resource
    private FileService fileService;

    @RequestMapping("/save")
    @ResponseBody
    public BaseResponse saveProject(@RequestBody Project project){
        if (projectService.isProjectExist(project.getId())) {
            Project targetProject=projectService.getProject(project.getId());
            UpdateUtil.copyNullProperties(targetProject,project);
            project.setMajor(targetProject.getMajor());
            project.setLevel(targetProject.getLevel());
            projectService.save(project);
            return RetResponse.success(DateUtil.dateToStr(new Date(),DateUtil.format1));
        }
        return RetResponse.error();
    }

    @RequestMapping("/review")
    @ResponseBody
    public BaseResponse reviewProject(@RequestBody Map<String,Object> obj){
        if (obj!=null){
            long id=(Integer)obj.get("id");
            int score=(Integer)obj.get("score");
            String remark=(String)obj.get("remark");
            boolean mark=(Boolean)obj.get("mark");
            ExpertReview review=projectService.getExpertReview(id);
            review.setScore(score);
            review.setRemark(remark);
            review.setMark(mark);
            projectService.saveReview(review);
            if (projectService.isAllExpertsMarked(review.getProject().getId()))
                changeState(review.getProject().getId(),4);
            return RetResponse.success();
        }
        return RetResponse.error();
    }

    @RequestMapping("/changeState")
    @ResponseBody
    public BaseResponse changeState(long projectId,int state){
        if (projectService.isProjectExist(projectId)){
            projectService.changeState(projectId, state);
            if (state==5)
                stageService.startStage(projectId,1);
            return RetResponse.success(DateUtil.dateToStr(new Date(),DateUtil.format1));
        }
        return RetResponse.error();
    }

    @RequestMapping("/{stage:.+}/uploadFile")
    @ResponseBody
    public BaseResponse uploadFile(@PathVariable String stage, long projectId,long fileId){
        if (projectService.isProjectExist(projectId)){
            Project project=projectService.getProject(projectId);
            ProjectFile file=fileService.getFile(fileId);
            if (stage.equals("mid")){
                project.getMidCheckFiles().add(file);
            }else{
                project.getFinalCheckFiles().add(file);
            }
            projectService.save(project);
            return RetResponse.success();
        }
        return RetResponse.error();
    }



    @RequestMapping("/new/{type:.+}")
    @ResponseBody
    public BaseResponse saveOrSubmitProject(@PathVariable String type,@RequestBody Project project){
        project.setLeader(((User)SubjectUtils.getSubject().getBindMap("user")));
        if (type.equals("save")){
            project.setState(-1);
            project.setSubmit(false);
        }else{
            project.setState(0);
            project.setSubmit(true);
        }
        return RetResponse.success(projectService.save(project).getId());
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse deleteProject(long id){
        if (projectService.isProjectExist(id)){
            Project project=projectService.getProject(id);
            deleteFile(project.getDeclaration());
            for (ProjectFile file:project.getCommitmentPics()) {
                deleteFile(file);
            }
            projectService.deleteProject(id);
            return RetResponse.success();
        }
        return RetResponse.error();
    }

    @RequestMapping("/getDetails/{id:.+}")
    public String getDetails(@PathVariable long id, Model model){
        Project project=projectService.getProject(id);
        int stage=0;
        switch (project.getState()){
            case 5:
            case 7:stage=1;break;
            case 8:
            case 9:
            case 10:stage=2;break;
        }
        ProjectFile file=project.getDeclaration();
        JSONArray array= JSON.parseArray(project.getMembers());

        StringBuilder members= new StringBuilder();
        for (int i=0;i<array.size()-1;i++){
            members.append(array.getString(i)).append("，");
        }
        members.append(array.getString(array.size()-1));

        List<String> pics=new ArrayList<>();
        for (ProjectFile pic:project.getCommitmentPics())
            pics.add("/file/"+pic.getId());

        List<Map<String,Object>> uploadFiles=new ArrayList<>();
        for (ProjectFile uploadFile:project.getMidCheckFiles()){
            Map<String,Object> map=new HashMap<>();
            map.put("id",uploadFile.getId());
            map.put("name",uploadFile.getName());
            map.put("stage","中期检查");
            map.put("uploadTime",DateUtil.dateToStr(uploadFile.getUploadTime(),DateUtil.format1));
            uploadFiles.add(map);
        }
        for (ProjectFile uploadFile:project.getFinalCheckFiles()){
            Map<String,Object> map=new HashMap<>();
            map.put("id",uploadFile.getId());
            map.put("name",uploadFile.getName());
            map.put("stage","结题验收");
            map.put("uploadTime",DateUtil.dateToStr(uploadFile.getUploadTime(),DateUtil.format1));
            uploadFiles.add(map);
        }

        if (project.getState()==3||project.getState()==4){
            List<ExpertReview> reviews=projectService.getExpertReviews(id);
            List<Map<String,Object>> maps = new ArrayList<>();
            for (ExpertReview review : reviews) {
                Map<String, Object> map = new HashMap<>();
                map.put("expertName", review.getExpert().getNick());
                if (review.isMark()) {
                    map.put("score", review.getScore() + "分");
                    map.put("remark", review.getRemark());
                }else {
                    map.put("score", "尚未评分");
                    map.put("remark", "该专家还没干活！");
                }
                maps.add(map);
            }
            model.addAttribute("reviews", maps);
        }
        model.addAttribute("submit",project.isSubmit());
        model.addAttribute("name",project.getName());
        model.addAttribute("leader",project.getLeader());
        model.addAttribute("major",project.getMajor());
        model.addAttribute("level",project.getLevel());
        model.addAttribute("phone",project.getPhone());
        model.addAttribute("email",project.getEmail());
        model.addAttribute("members",members);
        model.addAttribute("filename",file.getName());
        model.addAttribute("filepath","/file/"+file.getId());
        model.addAttribute("commitmentPics",pics);
        model.addAttribute("stage",stage);
        model.addAttribute("state",project.getState());
        model.addAttribute("uploadFiles",uploadFiles);
        model.addAttribute("user",SubjectUtils.getSubject().getBindMap("user"));
        return "projectDetails";
    }

    @RequestMapping("/{role:.+}/list")
    @ResponseBody
    public BaseResponse getProjectList(@PathVariable String role, int page, int limit){
        String userNumber= ((User)SubjectUtils.getSubject().getBindMap("user")).getUserNumber();
        Map<String, Object> obj = new HashMap<>();
        List<Map<String,Object>> list=new ArrayList<>();
        if (role.equals("expert")){
            Page<ExpertReview> projects=projectService.getProjectOfExpert(page, limit, userNumber);
            for (ExpertReview project:projects){
                Map<String,Object> map=new HashMap<>();
                map.put("id",project.getId());
                map.put("projectId",project.getProject().getId());
                map.put("mark",project.isMark());
                map.put("score",project.getScore());
                map.put("remark",project.getRemark());
                map.put("manager",project.getProject().getManager());
                map.put("level",project.getProject().getLevel());
                map.put("name",project.getProject().getName());
                map.put("leader",project.getProject().getLeader());
                map.put("major",project.getProject().getMajor());
                map.put("createTime", DateUtil.dateToStr(project.getProject().getCreateTime(),DateUtil.format1));
                list.add(map);
            }
            obj.put("count",projects.getTotalElements());
            obj.put("data",list);
        }else {
            Page<Project> projects;
            if (role.equals("teacher")) {
                projects = projectService.getProjectsOfLeader(page, limit, userNumber);
                for (Project project:projects){
                    Map<String,Object> map=new HashMap<>();
                    map.put("id",project.getId());
                    map.put("name",project.getName());
                    map.put("state",project.getState());
                    map.put("major",project.getMajor());
                    map.put("level",project.getLevel());
                    map.put("entrance",project.getEntrance());
                    map.put("manager",project.getManager());
                    map.put("createTime",DateUtil.dateToStr(project.getCreateTime(),DateUtil.format1));
                    map.put("updateTime",DateUtil.dateToStr(project.getUpdateTime(),DateUtil.format1));
                    map.put("submit",project.isSubmit());
                    list.add(map);
                }
            }else {
                projects = projectService.getProjectsOfManagerInDeclareStage(page, limit, userNumber);
                for (Project project:projects){
                    Map<String,Object> map=new HashMap<>();
                    map.put("id",project.getId());
                    map.put("name",project.getName());
                    map.put("leader",project.getLeader());
                    map.put("state",project.getState());
                    map.put("major",project.getMajor());
                    map.put("level",project.getLevel());
                    map.put("createTime",DateUtil.dateToStr(project.getCreateTime(),DateUtil.format1));
                    map.put("updateTime",DateUtil.dateToStr(project.getUpdateTime(),DateUtil.format1));
                    map.put("experts",project.getExperts());
                    list.add(map);
                }
            }
            obj.put("count", projects.getTotalElements());
            obj.put("data", list);
        }
        return RetResponse.success(obj);
    }

    @RequestMapping("/expertList")
    @ResponseBody
    public BaseResponse getExperts(){
        //todo change "admin" to "expert"
        List<User> experts=rbacService.getUsersByRole(rbacService.getRoleByRoleName("admin"));
        Map<String,Object> map=new HashMap<>();
        map.put("data",experts);
        map.put("count",experts.size());
        return RetResponse.success(map);
    }

    @RequestMapping("/distributeExperts")
    @ResponseBody
    public BaseResponse distributeExperts(long projectId,@RequestBody List<Long> ids){
        if (ids!=null&&ids.size()!=0) {
            declareService.distributeExpert(projectId, ids);
            changeState(projectId,3);
            return RetResponse.success();
        }
        return RetResponse.error();
    }

    @RequestMapping("/declare.htm")
    public String declarePage(Model model){
        model.addAttribute("user",(User) SubjectUtils.getSubject().getBindMap("user"));
        return "/projectDeclare";
    }

    private void deleteFile(ProjectFile file){
        if (FileUtil.delete(file.getUuid(),FileUtil.getSuffix(file.getName())))
            fileService.deleteFile(file.getId());
    }
}
