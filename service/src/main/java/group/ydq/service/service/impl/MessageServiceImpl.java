package group.ydq.service.service.impl;

import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.MessageService;
import group.ydq.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getPMList() {
        Map<String, Object> obj = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        List<Map<String, Object>> notices = new ArrayList<>();
        List<Message> messageList = messageRepository.findAllOrderByDate();
        for (Message message : messageList) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", message.getTitle());
            map.put("type", message.getType());
            map.put("content", message.getContent());
            map.put("remark", message.getRemark());
            map.put("date", DateUtil.dateToStr(message.getDate(), "yyyy-MM-dd HH:mm:ss"));
            map.put("sender", message.getSender());
            map.put("reciver", message.getReceiver());
            if (message.getType() == 0) {
                notices.add(map);
            } else {
                messages.add(map);
            }
        }
        obj.put("messages", messages);
        obj.put("notices", notices);
        return obj;
    }
}
