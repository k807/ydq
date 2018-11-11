package group.ydq.model.dao.pm;

import group.ydq.model.entity.pm.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
