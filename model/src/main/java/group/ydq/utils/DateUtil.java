package group.ydq.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daylight
 * @date 2018/11/28 17:10
 */
public class DateUtil {
    public static String dateToStr(Date date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date strToDate(String str){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pst=new ParsePosition(0);
        return format.parse(str,pst);
    }
}
