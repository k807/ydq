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
import group.ydq.utils.RetResponse;
import group.ydq.utils.StageCheckStatusToProjStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

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

    @RequestMapping("/addRule")
    private BaseResponse addRule(@RequestBody Map<String, Object> msg) throws ParseException, NullPointerException {
        String title = (String) msg.get("title");
        System.out.println(title);
        String content = (String) msg.get("content");
        ArrayList<String> stage = (ArrayList<String>) msg.get("stage");
        Long sender = Long.parseLong((String) msg.get("sender"));
        User u1 = userRepository.getOne(sender);
        for (int i = 0; i < stage.size(); i++) {
            User u2 = checkStageService.findACheckStageByCheckStageID(Long.parseLong(stage.get(i))).getProject().getLeader();
            Message m = new Message(new Date(), 0, title, content, "", u1, u2);
            messageService.sendMessage(m);
        }
        return RetResponse.success();
    }

    @RequestMapping("/getAll")
    private BaseResponse getAll() throws NullPointerException {
        List<CheckStage> all = checkStageService.getCheckStageByStageStatus(1);
        ArrayList<JSONObject> dataList = new ArrayList<>();
        for (CheckStage checkStage : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", checkStage.getId());
            jsonObject.put("pid", checkStage.getProject().getId());
            jsonObject.put("name", checkStage.getProject().getName());
            jsonObject.put("leader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("stage", checkStage.getStage());
            jsonObject.put("createTime", checkStage.getProject().getCreateTime());
            jsonObject.put("status", checkStage.getStatus());
            if(null == checkStage.getVerifiers()){
                jsonObject.put("verifier", "暂时还没有人审核");
            }else{
                jsonObject.put("verifier", checkStage.getVerifiers().getNick());// 这个位置如果审核人为空的话会报NPE
            }
            dataList.add(jsonObject);
        }
        return new BaseResponse(dataList);
    }


    @RequestMapping("/getByConditions")
    @ResponseBody
    private BaseResponse getByConditions(@RequestParam(name = "name", defaultValue = "") String name,
                                         @RequestParam(name = "leader", defaultValue = "") String leader,
                                         @RequestParam(name = "status", defaultValue = "") String status,
                                         @RequestParam(name = "stage", defaultValue = "1") int stage,
                                         @RequestParam(name = "createTime", defaultValue = "1970-01-01 00:00:00") String createTime,
                                         @RequestParam(name = "endTime", defaultValue = "2049-12-31 23:59:59") String endTime) {
        List<CheckStage> dataList = checkStageService.findByConditions(name, leader, stage, status, createTime, endTime);
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

    @RequestMapping("/changeState")
    @ResponseBody
    //这个接口用于并处理接收审核的数据
    private BaseResponse changeState(@RequestBody Map<String, Object> paramsMap){
        /*
         * checkStageID --> StageCheck表中的主键ID
         * checkStageStatus --> StageCheck表中的审核状态代码 1表示通过 2表示待整改（未通过）
         * checkStageCode --> StageCheck表中的阶段代码  1表示中期，2表示结题验收
         * checkMessage --> 将要写入数据库中的审核消息
         * beingVerifiedProjStatusCode --> Project表中的项目状态代码，共11种，可以由checkStageStatus和checkStageCode共同得出
         * messageTitle --> 发送的消息的标题
         *
         * */

        Long checkStageID = ((Integer)paramsMap.get("id")).longValue();
        String checkMessage = (String) paramsMap.get("msg");
        int checkStageCode = (int) paramsMap.get("stage");
        int checkStageStatus = (int) paramsMap.get("status");
        Long verifierID = ((Integer) paramsMap.get("verifier")).longValue();
        String messageTitle = null;
        CheckStage toBeChangedCheckStage = checkStageService.findACheckStageByCheckStageID(checkStageID);
        Project project = toBeChangedCheckStage.getProject();
        User verifier = userRepository.getOne(verifierID);
        int beingVerifiedProjStatusCode = StageCheckStatusToProjStatus.changeToProjectStatus(checkStageCode,checkStageStatus);
        Date endTime = null;
        switch (beingVerifiedProjStatusCode){
            case 7:
                messageTitle = "中期审核未通过！";
                break;
            case 8:
                messageTitle = "中期审核已经通过！";
                endTime = new Date(System.currentTimeMillis());
                checkStageService.startStage(project.getId(),2);
                break;
            case 9:
                messageTitle = "结题验收未通过！";
                break;
            case 10:
                endTime = new Date(System.currentTimeMillis());
                messageTitle = "结题验收已经通过！";
                break;
            default:
                messageTitle = "未定义的项目状态！";
                break;
        }
        projectService.changeState(project.getId(), beingVerifiedProjStatusCode);
        checkStageService.changeVerifyMessage(checkStageID, checkMessage, checkStageStatus, verifier,endTime);
        JSONObject remarkJson = new JSONObject();
        remarkJson.put("projectId", project.getId());
        remarkJson.put("projectName", project.getName());
        messageService.sendMessage(new Message(new Date(), 1, messageTitle, checkMessage, JSONObject.toJSONString(remarkJson), verifier, project.getLeader()));
        //System.out.println(checkStageID + checkMessage + checkStageCode + checkStageStatus + verifierID);
        return new BaseResponse("Change success!");
    }

}
