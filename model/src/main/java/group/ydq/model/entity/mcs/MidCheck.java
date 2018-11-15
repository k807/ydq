package group.ydq.model.entity.mcs;

import group.ydq.model.entity.dm.Project;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * author:Leo
 * date:2018/11/12
 */
@Entity
public class MidCheck {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Project project;

    private Date time;

    private String remarks;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public String getRemarks() { return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }
}
