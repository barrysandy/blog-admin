package com.xgb.org.controller.admin.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xgb.org.common.StringUtils;

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
    
    @Value("${web.imagesSavePath}")
    private String WEB_IMG_SAVE_PATH;
    
	
	/**
	 * URL upload/upImage
	 * 上传图片，并将其保存在数据库中，如果上传的图片保存了表单，则更新状态 
	 * @param file 上传的文件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upImage", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public String upImage(MultipartFile file){
		try {
			long startTime = System.currentTimeMillis();
	        //String filename = file.getOriginalFilename();
			String fileType = file.getOriginalFilename().split("\\.")[1];
			String filename = "T_" + String.valueOf(new Date().getTime()) + 
					"R_" + StringUtils.getGenerateRandNumber(9) + "." + fileType;
	        //写入本地磁盘
	        InputStream is = file.getInputStream();  
	        byte[] bs = new byte[1024];  
	        int len;
	        String diskPath = WEB_IMG_SAVE_PATH + filename;
	        String url = APP_PATH + "/images/" + filename;
	        OutputStream os = new FileOutputStream(new File(diskPath));  
	        while ((len = is.read(bs)) != -1) {  
	            os.write(bs, 0, len);  
	        }  
	        os.close();  
	        is.close();  
	        long  endTime = System.currentTimeMillis();
	        System.out.println("运行时间：" + String.valueOf(endTime-startTime) + "ms");
	        return "{ \"data\":\"ok\" , \"url\":\""+ url +"\" , \"diskPath\":\""+  diskPath + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"data\":\"error\" , \"url\":\"0\"}";
		}
    }
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "admin/upload/index";
	}
	
}
