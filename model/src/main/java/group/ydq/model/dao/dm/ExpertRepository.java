package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.ExpertReview;
import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/28 18:45
 */
public interface ExpertRepository extends JpaRepository<ExpertReview,Long>{
    Page<ExpertReview> findExpertProjectsByExpert(Pageable pageable, User expert);

    List<ExpertReview> findExpertReviewsByProject(Project project);

    boolean existsExpertReviewByProjectAndMarkTrue(Project project);
}
