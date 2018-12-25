package group.ydq.service.service.impl;

import group.ydq.model.entity.rbac.Permission;
import group.ydq.service.service.RBACService;
import group.ydq.service.service.TotalPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-12-25 23:05
 * =============================================
 */
@Service
public class TotalPermissionServiceImpl implements TotalPermissionService {
    @Autowired
    private RBACService rbacService;

    @Override
    public Permission getTotalAuthorityPermission() {
        return rbacService.getPermissionByPermissionName("authority");
    }
}
