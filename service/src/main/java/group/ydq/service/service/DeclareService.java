package group.ydq.service.service;

import group.ydq.model.entity.dm.DeclareRule;
import group.ydq.model.entity.dm.Project;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/12 16:25
 */
public interface DeclareService extends BaseService{
    void distributeExpert(long projectId, List<Long> expertIds);

    List<DeclareRule> getValuableRules();

    Page<DeclareRule> getAllRules(int page,int limit);

    DeclareRule getRule(long id);

    void addRule(DeclareRule rule);

    int getProjectNumOfRule(long ruleId);

    void delRule(long id);
}
