package group.ydq.model.dto;

import group.ydq.model.entity.dm.Project;

/**
 * @author Daylight
 * @date 2018/11/28 16:14
 */
public class ProjectDetails {
    private Long id;

    private int level;

    private String name;

    private String leader;

    private String phone;

    private String email;

    private int major;

    private String members;

    private String filepath;

    private String filename;

    private String commitmentId;

    public ProjectDetails() {
    }

    public ProjectDetails(Project project,String filename){
        this.id=project.getId();
        this.name=project.getName();
        this.leader=project.getLeader().getNick();
        this.phone=project.getPhone();
        this.level=project.getLevel();
        this.email=project.getEmail();
        this.major=project.getMajor();
        this.members=project.getMembers();
        this.filepath=project.getFilepath();
        this.filename=filename;
        this.commitmentId=project.getCommitmentId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(String commitmentId) {
        this.commitmentId = commitmentId;
    }
}
