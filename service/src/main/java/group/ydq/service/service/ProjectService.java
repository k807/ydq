package group.ydq.service.service;

import group.ydq.model.entity.dm.ExpertReview;
import group.ydq.model.entity.dm.Project;
import org.springframework.data.domain.Page;

import java.util.List;


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

    Page<Project> getProjectsOfManagerInDeclareStage(int page,int limit,String userNumber);

    Page<ExpertReview> getProjectOfExpert(int page, int limit, String userNumber);

    void changeState(long projectId,int state);

    void deleteProject(long id);

    List<ExpertReview> getExpertReviews(long projectId);

    ExpertReview getExpertReview(long id);

    boolean isAllExpertsMarked(long projectId);

    void saveReview(ExpertReview review);
}
