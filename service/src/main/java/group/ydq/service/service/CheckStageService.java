package group.ydq.service.service;

import group.ydq.model.entity.cs.CheckStage;

import java.util.List;

/**
 * @Author: Natsukawamasuzu
 * @Date: 2018/11/12 18:12
 */
public interface CheckStageService  extends  BaseService{
    void save(CheckStage s);
    List<CheckStage> findAll();
}
