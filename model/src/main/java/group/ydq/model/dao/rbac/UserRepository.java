package group.ydq.model.dao.rbac;

import group.ydq.model.entity.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNumber(String usernumber);
}
