package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import group.ydq.utils.FileUtil;
import group.ydq.utils.RetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;
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

    private String filepath= Objects.requireNonNull(ClassUtils.getDefaultClassLoader().getResource("")).getPath()+"static/upload/";

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
        projectFile.setFilePath(filepath);
        FileUtil.upload(file, filepath, uuid);
        fileService.upload(projectFile);
        return RetResponse.success(fileService.getFile(uuid));
    }

    @RequestMapping("/getName")
    @ResponseBody
    public BaseResponse getFileName(String uuid){
        return RetResponse.success(fileService.getFile(uuid).getName());
    }

    @RequestMapping("/{type:.+}/{id:.+}")
    public void download(@PathVariable String type,@PathVariable long id,HttpServletResponse response) throws IOException{
        ProjectFile projectFile=fileService.getFile(id);
        String uuid=projectFile.getUuid();
        String filename=projectFile.getName();

        File file=new File(filepath+uuid+FileUtil.getSuffix(filename));
        if (file.exists()){
            if (type.equals("doc"))
                response.setContentType("text/plain");
            else if (type.equals("img"))
                response.setContentType("image/jpeg");
            //修改下载文件的文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"),"iso-8859-1"));

            byte[] buffer = new byte[1024];
            FileInputStream fis; //文件输入流
            BufferedInputStream bis;
            OutputStream os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }
            bis.close();
            fis.close();
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse deleteFile(long id){
        ProjectFile file=fileService.getFile(id);
        if (FileUtil.delete(filepath,file.getUuid(),FileUtil.getSuffix(file.getName()))) {
            fileService.deleteFile(id);
            return RetResponse.success();
        }
        return RetResponse.error();
    }
}
