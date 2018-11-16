package group.ydq.service.service;

import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.entity.dm.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Daylight
 * @date 2018/11/12 16:32
 */
@Service("declareService")
public class DeclareServiceImpl extends BaseServiceImpl implements DeclareService {
    @Resource
    private ProjectRepository repository;
    /**
     * 项目保存操作
     * @param project
     */
    @Override
    public void save(Project project) {
        repository.save(project);
    }

    /**
     * 项目提交操作
     * @param project
     */
    @Override
    public void submit(Project project) {

    }

    /**
     * 查询项目详情
     * @param projectId
     * @return
     */
    @Override
    public Project getDeclaration(long projectId) {
        return repository.getOne(projectId);
    }
}
