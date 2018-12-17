package group.ydq.service.service;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;

import java.util.List;

/**
 * @author Simple
 * @date on 2018/11/12 18:00
 */
public interface MessageService {


    /*
     *  描述：查看所有站内消息
     *  权限：仅限超级管理员
     * */
    List<Message> messageList();

    /*
     *   描述：发送新的站内消息
     *   权限：超级管理员与行政管理员
     * */
    Message sendMessage(Message newMessage);

    /*
     *  描述：通过发送者id查询，参数为user.id
     *  权限：超级管理员与行政管理员
     * */
    List<Message> findBySender(User sender);

    /*
     *  描述：通过接受者id查询，参数为user.id
     *  权限：超级管理员、行政管理员、申报人
     * */
    List<Message> findByReceiver(User receiver);

}
