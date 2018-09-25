package com.xgb.org.controller.admin.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xgb.org.common.DateUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.domain.Material;
import com.xgb.org.service.MaterialService;

/**
 * UploadController
 * <p>Title: UploadController 文件上传控制器</p>
 * <p>Description: </p>
 *  	
 * @author XuGongBin
 * @version 1.0.0
 * @date 2018/8/21 10:00
 */
@Controller
@RequestMapping("upload")
public class UploadController {
	
    @Value("${app.path.admin}")
    private String APP_PATH;
    
    @Value("${app.image}")
    private String IMAGE;
    
    
    @Value("${web.imagesSavePath}")
    private String WEB_IMG_SAVE_PATH;
    
	@Autowired private MaterialService materialService;
	
	/**
	 * URL upload/upImage
	 * 上传图片，并将其保存在数据库中，如果上传的图片保存了表单，则更新状态 
	 * @param file 上传的文件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upImage", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public String upImage(MultipartFile file){
		long startTime = System.currentTimeMillis();
		//String filename = file.getOriginalFilename();
		String fileType = "";
		String filename = "";
		String diskPath = "";
		String url = "";
		try {
			String folder = DateUtils.getStringDate(DateUtils.dtDay);
			fileType = file.getOriginalFilename().split("\\.")[1];
			filename = "/T_" + String.valueOf(new Date().getTime()) + 
					"R_" + StringUtils.getGenerateRandNumber(9) + "." + fileType;
	        //写入本地磁盘
	        InputStream is = file.getInputStream();  
	        byte[] bs = new byte[1024];  
	        int len;
	        diskPath = WEB_IMG_SAVE_PATH + folder + filename;
	        url = APP_PATH + "/" + IMAGE +"/" + folder + filename;
	        File newFile = new File(WEB_IMG_SAVE_PATH + folder);  
	        if(!newFile.exists()){  
	        	newFile.mkdirs();  
	        } 
	        OutputStream os = new FileOutputStream(new File(diskPath));
	        while ((len = is.read(bs)) != -1) {  
	            os.write(bs, 0, len);  
	        }  
	        os.close();  
	        is.close();  
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"data\":\"error\" , \"url\":\"0\"}";
		}
		long  endTime = System.currentTimeMillis();
        
        //记录数据库
        Material material = new Material(StringUtils.getUUID(), filename, url, diskPath, fileType, 1,
        		DateUtils.getStringDate(DateUtils.simpleMinute),"");
        try {
			materialService.saveService(material);
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"data\":\"error\" , \"url\":\"0\"}";
		}
        //System.out.println("运行时间：" + String.valueOf(endTime-startTime) + "ms");
        return "{ \"data\":\"ok\" , \"url\":\""+ url +"\" , \"diskPath\":\""+  diskPath + "\"}";
    }
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "admin/upload/index";
	}
	
}
