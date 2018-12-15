package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import group.ydq.utils.FileUtil;
import group.ydq.utils.RetResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Daylight
 * @date 2018/11/30 19:56
 */
@RestController
public class FileController {
    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public BaseResponse uploadHead(@RequestParam("file") MultipartFile file,String uniqueId) throws IOException {
        if (!file.isEmpty()){
            ProjectFile projectFile=new ProjectFile();
            projectFile.setUniqueId(uniqueId);
            projectFile.setName(file.getOriginalFilename());
            projectFile.setFilePath(uniqueId+"/"+file.getOriginalFilename());
            FileUtil.upload(file.getBytes(),uniqueId,file.getOriginalFilename());
            fileService.upload(projectFile);
        }
        return RetResponse.success(fileService.getFile(uniqueId));
    }

    @RequestMapping("/file/getName")
    public BaseResponse getFileName(String uniqueId){
        return RetResponse.success(fileService.getFile(uniqueId).getName());
    }

    @RequestMapping("/file/delete")
    public BaseResponse deleteFile(String uniqueId){
        fileService.deleteFile(uniqueId);
        return RetResponse.success();
    }
}
