package com.genpact.entity;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class PersonalTaskAssignee implements TaskListener {

	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		String taskName = delegateTask.getName();
		//模拟数据库查询
		if(taskName.equals("审批[组长]")){
			delegateTask.setAssignee("组长");
		}else if("审批[部门经理]".equals(taskName)){
			delegateTask.setAssignee("部门经理");
		}else if("审批[总经理]".equals(taskName)){
			delegateTask.setAssignee("总经理");
		}
		
		
		// 如果组任务
//		delegateTask.addCandidateUsers(candidateUsers);
		
	}

}
