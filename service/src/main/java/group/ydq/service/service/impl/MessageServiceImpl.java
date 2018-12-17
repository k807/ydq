package group.ydq.service.service.impl;

import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.MessageService;
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
        return messageRepository.findAllOrderByDate();
    }



    @Override
    public Message sendMessage(Message newMessage) {
        messageRepository.save(newMessage);
        return newMessage;
    }

    @Override
    public List<Message> findBySender(User sender) {
        return messageRepository.findBySender(sender);
    }

    @Override
    public List<Message> findByReceiver(User receiver) {
        return messageRepository.findByReceiver(receiver);
    }
}
