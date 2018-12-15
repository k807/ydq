package group.ydq.utils;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Daylight
 * @date 2018/12/5 10:56
 */
public class FileUtil {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void upload(MultipartFile file, String filePath, String uuid) throws IOException{
        File dest = new File(filePath + uuid + getSuffix(file.getOriginalFilename()));
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        file.transferTo(dest);
    }

    public static boolean delete(String filepath,String uuid,String suffix){
        File file=new File(filepath+uuid+suffix);
        return file.delete();
    }

    public static String getSuffix(String fileName) {
        if(!fileName.contains(".")) {
            return "";
        }
        int dotIndex = fileName.indexOf(".");
        return fileName.substring(dotIndex, fileName.length());
    }
}
