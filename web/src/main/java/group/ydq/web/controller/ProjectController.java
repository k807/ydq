package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.dto.ProjectDetails;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.FileService;
import group.ydq.service.service.ProjectService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Daylight
 * @date 2018/11/29 0:41
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @Resource
    private FileService fileService;

    @RequestMapping("/save")
    @ResponseBody
    private BaseResponse saveProject(@RequestBody Project project){
        projectService.save(project);
        return new BaseResponse();
    }

    @RequestMapping("/submit")
    @ResponseBody
    private BaseResponse submitProject(@RequestBody Project project){
        projectService.submit(project);
        return new BaseResponse();
    }

    @RequestMapping("/getDetails")
    private BaseResponse getDetails(long projectId){
        BaseResponse response=new BaseResponse();
        Project project=projectService.getProject(projectId);
        String filename=fileService.getFile(project.getFilepath()).getName();
        response.setObject(new ProjectDetails(project,filename));
        return response;
    }
}
