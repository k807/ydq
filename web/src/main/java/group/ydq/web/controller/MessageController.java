package group.ydq.web.controller;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Simple
 * @date on 2018/11/12 22:28
 */
@RestController
@RequestMapping(value = "/pm")
public class MessageController {

    @Autowired
    MessageServiceImpl messageServiceImpl;

    /*
     * 查询所有消息
     * 以寄件人为参数
     * */
    @RequestMapping(value = "/messageBySender/{sender}")
    public List<Message> findBySender(User sender) {
        return messageServiceImpl.findBySender(sender);
    }

    /*
     * 查询所有消息
     * 以收件人为参数
     * */
    @RequestMapping(value = "/messageByReceiver/{receiver}")
    public List<Message> findByReceiver(User receiver) {
        return messageServiceImpl.findByReceiver(receiver);
    }


}
