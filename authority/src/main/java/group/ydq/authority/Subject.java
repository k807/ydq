package group.ydq.authority;

import group.ydq.authority.exception.ModifyIllegalException;

import java.util.Objects;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-26 22:06
 * =============================================
 */
public class Subject {
    // 唯一身份标识
    private String principal;
    // 通行证
    private String access;
    private String role;
    private String[] permissions;

    private boolean isLogin = false;

    public String getPrincipal() {
        return principal;
    }

    public String getAccess() {
        return access;
    }

    public void setPrincipal(String principal) {
        checkModifiable();
        this.principal = principal;
    }

    public void setAccess(String access) {
        checkModifiable();
        this.access = access;
    }

    public boolean login() {
        if (SubjectUtils.login(this)) {
            isLogin = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() {
        if (SubjectUtils.logout(this)) {
            isLogin = false;
            return true;
        } else {
            return false;
        }
    }

    // 在当前subject上绑定数据
    public void bind(Object object) {
        SubjectUtils.bind(this, object);
    }

    private void checkModifiable() {
        if (isLogin) {
            throw new ModifyIllegalException();
        }
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(this.getPrincipal())) {
            return super.hashCode();
        }
        return this.getPrincipal().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subject) {
            Subject other = (Subject) obj;
            if (this == obj) {
                return true;
            }
            if (Objects.isNull(this.getPrincipal())) {
                return super.equals(obj);
            }
            if (Objects.isNull(other.getPrincipal())) {
                return false;
            }
            return this.getPrincipal().equals(other.getPrincipal());
        }
        return false;
    }
}
