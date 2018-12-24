package com.genpact.test;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest extends BaseTest {

	private String processDefinitionKey = "receivetask";

	@Test
	public void deleteProcess() {
		String[] deploymentId = new String[] { "42501", "50001", "60001", "67501", "75001", "85001", "87501" };
		for (int i = 0; i < deploymentId.length; i++) {
			repositoryService.deleteDeployment(deploymentId[i], true);
		}

	}

	@Test
	public void deploymentAndStart() {
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment().addClasspathResource(folder + processDefinitionKey + bpmnExt).addClasspathResource(folder + processDefinitionKey + pngExt).name("接收任务流程").deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("============================");
		System.out.println(processInstance.toString());
	}
	
	/**
	 * receivetask 任务执行的时候不会在ACT_RU_TASK 表中产生数据，执行到这个任务节点的时候流程会暂停，等待接收到一个流程变量来手动推动任务执行下一步
	 */
	@Test
	public void nextStep(){
		//这个receivetask1 是receivetask.bpmn 文件中 汇总当日销售额 这个节点的id属性的值
		String sumActivityId = "receivetask1";
		//查询  流程实例key为receivetask 并且当前任务节点是 receivetask1 的正在执行的节点的执行对象
		Execution sumExecution =   runtimeService.createExecutionQuery().processDefinitionKey(processDefinitionKey).activityId(sumActivityId).singleResult();
		//设置流程变量
		runtimeService.setVariable(sumExecution.getId(), "sum", 20000);
		//当前流程向后执行一步 （即执行到给老板发送邮件这个任务节点）
		runtimeService.signal(sumExecution.getId());
		
		//从给老板发送邮件这个任务节点 获取流程变量
		String sendActivityId = "receivetask2";
		Execution sendExecution =   runtimeService.createExecutionQuery().processDefinitionKey(processDefinitionKey).activityId(sendActivityId).singleResult();
		Integer value = (Integer)runtimeService.getVariable(sendExecution.getId(), "sum");
		System.out.println("发送邮件的内容是： 当日汇总的金额"+value);
		//当前节点向后执行一步（即执行结束节点，流程结束）
		runtimeService.signal(sendExecution.getId());
	}

}
