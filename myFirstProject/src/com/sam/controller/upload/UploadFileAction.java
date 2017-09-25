package com.sam.controller.upload;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.offer.util.FileUtils;
import com.sam.controller.common.BaseAction;

public class UploadFileAction extends BaseAction {

	private MultipartFile[] fileList;
	private String uploadPath;
	
	@ResponseBody
	@RequestMapping(value = "/uploadFile", produces = "application/json")
	public Map<String, Object> doAction(HttpServletRequest request, @RequestParam("uploadFile") MultipartFile[] uploadFile, @RequestBody String data) {
		try {
			setMap(request, data);
			Object forward = null;
			String action = form.get("action").toString();
			if(uploadFile == null || uploadFile.length == 0){
				return null;
			}
			fileList = uploadFile;
			if(form.get("type") == null || "".equals(form.get("type").toString().trim())){
				return null;
			}
			uploadPath = request.getSession().getServletContext().getRealPath("/");
			uploadPath += form.get("type").toString();
			
			File file = new File(uploadPath);
			if(!file.exists()){
				file.mkdirs();
			}
			
			if("upload".equalsIgnoreCase(action)) forward = upload();
			
			return toJson(forward);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toJson(SUCCESS);
	}
	
	private Object upload(){
		try {
			for(int i = 0; i < fileList.length; i++){
				MultipartFile file = fileList[i];
				String fileName = String.valueOf(System.currentTimeMillis());
				String endless = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String filePath = uploadPath + File.separator + fileName + endless;
				FileUtils.saveFile(file, filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
