package group.ydq.service.service.impl;

import group.ydq.service.service.CheckStageService;

import group.ydq.model.dao.cs.CheckStageRepository;
import group.ydq.model.entity.cs.CheckStage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:Leo
 * date:2018/11/29
 */
@Service("checkStageService")
@Transactional
public class CheckStageServiceImpl implements CheckStageService {
    @Resource
    private CheckStageRepository stageDao;

    @Override
    public void save(CheckStage s) {
        stageDao.save(s);
    }

    @Override
    public List<CheckStage> findAll() {
        return stageDao.findAll();
    }

    @Override
    public CheckStage findACheckStageByCheckStageID(Long stageCheckID) {
        return stageDao.getCheckStagesById(stageCheckID);
    }

    @Override
    public void changeProjectStatus(Long stageCheckID, String adviceMessage, int changeToThisStatus) {
        stageDao.changeProjectStatus(stageCheckID,adviceMessage,changeToThisStatus);
    }

    @Override
    public List<CheckStage> getCheckStageByStageStatus(int stageStatus) {
        return stageDao.getCheckStagesByStageStatus(stageStatus);
    }
}
