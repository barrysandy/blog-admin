package com.xgb.org.controller.admin.index;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xgb.org.domain.Admin;
import com.xgb.org.service.AdminService;



@Controller
public class IndexController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/admin/login";
	}
	
	@PostMapping("/loginIn")
	public String loginIn(HttpServletRequest request,@RequestParam("name") String name,@RequestParam("password") String password) {
		try {
			System.out.println("name: " + name);
			System.out.println("password: " + password);
			Admin admin = adminService.getBeanByNameAndPassword(name, password);
			if(admin != null) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);
				return "redirect:/index";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/login";
	}
	
	
	@GetMapping("/loginOut")
	public String loginOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		try {
			request.setAttribute("1", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/admin/index";
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request) {
		try {
			request.setAttribute("1", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/admin/welcome";
	}
}
