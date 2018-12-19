package group.ydq.service.service.impl;

import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.CheckStageService;

import group.ydq.model.dao.cs.CheckStageRepository;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.service.service.ProjectService;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author:Leo
 * date:2018/11/29
 */
@Service("checkStageService")
@Transactional
public class CheckStageServiceImpl implements CheckStageService {

    @PersistenceContext
    private EntityManager entityManager;

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
    public void startStage(Long projectId,int stage) {
        CheckStage cs = new CheckStage();
        Project p = projectService.getProject(projectId);
        User u = userRepository.getOne(10000L);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setStage(stage);
        cs.setStatus(0);
        cs.setVerifiers(u);
        save(cs);
    }


    @Override
    @SuppressWarnings("unchecked")
    /*
    * 不要动下面这段代码！
    * */
    public List<CheckStage> findByConditions(String projectName, String leaderName, int projectStage, String stageStatus, String createTimeStart, String createTimeEnd) {
        String sqlSession = "";
        if(!"".equals(stageStatus)){
            int status = Integer.parseInt(stageStatus);
            sqlSession = "check_stage.`status` = " + status +" and ";
        }
        List<CheckStage> dataList = entityManager.createNativeQuery("select check_stage.* " +
                "from check_stage left join project on check_stage.project_id = project.id " +
                "right join `user` on project.leader_id = `user`.id " +
                "where project.`name` like '%" + projectName + "%' and " +
                "`user`.nick like '%" + leaderName + "%' and " +
                "check_stage.stage = " + projectStage + " and " + sqlSession +
                "project.create_time between '" + createTimeStart + "' and '" + createTimeEnd + "'",CheckStage.class).getResultList();
        return dataList;
    }

    @Override
    public void changeProjectStatus(Long stageCheckID, String adviceMessage, int changeToThisStatus, User verifierId) {
        stageDao.changeProjectStatus(stageCheckID,adviceMessage,changeToThisStatus,verifierId);
    }

    @Override
    public List<CheckStage> getCheckStageByStageStatus(int stageStatus) {
        return stageDao.getCheckStagesByStageStatus(stageStatus);
    }
}
