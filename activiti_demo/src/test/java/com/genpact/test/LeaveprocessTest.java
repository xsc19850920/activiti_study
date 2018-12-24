package com.genpact.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.genpact.entity.LeaveInfo;

public class LeaveprocessTest extends BaseTest{
	
	
	/**
	 * 部署流程
	 */
	@Test
	public void deployment(){
		//获取与流程定义和流程定义部署相关的对象service
		RepositoryService repositorySerivce = processEngine.getRepositoryService();
		//获取部署构造器
		DeploymentBuilder deploymentBuilder = repositorySerivce.createDeployment();
		//添加部署的名字
		deploymentBuilder.name("var test");
		//从classpath 下加载资源文件
		deploymentBuilder.addClasspathResource("activiti/leaveprocess.bpmn");
		deploymentBuilder.addClasspathResource("activiti/leaveprocess.png");
		//部署
		Deployment deployment = deploymentBuilder.deploy();
		System.out.println("====================================================");
		System.out.println("流程定义id ："+deployment.getId());
		System.out.println("流程定义name ："+deployment.getName());
		System.out.println("====================================================");
	}
	
	@Test
	public void startProcessInstanceByKey(){
		//与正在运行和启动流程相关的sercice
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//用key启动对应的流程（key 是***.bpmn 中 properties中process tab 中的 Id 的值 或者数据库中ACT_RE_PROCDEF的key列的值）
		//按key启动默认会启动流程定义中最后的版本
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leaveprocess");
		System.out.println("====================================================");
		System.out.println("流程实例id ："+processInstance.getId());
		System.out.println("流程定义id ："+processInstance.getProcessDefinitionId());
		System.out.println("流程定义key ："+processInstance.getProcessDefinitionKey());
		System.out.println("====================================================");
	}
	
	@Test
	public void findMyPersonalTask(){
//		String assignee = "组长";
//		String assignee = "部门经理";
		String assignee = "总经理";
		//与任务相关的service
		TaskService taskservice = processEngine.getTaskService();
		//任务查询对象
		TaskQuery taskQuery = taskservice.createTaskQuery();
//		List<Task> list = taskQuery.taskAssignee(assignee).list();
		List<Task> list = taskQuery.list();
		
		if(!CollectionUtils.isEmpty(list)){
			System.out.println("====================================================");
		
			for (Task task : list) {
				System.out.println("任务id ："+task.getId());
				System.out.println("任务审批人 ："+task.getAssignee());
				System.out.println("任务执行id ："+task.getExecutionId());
				System.out.println("任务名称id ："+task.getName());
				System.out.println("任务流程定义id ："+task.getProcessDefinitionId());
				System.out.println("任务流程实例id ："+task.getProcessInstanceId());
				System.out.println("任务创建时间 ："+task.getCreateTime());
				System.out.println("任务到期时间 ："+task.getDueDate());
			}
			System.out.println("====================================================");
			
		}
	}
	
	
	@Test
	public void completeMyPersonalTask(){
//		String taskId = "5004";
//		String taskId = "7502";
		String taskId = "10002";
		//与任务相关的service
		TaskService taskservice = processEngine.getTaskService();
		//根据任务id任务完成
		taskservice.complete(taskId);
	}
	
	
	@Test
	public void findHistoryTask(){
		List<Task> list = taskService.createTaskQuery().list();
		if(!CollectionUtils.isEmpty(list)){
			for (Task task : list) {
				System.out.println("=================================");
				System.out.println(task.toString());
				System.out.println("=================================");
			}
		}
	}
	
	
	@Test
	public void findProcessDefinition(){
		 repositoryService.createProcessDefinitionQuery()
		 				//条件查询
//		 				  .deploymentId(deploymentId) 
//		 				  .processDefinitionName(processDefinitionName)
		 				//排序
//		 				  .orderByDeploymentId()
//		 				  .asc()
		 				//分页
//		 				  .listPage(firstResult, maxResults)
		 				//单一结果
		 				.singleResult();
		 
				 
//		if(!CollectionUtils.isEmpty(list)){
//			for (Task task : list) {
//				System.out.println("=================================");
//				System.out.println(task.toString());
//				System.out.println("=================================");
//			}
//		}
	}
	
	@Test
	public void removeProcessDefinition(){
		String deploymentId = "2501";
		//只能删除没启动的流程
//		repositoryService.deleteDeployment(deploymentId);
		//及联删除部署流程
		repositoryService.deleteDeployment(deploymentId,true);
	}
	
