package group.ydq.service.service;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;

import java.util.List;
import java.util.Map;

/**
 * @author Simple
 * @date on 2018/11/12 18:00
 */
public interface MessageService {

    List<Message> messageList();

    Message sendMessage(Message newMessage);

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);

    Map<String, Object> getPMList();

}
