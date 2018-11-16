package group.ydq.model.entity.finalcheckstage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: Natsukawamasuzu
 * @date: 2018/11/16 17:30
 */
@Entity
public class NotificationList {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private Date notifyTime;

    private Date startTime;

    private Date endTime;

    private String checkStage;

    private String detailInfoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
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

    public String getCheckStage() {
        return checkStage;
    }

    public void setCheckStage(String checkStage) {
        this.checkStage = checkStage;
    }

    public String getDetailInfoUrl() {
        return detailInfoUrl;
    }

    public void setDetailInfoUrl(String detailInfoUrl) {
        this.detailInfoUrl = detailInfoUrl;
    }
}
