package group.ydq.model.dao.msc;

import group.ydq.model.entity.mcs.MidCheck;
import group.ydq.model.entity.mcs.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author:Leo
 * date:2018/11/15
 */
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
