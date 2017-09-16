package com.sam.controller.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sam.controller.common.BaseAction;

@Controller
public class LoginAction extends BaseAction {
	
	@ResponseBody
	@RequestMapping(value = "/login", produces = "application/json")
	public Map<String, Object> doAction(HttpServletRequest request, @RequestBody String data) {
		try {
			setMap(request, data);
			Object forward = null;
			String action = form.get("action").toString();
			
			if("register".equalsIgnoreCase(action)) forward = register();
			
			return toJson(forward);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toJson(SUCCESS);
	}

	public Object register() {
		try {
			if(form.get("type") == null){
				return ERROR;
			}
			if(!"1".equals(form.get("type")) && !"2".equals(form.get("type"))){
				return ERROR;
			}
			if("1".equals(form.get("type"))){
//				checkRequired("email", "goJobId", "cityId", "password");
			}
			if("2".equals(form.get("type"))){
//				checkRequired("companyName", "userName", "jobId", "email", "phone", "password");
			}
//			userService.save(form);
		} catch (Exception e) {
			e.printStackTrace();
			return addMessage(e.getMessage()+"不能为空!");
		}
		return redirect("login/login.jsp",true);
	}
}
