package com.xgb.org.controller.admin.index;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xgb.org.domain.Admin;
import com.xgb.org.domain.SystemMeun;
import com.xgb.org.service.AdminService;
import com.xgb.org.service.SystemMeunService;



@Controller
public class IndexController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SystemMeunService systemMeunService;
	
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/login";
	}
	
	@PostMapping("/loginIn")
	public String loginIn(HttpServletRequest request,@RequestParam("name") String name,@RequestParam("password") String password) {
		try {
			Admin admin = adminService.getBeanByNameAndPassword(name, password);
			if(admin != null) {
				List<SystemMeun> sessionMenus = systemMeunService.getMenuTreeService();
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);
				session.setAttribute("sessionMenus", sessionMenus);
				return "redirect:/index";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "login";
	}
	
	
	@GetMapping("/loginOut")
	public String loginOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.setAttribute("admin", null);
			session.setAttribute("sessionMenus", null);
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			List<SystemMeun> sessionMenus = (List<SystemMeun>) session.getAttribute("sessionMenus");
			request.setAttribute("sessionMenus", sessionMenus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/index";
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request) {
		try {
			request.setAttribute("1", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/welcome";
	}
}
