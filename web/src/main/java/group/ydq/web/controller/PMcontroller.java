package group.ydq.web.controller;

import group.ydq.model.entity.pm.Message;
import group.ydq.service.service.impl.MessageServiceImpl;
import group.ydq.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simple
 * @date on 2018/12/17 19:17
 */
@Controller
@RequestMapping(value = "/pm")
public class PMcontroller {
    public final static String format1 = "yyyy-MM-dd HH:mm:ss";

    @RequestMapping("/privatemessage")
    public String privateMessage() {
        return "privatemessage";
    }

    @Autowired
    MessageServiceImpl messageServiceImpl;



    @GetMapping(value = "/getPMList")
    @ResponseBody
    public Map<String, Object> getUserList() {
        Map<String, Object> obj = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        List<Map<String, Object>> notices = new ArrayList<>();
        List<Message> messageList = messageServiceImpl.messageList();
        for (Message message : messageList) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", message.getTitle());
            map.put("type", message.getType());
            map.put("content", message.getContent());
            map.put("remark", message.getRemark());
            map.put("date", DateUtil.dateToStr(message.getDate(), format1));
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
