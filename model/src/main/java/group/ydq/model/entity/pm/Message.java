package group.ydq.model.entity.pm;

import group.ydq.model.entity.rbac.User;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Simple on 2018/11/12.
 */
@Entity
public class Message {

    public Message() {
    }

    public Message(Date date, int type, String title, String content, String remark) {
        this.date = date;
        this.type = type;
        this.title = title;
        this.content = content;
        this.remark = remark;
    }

    public Message(Date date, int type, String title, String content, String remark, User sender, User receiver) {
        this.date = date;
        this.type = type;
        this.title = title;
        this.content = content;
        this.remark = remark;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Id
    @GeneratedValue
    private Long id;

    private Date date;
    /*
     * type说明站内消息类型
     * 0代表公告类型，即所有user都见
     * 1代表私信类型，即个别user可见
     * */
    private int type;

    /*
     * title可以用‘申报入口说明’、‘中期检查说明’等
     * */
    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    private String remark;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
