package group.ydq.model.entity.rbac;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 21:15
 * =============================================
 */
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private List<Permission> permissionList;

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

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(permissionList, role.permissionList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, permissionList);
    }
}
