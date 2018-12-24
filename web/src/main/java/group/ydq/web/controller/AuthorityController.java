package group.ydq.web.controller;

import group.ydq.authority.Subject;
import group.ydq.authority.SubjectUtils;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.RBACService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-30 21:47
 * =============================================
 */
@RestController
@RequestMapping("/authority/*")
public class AuthorityController {
    @Autowired
    private RBACService rbacService;

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public BaseResponse addUser(User user) {
        user.setCreateTime(new Date());
        rbacService.addUserWithDefaultRole(user);
        return new BaseResponse();
    }

    @RequestMapping(path = "/getSelfInfo")
    public BaseResponse getSelfInfo() {
        BaseResponse response = new BaseResponse();
        Subject subject = SubjectUtils.getSubject();
        User user = rbacService.getUserByUserNumber(subject.getPrincipal());
        response.setObject(user);
        return response;
    }

    /**
     * 根据角色获取用户列表
     *
     * @param role
     * @return
     */
    @RequestMapping(path = "/getUserByRole/{role}")
    public BaseResponse getUser(@PathVariable String role) {
        BaseResponse response = new BaseResponse();
        Role r = rbacService.getRoleByRoleName(role);
        List<User> userList = rbacService.getUsersByRole(r);
        response.setObject(userList);
        return response;
    }

    /**
     * 根据角色获取权限列表
     *
     * @param role
     * @return
     */
    @RequestMapping(path = "/getPermissionByRole/{role}")
    public BaseResponse getPermissionByRole(@PathVariable String role) {
        BaseResponse response = new BaseResponse();
        Role r = rbacService.getRoleByRoleName(role);
        response.setObject(r.getPermissionList());
        return response;
    }

    @RequestMapping(path = "/getDefaultRole")
    public BaseResponse getDefaultRole() {
        BaseResponse response = new BaseResponse();
        response.setObject(rbacService.getDefaultRole());
        return response;
    }

    @RequestMapping(path = "/getAllRoles")
    public BaseResponse getAllRoles() {
        BaseResponse response = new BaseResponse();
        List<Role> roles = rbacService.getAllRoles();
        response.setObject(roles);
        return response;
    }

    @RequestMapping(path = "/getAllPermissions")
    public BaseResponse getAllPermissions() {
        BaseResponse response = new BaseResponse();
        List<Permission> permissions = rbacService.getAllPermissions();
        response.setObject(permissions);
        return response;
    }

    @RequestMapping(path = "/getPermissionByUser/{userNumber}")
    public BaseResponse getPermissionsByUser(@PathVariable("userNumber") String userNumber) {
        BaseResponse baseResponse = new BaseResponse();
        Role role = rbacService.getRoleByUserNumber(userNumber);
        baseResponse.setObject(role.getPermissionList());
        return baseResponse;
    }

    /**
     * 更改角色权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping(path = "/updateRolePermission/{operate}/{oldRoleName}/{permissionName}")
    public BaseResponse updateRolePermission(@RequestParam("roleId") Long roleId,
                                             @PathVariable("operate") String operate,
                                             @PathVariable("oldRoleName") String oldRoleName,
                                             @PathVariable("permissionName") String permissionName) {
        BaseResponse response = new BaseResponse();
        Role oldRole = rbacService.getRoleById(roleId);
        if (oldRole.getName() != null && !oldRole.getName().equals(oldRoleName)) {
            response.setStatusCode("400");
            response.setMsg("please check you path!");
            return response;
        }
        Permission permission = rbacService.getPermissionByPermissionName(permissionName);
        oldRole.setUpdateTime(new Date());
        switch (operate) {
            case "add":
                oldRole.getPermissionList().add(permission);
                rbacService.updateRole(oldRole);
                break;
            case "delete":
                oldRole.getPermissionList().remove(permission);
                rbacService.updateRole(oldRole);
            default:
                break;
        }

        return response;
    }

    /**
     * 更改用户角色
     *
     * @param userNumber
     * @param oldRoleName
     * @param newRoleName
     * @return
     */
    @RequestMapping(path = "/updateUserRole/{oldRoleName}/{newRoleName}")
    public BaseResponse updateUserRole(@RequestParam("userNumber") String userNumber,
                                       @PathVariable("oldRoleName") String oldRoleName,
                                       @PathVariable("newRoleName") String newRoleName) {
        BaseResponse response = new BaseResponse();
        //  判断操作的object的role是否等于oldRoleName
        Role objectRole = rbacService.getRoleByUserNumber(userNumber);
        if (objectRole != null && !objectRole.getName().equals(oldRoleName)) {
            response.setStatusCode("400");
            response.setMsg("please check you path!");
            return response;
        }
        Role newRole = rbacService.getRoleByRoleName(newRoleName);
        User user = rbacService.getUserByUserNumber(userNumber);
        user.setRole(newRole);
        rbacService.updateUser(user);
        return response;
    }

