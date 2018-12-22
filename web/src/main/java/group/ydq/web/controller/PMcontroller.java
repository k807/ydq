package group.ydq.web.controller;

import group.ydq.authority.SubjectUtils;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.impl.MessageServiceImpl;
import group.ydq.utils.RetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 显示全部站内消息
     * */
    @GetMapping(value = "/getPMTable")
    @ResponseBody
    public Map<String, Object> getPMTable(int page, int limit) {
        return messageServiceImpl.getPMTable(page, limit);
    }

    /*
     * 新增站内消息
     * 以title、content、time、sender、receivers、remark为参数
     * */
    @RequestMapping(value = "/send")
    @ResponseBody
    public BaseResponse sendMessage(@RequestBody Message message) {
        User s = (User) SubjectUtils.getSubject().getBindMap("user");
        message.setDate(new Date());
        message.setSender(s);
//        Message messageOne = new Message(new Date(), type, title, content, remark);
        messageServiceImpl.sendMessage(message);
        return RetResponse.success();
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public BaseResponse deleteProject(Long id) {
        if (messageServiceImpl.isMsgExist(id)) {
            messageServiceImpl.deleteMessage(id);
            return RetResponse.success();
        }
        return RetResponse.error();
    }

}
