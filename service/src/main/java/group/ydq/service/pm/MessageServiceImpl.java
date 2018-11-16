package group.ydq.service.pm;

import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Simple
 * @date on 2018/11/12 18:00
 */
@Service
public class MessageServiceImpl implements MessageService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MessageRepository messageRepository ;

    @Override
    public List<Message> messageList() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> messageOne(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message sendMessage(Message newMessage) {
        messageRepository.save(newMessage);
        return newMessage;
    }

    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findBySender(User sender) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.where(builder.like(root.get("sender"), "%" + sender + "%"));
        return em.createQuery(query.select(root)).getResultList();
    }

    @Override
    public List<Message> findByReceiver(User receiver) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.where(builder.like(root.get("receiver"), "%" + receiver + "%"));
        return em.createQuery(query.select(root)).getResultList();
    }
}
