package com.txdata.contract.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.txdata.common.utils.R;
import com.txdata.common.utils.ShiroUtils;
import com.txdata.contract.dao.ContractRegInfoMapper;
import com.txdata.contract.dao.PayTypeMapper;
import com.txdata.contract.dao.ReceiveAmountMapper;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import com.txdata.contract.service.ActivitiService;
import com.txdata.contract.service.ContractRegInfoService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * activiti控制层//activiti控制层
 */
@Controller
@RequestMapping("/activiti")
public class ActivitiController {
    @Autowired
    private PayTypeMapper payTypeMapper;
    @Autowired
    private ReceiveAmountMapper receiveAmountMapper;
    @Autowired
    ContractRegInfoService contractRegInfoService;
    @Autowired
    ContractRegInfoMapper contractRegInfoMapper;
    @Autowired
    private RuntimeService runtimeService;//在流程运行时对流程实例进行管理与控制。
    @Autowired
    private TaskService taskService;//对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
    @Autowired
    private HistoryService historyService;//对流程的历史数据进行操作，包括查询、删除这些历史数据。
    @Autowired
    private ActivitiService activitiService;//流程实例service

    /**
     * 待审批列表
     * 通过userId，查出它的角色，再查出他的任务组列表，通过任务组列表，
     * 得到每一个实例id，通过实例id，找到应该要他审核的各个合同信息
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/noApprovalList")
    public R noApprovalList() {
        //查出当前用户的角色
        String role = ShiroUtils.getUser().getUsername();
        //查询用户组的待审批流程列表businessAuditor
        List<Task> tasks = taskService.createTaskQuery()//创建任务查询对象
                .taskAssignee(role)//办理人
                .list();//返回列表
        if (CollectionUtil.isNotEmpty(tasks)) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<ContractRegInfo> list = new ArrayList<>();//接收contractRegInfo
            for (Task task : tasks) {
                String processInstanceId = task.getProcessInstanceId();//流程实例id
                //通过流程实例获取合同
                ContractRegInfo contractRegInfo = contractRegInfoMapper.selectContractByInstanceId(processInstanceId);
                list.add(contractRegInfo);
            }
            map.put("contractRegInfoList", list);
            return R.success(map);
        }
        return R.error("对不起，你所查看的用户没有待审批合同，请重新登录！");
    }

    /**
     * 已审批列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/isApprovalList")
    public R isApprovalList(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        //查出当前登录用户的角色
        String role = contractRegInfoMapper.selectRoleByUserId(userId);
        System.out.println(role);
        //查询用户组的已审批流程列表
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(role)
                .finished()
                .list();
        if (CollectionUtil.isNotEmpty(tasks)) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<ContractRegInfo> list = new ArrayList<>();
            for (HistoricTaskInstance task : tasks) {
                String processInstanceId = task.getProcessInstanceId();
                ContractRegInfo contractRegInfo = contractRegInfoMapper.selectContractByInstanceId(processInstanceId);
                list.add(contractRegInfo);
            }
            map.put("contractRegInfoList", list);
            return R.success(map);
        }
        return R.error("对不起，你所查看的角色暂时没有已审批合同，请重新登录！");
    }

    /**
     * 审批
     * @param contractId 合同id
     * @param result 审批条件结果
     * @param comment 不同意的意见
     * @return
     */
    @ResponseBody
    @PostMapping("/Approval")
    public R Approval(String contractId,String result,String comment) {
        ContractRegInfo contractRegInfo = contractRegInfoService.selectByPrimaryKey(contractId);
        Task task = taskService.createTaskQuery().processDefinitionKey("myProcess").singleResult();
        String sealFlag = contractRegInfo.getSealFlag();//是否归档
        String name = task.getName();//任务名
        Map<String, Object> map = new HashMap<>();
        switch (name) {
            case "businessAudit":
                if (result.equals("submit")) {
                    map.put("businessFlag", result);
                    map.put("sealFlag", sealFlag);
                    taskService.complete(task.getId(), map);//同意，完成商务审批
                } else {
                    map.put("businessFlag", result);
                    map.put("comment", comment);
                    taskService.complete(task.getId(), map);//不同意，完成商务审批，打回
                }
                break;
            case "contractAudit":
                if (result.equals("submit")) {
                    map.put("contractFlag", result);
                    map.put("sealFlag", sealFlag);
                    taskService.complete(task.getId(), map);//同意，完成合同审批
                    task = taskService.createTaskQuery().processDefinitionKey("myProcess").singleResult();
                    taskService.complete(task.getId());//合同审核完直接归档
                    contractRegInfo.setSealFlag("1");
                    contractRegInfoMapper.updateByPrimaryKey(contractRegInfo);//修改合同状态
                    //发送通知给领导和出纳
                    List history = activitiService.getHistory(contractId);
                    activitiService.sendNotify(history);

                } else {
                    map.put("contractAuditor", result);
                    map.put("comment", comment);
                    taskService.complete(task.getId(), map);//不同意，完成合同审批，打回
                }
                break;
            default:
                return R.error("你非商务审核员或合同审核员，请退出");
        }
        return R.success(name + "审核结束，意见如下：" + map);
    }

    /**
     * 获取历史流流转
     */
    @ResponseBody
    @PostMapping("/getHistory")
    public R getHistory(String contractId) {
        List history = activitiService.getHistory(contractId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg","请求成功");
        map.put("data",history);
        return R.success(map);
    }

    /**
     *不同角色查看通知内容
     * @param request 用来获取登录ID
     * @param contractId 用来指定哪个合同的通知
     * @return
     */
    @ResponseBody
    @PostMapping("/seachNotify")
    public R oaNotify(HttpServletRequest request,String contractId) {
        String userId = request.getHeader("userId");
        //查出当前登录用户的角色
        String role = contractRegInfoMapper.selectRoleByUserId(userId);
        //非高层领导和会计出纳没有权限
        if ("topLeader".equals(role)  ||  "accountant".equals(role)) {
            String content = activitiService.seachNotify(role, contractId);
            return R.success(content);
        }
        return R.error("对不起，非高层领导和会计出纳，请重新登录！");
    }

}
