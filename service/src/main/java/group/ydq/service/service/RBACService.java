package group.ydq.service.service;

import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;

public interface RBACService extends BaseService {
    Permission[] getPermissionByRoleName(String roleName);

    User getRoleByUserNumber(String userNumber);

    void addRole(Role role);

    void addPermission(Permission permission);

    void addUser(User user);

    void updateRole(Role role);

    void updatePermission(Permission permission);

    void updateUser(User user);

    void deleteUser(User user);

    void deleteRole(Role role);

    void deletePermission(Permission permission);
}
