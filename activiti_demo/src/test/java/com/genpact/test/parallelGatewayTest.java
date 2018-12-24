package com.genpact.test;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class parallelGatewayTest extends BaseTest {
	
	private String processDefinitionKey =  "parallelgateway";
	@Test
	public void deploymentAndStart(){
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment()
						 .addClasspathResource(folder+processDefinitionKey+bpmnExt)
						 .addClasspathResource(folder+processDefinitionKey+pngExt)
						 .name("并行网管流程")
						 .deploy();
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("============================");
		System.out.println(processInstance.toString());
	}
	
	@Test
	public void findMyPersonalTask(){
		String assignee = "买家";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).singleResult();
		System.out.println("============================");
		System.out.println(task.toString());
		System.out.println("============================");
	}
	
	
	@Test
	public void comMyTask(){
		String assignee = "买家";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).singleResult();
		taskService.complete(task.getId());
	}
	
	
	@Test
	public void comMyTask1(){
		String assignee = "卖家";
		List<Task> task = taskService.createTaskQuery().taskAssignee(assignee).processDefinitionKey(processDefinitionKey).list();
		for (Task t : task) {
			taskService.complete(t.getId());
		}
		
	}
}
