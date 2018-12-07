package group.ydq.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:Leo
 * date:2018/11/30
 */
@RestController
@RequestMapping("/stage")
public class StageController {
//    @Resource
//    private ProjectService projectService;
//
//    @Resource
//    private FileService fileService;
//
//    @Resource
//    private CheckStageService checkStageService;
//
//    @RequestMapping("/startMid")
//    private BaseResponse startMid(long projectId){
//        CheckStage cs = new CheckStage();
//        Project p = new Project();
//        p = projectService.getProject(projectId);
//        cs.setProject(p);
//        cs.setStartTime(new Date());
//        cs.setUploadStatus(false);
//        cs.setStage(1);
//        cs.setSatus(0);
//        checkStageService.save(cs);
//        return new BaseResponse();
//    }
//
//    @RequestMapping("/startFinal")
//    private BaseResponse startFinal(long projectId){
//        CheckStage cs = new CheckStage();
//        Project p = new Project();
//        p = projectService.getProject(projectId);
//        cs.setProject(p);
//        cs.setStartTime(new Date());
//        cs.setUploadStatus(false);
//        cs.setStage(2);
//        cs.setSatus(0);
//        checkStageService.save(cs);
//        return new BaseResponse();
//    }
//
//    @RequestMapping("/getAll")
//    private BaseResponse getAll(){
//        List<CheckStage> all =  checkStageService.findAll();
//        return new BaseResponse();
//    }
}
