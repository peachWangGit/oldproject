package com.txdata.contract.domain;

import com.txdata.common.domain.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*合同登记信息--付款方式实体类*/
public class PayType extends DataEntity<PayType> {
    //付款方式id
    private String id;
    //合同id
    private String cid;
    //期数
    private String periodTimes;
    //付款金额
    private BigDecimal payMoney;
    //付款条件
    private String payCondition;
    //附加条件
    private String otherCondition;
    //备注
    private String remark;
    //删除状态码，0为删除，1为不删除
    //private String delFlag;

    public PayType(String id, String cid, String periodTimes, BigDecimal payMoney, String payCondition, String otherCondition, String remark) {
        this.id = id;
        this.cid = cid;
        this.periodTimes = periodTimes;
        this.payMoney = payMoney;
        this.payCondition = payCondition;
        this.otherCondition = otherCondition;
        this.remark = remark;
    }

    public PayType() {
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

    public String getPeriodTimes() {
        return periodTimes;
    }

    public void setPeriodTimes(String periodTimes) {
        this.periodTimes = periodTimes;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition;
    }

    public String getOtherCondition() {
        return otherCondition;
    }

    public void setOtherCondition(String otherCondition) {
        this.otherCondition = otherCondition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", periodTimes='" + periodTimes + '\'' +
                ", payMoney=" + payMoney +
                ", payCondition='" + payCondition + '\'' +
                ", otherCondition='" + otherCondition + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}