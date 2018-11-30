package group.ydq.service.service;

import group.ydq.model.entity.dm.Project;

import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/30 15:39
 */
public interface ProjectService extends BaseService{
    Project getProject(long projectId);

    void submit(Project project);

    void save(Project project);

    List<Project> getProjectsOfLeader(String userNumber);

    List<Project> getProjectsOfManager(String userNumber);

}
