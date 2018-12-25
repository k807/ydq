package group.ydq.model.dao.rbac;

import group.ydq.model.entity.rbac.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 21:17
 * =============================================
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
