package com.hansonboy.MySQL;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Sql2BeanTools {
	public static Object genBean_From_Sql_Id(String bean, int id) {
		try {
			Class<?> clz = Class.forName(bean);
			Object object = clz.getConstructor(
					Bean2SqlTools.getBeanTypeList(bean)).newInstance(
					MySQLTools.getSingletonMySQLTools().queryById(bean, id));
			return object;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static void exampleTest() {
		String beanString = "com.hansonboy.javaBean.Person";
		int id = 1002;
		System.out.println("Sql2BeanTools.generateClassFromBean"
				+ Sql2BeanTools.genBean_From_Sql_Id(beanString, id));
	}

}
