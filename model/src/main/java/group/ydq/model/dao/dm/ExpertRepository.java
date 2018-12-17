package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.ExpertProject;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Daylight
 * @date 2018/11/28 18:45
 */
public interface ExpertRepository extends JpaRepository<ExpertProject,Long>{
    Page<ExpertProject> findExpertProjectsByExpert(Pageable pageable,User expert);
}
