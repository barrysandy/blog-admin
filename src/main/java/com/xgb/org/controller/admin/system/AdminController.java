package com.xgb.org.controller.admin.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xgb.org.common.BootStrapPager;
import com.xgb.org.common.JsonUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.Admin;
import com.xgb.org.service.AdminService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired
	private AdminService adminService;
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "/admin/admin/list";
	}
	
	
	@ResponseBody
	@GetMapping("/lists")
	public String lists(Integer offset,Integer pageSize,String search) {
		if(offset == null || pageSize ==null) { return "Parameter Error"; }
		String json = null; //返回的数据
		try {
			int total = adminService.getCountService(search);
			BootStrapPager bootStrapPager = new BootStrapPager(offset, pageSize, total);
			List<Admin> list = adminService.getListService(bootStrapPager.getPageNum(), pageSize,search);
			bootStrapPager.setRows(list);
			bootStrapPager.setSearch(search);
			json = StringUtils.removeBracket(JsonUtils.toJSONString(bootStrapPager));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") String id) {
		Admin admin = new Admin("0","","","","");
		try {
			admin = adminService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id) {
		Admin admin = new Admin("0","","","","");
		try {
			if(id != null && !"0".equals(id)) {
				admin = adminService.getBeanByIdService(id);
			}
			request.setAttribute("admin", admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/admin/admin/update";
	}
	
	
	/**
	 * 保存/更新用户
	 * @param admin
	 * @return
	 */
	@PostMapping("")
	public String update(Admin admin) {
		try {
			if(admin != null) {
				if(!"0".equals(admin.getId())) {
					adminService.updateService(admin);
				}else {
					admin.setId(StringUtils.getUUID());
					adminService.saveService(admin);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = adminService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
