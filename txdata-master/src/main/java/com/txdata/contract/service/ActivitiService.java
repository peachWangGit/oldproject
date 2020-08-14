package com.txdata.contract.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.txdata.common.utils.KeyUtil;
import com.txdata.common.utils.R;
import com.txdata.contract.dao.*;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.OANotify;
import com.txdata.contract.domain.OANotifyRecord;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ActivitiService {
    @Autowired
    private ContractRegInfoMapper contractRegInfoMapper;
    @Autowired
    OANotifyMapper oaNotifyMapper;
    @Autowired
    OANotifyRecordMapper oaNotifyRecordMapper;
    @Autowired
    private PayTypeMapper payTypeMapper;
    @Autowired
    private ReceiveAmountMapper receiveAmountMapper;
    @Autowired
    private ContractRegInfoService contractRegInfoService;
    @Autowired
    private RuntimeService runtimeService;//在流程运行时对流程实例进行管理与控制。
    @Autowired
    private TaskService taskService;//对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
    @Autowired
    private IdentityService identityService;//提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。
    @Autowired
    private RepositoryService repositoryService;//提供一系列管理流程部署和流程定义的API。
    @Autowired
    private ProcessEngine processEngine;//继承EngineServers接口，并增加了对流程引擎名称的获取以及关闭。
    @Autowired
    private HistoryService historyService;//对流程的历史数据进行操作，包括查询、删除这些历史数据。
    public List getHistory(String contractId) {
        ContractRegInfo contractRegInfo = contractRegInfoMapper.selectByPrimaryKey(contractId);
        String instanceId = contractRegInfo.getInstanceId();
        List<HistoricActivityInstance> historicTaskInstanceList=historyService // 历史相关Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId(instanceId) // 执行流程实例id
                .list();
        List<Map<String, Object>> list = new ArrayList<>();
        for(HistoricActivityInstance historicTaskInstance:historicTaskInstanceList){
            Map<String, Object> map = new HashMap<>();
            map.put("TaskID",historicTaskInstance.getId());
            map.put("ProcessInstanceIdID",historicTaskInstance.getProcessInstanceId());
            map.put("ActivityName",historicTaskInstance.getActivityName());
            map.put("Assignee",historicTaskInstance.getAssignee());
            map.put("startTime",historicTaskInstance.getStartTime());
            map.put("EndTime",historicTaskInstance.getEndTime());
            list.add(map);
        }
        return list;
    }

    /**
     * 发送通知
     * @param list 历史流转详情
     * @return content 通知内容
     */
    public void sendNotify(List<Map<String, Object>> list) {
        OANotify oaNotify = new OANotify();
        OANotifyRecord oaNotifyRecordLeader = new OANotifyRecord();
        OANotifyRecord oaNotifyRecordAccount = new OANotifyRecord();
        if (CollectionUtil.isNotEmpty(list)) {
            String ProcessInstanceIdID = list.get(0).get("ProcessInstanceIdID").toString();//获取唯一流程定义ID
            String content = ProcessInstanceIdID + ":该合同审核过程如下：";
            for (Map<String, Object> map : list) {
                Object activityName = map.get("ActivityName");
                content = content + activityName.toString() + ":已通过,";
            }
            oaNotify.setContent(content);
            oaNotify.setTitle(ProcessInstanceIdID);
            String notifyId = KeyUtil.genUniqueKey();
            oaNotify.setId(notifyId);
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            oaNotify.setCreateDate(new Date());
            oaNotifyRecordLeader.setId(KeyUtil.genUniqueKey());
            oaNotifyRecordLeader.setNotifyId(notifyId);
            oaNotifyRecordLeader.setUserId("6");//0
            oaNotifyRecordAccount.setId(KeyUtil.genUniqueKey());
            oaNotifyRecordAccount.setNotifyId(notifyId);
            oaNotifyRecordAccount.setUserId("7");
            oaNotifyMapper.insert(oaNotify);
            oaNotifyRecordMapper.insert(oaNotifyRecordLeader);
            oaNotifyRecordMapper.insert(oaNotifyRecordAccount);
        }
    }

    /**
     * 不同角色查看通知
     * @param role
     * @param contartId
     * @return
     */
    public String seachNotify(String role, String contartId) {
        String instanceId = contractRegInfoMapper.selectInstanceIdByContractId(contartId);
        String id = oaNotifyMapper.selectIdByInstanceId(instanceId);
        OANotify oaNotify = oaNotifyMapper.selectByPrimaryKey(id);
        switch (role) {
            case "topLeader":
                OANotifyRecord oaNotifyRecord = oaNotifyRecordMapper.selectByNotifyIdAndUserId(id, "6");
                oaNotifyRecord.setReadDate(new Date());
                oaNotifyRecord.setIsRead("1");
                oaNotifyRecordMapper.updateByPrimaryKey(oaNotifyRecord);
                break;

            case "accountant":
                OANotifyRecord oaNotifyRecord1 = oaNotifyRecordMapper.selectByNotifyIdAndUserId(id, "7");
                oaNotifyRecord1.setReadDate(new Date());
                oaNotifyRecord1.setIsRead("1");
                oaNotifyRecordMapper.updateByPrimaryKey(oaNotifyRecord1);
                break;
        }
        return oaNotify.getContent();
    }
}
