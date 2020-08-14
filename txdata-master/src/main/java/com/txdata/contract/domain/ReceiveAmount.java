package com.txdata.contract.domain;

import com.txdata.common.domain.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/*合同回款信息--已收款项实体类*/
public class ReceiveAmount extends DataEntity<ReceiveAmount> {
    //已收款项id
    private String id;
    //合同id
    private String cid;
    //已收金额
    private BigDecimal receiveMoney;
    //回款银行
    private String paybackBank;
    //回款账号
    private String paybackAccount;
    //回款时间
    private Date paybackTime;
    //回单上传
    private String paybackSrc;
    //删除状态码，0为删除，1为不删除
   // private String delFlag;


    public ReceiveAmount(String id, String cid, BigDecimal receiveMoney, String paybackBank, String paybackAccount, Date paybackTime, String paybackSrc) {
        this.id = id;
        this.cid = cid;
        this.receiveMoney = receiveMoney;
        this.paybackBank = paybackBank;
        this.paybackAccount = paybackAccount;
        this.paybackTime = paybackTime;
        this.paybackSrc = paybackSrc;
    }

    public ReceiveAmount() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public String getPaybackBank() {
        return paybackBank;
    }

    public void setPaybackBank(String paybackBank) {
        this.paybackBank = paybackBank;
    }

    public String getPaybackAccount() {
        return paybackAccount;
    }

    public void setPaybackAccount(String paybackAccount) {
        this.paybackAccount = paybackAccount;
    }

    public Date getPaybackTime() {
        return paybackTime;
    }

    public void setPaybackTime(Date paybackTime) {
        this.paybackTime = paybackTime;
    }

    public String getPaybackSrc() {
        return paybackSrc;
    }

    public void setPaybackSrc(String paybackSrc) {
        this.paybackSrc = paybackSrc;
    }

    @Override
    public String toString() {
        return "ReceiveAmount{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", receiveMoney=" + receiveMoney +
                ", paybackBank='" + paybackBank + '\'' +
                ", paybackAccount='" + paybackAccount + '\'' +
                ", paybackTime=" + paybackTime +
                ", paybackSrc='" + paybackSrc + '\'' +
                '}';
    }
}