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
import com.xgb.org.domain.SystemUpdateLog;
import com.xgb.org.service.SystemUpdateLogService;

@Controller
@RequestMapping("updateLog")
public class SystemUpdateLogController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private SystemUpdateLogService systemUpdateLogService;
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "admin/updatelog/list";
	}
	
	
	@ResponseBody
	@GetMapping("/lists")
	public String lists(Integer offset,Integer pageSize) {
		if(offset == null || pageSize ==null) { return "Parameter Error"; }
		String json = null; //返回的数据
		try {
			int total = systemUpdateLogService.getCountService();
			BootStrapPager bootStrapPager = new BootStrapPager(offset, pageSize, total);
			List<SystemUpdateLog> list = systemUpdateLogService.getListService((bootStrapPager.getPageNum() - 1)/pageSize, pageSize);
			bootStrapPager.setRows(list);
			bootStrapPager.setSearch("");
			json = StringUtils.removeBracket(JsonUtils.toJSONString(bootStrapPager));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") String id) {
		SystemUpdateLog bean = new SystemUpdateLog("0", "", "", "", "", 0);
		try {
			bean = systemUpdateLogService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id) {
		SystemUpdateLog bean = new SystemUpdateLog("0", "", "", "", "", 0);
		try {
			if(id != null && !"0".equals(id)) {
				bean = systemUpdateLogService.getBeanByIdService(id);
			}
			request.setAttribute("bean", bean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/updatelog/update";
	}
	
	
	/**
	 * 保存/更新
	 * @param art
	 * @return
	 */
	@PostMapping("")
	public String update(SystemUpdateLog bean) {
		try {
			if(bean != null) {
				String id;
				if(!"0".equals(bean.getId())) {
					id = bean.getId();
					SystemUpdateLog old = systemUpdateLogService.getBeanByIdService(bean.getId());
					bean.setCreateTime(old.getCreateTime());
					bean.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					systemUpdateLogService.updateService(bean);
				}else {
					id = StringUtils.getUUID();
					bean.setId(id);
					bean.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					systemUpdateLogService.saveService(bean);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/updatelog/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = systemUpdateLogService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
