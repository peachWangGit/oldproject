package com.txdata.contract.service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txdata.common.persistence.CrudService;
import com.txdata.common.utils.KeyUtil;
import com.txdata.common.utils.StringUtils;
import com.txdata.contract.dao.ContractRegInfoMapper;
import com.txdata.contract.dao.PayTypeMapper;
import com.txdata.contract.dao.ReceiveAmountMapper;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ContractRegInfoService extends CrudService<ContractRegInfoMapper,ContractRegInfo> {
    @Autowired
    private ContractRegInfoMapper contractRegInfoMapper;
    @Autowired
    private PayTypeMapper payTypeMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ReceiveAmountMapper receiveAmountMapper;
    @Autowired
    private TaskService taskService;//对流程任务进行管理，例如任务提醒、任务完成和创建任务等。

    //事务注解
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(String cid) {
        List<PayType> payTypes = payTypeMapper.selectByCid(cid);
        for (PayType payType : payTypes) {
            String id = payType.getId();
            payTypeMapper.deleteByPrimaryKey(id);
        }
        return contractRegInfoMapper.deleteByPrimaryKey(cid);
    }

    /**
     * 修改和新增
     * 有id为修改，无id为新增
     * 无论是否有付款方式和回款信息，都默认添加信息
     * @param contractRegInfo
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int save(ContractRegInfo contractRegInfo, String listPay, String listBack, String userId) throws InvocationTargetException, IllegalAccessException {
        String id=contractRegInfo.getId();
        if (StringUtils.isBlank(id)) {//新增
            String del_flag = "0";
            String cid=KeyUtil.genUniqueKey();
            //工具类，获得一个时间戳加随机数的值
            //启动流程实例，字符串"myProcess"是BPMN模型文件里process元素的id
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
            contractRegInfo.setInstanceId(processInstance.getId());
            contractRegInfo.setSealFlag("0");//0
            contractRegInfo.setApprovalFlag("0");//0
            //合同基本信息添加
            contractRegInfo.setDelFlag(del_flag);
            contractRegInfo.setId(cid);
            List<PayType> payTypes = JSONObject.parseArray(listPay,PayType.class);//付款方式json转数组
            int row = contractRegInfoMapper.insert(contractRegInfo);//合同基本信息添加
            for (PayType payType : payTypes) {
                payType.setId(KeyUtil.genUniqueKey());//若有多个付款，id也不一样
                payType.setCid(cid);//cid一致
                payType.setDelFlag(del_flag);
                payTypeMapper.insert(payType);//付款方式添加
            }
            List<ReceiveAmount> receiveAmounts = JSONObject.parseArray(listBack,ReceiveAmount.class);//回款消息转数组
            for (ReceiveAmount receiveAmount : receiveAmounts) {
                receiveAmount.setId(KeyUtil.genUniqueKey());
                receiveAmount.setCid(cid);
                receiveAmount.setDelFlag(del_flag);
                receiveAmountMapper.insert(receiveAmount);//回款信息添加
            }
            //流程实例启动后，流程会跳转到申请节点
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            Map<String, Object> map = new HashMap<>();
            map.put("workFlag", "submit");
            map.put("SealFlag", "0");
            taskService.complete(task.getId());
            return row;

        } else {//修改，先更新主表，再更新子表
            int row=contractRegInfoMapper.updateByPrimaryKey(contractRegInfo);//更新主表
            List<PayType> payTypes = JSONObject.parseArray(listPay,PayType.class);
            payTypeMapper.deleteByCid(id);
            for (PayType payType : payTypes) {
                payType.setCid(id);
                payType.setId(KeyUtil.genUniqueKey());
                payTypeMapper.insert(payType);//更新付款子表
            }
            List<ReceiveAmount> receiveAmounts = JSONObject.parseArray(listBack,ReceiveAmount.class);
            receiveAmountMapper.deleteByCid(id);
            for (ReceiveAmount receiveAmount : receiveAmounts) {
                receiveAmount.setId(KeyUtil.genUniqueKey());
                receiveAmount.setCid(id);
                receiveAmountMapper.insert(receiveAmount);//更新回款子表
            }
            return row;
        }
    }

    /**
     * 按id查看单个 form
     * @param id
     * @return
     */

    public ContractRegInfo selectByPrimaryKey(String id) {
        ContractRegInfo contractRegInfo = null;
        contractRegInfo = contractRegInfoMapper.selectByPrimaryKey(id);//先查主表
        List<PayType> payTypes = payTypeMapper.selectByCid(id);//再查付款子表
        List<ReceiveAmount> receiveAmounts=receiveAmountMapper.selectByCid(id);//再查回款子表
        contractRegInfo.setPayTypes(payTypes);
        contractRegInfo.setReceiveAmounts(receiveAmounts);
        return contractRegInfo;
    }

    /**
     * 查看全部,分页
     * @return
     */

    public com.github.pagehelper.Page<ContractRegInfo> selectAll() {//0
        //先查全部，然后按需要进行分页
        com.github.pagehelper.Page<ContractRegInfo> contractRegInfos = contractRegInfoMapper.selectAll();
        for (ContractRegInfo contractRegInfo : contractRegInfos) {
            String cid = contractRegInfo.getId();
            List<PayType> payTypes = payTypeMapper.selectByCid(cid);//先查付款子表//0
            contractRegInfo.setPayTypes(payTypes);
            List<ReceiveAmount> receiveAmounts=receiveAmountMapper.selectByCid(cid);//再查回款子表
            contractRegInfo.setReceiveAmounts(receiveAmounts);
        }
        return contractRegInfos;
    }

    /**
     * 合同内容和状态修改
     * @param contractRegInfo 合同
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(ContractRegInfo contractRegInfo) {
        return contractRegInfoMapper.updateByPrimaryKey(contractRegInfo);
    }
}
