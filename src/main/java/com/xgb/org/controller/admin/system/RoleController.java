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
import com.xgb.org.common.DateUtils;
import com.xgb.org.common.JsonUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.SystemRole;
import com.xgb.org.service.SystemRoleService;

@Controller
@RequestMapping("role")
public class RoleController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired
	private SystemRoleService systemRoleService;
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "/admin/role/list";
	}
	
	
	@ResponseBody
	@GetMapping("/lists")
	public String lists(Integer offset,Integer pageSize,String search) {
		if(offset == null || pageSize ==null) { return "Parameter Error"; }
		String json = null; //返回的数据
		try {
			int total = systemRoleService.getCountService(search);
			BootStrapPager bootStrapPager = new BootStrapPager(offset, pageSize, total);
			List<SystemRole> list = systemRoleService.getListService(bootStrapPager.getPageNum(), pageSize,search);
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
		SystemRole baen = new SystemRole("0", "", "", "", "", 0);
		try {
			baen = systemRoleService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id) {
		SystemRole baen = new SystemRole("0", "", "", "", "", 0);
		try {
			if(id != null && !"0".equals(id)) {
				baen = systemRoleService.getBeanByIdService(id);
			}
			request.setAttribute("role", baen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/admin/role/update";
	}
	
	
	/**
	 * 保存/更新
	 * @param role
	 * @return
	 */
	@PostMapping("")
	public String update(SystemRole role) {
		try {
			if(role != null) {
				if(!"0".equals(role.getId())) {
					systemRoleService.updateService(role);
					role.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
				}else {
					role.setId(StringUtils.getUUID());
					role.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					systemRoleService.saveService(role);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/role/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = systemRoleService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
