package group.ydq.service.authority;

import group.ydq.authority.AuthorityChecker;
import group.ydq.authority.Subject;
import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.RBACService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-26 19:57
 * =============================================
 */
public class AuthorityCheckerImpl implements AuthorityChecker {
    @Autowired
    private RBACService rbacService;

    @Override
    public String[] getPermissionByRole(String roleName) {
        Permission[] permissions = rbacService.getPermissionByRoleName(roleName);
        String[] strings = new String[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            strings[i] = permissions[i].getPath();
        }
        return strings;
    }

    @Override
    public String getRoleByUser(String userNumber) {
        Role role = rbacService.getRoleByUserNumber(userNumber);
        return role.getName();
    }

    @Override
    public boolean checkPrincipal(Subject subject) {
        User user = rbacService.getUserByUserNumber(subject.getPrincipal());
        return user.getPassword().equals(subject.getAccess());
    }
}
