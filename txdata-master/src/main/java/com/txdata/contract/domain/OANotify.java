package com.txdata.contract.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/*通知通告*/
public class OANotify {
    private String id;//编号

    private String type;//类型

    private String title;//标题

    private String content;//内容

    private String files;//附件

    private String status;//状态 0草稿 1发布

    private String createBy;//创建者

    private Date createDate;//创建时间

    private String updateBy;//更新者

    private Date updateDate;//更新时间

    private String remarks;//备注信息

    private String delFlag;//删除标记

    public OANotify(String id, String type, String title, String content, String files, String status, String createBy, Date createDate, String updateBy, Date updateDate, String remarks, String delFlag) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.files = files;
        this.status = status;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
        this.remarks = remarks;
        this.delFlag = delFlag;
    }

    public OANotify() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "OANotify{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", files='" + files + '\'' +
                ", status='" + status + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", remarks='" + remarks + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}