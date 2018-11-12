package group.ydq.service.service;

import group.ydq.model.entity.rbac.User;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:29
 * =============================================
 */
public class RBACServiceImpl extends BaseServiceImpl implements RBACService {
    @Override
    public boolean checkPassword(User user) {
        return true;
    }

}
