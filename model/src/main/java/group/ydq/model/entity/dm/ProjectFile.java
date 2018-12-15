package group.ydq.model.entity.dm;

import javax.persistence.*;

/**
 * @author Daylight
 * @date 2018/11/30 19:52
 */
@Entity
public class ProjectFile {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String uniqueId;

    private String name;

    private String filePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
