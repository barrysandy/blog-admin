package com.xgb.org.controller.admin.art;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.xgb.org.domain.Admin;
import com.xgb.org.domain.Art;
import com.xgb.org.domain.Label;
import com.xgb.org.service.ArtService;
import com.xgb.org.service.LabelService;
import com.xgb.org.vo.VLabel;

@Controller
@RequestMapping("art")
public class ArtController {
	
	@Value("${app.path}")
	private String APP_PATH;
	
	@Autowired private ArtService artService;
	@Autowired private LabelService labelService;
	
	
	
	@GetMapping("/list")
	public String list(HttpServletRequest request) {
		String path = APP_PATH + request.getServletContext().getContextPath();
		request.setAttribute("app_path", path);
		return "admin/art/list";
	}
	
	
	@ResponseBody
	@GetMapping("/lists")
	public String lists(Integer offset,Integer pageSize,String search) {
		if(offset == null || pageSize ==null) { return "Parameter Error"; }
		String json = null; //返回的数据
		try {
			int total = artService.getCountService(search);
			BootStrapPager bootStrapPager = new BootStrapPager(offset, pageSize, total);
			List<Art> list = artService.getListService(bootStrapPager.getPageNum(), pageSize,search);
			bootStrapPager.setRows(list);
			bootStrapPager.setSearch(search);
			json = StringUtils.removeBracket(JsonUtils.toJSONString(bootStrapPager));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") String id,HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		Art art = new Art("0", "", "", "", "", "", null,0, "", "", "", 0 , 1,admin.getId());
		try {
			art = artService.getBeanByIdService(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@GetMapping("/toUpdate")
	public String toUpdate(HttpServletRequest request,String id) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		Art art = new Art("0", "", "", "", "", "", null,0, "", DateUtils.getStringDate(DateUtils.simpleMinute), "", 0 , 1,admin.getId());
		try {
			List<Label> labels = labelService.getListAll();
			List<VLabel> vLabels = new ArrayList<>();
			Iterator<Label> iterator = labels.iterator();
			if(labels != null) {
				if(labels.size() > 0) {
					while (iterator.hasNext()) {
						Label bean = iterator.next();
						VLabel vBean = new VLabel(bean.getId(), bean.getName(), bean.getSort(), 0);
						int exits = labelService.existenceData(bean.getId(), id);
						vBean.setSelectOn(exits);
						vLabels.add(vBean);
					}
				}
			}
			
			
			if(id != null && !"0".equals(id)) {
				art = artService.getBeanByIdService(id);
			}
			request.setAttribute("art", art);
			request.setAttribute("vLabels", vLabels);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/art/update";
	}
	
	
	/**
	 * 保存/更新
	 * @param art
	 * @return
	 */
	@PostMapping("")
	public String update(Art art,HttpServletRequest request,String diskPath) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		String[] lids = request.getParameterValues("lid");
		try {
			if(art != null) {
				String id;
				if(!"0".equals(art.getId())) {
					id = art.getId();
					Art oldArt = artService.getBeanByIdService(art.getId());
					art.setCreateTime(oldArt.getCreateTime());
					art.setUpdateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					art.setViews(oldArt.getViews());
					if(art.getAdminId() == null || "".equals(art.getAdminId())) {
						if(oldArt.getAdminId() == null || "".equals(oldArt.getAdminId())) {
							art.setAdminId(admin.getId());
						}else {
							art.setAdminId(oldArt.getAdminId());
						}
					}else System.err.println("222222");
					artService.updateService(art);
				}else {
					id = StringUtils.getUUID();
					art.setId(id);
					art.setCreateTime(DateUtils.getStringDate(DateUtils.simpleMinute));
					art.setAdminId(admin.getId());
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
		return "redirect:/art/list";
	}
	
	@GetMapping("/del")
	@ResponseBody
	public int del(String id) {
		int result = 0;
		try {
			if(id != null && !"0".equals(id)) {
				result = artService.deleteByIdService(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
