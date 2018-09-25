package com.xgb.org.controller.admin.material;

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
import com.xgb.org.common.PageUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.Art;
import com.xgb.org.domain.Material;
import com.xgb.org.service.ArtService;
import com.xgb.org.service.LabelService;
import com.xgb.org.service.MaterialService;

@Controller
@RequestMapping("material")
public class MaterialController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private ArtService artService;
	@Autowired private LabelService labelService;
	@Autowired private MaterialService materialService;
	
	@Value("${app.pageSize}")
	private Integer pageSize;
	
	/**
	 * 查看资源
	 * @param request
	 * @param search
	 * @param type 为空或"" 
	 * 			image，图片 类型  jpg jpeg png gif 
	 * 			video，视频 类型  mp4 avi...
	 * 			doc，文档 类型  pdf docx...
	 * 			music，音乐 类型  mp3...
	 * 			voice，语音 类型  ...
	 * @param cpage
	 * @return
	 */
	@GetMapping("/list")
	public String index(HttpServletRequest request,String search,String type,Integer cpage) {
		//Init
		if(search == null) { search = ""; }
		if(cpage == null) { cpage = 1; }
		if(type == null || "".equals(type)) { type = "image"; }
		try {
			int totalCurrent = materialService.getCountService(type, search);
			int totalPage = PageUtils.totalPage(totalCurrent, pageSize);
			int index = (cpage -1) * pageSize;
			List<Material> list = materialService.getListService(index, pageSize, type, search);
			request.setAttribute("list", list);
			request.setAttribute("search", search);
			request.setAttribute("type", type);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("cpage", cpage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/material/list_" + type;
	}
	
	
	
	@GetMapping("get")
	public String get(String materialId,HttpServletRequest req) {
		Material material = new Material();
		try {
			material = materialService.getBeanByMaterialIdService(materialId);
			req.setAttribute("material", material);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/material/info";
	}
	
	
	
	/**
	 * 保存/更新
	 * @param material
	 * @return
	 */
	@PostMapping("")
	public String update(Art art,HttpServletRequest request,String diskPath) {
		String[] lids = request.getParameterValues("lid");
		try {
			if(art != null) {
				String id = StringUtils.getUUID();
				if(!"0".equals(art.getId())) {
					id = art.getId();
					Art oldArt = artService.getBeanByIdService(art.getId());
					art.setCreateTime(oldArt.getCreateTime());
					art.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					art.setViews(oldArt.getViews());
					artService.updateService(art);
				}else {
					art.setId(id);
					art.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					artService.saveService(art,diskPath);
					
				}
				
				if(lids != null) {
					//清空该文章的标签
					labelService.deleteByArtIdService(id);
					//添加标签
					for (int i = 0; i < lids.length; i++) {
						labelService.saveArtLabelService(StringUtils.getUUID(), id, lids[i]);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/material/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String materialId) {
		int result = 0;
		try {
			if(materialId != null && !"".equals(materialId)) {
				result = materialService.deleteByMaterialIdService(materialId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
