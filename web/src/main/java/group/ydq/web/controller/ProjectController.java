package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.DeclareService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Daylight
 * @date 2018/11/29 0:41
 */
@RestController
@RequestMapping("/project/declare")
public class ProjectController {
    @Resource
    private DeclareService declareService;

    @RequestMapping("/teacher/save")
    private BaseResponse saveProject(Project project){
        declareService.save(project);
        return new BaseResponse();
    }

    @RequestMapping("/teacher/submit")
    private BaseResponse submitProject(Project project){
        declareService.submit(project);
        return new BaseResponse();
    }


}
