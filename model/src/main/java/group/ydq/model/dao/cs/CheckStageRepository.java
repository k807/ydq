package group.ydq.model.dao.cs;

import group.ydq.model.entity.cs.*;
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

  /*  //sql语句存在一些问题，暂时保留，不影响运行
    @Query(nativeQuery = true,
            value = "select check_stage.* from check_stage cs inner join project p on check_stage.project_id = project.id right join `user` on check_stage.verifiers_id = `user`.id where project.`name` like '%%' and `user`.nick like '%%' and check_stage.stage = 1 and check_stage.`status` = 1 and project.create_time between '1970-01-01 00:00:00' and '2049-12-31 23:59:59'")
    List<CheckStage> findByConditions(String projectName, String leaderName, int projectStage, int stageStatus, Date createTimeStart, Date createTimeEnd);
*/

    @Modifying
    @Query("update CheckStage set message = ?2, status = ?3 where id = ?1")
    void changeProjectStatus(Long stageCheckID, String adviceMessage, int changeToThisStatus);

    CheckStage getCheckStagesById(Long checkStageID);

    @Query(value = "select * from check_stage where stage = ?1",nativeQuery = true)
    List<CheckStage> getCheckStagesByStageStatus(int stageStatus);
}
