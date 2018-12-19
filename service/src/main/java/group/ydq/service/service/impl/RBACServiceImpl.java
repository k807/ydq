package group.ydq.service.service.impl;

import group.ydq.model.dao.rbac.PermissionRepository;
import group.ydq.model.dao.rbac.RoleRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.RBACService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:29
 * =============================================
 */
@Service
public class RBACServiceImpl extends BaseServiceImpl implements RBACService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    @Transactional
    public Permission[] getPermissionByRoleName(String roleName) {
        Role role = roleRepository.findByName(roleName);
        return role.getPermissionList().toArray(new Permission[0]);
    }

    @Override
    public User getRoleByUserNumber(String userNumber) {
        return userRepository.findByUserNumber(userNumber);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void deletePermission(Permission permission) {
        permissionRepository.delete(permission);
    }


}
