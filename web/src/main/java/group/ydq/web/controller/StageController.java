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

        for(int i = 0; i < all.size(); i++){
            for (int j = i + 1; j < all.size(); j++){
                if (all.get(i).getProject().getId().equals(all.get(j).getProject().getId())){
                    if(all.get(i).getProject().getState() <= all.get(j).getProject().getState()){
                        Collections.swap(all,i,j);
                    }
                    all.remove(j);
                    j = j - 1;
                }
            }
        }

        for (CheckStage checkStage : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", checkStage.getId());
            jsonObject.put("name", checkStage.getProject().getName());
            jsonObject.put("leader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("stage", checkStage.getStage());
            jsonObject.put("createTime", checkStage.getProject().getCreateTime());
            jsonObject.put("status", checkStage.getSatus());
            jsonObject.put("verifer", checkStage.getVerifiers().getNick());
            dataList.add(jsonObject);
        }

        return new BaseResponse(dataList);
    }

    @RequestMapping("/getByConditions")
    private BaseResponse getByConditions(JSONObject object){
        String projectName = object.getString("projectName");
        String projectLeader = object.getString("projectLeader");
        return new BaseResponse();
    }
}
