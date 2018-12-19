package group.ydq.web.controller;

import com.alibaba.fastjson.JSONObject;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.CheckStageService;
import group.ydq.service.service.FileService;
import group.ydq.service.service.MessageService;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.DateUtil;
import group.ydq.utils.RetResponse;
import group.ydq.utils.StageCheckStatusToProjStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author:Leo
 * date:2018/11/30
 */
@RestController
@RequestMapping("/stage")
public class StageController {
    @Resource
    private ProjectService projectService;

    @Resource
    private FileService fileService;

    @Resource
    private CheckStageService checkStageService;

    @Resource
    private MessageService messageService;

    @Resource
    private UserRepository userRepository;

    @RequestMapping("/startMid")
    private BaseResponse startMid(long projectId){
        checkStageService.startMid(projectId);
        return new BaseResponse();
    }

    @RequestMapping("/startFinal")
    private BaseResponse startFinal(long projectId){
        checkStageService.startFinal(projectId);
        return new BaseResponse();
    }

    @RequestMapping("/addRule")
    private  BaseResponse addRule(@RequestBody Map<String, Object> msg) throws ParseException,NullPointerException {
        String title = (String) msg.get("title");
        System.out.println(title);
        String content = (String) msg.get("content");
        ArrayList<String> stage = (ArrayList<String>) msg.get("stage");
        Long sender = Long.parseLong((String)msg.get("sender"));
        User u1 = userRepository.getOne(sender);
        for(int i = 0 ; i<stage.size();i++){
            User u2  = checkStageService.findACheckStageByCheckStageID(Long.parseLong(stage.get(i))).getProject().getLeader();
            Message m = new Message(new Date(),0,title,content,"",u1,u2);
            messageService.sendMessage(m);
        }
        return  RetResponse.success();
    }

