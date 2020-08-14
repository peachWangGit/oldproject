package com.txdata.contract.service;
import com.txdata.common.utils.DateUtils;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import com.txdata.contract.util.ExcelUtil;
import com.txdata.contract.util.ExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.txdata.common.utils.DateUtils.DATE_PATTERN;

@Service
public class PoiLoadService {
    @Autowired
    ContractRegInfoService contractRegInfoService;


    public HSSFWorkbook selectDetil(HttpServletResponse response) {
        //从数据库获取数据
        int size = 0;//合同个数
        List<ContractRegInfo> contractRegInfoList = contractRegInfoService.selectAll();
        size = contractRegInfoList.size();
        int maxRow = 0;//总行数
        //得到总行数，用来确定数组大小，才好设置Excel行数
        for (ContractRegInfo contractRegInfo : contractRegInfoList) {
            maxRow += (contractRegInfo.getPayTypes().size() > contractRegInfo.getReceiveAmounts().size())
                    ? contractRegInfo.getPayTypes().size()
                    : contractRegInfo.getReceiveAmounts().size();
        }
        BigDecimal payMoney = new BigDecimal(0);//总付款
        BigDecimal receiveMoney = new BigDecimal(0);//总回款
        for (ContractRegInfo contractRegInfo : contractRegInfoList) {
            List<PayType> payTypes = contractRegInfo.getPayTypes();
            for (PayType payType : payTypes) {
                payMoney=payMoney.add(payType.getPayMoney());//总付款累加
            }
            List<ReceiveAmount> receiveAmounts = contractRegInfo.getReceiveAmounts();
            for (ReceiveAmount receiveAmount : receiveAmounts) {
                receiveMoney=receiveMoney.add(receiveAmount.getReceiveMoney());//总回款累加
            }
        }
        Map<String,BigDecimal> moneyMap=new HashMap<>();//创建付款和回款总钱的集合
        moneyMap.put("payMoney", payMoney);
        moneyMap.put("receiveMoney", receiveMoney);
        //excel标题
        String[] title = {"序号", "合同名称", "合同编号", "填写人", "填写时间", "签约时间", "项目名称"
                , "合同金额(元)", "付款条件", "期款金额", "回款时间", "回款金额(元)"};
        //excel文件名
        String fileName = "合同明细表" + System.currentTimeMillis() + ".xls";
        //内容列表 行、列
        String[][] content = new String[maxRow][title.length];
        //sheet名
        String sheetName = "报销费用明细表";
        Map<Integer, Integer> map = new HashMap<>();;
        int startRow=0;//设置一个每一个合同开始行数 0 2 5
        for (int c = 0; c < size; c++) {//遍历第c个合同
            ContractRegInfo contractRegInfo = contractRegInfoList.get(c);
            int ptSize = contractRegInfo.getPayTypes().size();//付款行数
            int raSize = contractRegInfo.getReceiveAmounts().size();//回款行数
            int max = (ptSize > raSize) ? ptSize : raSize;//每个合同的最大行数
            map.put(c, max);//key：value   第几份合同：该合同最大行数
            for (int i = 0; i < max; i++) {//为每个合同的每一列赋值 2 3
                try {
                    content[i+startRow][0] = String.valueOf(c + 1); //序号
                    content[i+startRow][1] = contractRegInfo.getEntryName(); //合同名称
                    content[i+startRow][2] = contractRegInfo.getContractId();//合同编号
                    content[i+startRow][3] = contractRegInfo.getFirstPerson();//填写人
                    content[i+startRow][4] = String.valueOf(DateUtils.format(contractRegInfo.getPassTime(), DATE_PATTERN));//填写时间
                    content[i+startRow][5] = String.valueOf(DateUtils.format(contractRegInfo.getSignTime(), DATE_PATTERN));//签约时间
                    content[i+startRow][6] = contractRegInfo.getEntryName();//项目名称
                    content[i+startRow][7] = String.valueOf(contractRegInfo.getCount());//合同总金额
                    if (ptSize > raSize) {
                        content[i+startRow][8] = contractRegInfo.getPayTypes().get(i).getPayCondition();//付款条件
                        content[i+startRow][9] = String.valueOf(contractRegInfo.getPayTypes().get(i).getPayMoney());//期款金额
                        if (i+1 > raSize) {//超过自己本身行数就赋值为 /
                            content[i+startRow][10] = "/";//回款时间
                            content[i+startRow][11] = "/";//回款金额
                        } else {
                            content[i+startRow][10] = String.valueOf(DateUtils.format(contractRegInfo.getReceiveAmounts().get(i).getPaybackTime(), DATE_PATTERN));//回款时间
                            content[i+startRow][11] = String.valueOf(contractRegInfo.getReceiveAmounts().get(i).getReceiveMoney());//回款金额
                        }
                    } else if (ptSize < raSize) {
                        content[i+startRow][10] = String.valueOf(DateUtils.format(contractRegInfo.getReceiveAmounts().get(i).getPaybackTime(), DATE_PATTERN));//回款时间
                        content[i+startRow][11] = String.valueOf(contractRegInfo.getReceiveAmounts().get(i).getReceiveMoney());//回款金额
                        if (i+1 > ptSize) {
                            content[i+startRow][8] = "/";//付款条件
                            content[i+startRow][9] = "/";//期款金额
                        } else {
                            content[i+startRow][8] = contractRegInfo.getPayTypes().get(i).getPayCondition();//付款条件
                            content[i+startRow][9] = String.valueOf(contractRegInfo.getPayTypes().get(i).getPayMoney());//期款金额
                        }
                    } else {
                        content[i+startRow][8] = contractRegInfo.getPayTypes().get(i).getPayCondition();//付款条件
                        content[i+startRow][9] = String.valueOf(contractRegInfo.getPayTypes().get(i).getPayMoney());//期款金额
                        content[i+startRow][10] = String.valueOf(DateUtils.format(contractRegInfo.getReceiveAmounts().get(i).getPaybackTime(), DATE_PATTERN));//回款时间
                        content[i+startRow][11] = String.valueOf(contractRegInfo.getReceiveAmounts().get(i).getReceiveMoney());//回款金额
                    }
                } catch (Exception e) {
                }
            }
            startRow = startRow +max;
        }
        // 创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null, map,moneyMap);
        try {
            ExportUtil.exportExcel(response, fileName, wb);
        } catch (Exception e) {
        }
        return wb;
    }
}

