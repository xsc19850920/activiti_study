package com.genpact.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class BranchTest extends BaseTest{
	@Test
	public void deleteProcess(){
		String deploymentId = "35001";
		repositoryService.deleteDeployment(deploymentId, true);
	}

	@Test
	public void processDef(){
		String resourceName = "branchprocess";
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment()
						 .addClasspathResource(folder+resourceName+bpmnExt)
						 .addClasspathResource(folder+resourceName+pngExt)
						 .name("分支流程")
						 .deploy();
	}
	
	@Test
	public void startProcess(){
		String processDefinitionKey = "branchprocess";
		runtimeService.startProcessInstanceByKey(processDefinitionKey);
	}
	
	@Test
	public void findMyPersonalTask(){
		String processDefinitionKey = "branchprocess";
		String assignee = "支付人";
		List<Task> list = taskService.createTaskQuery()
				   .processDefinitionKey(processDefinitionKey)
				   .taskAssignee(assignee)
				   .orderByTaskCreateTime()
				   .asc()
				   .list();
		
		for (Task task : list) {
			System.out.println(task.toString());
		}
	}
	
	@Test
	public void compilteMyTesk() {
		String processDefinitionKey = "branchprocess";
		String assignee = "支付人";
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee(assignee).orderByTaskCreateTime().asc().singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pay", 11);
		taskService.complete(task.getId(), variables);
	}	
	
	
	@Test
	public void findMyPersonalTask2(){
		String processDefinitionKey = "branchprocess";
		String assignee = "密码";
		List<Task> list = taskService.createTaskQuery()
				   .processDefinitionKey(processDefinitionKey)
				   .taskAssignee(assignee)
				   .orderByTaskCreateTime()
				   .asc()
				   .list();
		
		for (Task task : list) {
			System.out.println(task.toString());
		}
	}
	
	
	@Test
	public void compilteMyTesk2() {
		String processDefinitionKey = "branchprocess";
		String assignee = "密码";
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee(assignee).orderByTaskCreateTime().asc().singleResult();
		taskService.complete(task.getId());
	}
	
	
	@Test
	public void findHisTask(){
		String assignee = "支付人";
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list();
		for (HistoricTaskInstance l : list) {
			System.out.println(l.toString());
			System.out.println("+++++++++++++++++++++++++");
		}
	}
	
	@Test
	public void findHisProcessInstance() {
		String processDefinitionKey = "branchprocess";
		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
		for (HistoricProcessInstance l : list) {
			System.out.println(l.getId()+"\t"+l.getName()+"\t"+l.getProcessDefinitionId()+"\t"+l.getStartTime());
			System.out.println("+++++++++++++++++++++++++");
		}
	}
	
	
	@Test
	public void findHistoricVariable(){
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().variableName("pay").list();
		for (HistoricVariableInstance l : list) {
			System.out.println(l.toString());
			System.out.println("+++++++++++++++++++++++++");
		}
	}
			
}
