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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
