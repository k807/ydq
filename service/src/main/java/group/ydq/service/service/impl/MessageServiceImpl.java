package group.ydq.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import group.ydq.authority.SubjectUtils;
import group.ydq.model.dao.pm.MessageRepository;
import group.ydq.model.dao.rbac.UserRepository;
import group.ydq.model.entity.pm.Message;
import group.ydq.model.entity.rbac.User;
import group.ydq.service.service.MessageService;
import group.ydq.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public Message checkUpdate() {
        User receiver = (User) SubjectUtils.getSubject().getBindMap("user");
        Message m = messageRepository.findTopByReceiverOrderByDateDesc(receiver);
        return m;
    }

    @Override
    public Message sendMessage(Message newMessage) {
        messageRepository.save(newMessage);
        return newMessage;
    }

    @Override
    public boolean isMsgExist(Long projectId) {
        return messageRepository.existsById(projectId);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getPMList() {
        Map<String, Object> obj = new HashMap<>();
        User receiver = (User) SubjectUtils.getSubject().getBindMap("user");
        List<Message> allList = messageRepository.findByReceiver(receiver);
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

    @Override
    public Map<String, Object> getPMTable(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        User sender = (User) SubjectUtils.getSubject().getBindMap("user");
        Page<Message> allList = messageRepository.findBySender(sender, pageable);
        Map<String, Object> map = new HashMap<>();

        map.put("statusCode", 200);
        map.put("count", allList.getTotalElements());

        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Message message : allList) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", message.getId());
            m.put("title", message.getTitle());
            m.put("date", DateUtil.dateToStr(message.getDate(), DateUtil.format4));
            m.put("type", message.getType() == 0 ? "公告" : "消息");
            m.put("content", message.getContent());
            m.put("sender", message.getSender().getNick());
            User r = message.getReceiver();
            m.put("receiver", r == null ? null : r.getNick());
            m.put("remark", message.getRemark());
            listMap.add(m);
        }
        map.put("object", listMap);

        return map;
    }

    @Override
    public Map<String, Object> getPMQuery(int type, String title, String receiver, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        User s = (User) SubjectUtils.getSubject().getBindMap("user");
        User r = null;
//        if (receiver != null && userRepository.existsById(receiver))
//            r = userRepository.getOne(receiver);
        Page<Message> allList = messageRepository.queryPMNick(type, title, s, receiver, pageable);
        Map<String, Object> map = new HashMap<>();

        map.put("statusCode", 200);
        map.put("count", allList.getTotalElements());

        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Message message : allList) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", message.getId());
            m.put("title", message.getTitle());
            m.put("date", DateUtil.dateToStr(message.getDate(), DateUtil.format4));
            m.put("type", message.getType() == 0 ? "公告" : "私信");
            m.put("content", message.getContent());
            m.put("sender", message.getSender().getNick());
            m.put("receiver", message.getReceiver() == null ? null : message.getReceiver().getNick());
            m.put("remark", message.getRemark());
            listMap.add(m);
        }
        map.put("object", listMap);

        return map;
    }


    private List<Map<String, Object>> listToMap(List<Message> list) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", list.get(i).getId());
            map.put("title", list.get(i).getTitle());
            map.put("type", list.get(i).getType());
            map.put("content", list.get(i).getContent());
            map.put("remark", JSONObject.parseObject(list.get(i).getRemark()));
            if (i < list.size() - 1 && list.get(i).getDate().getYear() == list.get(i + 1).getDate().getYear()) {
                map.put("date", DateUtil.dateToStr(list.get(i).getDate(), DateUtil.format5));
            } else {
                map.put("date", DateUtil.dateToStr(list.get(i).getDate(), DateUtil.format4));
            }
            map.put("sender", list.get(i).getSender().getNick());
            listMap.add(map);
        }
        return listMap;
    }

}
