package com.txdata.contract.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.txdata.common.controller.BaseController;
import com.txdata.common.utils.PageUtils;
import com.txdata.common.utils.Query;
import com.txdata.common.utils.R;
import com.txdata.contract.dao.ContractRegInfoMapper;
import com.txdata.contract.dao.PayTypeMapper;
import com.txdata.contract.dao.ReceiveAmountMapper;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import com.txdata.contract.service.ContractRegInfoService;
import com.github.pagehelper.PageInfo;
import com.txdata.contract.vo.ContractAndPaytypeVo;
import com.txdata.contract.vo.ExcelBackVo;
import com.txdata.contract.vo.ExcelContractDetailVo;
import com.txdata.contract.vo.ExcelPayVo;
import com.txdata.modules.project.domain.ProjectDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同登记信息Controller
 */
@Controller
@RequestMapping("/contractRegInfo")
public class ContractRegInfoController extends BaseController {
    @Autowired
    ContractRegInfoService contractRegInfoService;
    @Autowired
    ContractRegInfoMapper contractRegInfoMapper;
    @Autowired
    PayTypeMapper payTypeMapper;
    @Autowired
    ReceiveAmountMapper receiveAmountMapper;
    /**
     * 新增合同登记信息（合同登记/合同回款）
     * 有id为修改，无id为新增
     * @param contractRegInfo 合同登记=基本信息+付款+回款
     * @param listPay 付款
     * @param listBack 回款
     * @return 查询成功返回success
     */
    @ResponseBody
    @PostMapping(value = "/save",produces="application/json; utf-8")
    public R save( ContractRegInfo contractRegInfo,String listPay,String listBack,HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        String role = contractRegInfoMapper.selectRoleByUserId(request.getHeader("userId"));
        if (role.equals("worker") || role.equals("admin")) {//0
            if (contractRegInfoService.save(contractRegInfo, listPay, listBack, request.getHeader("userId")) > 0) {
                return R.success();
            } else {
                return R.error();
            }
        } else {
            return R.error("你不是admin（测试增加功能）或者worker，你不能创建提交合同");
        }
    }

    /**
     * 查看所有   分页查询
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public R list(@RequestParam(defaultValue = "1") int startPage,
                  @RequestParam(defaultValue = "2") int pageSize){
        //  创建Page对象，将page，limit参数传入，必须位于从数据库查询数据的语句之前，否则不生效
        Page<Object> page= PageHelper.startPage(startPage, pageSize);

        //  ASC是根据id 正向排序，DESC是反向排序
        PageHelper.orderBy("id ASC");
        // 从数据库查询，这里返回的的contractRegInfos就已经分页成功了
        Page<ContractRegInfo> contractRegInfos = contractRegInfoService.selectAll();
        // 获取查询记录总数，必须位于从数据库查询数据的语句之后，否则不生效
        int pageNum = page.getPageNum();//当前页
        long total = page.getTotal();//总页数

        // 一下是分页要求的信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg","请求成功");
        map.put("pageNum", pageNum);
        map.put("count",total);
        map.put("data",contractRegInfos);
        return R.success(map);
    }
    /**
     * 查询单个
     * @param id 合同id，必传
     * @return
     */
    @PostMapping( "/form")
    @ResponseBody
    public R form(@RequestParam(required = true)String id) {
        ContractRegInfo contractRegInfo = contractRegInfoService.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap<String, Object>();
        if (contractRegInfo == null) {
            return R.error("查无此数据！");
        }
        map.put("ContractRegInfo", contractRegInfo);
        return R.success(map);
    }

    /**
     * 逻辑删除
     * @param cid 合同id
     * @return
     */
    @PostMapping( "/remove")
    @ResponseBody
    public R remove(String cid){
        ContractRegInfo contractRegInfo = contractRegInfoMapper.selectByPrimaryKey(cid);
        contractRegInfo.setDelFlag("1");
        if(contractRegInfoMapper.updateByPrimaryKey(contractRegInfo)>0){//先改主表状态
            List<PayType> payTypes = payTypeMapper.selectByCid(cid);
            for (PayType payType : payTypes) {
                payType.setDelFlag("1");
                payTypeMapper.updateByPrimaryKey(payType);//再改付款表状态
            }
            List<ReceiveAmount> receiveAmounts = receiveAmountMapper.selectByCid(cid);
            for (ReceiveAmount receiveAmount : receiveAmounts) {
                receiveAmount.setDelFlag("1");
                receiveAmountMapper.updateByPrimaryKey(receiveAmount);//再改回款表状态
            }
            return R.success();
        }
        return R.error();
    }
    /**
     * 物理删除
     */
    @PostMapping( "/delete")
    @ResponseBody
    public R delete(String cid){
        List<PayType> payTypes = payTypeMapper.selectByCid(cid);
        for (PayType payType : payTypes) {
            payTypeMapper.deleteByPrimaryKey(payType.getId());//先删除付款子表
        }
        List<ReceiveAmount> receiveAmounts = receiveAmountMapper.selectByCid(cid);
        for (ReceiveAmount receiveAmount : receiveAmounts) {
            receiveAmountMapper.deleteByPrimaryKey(receiveAmount.getId());//再删除回款子表
        }
        if(contractRegInfoService.deleteByPrimaryKey(cid)>0){//再删除主表
            return R.success();
        }
        return R.error();
    }
    /**
     * Excel 明细接口
     * @return
     */
    @ResponseBody
    @PostMapping("/excel")
    public R ExcelDetail() {
        List<ContractRegInfo> contractRegInfos = contractRegInfoService.selectAll();
        ExcelContractDetailVo excelContractDetailVo = new ExcelContractDetailVo();
        ExcelPayVo excelPayVo = new ExcelPayVo();
        ExcelBackVo excelBackVo = new ExcelBackVo();
        List<ExcelPayVo> listPay=null;
        List<ExcelBackVo> listBack = null;
        for (ContractRegInfo contractRegInfo : contractRegInfos) {
            //开始进行对象拷贝，分别提取，分别拷贝
            BeanUtils.copyProperties(contractRegInfo,excelContractDetailVo);//拷贝主表信息
            List<PayType> payTypes = contractRegInfo.getPayTypes();
            for (PayType payType : payTypes) {
                BeanUtils.copyProperties(payType,excelPayVo);
                listPay.add(excelPayVo);
            }
            excelContractDetailVo.setExcelPayVoList(listPay);//拷贝付款子表信息

            List<ReceiveAmount> receiveAmounts = contractRegInfo.getReceiveAmounts();
            for (ReceiveAmount receiveAmount : receiveAmounts) {
                BeanUtils.copyProperties(receiveAmount,excelBackVo);
                listBack.add(excelBackVo);
            }
            excelContractDetailVo.setExcelBackVoList(listBack);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg","请求成功");
        map.put("data",excelContractDetailVo);
        return R.success(map);
    }
}
