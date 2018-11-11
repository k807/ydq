package group.ydq.model.entity.rbac;

import javax.persistence.*;
import java.util.List;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 21:41
 * =============================================
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String path;

    @ManyToMany
    private List<Role> roleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
