package com.txdata.contract.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractAndPaytypeVo {
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
}
