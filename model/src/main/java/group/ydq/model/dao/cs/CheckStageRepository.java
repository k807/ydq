package group.ydq.model.dao.cs;

import group.ydq.model.entity.cs.CheckStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * author:Leo
 * date:2018/11/29
 */
public interface CheckStageRepository extends JpaRepository<CheckStage,Long> {

    /*List<CheckStage> findByConditions()*/
    @Modifying
    @Query("update CheckStage set message = ?2, status = ?3 where id = ?1")
    void changeProjectStatus(Long stageCheckID, String adviceMessage, int changeToThisStatus);

    CheckStage getCheckStagesById(Long checkStageID);

    @Query(value = "select * from check_stage where stage = ?1",nativeQuery = true)
    List<CheckStage> getCheckStagesByStageStatus(int stageStatus);
}
