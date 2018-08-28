package com.xgb.org.controller.admin.label;

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
import com.xgb.org.domain.Label;
import com.xgb.org.service.LabelService;

@Controller
@RequestMapping("label")
public class LabelController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private LabelService labelService;
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		System.out.println("path: " + path);
		request.setAttribute("app_path", path);
		return "admin/label/list";
	}
	
	
	@ResponseBody
	@GetMapping("/lists")
	public String lists(Integer offset,Integer pageSize,String search) {
		if(offset == null || pageSize ==null) { return "Parameter Error"; }
		String json = null; //返回的数据
		try {
			int total = labelService.getCountService(search);
			BootStrapPager bootStrapPager = new BootStrapPager(offset, pageSize, total);
			List<Label> list = labelService.getListService(bootStrapPager.getPageNum(), pageSize,search);
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
		Label bean = new Label("0", "",  999);
		try {
			bean = labelService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id) {
		Label bean = new Label("0", "",  999);
		try {
			if(id != null && !"0".equals(id)) {
				bean = labelService.getBeanByIdService(id);
			}
			request.setAttribute("label", bean);
			
			System.out.println("toUpdate: lable " + bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/label/update";
	}
	
	
	/**
	 * 保存/更新
	 * @param art
	 * @return
	 */
	@PostMapping("")
	public String update(Label bean) {
		try {
			if(bean != null) {
				if(!"0".equals(bean.getId())) {
					labelService.updateService(bean);
				}else {
					bean.setId(StringUtils.getUUID());
					labelService.saveService(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/label/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = labelService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
