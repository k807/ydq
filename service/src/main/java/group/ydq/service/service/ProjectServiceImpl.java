package group.ydq.service.service;

import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.Project;
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
    private UserRepository userRepository;

    /**
     * 项目保存操作
     * @param project
     */
    @Override
    public void save(Project project) {
        project.setSubmit(false);
        projectRepository.save(project);
    }

    /**
     * 项目提交操作
     * @param project
     */
    @Override
    public void submit(Project project) {
        project.setSubmit(true);
        projectRepository.save(project);
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
    public List<Project> getProjectsOfLeader(String userNumber) {
        return projectRepository.findProjectsByLeader(userRepository.findByUserNumber(userNumber));
    }

    @Override
    public List<Project> getProjectsOfManager(String userNumber) {
        return projectRepository.findProjectsByManagerAndSubmit(userRepository.findByUserNumber(userNumber),true);
    }
}
