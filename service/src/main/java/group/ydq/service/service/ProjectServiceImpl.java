package group.ydq.service.service;

import group.ydq.model.dao.dm.ProjectRepository;
import group.ydq.model.entity.dm.Project;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Daylight
 * @date 2018/11/12 16:32
 */
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository repository;
    /**
     * 项目保存操作
     * @param project
     */
    @Override
    public void save(Project project) {

    }

    /**
     * 项目提交操作
     * @param project
     */
    @Override
    public void submit(Project project) {

    }
}
