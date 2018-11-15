package group.ydq.model.entity.mcs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * author:Leo
 * date:2018/11/15
 */
@Entity
public class UploadFile {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private MidCheck midCheck;

    private String name;

    private String url;

    private String remark;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public MidCheck getMidCheck() { return midCheck; }

    public void setMidCheck(MidCheck midCheck) { this.midCheck = midCheck; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }
}
