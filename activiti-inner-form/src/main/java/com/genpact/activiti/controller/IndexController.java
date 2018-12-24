package com.genpact.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genpact.activiti.constant.Constant;


@Controller
public class IndexController{
	@Autowired private RepositoryService repositoryService;
	@Autowired private RuntimeService runtimeService;
	@Autowired private TaskService taskService;
	@Autowired private HistoryService historyService;
	@Autowired private ManagementService managementService;
	@Autowired private IdentityService identityService;
	@Autowired private FormService formService;
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(String id,String password,HttpSession session){
		boolean  flag = identityService.checkPassword(id, password);
		if (flag) {

            // 查看用户是否存在
            User user = identityService.createUserQuery().userId(id).singleResult();
            session.setAttribute(Constant.LOGIN_USER, user);
	      /*
	       * 读取角色
	       */
            List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
            session.setAttribute("groups", groupList);

            String[] groupNames = new String[groupList.size()];
            for (int i = 0; i < groupNames.length; i++) {
                groupNames[i] = groupList.get(i).getName();
            }
            session.setAttribute("groupNames", ArrayUtils.toString(groupNames));

            return "redirect:/user/index";
        } else {
            return "redirect:/login.jsp?error=true";
        }
	}
	
	
	@RequestMapping(value="user/index",method=RequestMethod.GET)
	public String index(){
		 return "/main/index";
	}
	
	@RequestMapping(value="user/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(Constant.LOGIN_USER);
		return "redirect:login";
	}
	
}
