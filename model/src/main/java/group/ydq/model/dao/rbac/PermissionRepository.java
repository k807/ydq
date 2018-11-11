package group.ydq.model.dao.rbac;

import group.ydq.model.entity.rbac.Permission;
import group.ydq.model.entity.rbac.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
