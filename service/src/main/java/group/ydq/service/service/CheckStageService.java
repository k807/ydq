package group.ydq.service.service;

import group.ydq.model.entity.cs.CheckStage;
import group.ydq.model.entity.rbac.User;

import java.util.Date;
import java.util.List;

/**
 * @Author: Natsukawamasuzu
 * @Date: 2018/11/12 18:12
 */
public interface CheckStageService  extends  BaseService{
    void save(CheckStage s);
    List<CheckStage> findAll();
    CheckStage findACheckStageByCheckStageID(Long stageCheckID);
    void changeVerifyMessage(Long stageCheckID, String adviceMessage, int changeToThisStatus, User verifierId, Date endTime);
    void startStage(Long projectId,int stage);
    List<CheckStage> findByConditions(String projectName, String leaderName, int projectStage, String stageStatus, String createTimeStart, String createTimeEnd);
    List<CheckStage> getCheckStageByStageStatus(int stageStatus);
}
