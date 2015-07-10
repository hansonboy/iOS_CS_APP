package com.hansonboy.MySQL;

import java.lang.reflect.Field;  
import java.util.ArrayList;  
import java.util.List;  

import javax.activation.FileDataSource;

import com.hansonboy.Utils.StringUtil;

public class Bean2SqlTools {
	 public static String getBeanName(String bean){  
	        try {  
	            Class clz = Class.forName(bean);  
	            String clzStr = clz.toString();  
	            //得到类名  
	            String beanName = clzStr.substring(clzStr.lastIndexOf(".")+1).toLowerCase();  
	            return beanName;  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	            return "";  
	        }  
	    }  
//	    返回属性类型及名字
	    public static List<String> getBeanPropertyList(String bean){  
	        try {  
	            Class clz = Class.forName(bean);  
	            Field[] strs = clz.getDeclaredFields();  
	            List<String> propertyList = new ArrayList<String>();  
	            for (int i = 0; i < strs.length; i++) {  
	                String protype = strs[i].getType().toString();  
	                propertyList.add(protype.substring(protype.lastIndexOf(".")+1)+"`"+strs[i].getName());  
	            }  
	            return propertyList;  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
//	    返回属性类型
//	    public static String getBeanAttriList(String bean){
//	    	try {
//				Class clz = Class.forName(bean);
//				Field[] strs = clz.getDeclaredFields();
//				StringBuffer sBuffer = new StringBuffer();
//				for(int i = 0;i < strs.length;i++){
//					String protype = strs[i].getType().toString();
//					sBuffer.append(protype+",");
//				}
//				sBuffer.deleteCharAt(sBuffer.toString().lastIndexOf(","));
//				return sBuffer.toString();
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				return null;
//			}
//	    }
//	    返回属性类型
	    public static Class<?>[]  getBeanTypeList(String bean){
	    	try {
				Class clz = Class.forName(bean);
				Field [] strs = clz.getDeclaredFields();
				Class<?> typeList[] = new Class<?>[strs.length];
				for(int i = 0;i< strs.length;i++){
					typeList[i] = strs[i].getType();
				}
				return typeList;
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	    }
//	    返回属性名字
	    public static String getBeanFilesList(String bean){  
	        try {  
	            Class clz = Class.forName(bean);  
	            Field[] strs = clz.getDeclaredFields();  
	            StringBuffer sb = new StringBuffer();  
	            for (int i = 0; i < strs.length; i++) {  
	                String protype = strs[i].getType().toString();  
	                if (!strs[i].getName().equals("tableName")&&!strs[i].getType().equals("List")) {  
	                   sb.append(strs[i].getName()+",");  
	                }  
	            }  
	            sb.deleteCharAt(sb.toString().lastIndexOf(","));  
	            return sb.toString();  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    } 
	    public static List<String> getBeanAttriNameList(String bean){
	    	List<String> list = new ArrayList<>();
	    	try {
				Class clz = Class.forName(bean);
				Field [] attriFields = clz.getDeclaredFields();
				for(int i = 0; i<attriFields.length;i++){
					list.add(attriFields[i].getName());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	return list;
	    }
	      
	    /** 
	     * 生成建表語句 
	     * @param bean 
	     * @return 
	     */  
	    public static String genCreateTableSql(String bean){  
	        List<String> beanPropertyList =  getBeanPropertyList(bean);  
	        StringBuffer sb = new StringBuffer("create table "+getBeanName(bean)+"(\n");  
	        for (String string : beanPropertyList) {  
	            String[] propertys = string.split("`");  
	            if (!propertys[1].equals("tableName")&&!propertys[1].equals("param")&&!propertys[0].equals("List")) {  
	                if (propertys[1].equals("id")) {  
	                    sb.append("   id bigint primary key auto_increment,\n");  
	                } else {  
	                    if (propertys[0].equals("int")) {  
	                        sb.append("   " + propertys[1] + " int default 0 comment '',\n");  
	                    } else if (propertys[0].equals("String")) {  
	                        sb.append("   " + propertys[1] + " varchar(2000) default '' comment '',\n");  
	                    } else if (propertys[0].equals("double")) {  
	                        sb.append("   " + propertys[1] + " double(10,2) default 0.0 comment '',\n");  
	                    } else if (propertys[0].equals("Date")) {  
	                        sb.append("   " + propertys[1] + " datetime comment '',\n");  
	                    }  
	                }  
	            }  
	        }  
	        sb.append(")");  
	        sb.deleteCharAt(sb.lastIndexOf(","));  
	        return sb.toString();  
	    }  
	      
	    /** 
	     * 生成查询语句 
	     * @param bean 
	     * @return 
	     */  
	    
	    public static String genSelectAllSql(String bean){    
	        return "select *  from \n "+getBeanName(bean)+";";  
	    }  
	    public static String genDropTableSql(String bean){
	    	String beanNameString = getBeanName(bean);
	    	return "drop table "+beanNameString+";";
	    }
	    public static String genDeleteDataSql(String bean){
	    	String beanString = getBeanName(bean);
	    	return "delete from "+beanString+";";
	    }
	    /** 
	     * 生成插入语句 
	     * @param bean 
	     * @return 
	     */  
	    public static String genInsertSql(String bean){  
	        String filesList =  getBeanFilesList(bean);  
	        int fl = StringUtil.getCountSubStr(filesList,",")+1;  
	        String wenhao = "";  
	        for (int i = 0; i < fl; i++) {  
	            if(i==fl-1){  
	                wenhao = wenhao+"?";  
	            }else{  
	                wenhao = wenhao+"?,";  
	            }  
	        }  
	        return "insert into "+getBeanName(bean)+"("+filesList+") values("+wenhao+")";  
	    }  
	    public static String generateQuerySql(String bean,int id) {
			return "select * from "+getBeanName(bean)+" where id = "+id+";";
		}
	    public static void exampleTest(){
	    	String beanName =  "com.hansonboy.javaBean.Person";
			System.out.println(Bean2SqlTools
					.getBeanName(beanName));
			System.out.println(Bean2SqlTools
					.getBeanPropertyList(beanName));
//			System.out.println(Bean2SqlTools.getBeanAttriList(beanName));
			System.out.println(Bean2SqlTools.getBeanTypeList(beanName));
			System.out.println(Bean2SqlTools.getBeanAttriNameList(beanName));
			System.out.println(Bean2SqlTools
					.getBeanFilesList(beanName));
			System.out.println(Bean2SqlTools
					.genCreateTableSql(beanName));
			System.out.println(Bean2SqlTools
					.genSelectAllSql(beanName));
			System.out.println(Bean2SqlTools
					.genInsertSql(beanName));
			int id = 1002;
			System.out.println(Bean2SqlTools.generateQuerySql(beanName, id));
			
	    }
}

  
  

