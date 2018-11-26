package group.ydq.service.service;

import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.User;

public interface RBACService extends BaseService {
    Permission[] getPermissionByRoleName(String roleName);

    User getRoleByUserNumber(String userNumber);
}
