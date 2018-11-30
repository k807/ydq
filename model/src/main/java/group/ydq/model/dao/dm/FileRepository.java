package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daylight
 * @date 2018/11/30 19:54
 */
public interface FileRepository extends JpaRepository<ProjectFile,Long> {
    boolean existsByUniqueId(String uniqueId);

    ProjectFile getByUniqueId(String uniqueId);

    @Modifying
    @Transactional
    void deleteByUniqueId(String uniqueId);
}
