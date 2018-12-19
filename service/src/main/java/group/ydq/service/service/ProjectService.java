package group.ydq.service.service;

import group.ydq.model.entity.dm.ExpertProject;
import group.ydq.model.entity.dm.Project;
import org.springframework.data.domain.Page;


/**
 * @author Daylight
 * @date 2018/11/30 15:39
 */
public interface ProjectService extends BaseService{
    Project getProject(long projectId);

    boolean isProjectExist(long projectId);

    Project save(Project project);

    Page<Project> getProjectsOfLeader(int page, int limit, String userNumber);

    Page<Project> getProjectsOfManager(int page,int limit,String userNumber);

    Page<ExpertProject> getProjectOfExpert(int page,int limit,String userNumber);

    void changeState(long projectId,int state);

    void deleteProject(long id);
}
