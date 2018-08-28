package com.xgb.org.controller.admin.system;

import java.util.ArrayList;
import java.util.Date;
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

import com.xgb.org.common.DateUtils;
import com.xgb.org.common.JsonUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.SystemMeun;
import com.xgb.org.service.SystemMeunService;

@Controller
@RequestMapping("menu")
public class MenuController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired
	private SystemMeunService systemMeunService;
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request,Integer lord) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		try {
			List<SystemMeun> list = systemMeunService.getMenuTreeService();
			List<SystemMeun> listAll = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				listAll.add(list.get(i));
				if(list.get(i).getChildrens() != null) {
					if(list.get(i).getChildrens().size() > 0) {
						listAll.addAll(list.get(i).getChildrens());
					}
				}
			}
			request.setAttribute("app_path", path);
			request.setAttribute("list", list);
			request.setAttribute("listAll", listAll);
			request.setAttribute("lord", lord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/menu/list";
	}
	
	@ResponseBody
	@GetMapping("/lists1")
	public String lists() {
		String json = null; //返回的数据
		try {
			List<SystemMeun> list = systemMeunService.getMenuTreeService();
			json = StringUtils.removeBracket(JsonUtils.toJSONString(list));
			System.err.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}	
/*	@ResponseBody
	@GetMapping("/lists")
	public String lists() {
		String json = null; //返回的数据
		try {
			List<SystemMeun> list = systemMeunService.getMenuTreeService();
			List<VMenu> listVo = new ArrayList<>();
			if(list != null) {
				if(list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						SystemMeun menu = list.get(i);
						if(menu != null) {
							List<VMenu> vmenuChildrens = new ArrayList<>();
							if(menu.getChildrens() != null) {
								if(menu.getChildrens().size() > 0) {
									for (int j = 0; j < menu.getChildrens().size(); j++) {
										SystemMeun menuTemp = menu.getChildrens().get(j);
										VMenu vmenuTemp = new VMenu(menuTemp.getName(), menuTemp.getUrl(), String.valueOf(menuTemp.getSort()));
										vmenuChildrens.add(vmenuTemp);
									}
									
								}
							}
							VMenu vmenu = new VMenu(menu.getName(), menu.getUrl(), String.valueOf(menu.getSort()));
							vmenu.setNodes(vmenuChildrens);
							listVo.add(vmenu);
						}
						
					}
				}
			}
			
			json = JsonUtils.toJSONString(listVo);
			System.err.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}*/
	
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") String id) {
		SystemMeun baen = new SystemMeun("0", "", "", "", "", "", 1, 999, "0", "fa fa-sliders", null);
		try {
			baen = systemMeunService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id,String parentId) {
		SystemMeun baen = new SystemMeun("0", "", "", "", "", "",1, 999, "0", "fa fa-sliders",null);
		try {
			if(id != null && !"0".equals(id)) {
				baen = systemMeunService.getBeanByIdService(id);
			}else {
				baen.setParentId(parentId);
			}
			request.setAttribute("menu", baen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/menu/update";
	}
	
	
	/**
	 * 保存/更新
	 * @param role
	 * @return
	 */
	@PostMapping("")
	public String update(SystemMeun bean) {
		try {
			if(bean != null) {
				if(!"0".equals(bean.getId())) {
					bean.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					systemMeunService.updateService(bean);
				}else {
					String id = StringUtils.getGenerateRandNumber(2) + new Date().getTime();
					bean.setId(id);
					bean.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					systemMeunService.saveService(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/menu/list?lord=1";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = systemMeunService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