    @RequestMapping("/getAll")
    private BaseResponse getAll() throws NullPointerException{
        List<CheckStage> all =  checkStageService.getCheckStageByStageStatus(1);
        ArrayList<JSONObject> dataList = new ArrayList<>();

       /* for(int i = 0; i < all.size(); i++){
            for (int j = i + 1; j < all.size(); j++){
                if (all.get(i).getProject().getId().equals(all.get(j).getProject().getId())){
                    if(all.get(i).getProject().getState() <= all.get(j).getProject().getState()){
                        Collections.swap(all,i,j);
                    }
                    all.remove(j);
                    j = j - 1;
                }
            }
        }*/

        for (CheckStage checkStage : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", checkStage.getId());
            jsonObject.put("name", checkStage.getProject().getName());
            jsonObject.put("leader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("stage", checkStage.getStage());
            jsonObject.put("createTime", checkStage.getProject().getCreateTime());
            jsonObject.put("status", checkStage.getStatus());
            jsonObject.put("verifier", checkStage.getVerifiers().getNick());// 这个位置如果审核人为空的话会报NPE
            dataList.add(jsonObject);
        }
        return new BaseResponse(dataList);
    }



    @RequestMapping("/getByConditions")
    @ResponseBody
    private BaseResponse getByConditions(@RequestParam(name = "name",defaultValue = "") String name,
                                         @RequestParam(name = "leader", defaultValue = "") String leader,
                                         @RequestParam(name = "status", defaultValue = "") String status,
                                         @RequestParam(name = "stage", defaultValue = "1") int stage,
                                         @RequestParam(name = "createTime",defaultValue = "1970-01-01 00:00:00") String createTime,
                                         @RequestParam(name = "endTime",defaultValue = "2049-12-31 23:59:59") String endTime){
        List<CheckStage> dataList = checkStageService.findByConditions(name,leader,stage,status,createTime,endTime);
        List<JSONObject> decoratedDataList = new ArrayList<>();
        for (CheckStage checkStage : dataList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", checkStage.getId());
            jsonObject.put("name", checkStage.getProject().getName());
            jsonObject.put("leader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("stage", checkStage.getStage());
            jsonObject.put("createTime", checkStage.getProject().getCreateTime());
            jsonObject.put("status", checkStage.getStatus());
            jsonObject.put("verifier", checkStage.getVerifiers().getNick());// 这个位置如果审核人为空的话会报NPE
            decoratedDataList.add(jsonObject);
        }
        return new BaseResponse(decoratedDataList);
    }

    @RequestMapping(value = "/denied",method = {RequestMethod.POST, RequestMethod.GET} )
    @ResponseBody
    //这个接口用于处理审核未通过的项目的内容
    private BaseResponse changeToUnpassed(@RequestBody Map<String, Object> paramsMap){

        /*
        * stageCheckID --> StageCheck表中的主键ID
        * stageStatus --> StageCheck表中的审核状态代码 1表示通过 2表示待整改（未通过）
        * projectStage --> StageCheck表中的阶段代码  1表示中期，2表示结题验收
        * projectID --> 根据CheckStage表中的ID得出的项目ID编号
        * projectStatus --> Project表中的项目状态代码，共11种，可以由projectStage和stageStatus共同得出
        *
        * */
        Long stageCheckID = ((Integer) paramsMap.get("id")).longValue();
        String message = (String) paramsMap.get("msg");
        int stageStatus = (int) paramsMap.get("status");
        int verifierID = (int)paramsMap.get("verifier");
        User verifier = userRepository.getOne((long) verifierID);  //获取审核人
        Project project = checkStageService.findACheckStageByCheckStageID(stageCheckID).getProject();
        Long projectID = project.getId();
        User projectLeader = project.getLeader();
        int projectStage = (int) paramsMap.get("stage");//审核没有通过不必更改项目所处的阶段，但是留着这个可能还会有用……
        String messageTitle = projectStage==1 ? "中期检查未通过": "结题验收未通过";
        int projectStatus = StageCheckStatusToProjStatus.changeToProjectStatus(projectStage,stageStatus);
        checkStageService.changeProjectStatus(stageCheckID,message,stageStatus,verifier);
        messageService.sendMessage(new Message(new Date(),1,messageTitle,message,"<a lay-href = '/project/getDetails/" + projectID + " '>项目详情</a>",verifier,projectLeader));
        return new BaseResponse("change success!");
    }


    @RequestMapping(value = "/accepted",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    //这个接口用于处理审核通过的项目
    private BaseResponse changeToPassed(@RequestBody Map<String, Object> paramsMap){
        /*
         * stageCheckID --> StageCheck表中的主键ID
         * stageStatus --> StageCheck表中的审核状态代码 1表示通过 2表示待整改（未通过）
         * projectStage --> StageCheck表中的阶段代码  1表示中期，2表示结题验收
         * projectID --> 根据CheckStage表中的ID得出的项目ID编号
         * projectStatus --> Project表中的项目状态代码，共11种，可以由projectStage和stageStatus共同得出
         *
         * */
        Long stageCheckID = ((Integer) paramsMap.get("id")).longValue();
        String message = (String) paramsMap.get("msg");
        int stageStatus = (int) paramsMap.get("status");
        int verifierID = (int)paramsMap.get("verifier");
        User verifier = userRepository.getOne((long) verifierID);
        int projectStage = (int) paramsMap.get("stage");//审核没有通过不必更改项目所处的阶段，但是留着这个可能还会有用……
        Project project = checkStageService.findACheckStageByCheckStageID(stageCheckID).getProject();
        Long projectID = project.getId();
        User projectLeader = project.getLeader();
        int projectStatus = StageCheckStatusToProjStatus.changeToProjectStatus(projectStage,stageStatus);
        if(projectStage == 1){
            //如果项目处于中期的话，就进入结题验收阶段
            startFinal(projectID);
        }
        String messageTitle = projectStage==1 ? "中期检查通过": "结题验收通过";
        messageService.sendMessage(new Message(new Date(),1,messageTitle,message,"<a lay-href = '/project/getDetails/" + projectID + " '>项目详情</a>",verifier,projectLeader));
        projectService.changeState(projectID,projectStatus);
        checkStageService.changeProjectStatus(stageCheckID,message,stageStatus,verifier);
        return new BaseResponse("change success!");
    }

}
