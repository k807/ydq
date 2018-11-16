package group.ydq.model.entity.cs;

import group.ydq.model.entity.rbac.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * author:Leo
 * date:2018/11/15
 */
@Entity
public class UploadFile {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CheckStage checkStage;

    private String name;

    private String url;

    @ManyToOne
    private User uploader;

    private Date uploadTime;

    private String remark;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public CheckStage getCheckStage() {
        return checkStage;
    }

    public void setCheckStage(CheckStage checkStage) {
        this.checkStage = checkStage;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }
}
