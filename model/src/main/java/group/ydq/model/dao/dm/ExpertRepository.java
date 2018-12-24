package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.ExpertReview;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/28 18:45
 */
public interface ExpertRepository extends JpaRepository<ExpertReview,Long>{
    Page<ExpertReview> findExpertProjectsByExpert(Pageable pageable, User expert);

    List<ExpertReview> findExpertReviewsByProject(Project project);

    boolean existsExpertReviewByProjectAndMarkTrue(Project project);

    @Query("select expertReview from ExpertReview expertReview JOIN expertReview.project project where project.name like %?2 and project.level in ?3 and project.major in ?5 and project.createTime between ?6 and ?7 and expertReview.expert=?1 and expertReview.mark = ?4")
    Page<ExpertReview> queryExpertReviewsWithMarkCondition(User expert, String name, Collection level, boolean state, Collection major, Date start, Date end,Pageable pageable);

    @Query("select expertReview from ExpertReview expertReview JOIN expertReview.project project where project.name like %?2 and project.level in ?3 and project.major in ?4 and project.createTime between ?5 and ?6 and expertReview.expert=?1")
    Page<ExpertReview> queryExpertReviewsWithoutMarkCondition(User expert, String name, Collection level, Collection major, Date start, Date end,Pageable pageable);
}
