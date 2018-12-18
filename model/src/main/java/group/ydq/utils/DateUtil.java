package group.ydq.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daylight
 * @date 2018/11/28 17:10
 */
public class DateUtil {
    public final static String format1="yyyy-MM-dd HH:mm:ss";
    public final static String format2="yyyy-MM-dd";
    public static final String format3="yyyy-MM-dd HH:mm";
    public static final String format4="yyyy年MM月dd日 HH:mm";
    public static final String format5="MM月dd日 HH:mm";


    public static String dateToStr(Date date,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date strToDate(String str,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        ParsePosition pst=new ParsePosition(0);
        return sdf.parse(str,pst);
    }
}
