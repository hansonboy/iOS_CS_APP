package com.hansonboy.Servelet;

import java.beans.Beans;

import org.apache.commons.io.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hansonboy.JSON.JSONTools;
import com.hansonboy.MySQL.Bean2SqlTools;
import com.hansonboy.MySQL.MySQLTools;
import com.hansonboy.MySQL.Sql2BeanTools;
import com.hansonboy.Utils.StringUtil;
import com.hansonboy.javaBean.Person;


/**
 * Servlet implementation class HttpServer
 */
public class HttpServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HttpServer() {
        super();
        // TODO Auto-generated constructor stub
		Bean2SqlTools.exampleTest();
		JSONTools.exampleTest();
		MySQLTools.exampleTest();
		Sql2BeanTools.exampleTest();
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String beanString = "com.hansonboy.javaBean.Person";
		String idString = request.getParameter("id");
		try {
			Person person = (Person)Sql2BeanTools.genBean_From_Sql_Id(beanString, Integer.parseInt(idString));	
//			response.getWriter().println("I come from Server"); 
			response.getWriter().print(JSONTools.createJSONString("Person", person));
		} catch (Exception e) {
			// TODO: handle exception
		System.out.println("HttpServer.doGet()");
		response.setContentType("text/html;charset=utf-8");
		response.sendError(400, "id参数不得为空");
		e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
