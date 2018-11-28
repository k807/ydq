package group.ydq.model.dto;

import group.ydq.model.entity.dm.Project;
import group.ydq.utils.DateUtil;
import group.ydq.utils.DataTransUtil;

/**
 * @author Daylight
 * @date 2018/11/28 16:56
 */
public class ManagerDetails {
    private Long id;
    private String name;
    private String leader;
    private String major;
    private String createTime;
    private String updateTime;
    private String state;

    public ManagerDetails(Project project) {
        this.id=project.getId();
        this.name=project.getName();
        this.leader=project.getLeader().getNick();
        this.major= DataTransUtil.major(project.getMajor());
        this.createTime= DateUtil.dateToStr(project.getCreateTime());
        this.updateTime =DateUtil.dateToStr(project.getUpdateTime());
        this.state= DataTransUtil.managerState(project.getState());
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
}
