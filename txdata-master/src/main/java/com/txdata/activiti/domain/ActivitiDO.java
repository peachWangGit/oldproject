package com.txdata.activiti.domain;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import com.alibaba.fastjson.annotation.JSONField;
import com.txdata.common.domain.BaseEntity;
import com.txdata.common.utils.StringUtils;
import com.txdata.common.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流相关实体类
 * @author huangmk
 * @since 2018-12-03
 *
 */
public class ActivitiDO extends BaseEntity<ActivitiDO> {
	
	private static final long serialVersionUID = 1L;

	private String taskId; 		// 任务编号
	private String taskName; 	// 任务名称
	private String taskDefKey; 	// 任务定义Key（任务环节标识）

	private String procInsId; 	// 流程实例ID
	private String procInsSuspended; // 流程状态
	private String procDefId; 	// 流程定义ID
	private String procDefKey; 	// 流程定义Key（流程定义标识）

	private String businessTable;	// 业务绑定Table
	private String businessId;		// 业务绑定ID
	
	private String title; 		// 任务标题

	private String status; 		// 任务状态（todo/claim/finish）

//	private String procExecUrl; 	// 流程执行（办理）RUL
	private String comment; 	// 任务意见
	private String attachmentURL;	// 附件URL
	private String attachmentName;	// 附件名称
	private String flag; 		// 意见状态
	
	private Task task; 			// 任务对象
	private ProcessDefinition procDef; 	// 流程定义对象
	private ProcessInstance procIns;	// 流程实例对象
	private HistoricTaskInstance histTask; // 历史任务
	private HistoricActivityInstance histIns;	//历史活动任务

	private String assignee; // 任务执行人编号
	private String assigneeName; // 任务执行人名称

	private Variable vars; 		// 流程变量
//	private Variable taskVars; 	// 流程任务变量
	
	private Date beginDate;	// 开始查询日期
	private Date endDate;	// 结束查询日期

	private List<ActivitiDO> list; // 任务列表
	
	private String taskCreateTime; //任务的创建时间

	private HistoricProcessInstance hisProcIns;	//历史流程实例     create by Zeo ,2017-3-13
	@JsonIgnore
	private String activityId; // 活动id

	public ActivitiDO() {
		super();
	}
	
