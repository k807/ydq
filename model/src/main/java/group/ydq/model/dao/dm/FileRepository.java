package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Daylight
 * @date 2018/11/30 19:54
 */
public interface FileRepository extends JpaRepository<ProjectFile,Long> {
    ProjectFile getProjectFileByUuid(String uuid);

}
