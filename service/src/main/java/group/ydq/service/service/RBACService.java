package group.ydq.service.service;

import group.ydq.model.entity.rbac.User;

public interface RBACService extends BaseService {
    boolean checkPassword(User user);
}
