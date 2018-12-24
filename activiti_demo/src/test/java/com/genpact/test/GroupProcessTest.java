package com.genpact.test;

import java.util.List;

import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;


/**
 * 用户组 组任务流程
 * 
 * 如果是个人任务TYPE的类型表示participant（参与者）
        如果是组任务TYPE的类型表示candidate（候选者）和participant（参与者）
 */
public class GroupProcessTest extends BaseTest {

	private String processDefinitionKey = "groupprocess";

	
	/**
	 * 组任务分配三种方式
	 * 方式、在usergroup.bpmn中属性中不设置assignee这个属性，设置Candidate group 。。。这个属性 比如 部门经理
	 * 这个部门经理实际上是 
	 * ACT_ID_GROUP,表里面的一条数据
	 * 所以这个用户组组任务 需要用到 IdentityService
	 *  
	 *  ACT_ID_INFO, ACT_ID_MEMBERSHIP, ACT_ID_USER
	 *
	 */
	@Test
	public void deploymentAndStart() {
		String bpmnExt = ".bpmn";
		String pngExt = ".png";
		String folder = "activiti/";
		repositoryService.createDeployment().addClasspathResource(folder + processDefinitionKey + bpmnExt)
						.addClasspathResource(folder + processDefinitionKey + pngExt).name("用户组组任务分配流程").deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("============================");
		System.out.println(processInstance.toString());
		
		/**
		 * 创建用户和用户组
		 * 并且关联他们之间的关系
		 */
		Group group1 = new GroupEntity("1");
		group1.setName("部门经理");
		Group group2 = new GroupEntity("2");
		group2.setName("总经理");
		
		identityService.saveGroup(group1);
		identityService.saveGroup(group2);
		
		User user1  = new UserEntity("1");
		user1.setFirstName("夏");
		user1.setLastName("胜春");
		
		User user2 = new UserEntity("2");
		user2.setFirstName("陈");
		user2.setLastName("刚");
		
		User user3 = new UserEntity("3");
		user3.setFirstName("王");
		user3.setLastName("冠");
		identityService.saveUser(user1);
		identityService.saveUser(user2);
		identityService.saveUser(user3);
		
		identityService.createMembership("1", "1");
		identityService.createMembership("2", "1");
		identityService.createMembership("3", "2");
		
		
		
	}
	
	/**
	 * 注意：组任务在没有被用户认领之前是不能在个人任务中查询到的，
	 * 那个人认领的组任务 ，那个人的个人任务中才能察看到
	 * 否则只能通过组任务查询到
	 */
	@Test
	public void findMyPersonalTask(){
		Task task = taskService.createTaskQuery().taskAssignee("1").singleResult();
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
		String candidateUser = "1";
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
		String userId = "1";
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
	
	@Test
	public void test(){
		Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
		//查询任务的候选人
		List<IdentityLink> list = taskService.getIdentityLinksForTask(task.getId());
		IdentityLink candidateObj = null;
		for (IdentityLink l : list) {
			if(l.getGroupId() != null){
				candidateObj = l;
				break;
			}
		}
		
		List<User> userList = identityService.createUserQuery().memberOfGroup(candidateObj.getGroupId()).list();
		
		for (User user : userList) {
			System.out.println("=============================");
			System.out.println("id: "+user.getId() + ", name :" + user.getFirstName() +user.getLastName());
		}
		
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
	 * 查询历史候选人列表
	 */
	@Test
	public void findHisCandidateList(){
//		HistoricTaskInstance histask = historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForTask("8");
		for (HistoricIdentityLink l : list) {
			System.out.println("groupId :" +l.getGroupId() + ",ProcessInstanceId :" +l.getProcessInstanceId() +",TaskId :" +l.getTaskId() +",Type :" +l.getType() +",UserId :" +l.getUserId());
		}
	}

}
