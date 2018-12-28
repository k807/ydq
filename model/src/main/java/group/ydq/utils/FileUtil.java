package group.ydq.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Daylight
 * @date 2018/12/5 10:56
 */
public class FileUtil {

    /** 绝对路径 **/
    public static String absolutePath = "";

    /** 静态目录 **/
    public static String staticDir = "static/upload/";

    public static String upload(MultipartFile file, String uuid) throws IOException {
        //第一次会创建文件夹
        createDirIfNotExists();

        String resultPath =  uuid+getSuffix(file.getOriginalFilename());

        //存文件
        File uploadFile = new File(absolutePath, staticDir + resultPath);
        file.transferTo(uploadFile);

        return resultPath;
    }

    /**
     * 创建文件夹路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createDirIfNotExists() {
        if (!absolutePath.isEmpty()) {return;}

        //获取跟目录
        File file;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取根目录失败，无法创建上传目录！");
        }
        if(!file.exists()) {
            file = new File("");
        }

        absolutePath = file.getAbsolutePath();

        File upload = new File(absolutePath, staticDir );
        if(!upload.exists()) {
            upload.mkdirs();
        }
    }

    public static boolean delete(String uuid,String suffix) {
        File file = new File(absolutePath, staticDir + uuid + suffix);
        return file.exists() && file.delete();
    }

    public static String getSuffix(String fileName) {
        if(!fileName.contains(".")) {
            return "";
        }
        int dotIndex = fileName.indexOf(".");
        return fileName.substring(dotIndex, fileName.length());
    }
}
