package group.ydq.utils;

/**
 * @author Daylight
 * @date 2018/11/28 17:02
 */
public class DataTransUtil {
    public static String projectLevel(int level){
        switch (level){
            case 0:
                return "校级一类";
            case 1:
                return "校级二类";
            default:
                return "其他级别";
        }
    }

    public static String major(int major){
        switch (major){
            case 0:
                return "计算机科学与技术";
            case 1:
                return "电子信息工程";
            case 2:
                return "电子信息科学与技术";
            default:
                return "其他专业";
        }
    }

    public static String managerState(int state){
        switch (state){
            case -1:
                return "未提交";
            case 0:
                return "审核中";
            case 1:
                return "初审通过";
            case 2:
                return "立项评审中";
            case 3:
                return "立项评审完成";
            case 4:
                return "已立项";
            default:
                return "未知状态";
        }
    }

    public static String expertState(int state){
        switch (state){
            case 0:
                return "待审核";
            case 1:
                return "已审核";
            default:
                return "未知状态";
        }
    }
}
