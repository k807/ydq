package group.ydq.model.entity.dm;

import group.ydq.model.entity.rbac.User;

import javax.persistence.*;

/**
 * @author Daylight
 * @date 2018/11/28 18:26
 */
@Entity
public class ExpertProject {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Project project;

    @ManyToOne
    private User expert;

    private boolean mark;  //专家是否给项目打分

    private int score;

    private String remark;

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

    public User getExpert() {
        return expert;
    }

    public void setExpert(User expert) {
        this.expert = expert;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
