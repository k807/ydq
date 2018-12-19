package group.ydq.service.service.impl;

import group.ydq.model.dao.dm.ExpertRepository;
import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.ExpertProject;
import group.ydq.model.entity.dm.Project;
import group.ydq.service.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return projectRepository.findProjectsByManagerAndSubmit(pageable,userRepository.findByUserNumber(userNumber),true);
    }

    @Override
    public Page<ExpertProject> getProjectOfExpert(int page, int limit, String userNumber) {
        Pageable pageable=PageRequest.of(page-1,limit);
        return expertRepository.findExpertProjectsByExpert(pageable,userRepository.findByUserNumber(userNumber));
    }

    @Override
    public void changeState(long projectId, int state) {
        projectRepository.changeState(projectId, state);
    }

    @Override
    public void deleteProject(long id) {
        projectRepository.deleteById(id);
    }
}
