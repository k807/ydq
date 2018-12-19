package group.ydq.web.controller;

import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.impl.MessageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @author Simple
 * @date on 2018/12/17 19:17
 */
@Controller
@RequestMapping(value = "/pm")
public class PMcontroller {

    @RequestMapping("/messageList")
    public String messageList() {
        return "messageList";
    }

    @RequestMapping("/messageTable")
    public String messageTable() {
        return "messageTable";
    }

    @Resource
    MessageServiceImpl messageServiceImpl;

    @Resource
    UserRepository userRepository;

    /*
     * 查询最新一条消息
     * 比较网页登入时间和消息的发送时间
     * 更改消息的红点提示
     * */
    @GetMapping(value = "/checkUpdate")
    @ResponseBody
    public Message checkUpdate() {
        return messageServiceImpl.checkUpdate();
    }

    /*
     * 查询站内消息
     * 以notices和messages分类
     * */
    @GetMapping(value = "/getPMList")
    @ResponseBody
    public Map<String, Object> getPMList() {
        return messageServiceImpl.getPMList();
    }

    /*
     * 新增站内消息
     * 以title、content、time、sender、receivers、remark为参数
     * */
    @RequestMapping(value = "/send")
    @ResponseBody
    public Message sendMessage(String title, int type, String sender, String receiver, String content, String remark) throws ParseException {
        User s = userRepository.getOne(1L);
        User r = userRepository.getOne(1L);
        Message messageOne = new Message(new Date(), type, title, content, remark, s, r);
//        Message messageOne = new Message(new Date(), type, title, content, remark);
        return messageServiceImpl.sendMessage(messageOne);
    }

    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public void deleteMessage(Long id) {
        messageServiceImpl.deleteMessage(id);
    }


}
