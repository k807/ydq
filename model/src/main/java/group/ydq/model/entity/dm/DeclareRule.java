package group.ydq.model.entity.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
import group.ydq.model.entity.rbac.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 申报规则
 * @author Daylight
 * @date 2018/11/27 0:07
 */
@Entity
public class DeclareRule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User publisher;

    private String title;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endTime;

    private int major;

    private String ruleContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMajor() {
        switch (this.major){
            case 0:return "计算机科学与技术";
            case 1:return "电子信息工程";
            case 2:return "电子信息科学与技术";
            case 3:return "数字媒体技术";
            default:return "";
        }
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }
}
