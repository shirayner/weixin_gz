package com.ray.weixin.gz.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.message.ReplyMessageService;



/**
 * Servlet implementation class WeiXinServlet
 */         
public class WeiXinServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(WeiXinServlet.class);



	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public WeiXinServlet() {
		// TODO Auto-generated constructor stub
	}

	//1.接收  回调模式  的请求
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		logger.info("get--------------");
		//一、校验URL
		//1.准备校验参数
		// 微信加密签名  
		String msgSignature = request.getParameter("signature");  
		// 时间戳  
		String timeStamp = request.getParameter("timestamp");  
		// 随机数  
		String nonce = request.getParameter("nonce");  
		// 随机字符串  
		String echoStr = request.getParameter("echostr");  

		PrintWriter out=null;
		try {
			//2.校验url
			//2.1 创建加解密类
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Env.TOKEN,Env.ENCODING_AES_KEY,Env.APP_ID);

			//2.2进行url校验
			//不抛异常就说明校验成功
			String sEchoStr= wxcpt.verifyUrl_WXGZ(msgSignature, Env.TOKEN, timeStamp, nonce,echoStr);

			//2.3若校验成功，则原样返回 echoStr

			out = response.getWriter(); 
			out.print(sEchoStr);  




		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();  
				out = null;                       //释放资源
			}
		}
	}

	//2.接收  微信消息和事件  的请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("post--------------");
		//1.将请求、响应的编码均设置为UTF-8（防止中文乱码）  
		request.setCharacterEncoding("UTF-8");  
		response.setCharacterEncoding("UTF-8");  

		//2.调用消息业务类接收消息、处理消息  
		String respMessage = ReplyMessageService.reply(request);  

		//3.响应消息  
		PrintWriter out = response.getWriter();  
		out.print(respMessage);  
		out.close(); 



	}

}
