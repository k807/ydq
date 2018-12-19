package group.ydq.model.dao.cs;

import group.ydq.model.entity.cs.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author:Leo
 * date:2018/11/15
 */
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
