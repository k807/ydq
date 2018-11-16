package group.ydq.web.controller;

import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.pm.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Simple
 * @date on 2018/11/12 22:28
 */
@RestController
@RequestMapping("/test")
public class MessageController {

    @Autowired
    MessageServiceImpl messageServiceImpl = new MessageServiceImpl();

    /*
     * 查询所有
     * 无参，仅供超级管理员查看
     * */
    @GetMapping(value = "/messages")
    public List<Message> messageList() {
        return messageServiceImpl.messageList();
    }

    /*
     * 单个查询
     * 参数为message.id，仅供超级管理员查看
     * */
    @GetMapping(value = "/messages/{id}")
    Optional<Message> messageOne(@PathVariable("id") Long id) {
        return messageServiceImpl.messageOne(id);
    }

    /*
     * 新增站内消息
     * 以title、content、time、sender、receivers、remark为参数
     * */
    @GetMapping(value = "/add")
    public Message sendMessage() {
        /*@RequestParam("title") String title,
                               @RequestParam("time") Date time,
                               @RequestParam("content") String content,
                               @RequestParam("sender") User sender,
                               @RequestParam("receiver") User receiver*/
        Message messageOne = new Message();
        User senderUser = new User();
        senderUser.setId(Long.valueOf(new Random().nextInt(10)));
        senderUser.setPhone("123");
        User receiverUser = new User();
        receiverUser.setId(Long.valueOf(new Random().nextInt(10)));
        receiverUser.setPhone("456");
        messageOne.setTitle("title");
        messageOne.setTime(new Date());
        messageOne.setContent("content");
        messageOne.setSender(senderUser);
        messageOne.setReceiver(receiverUser);
        return messageServiceImpl.sendMessage(messageOne);
    }

    /*
     * 根据id删除message
     *
     * */
    @GetMapping(value = "/deleteOne/{id}")
    public void deleteOne(Long id) {
        messageServiceImpl.delete(id);
    }

    /*
     * 查询所有消息
     * 以寄件人为参数
     * */
    @GetMapping(value = "/messageBySender/{sender}")
    public List<Message> findBySender(@PathVariable("sender") User sender) {
        return messageServiceImpl.findBySender(sender);
    }

    /*
     * 查询所有消息
     * 以收件人为参数
     * */
    @GetMapping(value = "/messageByReceiver/{receiver}")
    public List<Message> findByReceiver(@PathVariable("receiver") User receiver) {
        return messageServiceImpl.findByReceiver(receiver);
    }

    public static void main(String[] args) {
        System.out.println(new Random().nextInt(10));
    }

}
