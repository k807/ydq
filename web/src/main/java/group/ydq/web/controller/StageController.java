package group.ydq.web.controller;

import com.alibaba.fastjson.JSONObject;
import group.ydq.authority.SubjectUtils;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.CheckStageService;
import group.ydq.service.service.MessageService;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.RetResponse;
import group.ydq.utils.StageCheckStatusToProjStatus;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * author:Leo
 * date:2018/11/30
 *
 */
@RestController
@RequestMapping("/stage")
public class StageController {
    @Resource
    private ProjectService projectService;

    @Resource
    private CheckStageService checkStageService;

    @Resource
    private MessageService messageService;

    @RequestMapping("/addRule")
    @SuppressWarnings("unchecked")
    private BaseResponse addRule(@RequestBody Map<String, Object> msg, HttpServletRequest httpServletRequest) throws NullPointerException {
        String title = (String) msg.get("title");
        System.out.println(title);
        String content = (String) msg.get("content");
        ArrayList<String> stage = (ArrayList<String>) msg.get("stage");
        User u1 = (User) SubjectUtils.getSubject().getBindMap("user");
        for (String s : stage) {
            User u2 = checkStageService.findACheckStageByCheckStageID(Long.parseLong(s)).getProject().getLeader();
            Message m = new Message(new Date(), 0, title, content, "", u1, u2);
            messageService.sendMessage(m);
        }
        return RetResponse.success();
    }

    @RequestMapping("/getAll")
    private BaseResponse getAll(@RequestParam(name = "page",defaultValue = "1") int page,
                                @RequestParam(name = "limit", defaultValue = "15") int limit, HttpServletRequest httpServletRequest) throws NullPointerException {
        Page<CheckStage> all = checkStageService.findCheckStagesByStage(page,limit,1);
        return new BaseResponse(checkStageService.decorateData(all));
    }


    @RequestMapping("/getByConditions")
    @ResponseBody
    private BaseResponse getByConditions(@RequestParam(name = "page",defaultValue = "1") int page,
                                         @RequestParam(name = "limit", defaultValue = "15") int limit,
                                         @RequestParam(name = "name", defaultValue = "") String name,
                                         @RequestParam(name = "leader", defaultValue = "") String leader,
                                         @RequestParam(name = "status", defaultValue = "") String status,
                                         @RequestParam(name = "stage", defaultValue = "1") int stage,
                                         @RequestParam(name = "createTime", defaultValue = "1970-01-01 00:00:00") String createTime,
                                         @RequestParam(name = "endTime", defaultValue = "2049-12-31 23:59:59") String endTime, HttpServletRequest httpServletRequest) {
        Page<CheckStage> dataList = checkStageService.findByConditions(page,limit,name, leader, stage, status, createTime, endTime);
        return new BaseResponse(checkStageService.decorateData(dataList));
    }

    @RequestMapping("/changeState")
    @ResponseBody
    //这个接口用于并处理接收审核的数据
    private BaseResponse changeState(@RequestBody Map<String, Object> paramsMap,HttpServletRequest httpServletRequest){
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
        User verifier = (User) SubjectUtils.getSubject().getBindMap("user");
        String messageTitle;
        CheckStage toBeChangedCheckStage = checkStageService.findACheckStageByCheckStageID(checkStageID);
        Project project = toBeChangedCheckStage.getProject();
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
        return new BaseResponse("Change success!");
    }

}
