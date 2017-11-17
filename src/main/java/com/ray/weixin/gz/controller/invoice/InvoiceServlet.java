package com.ray.weixin.gz.controller.invoice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ray.mytest.StringTest;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.invoice.InvoiceService;
import com.ray.weixin.gz.util.AuthHelper;

/**
 * Servlet implementation class InvoiceServlet
 */
public class InvoiceServlet extends HttpServlet {
	private static final Logger log = LogManager.getLogger(InvoiceServlet.class);

	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InvoiceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.将请求、响应的编码均设置为UTF-8（防止中文乱码）  
		request.setCharacterEncoding("UTF-8");  
		response.setCharacterEncoding("UTF-8"); 
		
		
		
		//2.接收请求参数
		//得到一个2层的转义字符串
		String invoiceListStr_escape=request.getParameter("invoiceListStr");
		System.out.println("invoiceListStr_escape:"+invoiceListStr_escape);


		//3.查询发票详细信息
		String accessToken=null;
		JSONArray InvoiceInfo=null;
		try {
			accessToken = AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
			InvoiceInfo=InvoiceService.listInvoiceInfo(accessToken, invoiceListStr_escape);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(InvoiceInfo.toJSONString());

		//4.将发票详细信息发送至前台	
		PrintWriter out = response.getWriter(); 
		out.print(InvoiceInfo.toJSONString());  
		out.flush();
		out.close();  
		out = null;  



	}

}
