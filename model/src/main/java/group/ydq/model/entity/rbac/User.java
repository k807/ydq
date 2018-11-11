package group.ydq.model.entity.rbac;

import javax.persistence.*;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 21:44
 * =============================================
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String nick;

    private String userNumber;

    private String password;

    private String phone;

    @ManyToOne
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
