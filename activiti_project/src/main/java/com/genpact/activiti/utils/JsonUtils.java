package com.genpact.activiti.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	public static void pintJsonObject(HttpServletResponse response,Object object){
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.toJSONString(object));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void pintJsonArray(HttpServletResponse response,Object object){
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONArray.toJSONString(object));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void success(HttpServletResponse response){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "success");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONArray.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void error(HttpServletResponse response){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "error");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONArray.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
