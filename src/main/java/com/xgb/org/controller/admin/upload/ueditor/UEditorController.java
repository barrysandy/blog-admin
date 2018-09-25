package com.xgb.org.controller.admin.upload.ueditor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;

@Controller

//@RequestMapping("ueditor")
public class UEditorController {
	@RequestMapping("/")
	private String showPage() {
		return "index";
	}

	@RequestMapping(value = "/config")
	public void config(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		request.setCharacterEncoding( "utf-8" );
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			
			String exec = new ActionEnter(request, rootPath).exec();
			System.err.println("exec: " + exec + " rootPath: " + rootPath);
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
