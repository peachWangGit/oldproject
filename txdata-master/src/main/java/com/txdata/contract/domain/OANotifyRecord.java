package com.txdata.contract.domain;



import java.util.Date;
/*通知通告发送记录*/

public class OANotifyRecord {
    private String id;//编号

    private String notifyId;//通知通告ID

    private String userId;//接受人

    private String isRead;//阅读标记

    private Date readDate;//阅读时间

    public OANotifyRecord(String id, String notifyId, String userId, String isRead, Date readDate) {
        this.id = id;
        this.notifyId = notifyId;
        this.userId = userId;
        this.isRead = isRead;
        this.readDate = readDate;
    }

    public OANotifyRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    @Override
    public String toString() {
        return "OANotifyRecord{" +
                "id='" + id + '\'' +
                ", notifyId='" + notifyId + '\'' +
                ", userId='" + userId + '\'' +
                ", isRead='" + isRead + '\'' +
                ", readDate=" + readDate +
                '}';
    }
}