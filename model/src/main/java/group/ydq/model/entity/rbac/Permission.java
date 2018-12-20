package group.ydq.model.entity.rbac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, path);
    }
}
