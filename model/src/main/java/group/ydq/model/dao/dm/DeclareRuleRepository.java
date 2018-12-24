package group.ydq.model.dao.dm;

import group.ydq.model.entity.dm.DeclareRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/28 16:02
 */
public interface DeclareRuleRepository extends JpaRepository<DeclareRule,Long> {
    List<DeclareRule> findDeclareRulesByEndTimeAfter(Date date);

    Page<DeclareRule> queryDeclareRulesByTitleContainingAndMajorInAndStartTimeAfterAndEndTimeBefore(Pageable pageable,String title, Collection major, Date start, Date end);
}
