package group.ydq.model.dto;

import group.ydq.model.entity.dm.ExpertProject;
import group.ydq.utils.DateUtil;
import group.ydq.utils.DataTransUtil;

/**
 * @author Daylight
 * @date 2018/11/28 17:38
 */
public class ExpertDetails {
    private Long id;
    private String name;
    private String leader;
    private String major;
    private String createTime;
    private String updateTime;
    private String state;
    private int score;
    private String remark;

    public ExpertDetails(ExpertProject ep){
        this.id=ep.getId();
        this.name=ep.getProject().getName();
        this.leader=ep.getProject().getLeader().getNick();
        this.major= DataTransUtil.major(ep.getProject().getMajor());
        this.createTime= DateUtil.dateToStr(ep.getProject().getCreateTime());
        this.updateTime=DateUtil.dateToStr(ep.getProject().getUpdateTime());
        this.state= DataTransUtil.expertState(ep.getState());
        this.score=ep.getScore();
        this.remark=ep.getRemark();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
