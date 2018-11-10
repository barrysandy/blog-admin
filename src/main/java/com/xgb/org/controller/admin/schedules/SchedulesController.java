package com.xgb.org.controller.admin.schedules;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xgb.org.common.DateUtils;
import com.xgb.org.common.JsonUtils;
import com.xgb.org.common.Result;
import com.xgb.org.domain.Admin;
import com.xgb.org.domain.Schedules;
import com.xgb.org.service.SchedulesService;
import com.xgb.org.vo.VSchedules;

@Controller
@RequestMapping("schedule")
public class SchedulesController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private SchedulesService schedulesService;
	
	@GetMapping("index")
	public String index(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "admin/schedules/index";
	}
	
	
	@GetMapping("getCurrentMonthSchedules")
	@ResponseBody
	public Result getCurrentMonthSchedules(HttpServletRequest request) {
		String result = null;
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		String bTime = DateUtils.getBeginTime();
		String eTime = DateUtils.getEndTime();
		List<VSchedules> list = new ArrayList<>();
		try {
			list = schedulesService.getListService(bTime, eTime, "", admin.getId());
			result = JsonUtils.toJSONString(list);
		}catch(Exception e){
			e.printStackTrace();//统一的异常处理
			return new Result<String>(2,"error",e.getMessage());
		}
		return new Result<String>(1,"success","success",result);
	}
	

	
	@GetMapping("del")
	public String del(HttpServletRequest request,Integer id) {
		try {
			schedulesService.deleteByIdService(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/schedule/index";
	}
	
	@GetMapping("add")
	public String add(HttpServletRequest request,String date) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		Schedules bean = new Schedules(0, "", "", 1, 0, "", "", "", 0, "");
		request.setAttribute("bean", bean);
		request.setAttribute("path", path);
		return "admin/schedules/addOrUpdate";
	}
	
	@GetMapping("update")
	public String update(HttpServletRequest request,Integer id) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		try {
			Schedules bean = schedulesService.getBeanByIdService(id);
			request.setAttribute("bean", bean);
			System.err.println(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("path", path);
		return "admin/schedules/addOrUpdate";
	}
	
	@PostMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,Schedules bean) {
		String result = "0";
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		try {
			if(bean.getId() == 0) {
				bean.setAdminId(admin.getId());
				bean.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
				schedulesService.saveService(bean);
				result = "1";
			}else {
				bean.setAdminId(admin.getId());
				bean.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
				schedulesService.updateService(bean);
				result = "1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
	
/*	@GetMapping("/del")
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
	}*/
	

}
