package group.ydq.model.dto;

import group.ydq.model.entity.dm.Project;
import group.ydq.utils.DataTransUtil;

/**
 * @author Daylight
 * @date 2018/11/28 16:14
 */
public class ProjectDetails {
    private Long id;

    private String level;

    private String name;

    private String leader;

    private String phone;

    private String email;

    private String major;

    private String members;

    private String filepath;

    public ProjectDetails(Project project) {
        this.id=project.getId();
        this.level= DataTransUtil.projectLevel(project.getLevel());
        this.name=project.getName();
        this.leader=project.getLeader().getNick();
        this.phone=project.getPhone();
        this.email=project.getEmail();
        this.major= DataTransUtil.major(project.getMajor());
        this.members=project.getMembers();
        this.filepath=project.getFilepath();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
