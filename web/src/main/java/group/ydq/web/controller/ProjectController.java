package group.ydq.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.RetResponse;
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
    private FileService fileService;

    @RequestMapping("/save")
    @ResponseBody
    private BaseResponse saveProject(@RequestBody Project project){
        project.setState(0);
        project.setSubmit(false);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        projectService.save(project);
        return RetResponse.success();
    }

    @RequestMapping("/submit")
    @ResponseBody
    private BaseResponse submitProject(@RequestBody Project project){
        project.setState(0);
        project.setSubmit(true);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        projectService.submit(project);
        return RetResponse.success();
    }

    @RequestMapping("/getDetails/{id:.+}")
    private String getDetails(@PathVariable long id, Model model){
        Project project=projectService.getProject(id);
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

        model.addAttribute("name",project.getName());
        model.addAttribute("leader",project.getLeader().getNick());
        model.addAttribute("major",project.getMajor());
        model.addAttribute("level",project.getLevel());
        model.addAttribute("phone",project.getPhone());
        model.addAttribute("email",project.getEmail());
        model.addAttribute("members",members);
        model.addAttribute("filename",file.getName());
        model.addAttribute("filepath","/file/"+file.getId());
        model.addAttribute("commitmentPics",pics);
        return "projectDetails";
    }
}
