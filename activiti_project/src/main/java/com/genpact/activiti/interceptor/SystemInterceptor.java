package com.genpact.activiti.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SystemInterceptor implements  HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//简写
//		String url  = "/activiti_project/user/login";
//		if(!request.getRequestURI().equals(url)){
//			if(null  == request.getSession().getAttribute(Constant.LOGIN_USER)){
//				response.sendRedirect("http://localhost:8181/activiti_project");
//			}
//		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
}
