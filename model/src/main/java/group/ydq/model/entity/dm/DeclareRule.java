package group.ydq.model.entity.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
import group.ydq.model.entity.rbac.User;

import javax.persistence.*;
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

    @Column(columnDefinition="TEXT")
    private String ruleContent;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public int getMajor() {
        return major;
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
