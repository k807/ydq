package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/12 16:22
 */
public interface ProjectRepository extends JpaRepository<Project,Long>{
    List<Project> findProjectsByLeader(User leader);

    List<Project> findProjectsByManagerAndSubmit(User manager,boolean submit);

    @Modifying
    @Transactional
    @Query("update Project as p set p.state=?2 where p.id=?1")
    void changeState(long id,int state);
}
