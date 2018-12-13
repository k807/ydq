package group.ydq.web.controller;

import com.alibaba.fastjson.JSONObject;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.CheckStageService;
import group.ydq.service.service.FileService;
import group.ydq.service.service.ProjectService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        return new BaseResponse(all);
    }

    @RequestMapping(value = "/getByConditions", method = RequestMethod.POST)
    private void getInfoByConditions(@RequestBody JSONObject object){
        System.out.println(object);
        String studentRegisterChannel = object.getString("studentRegisterChannel");
        String studentEndTime = object.getString("studentEndTime");
        String studentStartTime = object.getString("studentStartTime");
        String studentNumber = object.getString("sutdentNumber");
        String studentName = object.getString("studentName");
        String studentState = object.getString("studentState");
    }
}
