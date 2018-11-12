package group.ydq.model.entity.dm;

import group.ydq.model.entity.rbac.User;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daylight
 * @date 2018/11/12 16:02
 */
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private int level;

    private String name;

    @ManyToOne
    private User leader;

    private String phone;

    private String email;

    private String major;

    private List<Member> members;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob", nullable=true)
    private byte[] commitment;

    private String filepath;

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

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public byte[] getCommitment() {
        return commitment;
    }

    public void setCommitment(byte[] commitment) {
        this.commitment = commitment;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
