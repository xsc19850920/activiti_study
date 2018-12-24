package com.genpact.activiti.test;

import java.io.FileNotFoundException;
import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class Part1_simple extends BaseTest {
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule() ;

	
	
	@Test
	public void test() throws FileNotFoundException{
		String user = "sxia";
		
		String resourceName = "MyProcess";
//		String bpmnExt = ".bpmn";
//		String pngExt = ".png";
//		String folder = "activiti/";
//		String deploymentId = activitiRule.getRepositoryService().createDeployment()
//						 .addClasspathResource(folder+resourceName+bpmnExt)
//						 .addClasspathResource(folder+resourceName+pngExt)
//						 .name("分支流程")
//						 .deploy().getId();
//		String deploymentId = "1";
//		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
//		String processInstanceId = processInstance.getId();
//		System.out.println(String.format("getProcessDefinitionKey : %s",processInstanceId));
//		
//		
		String processInstanceId  = "2501";
		List<Task> taskList = activitiRule.getTaskService().createTaskQuery().processInstanceId(processInstanceId).taskAssignee(user).list();
		
		for (Task task : taskList) {
			activitiRule.getTaskService().complete(task.getId());
		}
		
		
		
	}
}
