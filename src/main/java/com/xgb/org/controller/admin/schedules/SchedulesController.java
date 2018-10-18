package com.xgb.org.controller.admin.schedules;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xgb.org.common.DateUtils;
import com.xgb.org.domain.Admin;
import com.xgb.org.domain.Schedules;
import com.xgb.org.service.SchedulesService;

@Controller
@RequestMapping("schedule")
public class SchedulesController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private SchedulesService schedulesService;
	
	@GetMapping("index")
	public String index(HttpServletRequest request) {
		System.err.println("admin/schedules/index");
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "admin/schedules/index";
	}
	
	
	/**
	 * 保存/更新
	 * @param art
	 * @return
	 */
	@GetMapping("")
	@ResponseBody
	public String update(Schedules bean,HttpServletRequest request) {
		System.out.println("");
		String result = "0";
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		try {
			if(bean != null) {
				Integer id;
				if(bean.getId() != null) {
					if(bean.getId() != 0) {
						id = bean.getId();
						schedulesService.updateService(bean);
					}
					
				}else {
					id = null;
					bean = new Schedules(id, "title", "typese", 1, 0, "runTime", "createTime", "updateTime", 0, "adminId", null);
					bean.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					bean.setAdminId(admin.getId());
					result = schedulesService.saveService(bean) + "";
					
				}
			}else {
				Integer id;
				id = null;
				bean = new Schedules(id, "title", "typese", 1, 0, "runTime", "createTime", "updateTime", 0, "adminId", null);
				bean.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
				bean.setAdminId(admin.getId());
				result = schedulesService.saveService(bean) + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(Integer id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = schedulesService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
