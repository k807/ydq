package group.ydq.model.dao.cs;

import group.ydq.model.entity.cs.*;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * author:Leo
 * date:2018/11/29
 */
public interface CheckStageRepository extends JpaRepository<CheckStage,Long> {

    @Modifying
    @Query("update CheckStage set message = ?2, status = ?3, verifiers=?4, endTime = ?5 where id = ?1")
    void changeVerifyMessage(Long stageCheckID, String adviceMessage, int changeToThisStatus, User verifier, Date endTime);

    Page<CheckStage> findCheckStagesByStage(Pageable pageable, int stage);

}
