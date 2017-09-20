package com.sam.controller.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sam.util.RedisJava;

public class BaseAction {

	public HttpSession httpSession;
	
	public Hashtable<String, Object> form = new Hashtable<String, Object>();
	public Map<String, Object> redisMap = new HashMap<String, Object>();
	
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected SimpleDateFormat ymd = new SimpleDateFormat("yyyy/MM/dd");
	protected SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static String SUCCESS = "success";
	public static String ERROR = "error";
	public boolean isMsg = false;
	public String isRedirect = "0";
	
	public Hashtable<String, Object> getForm() {
		return form;
	}

	public void setForm(Hashtable<String, Object> form) {
		this.form = form;
	}
	
	public String setMap(HttpServletRequest request, String data){
		if(request == null){
			return "request error";
		}
		request.getParameter("action");
		Map<String, String[]> map = request.getParameterMap();
		if(map == null){
			return "map error";
		}
		if(map.get("action") == null || map.get("action").length == 0){
			return "action error";
		}
		form.clear();
		for(Map.Entry<String, String[]> entry : map.entrySet()){
			if(entry.getKey() == null){
				return entry.getValue()+" error";
			}
			if(entry.getValue() == null){
				return entry.getKey()+" error";
			}
			form.put(entry.getKey(), entry.getValue()[0]);
		}
		putToForm(data);
		return SUCCESS;
	}
	
	private void sessionToForm(HttpServletRequest request){
		RedisJava.openRedis(redisMap);
	}
	
	public String addMessage(String msg){
		isMsg = true;
		return msg;
	}
	
	public String redirect(String str) {
		isRedirect = "2";
		return str;
	}
	
	public String redirect(String str, boolean isTop) {
		isRedirect = "3";
		return str;
	}
	
	public Map<String, Object> toJson(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		String status = "1";
		if(obj != null){
			status = isRedirect;
		}
		map.put("time", sdt.format(new Date()));
		map.put("status", status);
		if (isMsg) {
			map.put("msg", obj);
		}else {
			map.put("data", obj);
		}
		return map;
	}
	
	public void putToForm(String record){
		Hashtable<String, Object> dataForm = JSON.parseObject(record, new TypeReference<Hashtable<String, Object>>(){});
		form.putAll(dataForm);
	}
	
	public void checkRequired(String... params) throws Exception {
		for(int i = 0; i < params.length; i++){
			if(form.get(params[i]) == null){
				throw new Exception(params[i]);
			}
			if("".equals(form.get(params[i]))){
				throw new Exception(params[i]);
			}
		}
	}
	
}
