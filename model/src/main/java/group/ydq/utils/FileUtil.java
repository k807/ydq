package group.ydq.utils;

import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Daylight
 * @date 2018/12/5 10:56
 */
public class FileUtil {
    public static void upload(byte[] file, String uniqueId, String filename) throws IOException{
        String path = Objects.requireNonNull(ClassUtils.getDefaultClassLoader().getResource("")).getPath()+"static/upload/"+uniqueId;
        File targetfile = new File(path);
        if(targetfile.mkdirs()) {
            FileOutputStream out = new FileOutputStream(path+"/"+filename);
            out.write(file);
            out.flush();
            out.close();
        }
    }

    public static boolean delete(String uniqueId){
        String path = Objects.requireNonNull(ClassUtils.getDefaultClassLoader().getResource("")).getPath()+"static/upload/"+uniqueId;
        File targetfile = new File(path);
        return targetfile.delete();
    }
}