    /**
     * 对用户进行操作，需要指明用户的角色
     * 如果用户没有角色，则role可以任意指定
     * 不能通过这个接口修改用户角色
     *
     * @param object
     * @param operate
     * @param role
     * @return
     */
    @RequestMapping(path = "/{role}/user/{operate}", method = RequestMethod.POST)
    public BaseResponse updateUser(@RequestBody User object, @PathVariable("operate") String operate, @PathVariable("role") String role) {
        BaseResponse response = new BaseResponse();
        Role objectRole = null;
        if (!"add".equals(operate) && !"update".equals(operate)) {
            objectRole = rbacService.getRoleByUserNumber(object.getUserNumber());
        }
        if("update".equals(operate)){
            objectRole = rbacService.getUerById(object.getId()).getRole();
        }
        if (!"add".equals(operate) && objectRole != null && !objectRole.getName().equals(role)) {
            response.setStatusCode("400");
            response.setMsg("please check you path!");
            return response;
        }
        switch (operate) {
            case "add":
                rbacService.addUserWithDefaultRole(object);
                break;
            case "update":
                // 不能修改role
                rbacService.updateUserExcludeRole(object);
                break;
            case "delete":
                rbacService.deleteUser(object);
                break;
            default:
                break;
        }
        return response;
    }

    /**
     * 对角色进行操作
     *
     * @param object
     * @param operate
     * @param role
     * @return
     */
    @RequestMapping(path = "/role/{operate}/{role}", method = RequestMethod.POST)
    public BaseResponse updateRole(@RequestBody Role object, @PathVariable("operate") String operate, @PathVariable("role") String role) {
        BaseResponse response = new BaseResponse();
        Role oldObject = null;
        if (!Objects.isNull(object.getId())) {
            oldObject = rbacService.getRoleById(object.getId());
        }
        if (!"add".equals(operate) &&
                (role == null || !role.equals(oldObject.getName()))) {
            response.setStatusCode("400");
            response.setMsg("please check you path!");
            return response;
        }
        switch (operate) {
            case "add":
                rbacService.addRole(object);
                break;
            case "update":
                rbacService.updateRole(object);
                break;
            case "delete":
                rbacService.deleteRole(object);
                break;
            default:
                break;
        }
        return response;
    }

    /**
     * 对权限进行操作
     *
     * @param object
     * @param operate
     * @param permission
     * @return
     */
    @RequestMapping(path = "/permission/{operate}/{permission}", method = RequestMethod.POST)
    public BaseResponse updatePermission(@RequestBody Permission object, @PathVariable("operate") String operate, @PathVariable("permission") String permission) {
        BaseResponse response = new BaseResponse();
        Permission oldObject = null;
        if (!Objects.isNull(object.getId())) {
            oldObject = rbacService.getPermssionById(object.getId());
        }
        if (!"add".equals(operate) &&
                (permission == null || !permission.equals(oldObject.getName()))) {
            response.setStatusCode("400");
            response.setMsg("please check you path!");
            return response;
        }
        switch (operate) {
            case "add":
                rbacService.addPermission(object);
                break;
            case "update":
                rbacService.updatePermission(object);
                break;
            case "delete":
                rbacService.deletePermission(object);
                break;
            default:
                break;
        }
        return new BaseResponse();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody User user, HttpServletRequest request) {
        Subject subject = new Subject();
        subject.setPrincipal(user.getUserNumber());
        subject.setAccess(user.getPassword());
        BaseResponse baseResponse = new BaseResponse();
        if (subject.login()) {
            HttpSession session = request.getSession();//获取session并将userName存入session对象
            session.setAttribute("user", rbacService.getUserByUserNumber(user.getUserNumber()));
            return baseResponse;
        } else {
            baseResponse.setStatusCode("400");
            baseResponse.setMsg("login fail");
            return baseResponse;
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public BaseResponse logout() {
        Subject subject = SubjectUtils.getSubject();
        BaseResponse baseResponse = new BaseResponse();
        if (subject.logout()) {
            return baseResponse;
        } else {
            baseResponse.setStatusCode("400");
            baseResponse.setMsg("logout fail");
            return baseResponse;
        }
    }
}
