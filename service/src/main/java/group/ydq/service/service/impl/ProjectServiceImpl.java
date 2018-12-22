package group.ydq.service.service.impl;

import group.ydq.model.dao.dm.ExpertRepository;
import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.ExpertReview;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.ProjectService;
import group.ydq.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/30 15:40
 */
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {
    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ExpertRepository expertRepository;

    @Resource
    private UserRepository userRepository;

    /**
     * 项目保存操作
     * @param project
     */
    @Override
    public Project save(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    /**
     * 查询项目详情
     * @param projectId
     * @return
     */
    @Override
    public Project getProject(long projectId) {
        return projectRepository.getOne(projectId);
    }

    @Override
    public boolean isProjectExist(long projectId) {
        return projectRepository.existsProjectById(projectId);
    }

    @Override
    public Page<Project> getProjectsOfLeader(int page, int limit, String userNumber) {
        Pageable pageable= PageRequest.of(page-1,limit);
        return projectRepository.findProjectsByLeader(pageable,userRepository.findByUserNumber(userNumber));
    }

    @Override
    public Page<Project> getProjectsOfManager(int page, int limit,String userNumber) {
        Pageable pageable= PageRequest.of(page-1,limit);
        return projectRepository.findProjectsByManagerAndSubmitTrue(pageable,userRepository.findByUserNumber(userNumber));
    }

    @Override
    public Page<Project> getProjectsOfManagerInDeclareStage(int page, int limit, String userNumber) {
        Pageable pageable= PageRequest.of(page-1,limit);
        return projectRepository.findProjectsByManagerAndSubmitTrueAndStateBetween(pageable,userRepository.findByUserNumber(userNumber),0,6);
    }

    @Override
    public Page<ExpertReview> getProjectOfExpert(int page, int limit, String userNumber) {
        Pageable pageable=PageRequest.of(page-1,limit);
        return expertRepository.findExpertProjectsByExpert(pageable,userRepository.findByUserNumber(userNumber));
    }

    @Override
    public void changeState(long projectId, int state) {
        projectRepository.changeState(projectId, state,new Date());
    }

    @Override
    public void deleteProject(long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ExpertReview> getExpertReviews(long projectId) {
        return expertRepository.findExpertReviewsByProject(projectRepository.getOne(projectId));
    }

    @Override
    public ExpertReview getExpertReview(long id) {
        return expertRepository.getOne(id);
    }

    @Override
    public boolean isAllExpertsMarked(long projectId) {
        return expertRepository.existsExpertReviewByProjectAndMarkTrue(projectRepository.getOne(projectId));
    }

    @Override
    public Page<Project> searchProject(int role, int page, int limit, User user,String name, String level, String state, String major, String start, String end) {
        Pageable pageable=PageRequest.of(page-1,limit);
        switch (role){
            case 1:
                return projectRepository.queryProjectsByLeaderAndNameContainingAndLevelInAndStateInAndMajorInAndCreateTimeBetween(pageable,user,name, strToCollection(level), strToCollection(state), strToCollection(major), DateUtil.strToDate(start,DateUtil.format1),DateUtil.strToDate(end,DateUtil.format1));
            case 2:
                return projectRepository.queryProjectsByManagerAndSubmitTrueAndNameContainingAndLevelInAndStateInAndMajorInAndCreateTimeBetween(pageable,user,name, strToCollection(level), strToCollection(state), strToCollection(major), DateUtil.strToDate(start,DateUtil.format1),DateUtil.strToDate(end,DateUtil.format1));
        }
        return null;
    }

    @Override
    public Page<ExpertReview> searchExpertReview(int page, int limit, User user,String name, String level, String state, String major, String start, String end) {
        Pageable pageable=PageRequest.of(page-1,limit);
        if (state.equals(""))
            return expertRepository.queryExpertReviewsWithoutMarkCondition(user,name, strToCollection(level),  strToCollection(major),DateUtil.strToDate(start,DateUtil.format1),DateUtil.strToDate(end,DateUtil.format1),pageable);
        else
            return expertRepository.queryExpertReviewsWithMarkCondition(user,name, strToCollection(level), Boolean.parseBoolean(state), strToCollection(major),DateUtil.strToDate(start,DateUtil.format1),DateUtil.strToDate(end,DateUtil.format1),pageable);
    }

    @Override
    public void saveReview(ExpertReview review) {
        expertRepository.save(review);
    }

    static Collection strToCollection(String str){
        List<Integer> integers=new ArrayList<>();
        int integer=-2;
        if (str!=null&&!str.equals(""))
            integer=Integer.parseInt(str);
        if (integer==-2){
            for (int i=-1;i<11;i++){
                integers.add(i);
            }
        }else
            integers.add(integer);
        return integers;
    }
}
