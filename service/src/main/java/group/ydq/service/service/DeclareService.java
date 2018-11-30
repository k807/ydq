package group.ydq.service.service;

import group.ydq.model.entity.dm.Project;

import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/12 16:25
 */
public interface DeclareService extends BaseService{
    void distributeExpert(long projectId, List<Long> expertIds);


}
