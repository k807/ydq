package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import group.ydq.utils.FileUtil;
import group.ydq.utils.RetResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Daylight
 * @date 2018/11/30 19:56
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    @ResponseBody
    public BaseResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty())
            return RetResponse.error();

        String uuid= UUID.randomUUID().toString().replace("-","").toLowerCase();
        String filename=file.getOriginalFilename();

        ProjectFile projectFile = new ProjectFile();
        projectFile.setUuid(uuid);
        projectFile.setName(filename);
        projectFile.setUploadTime(new Date());
        FileUtil.upload(file, uuid);
        return RetResponse.success(fileService.upload(projectFile));
    }

    @RequestMapping("/getName")
    @ResponseBody
    public BaseResponse getFileName(String uuid){
        return RetResponse.success(fileService.getFile(uuid).getName());
    }

    @RequestMapping("/{id:.+}")
    public ResponseEntity<FileSystemResource> download(@PathVariable long id) throws IOException{
        if (fileService.isFileExist(id)) {
            ProjectFile projectFile = fileService.getFile(id);
            String uuid = projectFile.getUuid();
            String filename = projectFile.getName();
            File file = new File(FileUtil.filepath + uuid + FileUtil.getSuffix(filename));
            if (file.exists()) {
                return ResponseEntity
                        .ok()
                        .header("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"))
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(new FileSystemResource(file));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse deleteFile(long id){
        ProjectFile file=fileService.getFile(id);
        if (FileUtil.delete(file.getUuid(),FileUtil.getSuffix(file.getName()))) {
            fileService.deleteFile(id);
            return RetResponse.success();
        }
        return RetResponse.error();
    }
}
