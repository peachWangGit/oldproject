package com.txdata.contract.domain;

import com.txdata.common.domain.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*合同登记*/
public class ContractRegInfo extends DataEntity<ContractRegInfo> {
    //合同登记信息id
    private String id;
    //项目名称
    private String entryName;
    //合同名称
    private String contractName;
    //合同编号
    private String contractId;
    //签约时间
    private Date signTime;
    //甲方
    private String firstPerson;
    //乙方
    private String secondPerson;
    //委托单位名称
    private String trustName;
    //合同总金额
    private BigDecimal count;
    //履约保证金
    private BigDecimal performanceBond;
    //服务年限
    private Date serviceTime;
    //商务成本
    private BigDecimal businessCost;
    //合同客户名称
    private String customName;
    //联系电话
    private String phone;
    //交付时间
    private Date passTime;
    //合同上传路径
    private String contract;
    //流程实例ID
    private String instanceId;
    //是否归档
    private String sealFlag;//0为待归档，1位已归档
    //合同状态
    private String approvalFlag;//0为待审批，1位已审批，2为驳回

    //删除状态码，0为删除，1为不删除
    //private String delFlag;
    //付款
    List<PayType> payTypes;
    //回款
    List<ReceiveAmount> receiveAmounts;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(String firstPerson) {
        this.firstPerson = firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(String secondPerson) {
        this.secondPerson = secondPerson;
    }

    public String getTrustName() {
        return trustName;
    }

    public void setTrustName(String trustName) {
        this.trustName = trustName;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getPerformanceBond() {
        return performanceBond;
    }

    public void setPerformanceBond(BigDecimal performanceBond) {
        this.performanceBond = performanceBond;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public BigDecimal getBusinessCost() {
        return businessCost;
    }

    public void setBusinessCost(BigDecimal businessCost) {
        this.businessCost = businessCost;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getSealFlag() {
        return sealFlag;
    }

    public void setSealFlag(String sealFlag) {
        this.sealFlag = sealFlag;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public List<PayType> getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(List<PayType> payTypes) {
        this.payTypes = payTypes;
    }

    public List<ReceiveAmount> getReceiveAmounts() {
        return receiveAmounts;
    }

    public void setReceiveAmounts(List<ReceiveAmount> receiveAmounts) {
        this.receiveAmounts = receiveAmounts;
    }

    @Override
    public String toString() {
        return "ContractRegInfo{" +
                "id='" + id + '\'' +
                ", entryName='" + entryName + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractId='" + contractId + '\'' +
                ", signTime=" + signTime +
                ", firstPerson='" + firstPerson + '\'' +
                ", secondPerson='" + secondPerson + '\'' +
                ", trustName='" + trustName + '\'' +
                ", count=" + count +
                ", performanceBond=" + performanceBond +
                ", serviceTime=" + serviceTime +
                ", businessCost=" + businessCost +
                ", customName='" + customName + '\'' +
                ", phone='" + phone + '\'' +
                ", passTime=" + passTime +
                ", contract='" + contract + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", sealFlag='" + sealFlag + '\'' +
                ", approvalFlag='" + approvalFlag + '\'' +
                ", payTypes=" + payTypes +
                ", receiveAmounts=" + receiveAmounts +
                '}';
    }

    public ContractRegInfo(String id, String entryName, String contractName, String contractId, Date signTime, String firstPerson, String secondPerson, String trustName, BigDecimal count, BigDecimal performanceBond, Date serviceTime, BigDecimal businessCost, String customName, String phone, Date passTime, String contract, String instanceId, String sealFlag, String approvalFlag, List<PayType> payTypes, List<ReceiveAmount> receiveAmounts) {
        this.id = id;
        this.entryName = entryName;
        this.contractName = contractName;
        this.contractId = contractId;
        this.signTime = signTime;
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.trustName = trustName;
        this.count = count;
        this.performanceBond = performanceBond;
        this.serviceTime = serviceTime;
        this.businessCost = businessCost;
        this.customName = customName;
        this.phone = phone;
        this.passTime = passTime;
        this.contract = contract;
        this.instanceId = instanceId;
        this.sealFlag = sealFlag;
        this.approvalFlag = approvalFlag;
        this.payTypes = payTypes;
        this.receiveAmounts = receiveAmounts;
    }

    public ContractRegInfo() {
    }
}