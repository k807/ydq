package group.ydq.service.pm;

import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simple
 * @date on 2018/11/12 18:00
 */
@Service
public class MessageServiceImpl implements MessageService {


    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> messageList() {
        return messageRepository.findAll();
    }

    @Override
    public Message messageOne(Long id) {
        return null;
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
    public List<Message> findBySender(User sender, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        return messageRepository.findBySender(sender, pageable);
    }

    @Override
    public List<Message> findByReceiver(User receiver, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        return messageRepository.findByReceiver(receiver, pageable);
    }
}
