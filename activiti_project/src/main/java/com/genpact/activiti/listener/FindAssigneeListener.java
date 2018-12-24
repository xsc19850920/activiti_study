package com.genpact.activiti.listener;

import javax.servlet.http.HttpSession;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.UserService;
import com.genpact.activiti.utils.Constant;

public class FindAssigneeListener implements TaskListener{
	
	private static final long serialVersionUID = 1L;
	
	public void notify(DelegateTask delegateTask) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(); 
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		User loginUser =(User)session.getAttribute(Constant.LOGIN_USER);
		UserService userService =(UserService)wac.getBean("userService");
		loginUser = userService.findOne(loginUser.getId());
		delegateTask.setAssignee(loginUser.getManager().getId()+"");
	}

}
