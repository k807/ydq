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

    //sql语句存在一些问题，暂时保留，不影响运行
    @Query(value = "select check_stage.* from check_stage right join project on check_stage.project_id = project.id right join `user` on check_stage.verifiers_id = `user`.id where project.`name` LIKE '%项目%' and `user`.nick LIKE '%%' and check_stage.stage = 1 and check_stage.status = 1 and project.create_time >= '2018-12-15 00:00:00' and project.create_time <= '2018-12-16 00:00:00';", nativeQuery = true)
    List<CheckStage> findByConditions();

    @Modifying
    @Query("update CheckStage set message = ?2, status = ?3 where id = ?1")
    void changeProjectStatus(Long stageCheckID, String adviceMessage, int changeToThisStatus);

    CheckStage getCheckStagesById(Long checkStageID);

    @Query(value = "select * from check_stage where stage = ?1",nativeQuery = true)
    List<CheckStage> getCheckStagesByStageStatus(int stageStatus);
}
