package group.ydq.web.controller;

import com.alibaba.fastjson.JSONObject;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.CheckStageService;
import group.ydq.service.service.FileService;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.RetResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @RequestMapping("/startMid")
    private BaseResponse startMid(long projectId){
        CheckStage cs = new CheckStage();
        Project p = new Project();
        p = projectService.getProject(projectId);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(1);
        cs.setSatus(0);
        checkStageService.save(cs);
        return new BaseResponse();
    }

    @RequestMapping("/startFinal")
    private BaseResponse startFinal(long projectId){
        CheckStage cs = new CheckStage();
        Project p = new Project();
        p = projectService.getProject(projectId);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(2);
        cs.setSatus(0);
        checkStageService.save(cs);
        return new BaseResponse();
    }

    @RequestMapping("/getAll")
    private BaseResponse getAll(){
        List<CheckStage> all =  checkStageService.findAll();
        ArrayList<JSONObject> dataList = new ArrayList<>();
        for (CheckStage checkStage : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("checkStageID", checkStage.getId());
            jsonObject.put("projectName", checkStage.getProject().getName());
            jsonObject.put("projectLeader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("projectStage", checkStage.getStage());
            jsonObject.put("projectCreateTime", checkStage.getProject().getCreateTime());
            jsonObject.put("projectStatus", checkStage.getSatus());
            jsonObject.put("projectVerifers", checkStage.getVerifiers().getNick());
            dataList.add(jsonObject);
        }
        return new BaseResponse(dataList);
    }

    @RequestMapping("/getByConditions")
    private BaseResponse getByConditions(JSONObject object){
        String projectName = object.getString("projectName");
        return new BaseResponse();
    }
}
