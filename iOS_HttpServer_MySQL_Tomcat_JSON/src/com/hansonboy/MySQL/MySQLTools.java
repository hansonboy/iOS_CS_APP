package com.hansonboy.MySQL;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.tribes.io.ObjectReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.mysql.fabric.xmlrpc.base.Value;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class MySQLTools {
	private static MySQLTools singletonMySQLTools;
	Connection connection = null;

	private MySQLTools() {
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("success loading mysql driver");
		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("MySQLTools.MySQLTools() :error loading mysql driver");
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", null);
			System.out.println("Success connection to mysql");
		} catch (Exception e) {
			System.out.println("Fail to connect to mysql");
			e.printStackTrace();
		}
	}

	public synchronized static MySQLTools getSingletonMySQLTools() {
		if (singletonMySQLTools == null) {
			singletonMySQLTools = new MySQLTools();
		}
		return singletonMySQLTools;
	}

	// 查询

	public void queryAll(String beanNameString) {
		try {
			if (connection == null)
				System.out.println("connection= null");
			Statement statement = connection.createStatement();
			String sqlString = "select * from "
					+ Bean2SqlTools.getBeanName(beanNameString);
			ResultSet rs = statement.executeQuery(sqlString);
			while (rs.next()) {
				String[] attriStrings = Bean2SqlTools.getBeanFilesList(
						beanNameString).split("[,]");
				Class<?>[] typeList = Bean2SqlTools
						.getBeanTypeList(beanNameString);
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < typeList.length; i++) {
					Class<?> aClass = typeList[i];
					stringBuffer.append(attriStrings[i] + ":"
							+ rs.getObject(i + 1, aClass).toString() + " ");
				}
				System.out.println("queryAll"+stringBuffer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getResultSetCount(ResultSet rs) {
		int rowCount = 0;
		// 得到当前行号，也就是记录数
		try {
			rs.last(); // 移到最后一行
			rowCount = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowCount;

	}
	//只是解析了一条记录
	public static Object[] parsingResultSet(ResultSet rs, String beanNameString) {

		Object[] valueList = new Object[Bean2SqlTools.getBeanTypeList(beanNameString).length];
		try {
			while (rs.next()) {
				Class<?> typeList[] = Bean2SqlTools
						.getBeanTypeList(beanNameString);
				for (int i = 0; i < typeList.length; i++) {
					Class<?> aclass = typeList[i];
					valueList[i] = rs.getObject(i+1,aclass); 
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valueList;
	}

	public Object[] queryById(String beanNameString, int id) {
		if (connection == null) {
			System.out.println("connection = null");
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(Bean2SqlTools
					.generateQuerySql(beanNameString, id));
			return parsingResultSet(rSet, beanNameString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 增删表，增删数据
	public void executeUpdateSQLString(String sqlString) {
		try {
			if (connection == null)
				System.out
						.println("MySQLTools.executeUpdateSQLString():connection = null");
			Statement statement = connection.createStatement();
			statement.executeUpdate(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 从文件中读入要加入数据库表中的数据
	// todo: 文件中的数据每条为一行，以“,”划分列
	// beannameString 为javaBean name
	public void insert(String beanNameString, String pathname) {
		try {
			PreparedStatement Statement = connection
					.prepareStatement(Bean2SqlTools
							.genInsertSql(beanNameString));

			List<String> strings = FileUtils.readLines(new File(pathname));
//			System.out.println(strings);

			for (int i = 0; i < strings.size(); i++) {
				String string = strings.get(i);
				String[] strs = string.split("[ |,]");
//				System.out.println(strs);
				for (int j = 0; j < strs.length; j++) {
					Statement.setString(j + 1, strs[j]);
				}
				if (Statement.executeUpdate() == 0) {
					System.out.println("MySQLTools.insert():insert error");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void exampleTest() {
		String beanNameString = "com.hansonboy.javaBean.Person";
		String filePathString = "G:\\NetDevForWin\\workplace\\iOS_Dev_GitHub\\iOS_CS_APP\\iOS_HttpServer_MySQL_Tomcat_JSON\\persons.txt";
		MySQLTools.getSingletonMySQLTools().executeUpdateSQLString(
				Bean2SqlTools.genDropTableSql(beanNameString));
		MySQLTools.getSingletonMySQLTools().executeUpdateSQLString(
				Bean2SqlTools.genCreateTableSql(beanNameString));
		MySQLTools.getSingletonMySQLTools().queryAll(beanNameString);
		MySQLTools.getSingletonMySQLTools().insert(beanNameString,
				filePathString);
		MySQLTools.getSingletonMySQLTools().queryAll(beanNameString);
		int id = 1002;
		System.out.println("queryById:"+MySQLTools.getSingletonMySQLTools().queryById(
				beanNameString, id));
	}
}
