package group.ydq.service.service.impl;

import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.CheckStageService;

import group.ydq.model.dao.cs.CheckStageRepository;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.service.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private ProjectService projectService;

    @Resource
    private UserRepository userRepository;

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
    public void startMid(Long projectId) {
        CheckStage cs = new CheckStage();
        Project p = projectService.getProject(projectId);
        User u = userRepository.getOne(10000L);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(1);
        cs.setStatus(0);
        cs.setVerifiers(u);
        save(cs);
    }

    @Override
    public void startFinal(Long projectId) {
        CheckStage cs = new CheckStage();
        Project p = projectService.getProject(projectId);
        User u = userRepository.getOne(10000L);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setUploadStatus(false);
        cs.setStage(2);
        cs.setStatus(0);
        cs.setVerifiers(u);
        save(cs);
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
