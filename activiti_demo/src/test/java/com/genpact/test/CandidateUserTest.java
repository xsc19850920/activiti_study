package com.genpact.test;

import java.util.List;

import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;


/**
 * 注意 这个操作的核心是Candidate 因为clami这个方法是在taskService上的
 * 
 * 
 * 
 * 组用户流程 启动的时候在 ACT_RU_TASK 表中 assignee 字段没有值，
 * 但是会在候选人（candidate）和参与者 （participant） 保存到ACT_RU_IDENTITYLINK 表中
 * 只有用户认领后才会有值 （注意  认领的用户可以不是候选人 可以自定义指定 ，如果用户自定义指定了 他候选人中会自动加上用户自定义的用户）
 * 
 * 
 * 如果是个人任务TYPE的类型表示participant（参与者）
        如果是组任务TYPE的类型表示candidate（候选者）和participant（参与者）
 */
public class CandidateUserTest extends BaseTest {

	private String processDefinitionKey = "usergroup";

	
	/**
	 * 组任务分配三种方式
	 * 方式一、在usergroup.bpmn中属性中不设置assignee这个属性，设置Candidate user 。。。这个属性，应该是多个人中间用，分割
	 * 
	 * 方式二 与个人任务的办理人原理相同  设置Candidate user 。。 这个属性值为流程变量 在启动的流程的时候设置流程变量的值 （注意 这个流程变量的值中至少包含一个,）
	 * 
	 * 方式三  与个人任务的办理人原理相同 Candidate user 。。属性值不用设置 自定义类实现 TaskListener 参见PersonalTaskAssignee 中组任务的部分 
	 *      
	 */
	@Test
	public void deploymentAndStart() {
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/usergroup/";
		repositoryService.createDeployment().addClasspathResource(folder + processDefinitionKey + bpmnExt)
						.addClasspathResource(folder + processDefinitionKey + pngExt).name("组任务分配流程").deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("============================");
		System.out.println(processInstance.toString());
	}
	
	/**
	 * 注意：组任务在没有被用户认领之前是不能在个人任务中查询到的，
	 * 那个人认领的组任务 ，那个人的个人任务中才能察看到
	 * 否则只能通过组任务查询到
	 */
	@Test
	public void findMyPersonalTask(){
		Task task = taskService.createTaskQuery().taskAssignee("zhao").singleResult();
		System.out.println("====================");
		if(null == task){
			System.out.println("组任务在没被用户认领之前不能再个人任务中查询到");
		}else{
			System.out.println(task.toString());
		}
	}
	
	
	@Test
	public void findMyGroupTask(){
		//用候选人察看组任务
		String candidateUser = "zhao";
		List<Task> list = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
		if(!CollectionUtils.isEmpty(list)){
			for (Task task : list) {
				System.out.println(task.toString());
			}
		}
	}
	
	/**
	 * 认领任务
	 */
	@Test
	public void claimTask(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		// 这个任务可以是用户自定义的 也可以是参与者（participant）
		String userId = "zhou";
		taskService.claim(task.getId(), userId);
	}
	/**
	 * 个人任务回退到组任务 （注意 这个个人任务必须是组任务认领过来的才能回退）
	 */
	@Test
	public void backTask(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		// 这个任务可以是用户自定义的
		taskService.setAssignee(task.getId(), null);
	}
	
	
	@Test
	public void finishMyTask(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		taskService.complete(task.getId());
	}
	
	
	
	/**
	 * 查询正在执行任务的候选人列表
	 */
	@Test
	public void findRuTaskCandidateList(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		List<IdentityLink> list = taskService.getIdentityLinksForTask(task.getId());
		if(!CollectionUtils.isEmpty(list)){
			for (IdentityLink l : list) {
				System.out.println(l.toString());
			}
		}
	}
	
	
	
	/**
	 * 查询流程实例参与者列表 
	 */
	@Test
	public void participantUserTask(){
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		 List<IdentityLink> list = runtimeService.getIdentityLinksForProcessInstance(processInstance.getId());
		for (IdentityLink identityLink : list) {
			System.out.println(identityLink.toString());
		}
		
	}
	
	/**
	 * 查询历史候选人列表
	 */
	@Test
	public void findHisCandidateList(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForTask(task.getId());
		for (HistoricIdentityLink l : list) {
			System.out.println(l.getType());
		}
	}
	
	/**
	 * 查询历史参与者列表
	 */
	@Test
	public void findHisParticipantList(){
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForProcessInstance(processInstance.getId());
		for (HistoricIdentityLink l : list) {
			System.out.println(l.getType());
		}
	}
	
	
	/**
	 * 向任务候选人中添加用户
	 */
	@Test
	public void addCandidateUser(){
		String userId = "wu";
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		taskService.addCandidateUser(task.getId(), userId);
	}
	
	/**
	 * 向流程实例中添加参与者
	 */
	@Test
	public void addParticipantUser(){
		String userId = "wu";
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		runtimeService.addParticipantUser(processInstance.getId(), userId);
		
	}
	
	
	


}
