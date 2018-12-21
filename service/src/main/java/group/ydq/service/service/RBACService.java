package group.ydq.service.service;

import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;

import java.util.List;

public interface RBACService extends BaseService {
    Permission[] getPermissionByRoleName(String roleName);

    Role getRoleByUserNumber(String userNumber);

    Role getRoleByRoleName(String roleName);

    void addRole(Role role);

    void addPermission(Permission permission);

    void addUser(User user);

    void updateRole(Role role);

    void updatePermission(Permission permission);

    void updateUser(User user);

    void updateUserExcludeRole(User user);

    void deleteUser(User user);

    void deleteRole(Role role);

    void deletePermission(Permission permission);

    User getUserByUserNumber(String userNumber);

    Role getRoleById(Long id);

    Permission getPermssionById(Long id);

    Permission getPermissionByPermissionName(String name);

    List<User> getUsersByRole(Role role);

    List<Role> getAllRoles();

    List<Permission> getAllPermissions();
}
