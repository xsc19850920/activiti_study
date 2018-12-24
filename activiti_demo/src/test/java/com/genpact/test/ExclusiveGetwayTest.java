package com.genpact.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ExclusiveGetwayTest extends BaseTest {
	@Test
	public void deploymentAndStart(){
		String resourceName = "exclusivegetway";
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment()
						 .addClasspathResource(folder+resourceName+bpmnExt)
						 .addClasspathResource(folder+resourceName+pngExt)
						 .name("排他网管流程")
						 .deploy();
		
		String processDefinitionKey = "exclusivegetway";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("============================");
		System.out.println(processInstance.toString());
	}
	
	@Test
	public void findMyPersonalTask(){
		String assignee = "申请人";
		String processDefinitionKey = "exclusivegetway";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).singleResult();
		System.out.println("============================");
		System.out.println(task.toString());
		System.out.println("============================");
	}
	
	
	@Test
	public void comMyTask(){
		String assignee = "申请人";
		String processDefinitionKey = "exclusivegetway";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("money", 500);
		taskService.complete(task.getId(), variables);
	}
	
	
	@Test
	public void comMyTask1(){
//		String assignee = "部门经理";
		String assignee = "财务";
		String processDefinitionKey = "exclusivegetway";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).singleResult();
		taskService.complete(task.getId());
	}
}
