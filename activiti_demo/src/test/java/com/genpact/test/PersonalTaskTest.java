package com.genpact.test;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;




public class PersonalTaskTest extends BaseTest {
	/**
	 * 
	 * 设置任务的办理人共有三种方式
	 * 方式一：在*.bpmn文件中指定办理人assignee 例如"王五"
	 * 方式二：用流程变量指定*.bpmn文件中办理人assignee 例如 ${userName} 或者 ${userName }
	 * 		 然后再流程启动的时候指定这个流程变量的值
	 * 方式三： 
	 *  	1.不用设置*.bpmn文件中办理人assignee
	 * 		2.自定义javabean 实现implements TaskListener 接口 重写public void notify(DelegateTask delegateTask) 
	 * 		在 delegateTask.setAssignee("某个办理人");之前
	 * 		3.查询用户自定义的数据库关系得到当前用户的上级领导id或者名字。
	 * 		4.然后设置成当前任务节点的办理人
	 * 		5.在*.bpmn中的要设置办理人的节点的Listeners tab 页中添加用户自定义的类
	 * 		
	 */
	private String processDefinitionKey = "personaltask";

	@Test
	public void deploymentAndStart() {
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment().addClasspathResource(folder + processDefinitionKey + bpmnExt).addClasspathResource(folder + processDefinitionKey + pngExt).name("个人任务流程").deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("============================");
		System.out.println(processInstance.toString());
	}
	
	/**
	 * receivetask 任务执行的时候不会在ACT_RU_TASK 表中产生数据，执行到这个任务节点的时候流程会暂停，等待接收到一个流程变量来手动推动任务执行下一步
	 */
	@Test
	public void finishMyTask(){
//		String assignee = "组长";
//		String assignee = "部门经理";
		String assignee = "总经理";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).singleResult();
		taskService.complete(task.getId());
	}
	/**
	 * 假设张三出差了不能处理流程，
	 * 需要李四来处理张三的流程
	 */
	@Test
	public void assigneeSomebodyTask(){
		String assignee = "张三";
		Task task = taskService.createTaskQuery().taskAssignee(assignee).singleResult();
		taskService.setAssignee(task.getId(), "李四");
	}
	
		
		

}
