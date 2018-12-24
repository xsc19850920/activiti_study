package com.genpact.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genpact.activiti.entity.LeaveBill;
import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.ActivitiService;
import com.genpact.activiti.service.LeaveBillService;
import com.genpact.activiti.utils.Constant;

@Controller
public class LeaveBillController {
	@Autowired
	private LeaveBillService leaveBillService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@RequestMapping(value="leavebill/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request,Model model){
		User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		List<LeaveBill> list = leaveBillService.findByUserId(user.getId());
		model.addAttribute("list", list);
		return "leavebill/list";
	}

	@RequestMapping(value="leavebill/opt",method=RequestMethod.GET)
	public String opt(Long leaveBillId,Model model){
		if(leaveBillId != null){
			LeaveBill bdObj = leaveBillService.findById(leaveBillId);
			model.addAttribute("obj", bdObj);
			model.addAttribute("opt", "view");
		}else{
			model.addAttribute("opt", "add");
		}
		return "leavebill/opt";
	}
	
	@RequestMapping(value="leavebill/opt",method=RequestMethod.POST)
	public String opt(LeaveBill leaveBill ,HttpSession session,Model model){
		boolean result= false;
		if(leaveBill.getId() == null){
			leaveBill.setUser((User)session.getAttribute(Constant.LOGIN_USER));
			leaveBill.setState(0);
			leaveBill.setDeleteFlag(0);
			result = leaveBillService.save(leaveBill);
		}else{
			LeaveBill bdObj = leaveBillService.findById(leaveBill.getId());
			User user = bdObj.getUser();
			BeanUtils.copyProperties(leaveBill, bdObj);
			bdObj.setUser(user);
			result = leaveBillService.save(bdObj);
		}
		
		model.addAttribute("status", result);
		return "redirect:list";
	}
	
	
	@RequestMapping(value="leavebill/del/{id}",method=RequestMethod.GET)
	public String del(@PathVariable("id") Long id,Model model ){
		boolean result = leaveBillService.delete(id);
		model.addAttribute("status", result);
		return "redirect:../list";
	}
	
	
}