	@Test
	public void findPng(){
		String resourceName = null;
		String deploymentId = "15001";
		List<String> nameList = repositoryService.getDeploymentResourceNames(deploymentId);
		for (String name : nameList) {
			if(name.indexOf(".png")!= -1){
				resourceName = name;
				break;
			}
		}
		InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File("d:/"+ resourceName));
			int flag = -1;
			byte[] b = new byte[1024];
			while((flag = in.read(b)) != -1){
				out.write(b,0,flag);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查询最后版本的流程定义
	 */
	
	@Test
	public void findLastVersionProcessDefinition(){
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for (ProcessDefinition processDefinition : list) {
			map.put(processDefinition.getKey(), processDefinition);
		}
		for (Map.Entry<String, ProcessDefinition> m : map.entrySet()) {
			System.out.println("==========================");
			System.out.println("key : "+ m.getKey());
			System.out.println("ProcessDefinition Name : " + m.getValue().getName());
		}
	}
	
	/**
	 * 按照流程key删除流程
	 */
	
	@Test
	public void deleteByProcessDefinitionKey(){
		String processDefinitionKey = "leaveprocess";
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();
		for (ProcessDefinition processDefinition : list) {
			repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
		}
	}
	
	
	/**
	 * 查询流程实例状态（是否结束）
	 */
	@Test
	public void isProcessInstanceFinsh(){
		String processDefinitionKey = "leaveprocess";
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		if(processInstance == null){
			System.out.println("流程已经结束");
		}else{
			System.out.println("流程正在进行");
		}
	}
	/**
	 * 查询某办理人的历史任务
	 */
	@Test
	public void findTaskHisByAssignee(){
		String assignee = "组长";
		 List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
											.taskAssignee(assignee).list();
		for (HistoricTaskInstance h : list) {
			System.out.println(h.toString());
		}
	}
	
	/**
	 * 查询流程实例历史
	 */
	@Test
	public void findTaskHis(){
		String processInstanceId = "1001";
		HistoricProcessInstance  historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				 							.processInstanceId(processInstanceId).singleResult();
		
	}
	
	
	
	/**
	 * 添加流程变量基本类型
	 */
	@Test
	public void setAndGetVar(){
		// 一 设置流程变量
		//step 1 部署
		
		/*//获取部署构造器
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//添加部署的名字
		deploymentBuilder.name("var test");
		//从classpath 下加载资源文件
		deploymentBuilder.addClasspathResource("activiti/leaveprocess.bpmn");
		deploymentBuilder.addClasspathResource("activiti/leaveprocess.png");
		//部署
		Deployment deployment = deploymentBuilder.deploy();
		System.out.println("====================================================");
		System.out.println("流程定义id ："+deployment.getId());
		System.out.println("流程定义name ："+deployment.getName());
		System.out.println("====================================================");
		
		//step 2 启动
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leaveprocess");*/
		
		//设置流程变量可以通过两个service 
		
		//方式一
		
		//local 方式会和当前task榜定，如果task完成执行中就不会查到 ，数据库中的值不会覆盖 （建议不用这个 ） 
//		runtimeService.setVariableLocal(executionId, variableName, value);
//		runtimeService.setVariablesLocal(executionId, variables);

//		Map<String,Object> variables = new HashMap<String, Object>();
//		variables.put("leaveDays", 4);
//		variables.put("leaveTime", new Date());
		
		
//		String executionId = "17505";
//		runtimeService.setVariables(executionId, variables);
		
		//方式二
		//同上
//		taskService.setVariableLocal(taskId, variableName, value);
//		taskService.setVariablesLocal(taskId, variables);
//		String taskId = "17508";
//		taskService.setVariables(taskId, variables);
		
		
		
		// 二 设置流程变量
		
		//方式一
//		String executionId = "17505";
//		Map<String, Object> variables = runtimeService.getVariables(executionId);
//		for (Map.Entry<String, Object> map : variables.entrySet()) {
//			System.out.println(map.getKey());
//			System.out.println(map.getValue());
//		}
		
		
		//方式二
		String taskId = "17508";
//		Map<String, Object> variables = taskService.getVariables(taskId);
		//按照变量名过滤集合
		Collection<String> coll = new ArrayList<String>();
		coll.add("leaveDays");
		Map<String, Object> variables = taskService.getVariables(taskId, coll);
		for (Map.Entry<String, Object> map : variables.entrySet()) {
			System.out.println(map.getKey());
			System.out.println(map.getValue());
		}
	}
	
	
	/**
	 * 添加流程变量javabean类型
	 * 
	 * 这种类型有要求
	 * 1. javabean 必须实现 implements Serializable 
	 * 2.必须private static final long serialVersionUID = 1L;
	 * 否则一点javabean的属性有变化读取流程变量的时候就会抛出异常
	 */
	@Test
	public void setAndGetVarWithJavaBean(){
//		String executionId = "17505";
//		LeaveInfo info = new LeaveInfo(5,new Date(),"请假出去玩");
//		runtimeService.setVariable(executionId, "leaveInfo", info);
		
//		Collection<String> coll = new ArrayList<String>();
//		coll.add("leaveInfo");
//		Map<String, Object> variables = runtimeService.getVariables(executionId,coll);
//		for (Map.Entry<String, Object> map : variables.entrySet()) {
//			System.out.println(map.getKey());
//			System.out.println(map.getValue().toString());
//		}
		
		
		
//		String taskId = "17508";
//		LeaveInfo info = new LeaveInfo(6,new Date(),"请假出去玩 1");
//		taskService.setVariable(taskId, "leaveInfo", info);
		
		
		String taskId = "17508";
		Collection<String> coll = new ArrayList<String>();
		coll.add("leaveInfo");
		Map<String, Object> variables = taskService.getVariables(taskId,coll);
		for (Map.Entry<String, Object> map : variables.entrySet()) {
			System.out.println(map.getKey());
			System.out.println(map.getValue().toString());
		}
	}
}
