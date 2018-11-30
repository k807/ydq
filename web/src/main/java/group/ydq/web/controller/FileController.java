package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Daylight
 * @date 2018/11/30 19:56
 */
@RestController
public class FileController {
    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public BaseResponse uploadHead(@RequestParam("file") MultipartFile file,String uniqueId,String name) throws IOException {
        if (!file.isEmpty()){
            ProjectFile projectFile=new ProjectFile();
            projectFile.setUniqueId(uniqueId);
            projectFile.setName(name);
            projectFile.setFile(file.getBytes());
            fileService.upload(projectFile);
        }
        return new BaseResponse();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/img/{uniqueId:.+}")
    public void getImage(@PathVariable String uniqueId, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        byte[] data;
        if (fileService.isFileExist(uniqueId)){
            data=fileService.getFile(uniqueId).getFile();
        }else {
            InputStream inputStream=new ClassPathResource("static/assets/layui/images/icon_failure.png").getInputStream();
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        }
        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/file/{uniqueId:.+}/{name:.+}")
    public void getFile(@PathVariable String uniqueId,@PathVariable String name, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        if (fileService.isFileExist(uniqueId)) {
            OutputStream stream = response.getOutputStream();
            stream.write(fileService.getFile(uniqueId).getFile());
            stream.flush();
            stream.close();
        }
    }

    @RequestMapping("/file/getName")
    public BaseResponse getFileName(String uniqueId){
        BaseResponse response=new BaseResponse();
        response.setObject(fileService.getFile(uniqueId).getName());
        return response;
    }

    @RequestMapping("/file/delete")
    public BaseResponse deleteFile(String uniqueId){
        fileService.deleteFile(uniqueId);
        return new BaseResponse();
    }
}
