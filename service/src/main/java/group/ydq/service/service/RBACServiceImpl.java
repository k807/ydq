package group.ydq.service.service;

import group.ydq.model.dao.rbac.RoleRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;
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
}
