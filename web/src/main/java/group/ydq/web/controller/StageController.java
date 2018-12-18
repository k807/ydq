package group.ydq.web.controller;

import com.alibaba.fastjson.JSONObject;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.CheckStageService;
import group.ydq.service.service.FileService;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.RetResponse;
import group.ydq.utils.StageCheckStatusToProjStatus;
import org.springframework.web.bind.annotation.*;

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
        Project p = projectService.getProject(projectId);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(1);
        cs.setStatus(0);
        checkStageService.save(cs);
        return new BaseResponse();
    }

    @RequestMapping("/startFinal")
    private BaseResponse startFinal(long projectId){
        CheckStage cs = new CheckStage();
        Project p = projectService.getProject(projectId);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(2);
        cs.setStatus(0);
        checkStageService.save(cs);
        return new BaseResponse();
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
            jsonObject.put("verifer", checkStage.getVerifiers().getNick());// 这个位置如果审核人为空的话会报NPE
            dataList.add(jsonObject);
        }

        return new BaseResponse(dataList);
    }



    @RequestMapping("/getByConditions")
    private BaseResponse getByConditions(@RequestBody Map<String, Object> searchParamMap){

        //TODO: 在这里添加service层的调用
        System.out.println(searchParamMap);
        return new BaseResponse();
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
        Integer tempContainer = (Integer) paramsMap.get("id");
        Long stageCheckID = tempContainer.longValue();
        String message = (String) paramsMap.get("msg");
        int stageStatus = (int) paramsMap.get("status");
        int projectStage = (int) paramsMap.get("stage");//审核没有通过不必更改项目所处的阶段，但是留着这个可能还会有用……
        Long projectID = checkStageService.findACheckStageByCheckStageID(stageCheckID).getProject().getId();
        int projectStatus = StageCheckStatusToProjStatus.changeToProjectStatus(projectStage,stageStatus);
        checkStageService.changeProjectStatus(stageCheckID,message,stageStatus);
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
        Integer tempContainer = (Integer) paramsMap.get("id");
        Long stageCheckID = tempContainer.longValue();
        String message = (String) paramsMap.get("msg");
        int stageStatus = (int) paramsMap.get("status");
        int projectStage = (int) paramsMap.get("stage");//审核没有通过不必更改项目所处的阶段，但是留着这个可能还会有用……
        Long projectID = checkStageService.findACheckStageByCheckStageID(stageCheckID).getProject().getId();
        int projectStatus = StageCheckStatusToProjStatus.changeToProjectStatus(projectStage,stageStatus);
        if(projectStage == 1){
            //如果项目处于中期的话，就进入结题验收阶段
            startFinal(projectID);
        }else if (projectStage == 2){
            //若果项目结题验收通过
            // TODO::更新project表中的state状态码 （改为projectStatus变量）

        }
        return new BaseResponse();
    }

}
