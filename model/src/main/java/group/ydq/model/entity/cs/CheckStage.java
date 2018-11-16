package group.ydq.model.entity.cs;

import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * author:Leo
 * date:2018/11/12
 */
@Entity
public class CheckStage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    private int stage;  //项目所处阶段（中期或者后期）

    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(boolean uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getSatus() {
        return satus;
    }

    public void setSatus(int satus) {
        this.satus = satus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<User> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(List<User> verifiers) {
        this.verifiers = verifiers;
    }

    private boolean uploadStatus;   //材料是否上传

    private int satus;  // 所处阶段审核状态

    private String remarks;

    @OneToMany
    private List<User> verifiers;//审核人

}
