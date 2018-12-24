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

    // 创建用户并且如果用户没有指定角色，则给定默认角色
    void addUserWithDefaultRole(User user);

    Role getDefaultRole();

    User getUerById(Long id);

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
