package group.ydq.service.service.impl;

import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.dao.rbac.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
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
        List<Message> allList = messageRepository.findAllOrderByDate();
        List<Message> noticeList = new ArrayList<>();
        List<Message> messageList = new ArrayList<>();
        for (Message m : allList) {
            int type = m.getType();
            if (type == 0) {
                noticeList.add(m);
            } else {
                messageList.add(m);
            }
        }
        obj.put("notices", listToMap(noticeList));
        obj.put("messages", listToMap(messageList));
        return obj;
    }

    private List<Map<String, Object>> listToMap(List<Message> list) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", list.get(i).getId());
            map.put("title", list.get(i).getTitle());
            map.put("type", list.get(i).getType());
            map.put("content", list.get(i).getContent());
            map.put("remark", list.get(i).getRemark());
            if (i < list.size() - 1 && list.get(i).getDate().getYear() == list.get(i + 1).getDate().getYear()) {
                map.put("date", DateUtil.dateToStr(list.get(i).getDate(), DateUtil.format5));
            } else {
                map.put("date", DateUtil.dateToStr(list.get(i).getDate(), DateUtil.format4));
            }
            User sender = list.get(i).getSender();
            User receiver = list.get(i).getReceiver();
            System.out.println(sender);
            System.out.println(receiver);
            map.put("sender", sender.getNick());
            map.put("receiver", receiver.getNick());
            listMap.add(map);
        }
        return listMap;
    }
}
