package group.ydq.service.service;

import group.ydq.model.dao.dm.ExpertRepository;
import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.dm.ExpertProject;
import group.ydq.model.entity.dm.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/12 16:32
 */
@Service("declareService")
public class DeclareServiceImpl extends BaseServiceImpl implements DeclareService {
    @Resource
    private UserRepository userDao;
    @Resource
    private ProjectRepository projectDao;
    @Resource
    private ExpertRepository expertDao;

    @Override
    public void distributeExpert(long projectId, List<Long> expertIds) {
        Project project=projectDao.getOne(projectId);
        project.setExperts(userDao.findUsersByIdIn(expertIds));
        projectDao.save(project);
        for (Long id:expertIds) {
            ExpertProject expertProject = new ExpertProject();
            expertProject.setExpert(userDao.getOne(id));
            expertProject.setProject(project);
            expertDao.save(expertProject);
        }
    }
}
