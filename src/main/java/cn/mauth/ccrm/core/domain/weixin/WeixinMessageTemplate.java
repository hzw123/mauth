package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class WeixinMessageTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dbid;
    private String weixinAccountId;
    private int templateType;
    private String templateId;
    private String remark;
    @Column(columnDefinition = "datetime")
    private Date updateTime;
    private String updateUser;

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getWeixinAccountId() {
        return weixinAccountId;
    }

    public void setWeixinAccountId(String weixinAccountId) {
        this.weixinAccountId = weixinAccountId;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}