	public String getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(String taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public String getTaskId() {
		if (taskId == null && task != null){
			taskId = task.getId();
		}
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		if (taskName == null && task != null){
			taskName = task.getName();
		}
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDefKey() {
		if (taskDefKey == null && task != null){
			taskDefKey = task.getTaskDefinitionKey();
		}
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskCreateDate() {
		if (task != null){
			return task.getCreateTime();
		}
		return null;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndDate() {
		if (histTask != null){
			return histTask.getEndTime();
		}
		return null;
	}
	
	@JsonIgnore
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@JsonIgnore
	public ProcessDefinition getProcDef() {
		return procDef;
	}

	public void setProcDef(ProcessDefinition procDef) {
		this.procDef = procDef;
	}
	
	@JsonIgnore
	@JSONField(serialize = false)
	public String getProcDefName() {
		return procDef.getName();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public ProcessInstance getProcIns() {
		return procIns;
	}

	public void setProcIns(ProcessInstance procIns) {
		this.procIns = procIns;
		if (procIns != null && procIns.getBusinessKey() != null){
			String[] ss = procIns.getBusinessKey().split(":");
			setBusinessTable(ss[0]);
			setBusinessId(ss[1]);
		}
	}

//	public String getProcExecUrl() {
//		return procExecUrl;
//	}
//
//	public void setProcExecUrl(String procExecUrl) {
//		this.procExecUrl = procExecUrl;
//	}

	public String getProcInsSuspended() {
		return procInsSuspended;
	}

	public void setProcInsSuspended(String procInsSuspended) {
		this.procInsSuspended = procInsSuspended;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	@JsonIgnore
	public HistoricTaskInstance getHistTask() {
		return histTask;
	}

	public void setHistTask(HistoricTaskInstance histTask) {
		this.histTask = histTask;
	}

//	@JsonIgnore
	public HistoricActivityInstance getHistIns() {
		return histIns;
	}

	public void setHistIns(HistoricActivityInstance histIns) {
		this.histIns = histIns;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format= "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAttachmentURL() {
		return attachmentURL;
	}

	public void setAttachmentURL(String attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProcDefId() {
		if (procDefId == null && task != null){
			procDefId = task.getProcessDefinitionId();
		}
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInsId() {
		if (procInsId == null && task != null){
			procInsId = task.getProcessInstanceId();
		}
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssignee() {
		if (assignee == null && task != null){
			assignee = task.getAssignee();
		}
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<ActivitiDO> getList() {
		return list;
	}

	public void setList(List<ActivitiDO> list) {
		this.list = list;
	}

	public Variable getVars() {
		return vars;
	}

	public void setVars(Variable vars) {
		this.vars = vars;
	}
	
	/**
	 * 通过Map设置流程变量值
	 * @param map
	 */
	public void setVars(Map<String, Object> map) {
		this.vars = new Variable(map);
	}

//	public Variable getTaskVars() {
//		return taskVars;
//	}
//
//	public void setTaskVars(Variable taskVars) {
//		this.taskVars = taskVars;
//	}
//
//	/**
//	 * 通过Map设置流程任务变量值
//	 * @param map
//	 */
//	public void setTaskVars(Map<String, Object> map) {
//		this.taskVars = new Variable(map);
//	}

	/**
	 * 获取流程定义KEY
	 * @return
	 */
	public String getProcDefKey() {
		if (StringUtils.isBlank(procDefKey) && StringUtils.isNotBlank(procDefId)){
			procDefKey = StringUtils.split(procDefId, ":")[0];
		}
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
	/**
	 * 获取过去的任务历时
	 * @return
	 */
	public String getDurationTime(){
		if (histIns!=null && histIns.getDurationInMillis() != null){
			return TimeUtils.toTimeString(histIns.getDurationInMillis());
		}
		return "";
	}
	
	/**
	 * 是否是一个待办任务
	 * @return
	 */
	public boolean isTodoTask(){
		return "todo".equals(status) || "claim".equals(status);
	}
	
	/**
	 * 是否是已完成任务
	 * @return
	 */
	public boolean isFinishTask(){
		return "finish".equals(status) || StringUtils.isBlank(taskId);
	}

	@Override
	public void preInsert() {
		
	}

	@Override
	public void preUpdate() {
		
	}

	public HistoricProcessInstance getHisProcIns() {
		return hisProcIns;
	}

	public void setHisProcIns(HistoricProcessInstance hisProcIns) {
		this.hisProcIns = hisProcIns;
		if (hisProcIns != null && hisProcIns.getBusinessKey() != null){
			String[] ss = hisProcIns.getBusinessKey().split(":");
			setBusinessTable(ss[0]);
			setBusinessId(ss[1]);
		}
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public ActivitiDO(String taskId, String taskName, String taskDefKey, String procInsId, String procInsSuspended, String procDefId, String procDefKey, String businessTable, String businessId, String title, String status, String comment, String attachmentURL, String attachmentName, String flag, Task task, ProcessDefinition procDef, ProcessInstance procIns, HistoricTaskInstance histTask, HistoricActivityInstance histIns, String assignee, String assigneeName, Variable vars, Date beginDate, Date endDate, List<ActivitiDO> list, String taskCreateTime, HistoricProcessInstance hisProcIns, String activityId) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDefKey = taskDefKey;
		this.procInsId = procInsId;
		this.procInsSuspended = procInsSuspended;
		this.procDefId = procDefId;
		this.procDefKey = procDefKey;
		this.businessTable = businessTable;
		this.businessId = businessId;
		this.title = title;
		this.status = status;
		this.comment = comment;
		this.attachmentURL = attachmentURL;
		this.attachmentName = attachmentName;
		this.flag = flag;
		this.task = task;
		this.procDef = procDef;
		this.procIns = procIns;
		this.histTask = histTask;
		this.histIns = histIns;
		this.assignee = assignee;
		this.assigneeName = assigneeName;
		this.vars = vars;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.list = list;
		this.taskCreateTime = taskCreateTime;
		this.hisProcIns = hisProcIns;
		this.activityId = activityId;
	}
}
