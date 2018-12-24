package com.genpact.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.UserService;
import com.genpact.activiti.utils.Constant;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="user/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="user/login",method=RequestMethod.POST)
	public String login(String username,String password,Model model,HttpServletRequest request){
		User user = userService.findByNameAndPassword(username, password);
		if(null == user){
			model.addAttribute("message", "用户名或者密码错误");
			return "login";
		}else{
			request.getSession().setAttribute(Constant.LOGIN_USER, user);
			return "index";
		}
	}
	
	
	@RequestMapping(value="user/index",method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="user/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(Constant.LOGIN_USER);
		return "redirect:login";
	}
}
