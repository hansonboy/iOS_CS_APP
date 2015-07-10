package com.hansonboy.JSON;

import java.util.List;

import com.hansonboy.MySQL.Bean2SqlTools;
import com.hansonboy.MySQL.Sql2BeanTools;

import net.sf.json.JSONObject;

public class JSONTools {
	public static String createJSONString(String key,Object value){
		String rt = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(key, value);
			rt =  jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}
		return rt;
	}
	public static Object[] genObjectsFromJsonString(String jsonString){
		
		return null;
	}
	public static Object genBeanFromJsonString(String bean,String jsonString){
		
		Class<?> []typeArray = Bean2SqlTools.getBeanTypeList(bean);
		Object [] objects = JSONTools.genObjectsFromJsonString(jsonString);
		
		
		return null;
	}
	public static void exampleTest(){
		System.out.println(JSONTools.createJSONString("name", "wangjianwei"));
	}
}
