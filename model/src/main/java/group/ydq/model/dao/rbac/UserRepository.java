package group.ydq.model.dao.rbac;

import group.ydq.model.entity.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNumber(String usernumber);

    List<User> findUsersByIdIn(List<Long> ids);
}
