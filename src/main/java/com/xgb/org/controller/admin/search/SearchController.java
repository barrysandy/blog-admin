package com.xgb.org.controller.admin.search;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.Art;
import com.xgb.org.service.ArtService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired private ArtService artService;
	
	@Value("${app.pageSize}")
	private Integer pageSize;
	
	@GetMapping("list")
	public String index(HttpServletRequest request,String search) {
		try {
			List<Art> list = artService.getListService(0, pageSize, search);
			if(search != null && !"".equals(search)) { list = getSeachList(list,search,"red"); }
			request.setAttribute("list", list);
			request.setAttribute("search", search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/search/index";
	}
	
	@GetMapping("")
	public String search(HttpServletRequest request,String search) {
		//Init
		if(search == null) { search = ""; }
		try {
			List<Art> list = artService.getListService(0, pageSize, search);
			if(search != null && !"".equals(search)) { list = getSeachList(list,search,"red"); }
			request.setAttribute("list", list);
			request.setAttribute("search", search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search/index2";
	}
	
	public static List<Art> getSeachList(List<Art> list,String seach,String color){
		if(list != null) {
			if(list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String title = list.get(i).getTitle();
					if(title != null && !"".equals(title)) {
						title = StringUtils.setStrColor(title,seach,color);
					}
					
					String descM = list.get(i).getDescM();
					if(descM != null && !"".equals(descM)) {
						descM = StringUtils.setStrColor(descM,seach,color);
					}
					
					list.get(i).setTitle(title);
					list.get(i).setDescM(descM);
				}
			}
		}
		return list;
	}

}
