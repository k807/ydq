package group.ydq.utils;

/**
 * @author: Natsukawamasuzu
 * @date: 2018/12/14 23:27
 */
public class StageCheckStatusToProjStatus {

    public static int changeToProjectStatus(int stageCode, int statusCode){
        int judgeCode = 3*stageCode - statusCode;
        int projectStatus = -1;
        switch (judgeCode){
            case 1: projectStatus = 7; break;
            case 2: projectStatus = 8; break;
            case 4: projectStatus = 9; break;
            case 5: projectStatus = 10; break;
            default: break;
        }
        return projectStatus;
    }

}
