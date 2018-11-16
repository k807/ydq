package group.ydq.model.entity.finalcheckstage;

import group.ydq.model.entity.dm.Project;
import group.ydq.model.entity.rbac.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * @author: Natsukawamasuzu
 * @date: 2018/11/12 18:31
 */

@Entity
public class FinalCheck {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Project project;

    private User submitUser;
    
    private boolean submitState;
    
    private String submittedFileUrl;

    private Date submitDate;

    private int checkState;

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

    public User getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(User submitUser) {
        this.submitUser = submitUser;
    }

    public boolean isSubmitState() {
        return submitState;
    }

    public void setSubmitState(boolean submitState) {
        this.submitState = submitState;
    }

    public String getSubmittedFileUrl() {
        return submittedFileUrl;
    }

    public void setSubmittedFileUrl(String submittedFileUrl) {
        this.submittedFileUrl = submittedFileUrl;
    }

    public int getCheckState() {
        return checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
