package com.txdata.contract.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelContractDetailVo {
    //合同登记信息id
    private String id;
    //合同名称
    private String contractName;
    //合同编号
    private String contractId;
    //甲方(甲方)
    private String firstPerson;
    //交付时间(填写时间)
    private Date passTime;
    //签约时间
    private Date signTime;
    //项目名称
    private String entryName;
    //合同总金额
    private BigDecimal count;
    //支付vo集合
    List<ExcelPayVo> excelPayVoList;
    //回款Vo集合
    List<ExcelBackVo> excelBackVoList;

}
