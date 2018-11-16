package group.ydq.model.dao.pm;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);
}
