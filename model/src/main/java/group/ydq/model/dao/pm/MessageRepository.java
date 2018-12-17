package group.ydq.model.dao.pm;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Simple
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m from Message m ORDER BY m.date ASC")
    List<Message> findAllOrderByDate();

    @Query(value = "select m from Message m where m.sender = ?1 ORDER BY m.date ASC")
    List<Message> findBySender(User sender);

    @Query(value = "select m from Message m where m.receiver = ?1 ORDER BY m.date ASC")
    List<Message> findByReceiver(User receiver);

}
