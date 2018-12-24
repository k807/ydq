package group.ydq.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.CheckStageService;

import group.ydq.model.dao.cs.CheckStageRepository;
import group.ydq.model.entity.cs.CheckStage;
import group.ydq.service.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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
        return stageDao.getOne(stageCheckID);
    }

    @Override
    public void startStage(Long projectId,int stage) {
        CheckStage cs = new CheckStage();
        Project p = projectService.getProject(projectId);
        cs.setProject(p);
        cs.setStartTime(new Date());
        cs.setStage(stage);
        cs.setStatus(0);
        save(cs);
    }


    @Override
    @SuppressWarnings("unchecked")
    /*
    * 不要动下面这段代码！
    * */
    public Page<CheckStage> findByConditions(int page, int limit, String projectName, String leaderName, int projectStage, String stageStatus, String createTimeStart, String createTimeEnd) {
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
                "project.create_time between '" + createTimeStart + "' and '" + createTimeEnd + " ' " ,CheckStage.class).getResultList();

        return
                limit * page < dataList.size() ?
                new PageImpl<>(dataList.subList((page - 1)*limit, page*limit),Pageable.unpaged(), dataList.size()):
                new PageImpl<>(dataList.subList((page - 1)*limit,dataList.size()),Pageable.unpaged(),dataList.size());
    }

    @Override
    public void changeVerifyMessage(Long stageCheckID, String adviceMessage, int changeToThisStatus, User verifierId, Date endTime) {
        stageDao.changeVerifyMessage(stageCheckID,adviceMessage,changeToThisStatus,verifierId,endTime);
    }

    @Override
    public Page<CheckStage> findCheckStagesByStage(int page, int limit, int stageStatus) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return stageDao.findCheckStagesByStage(pageable, stageStatus);
    }

    @Override
    public Map<String, Object> decorateData(Page<CheckStage> checkStages){
        Map<String, Object> dataMap = new HashMap<>();
        List<JSONObject> decoratedDataList = new ArrayList<>();
        for (CheckStage checkStage : checkStages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", checkStage.getId());
            jsonObject.put("pid", checkStage.getProject().getId());
            jsonObject.put("name", checkStage.getProject().getName());
            jsonObject.put("leader", checkStage.getProject().getLeader().getNick());
            jsonObject.put("stage", checkStage.getStage());
            jsonObject.put("createTime", checkStage.getProject().getCreateTime());
            jsonObject.put("status", checkStage.getStatus());
            if(null == checkStage.getVerifiers()){
                jsonObject.put("verifier", "待审核");
            }else{
                jsonObject.put("verifier", checkStage.getVerifiers().getNick());
            }
            decoratedDataList.add(jsonObject);
        }
        dataMap.put("count",checkStages.getTotalElements());
        dataMap.put("data",decoratedDataList);
        return  dataMap;
    }
}
