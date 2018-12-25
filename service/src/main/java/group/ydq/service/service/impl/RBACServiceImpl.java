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
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:29
 * =============================================
 */
@Service
public class RBACServiceImpl extends BaseServiceImpl implements RBACService {
    private static final String DEFAULT_ROLE = "supermanager";

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
    public Role getRoleByUserNumber(String userNumber) {
        User user = userRepository.findByUserNumber(userNumber);
        return user.getRole();
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void addRole(Role role) {
        role.setUpdateTime(new Date());
        role.setCreateTime(new Date());
        roleRepository.save(role);
    }

    @Override
    public void addPermission(Permission permission) {
        permission.setCreateTime(new Date());
        permission.setUpdateTime(new Date());
        permissionRepository.save(permission);
    }

    @Override
    public void addUser(User user) {
        user.setCreateTime(new Date());
        userRepository.save(user);
    }

    @Override
    public void addUserWithDefaultRole(User user) {
        Role defaultRole = getDefaultRole();
        user.setRole(defaultRole);
        user.setCreateTime(new Date());
        addUser(user);
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE);
    }

    @Override
    public User getUerById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void updateRole(Role role) {
        Long id = role.getId();
        Role origin = roleRepository.getOne(id);
        if (!CollectionUtils.isEmpty(role.getPermissionList())) {
            origin.setPermissionList(role.getPermissionList());
        }
        if (role.getName() != null) {
            origin.setName(role.getName());
        }
        role.setUpdateTime(new Date());
        roleRepository.save(origin);
    }

    @Override
    public void updatePermission(Permission permission) {
        permission.setUpdateTime(new Date());
        permissionRepository.save(permission);
    }

    @Override
    public void updateUser(User user) {
        Long id = user.getId();
        User origin = userRepository.getOne(id);
        if (user.getRole() != null) {
            origin.setRole(user.getRole());
        }
        if (user.getPassword() != null) {
            origin.setPassword(user.getPassword());
        }
        if (user.getNick() != null) {
            origin.setNick(user.getNick());
        }
        if (user.getUserNumber() != null) {
            origin.setUserNumber(user.getUserNumber());
        }
        userRepository.save(origin);
    }

    @Override
    public void updateUserExcludeRole(User user) {
        Long id = user.getId();
        User origin = userRepository.getOne(id);
        if (user.getPassword() != null) {
            origin.setPassword(user.getPassword());
        }
        if (user.getNick() != null) {
            origin.setNick(user.getNick());
        }
        if (user.getUserNumber() != null) {
            origin.setUserNumber(user.getUserNumber());
        }
        userRepository.save(origin);
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

    @Override
    public User getUserByUserNumber(String userNumber) {
        return userRepository.findByUserNumber(userNumber);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Permission getPermssionById(Long id) {
        return permissionRepository.getOne(id);
    }

    @Override
    public Permission getPermissionByPermissionName(String name) {
        return permissionRepository.findByName(name);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findUsersByRoleId(role.getId());
    }

    @Override
    public List<Role> getAllRolesWithPermission() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getAllRolesWithoutPermission() {
        List<Role> allRole = roleRepository.findAll();
        for (Role role : allRole) {
            role.setPermissionList(null);
        }
        return allRole;
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

}
