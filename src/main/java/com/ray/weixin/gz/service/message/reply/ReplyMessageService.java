package com.ray.weixin.gz.service.message.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.model.message.response.TextMessage;
import com.ray.weixin.gz.service.message.cutom.CustomMessageService;
import com.ray.weixin.gz.util.MessageUtil;


/**@desc  : 发送消息-被动回复消息业务类
 * Passive reply message
 * @author: shirayner
 * @date  : 2017年10月31日 下午12:24:41
 */
public class ReplyMessageService {
	private static final Logger logger = LogManager.getLogger(ReplyMessageService.class);

	/**
	 * @desc ：1.回复消息
	 *  
	 * @param request
	 * @return 
	 *   String 回复消息的加密xml字符串
	 */
	public static String reply( HttpServletRequest request ) {
		String parms=JSON.toJSONString(request.getParameterMap()); 
		logger.info("parms:"+parms);
		//1.解密：从request中获取消息明文
		String xmlMsg=decryptMsg(request);
		logger.info(xmlMsg);

		//2.获取回复消息(明文)
		String replyMsg = getReplyMsg( xmlMsg);

		//3.根据消息加密方式判断是否加密
		String timeStamp = request.getParameter("timestamp");   // 时间戳    
		String nonce = request.getParameter("nonce");          // 随机数  
		String encryptType=request.getParameter("encrypt_type");

		//3.1 安全模式-加密：将回复消息加密
		if(null!=encryptType) {
			WXBizMsgCrypt wxcpt=null;
			try {
				wxcpt = new WXBizMsgCrypt(Env.TOKEN,Env.ENCODING_AES_KEY,Env.APP_ID);
				replyMsg=wxcpt.EncryptMsg(replyMsg, timeStamp, nonce);

			} catch (AesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		return replyMsg;
	}

	/**
	 * @desc ：2.从request中获取消息明文
	 *  从request中获取加密消息，将其解密并返回
	 * @param request
	 * @return String   消息明文
	 */
	public static String decryptMsg(HttpServletRequest request) {

		String postData="";   // 密文，对应POST请求的数据
		String result="";     // 明文，解密之后的结果

		String msgSignature = request.getParameter("msg_signature"); // 微信加密签名  
		String timeStamp = request.getParameter("timestamp");   // 时间戳    
		String nonce = request.getParameter("nonce");          // 随机数  
		String encryptType=request.getParameter("encrypt_type");

		try {
			//1.获取加密的请求消息：使用输入流获得加密请求消息postData
			ServletInputStream in = request.getInputStream();
			BufferedReader reader =new BufferedReader(new InputStreamReader(in));  

			String tempStr="";   //作为输出字符串的临时串，用于判断是否读取完毕  
			while(null!=(tempStr=reader.readLine())){  
				postData+=tempStr;  
			}  

			logger.info("postData:"+postData);

			//2.获取消息明文：对加密的请求消息进行解密获得明文 
			if(null!=encryptType) {
				logger.info("安全模式：消息被加密");
				WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Env.TOKEN,Env.ENCODING_AES_KEY,Env.APP_ID);
				result=wxcpt.DecryptMsg(msgSignature, timeStamp, nonce, postData);
			}else {
				logger.info("明文模式");
				result=postData;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (AesException e) {
			e.printStackTrace();
		}  

		return result;
	}

	/**
	 * @desc ：获取回复消息
	 *  
	 * @param request
	 * @return String  返回加密后的回复消息
	 */
	public static String getReplyMsg(String xmlMsg){
		String replyMsg = null; 

		try {
			//2.解析微信发来的请求,解析xml字符串
			Map<String, String> requestMap= MessageUtil.parseXml(xmlMsg);	

			//3.获取请求参数
			//3.1 企业微信CorpID  
			String fromUserName = requestMap.get("FromUserName");  
			//3.2 成员UserID
			String toUserName = requestMap.get("ToUserName");  
			//3.3 消息类型与事件 
			String msgType = requestMap.get("MsgType"); 
			String eventType = requestMap.get("Event");  
			String eventKey = requestMap.get("EventKey"); 
			logger.info("fromUserName:"+fromUserName);
			logger.info("toUserName:"+toUserName);
			logger.info("msgType:"+msgType);
			logger.info("Event:"+eventType+"  eventKey:"+eventKey);

			//4.组装 回复文本消息  
			//4.1 若不是文本消息，则不使用客服消息
			if(!msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				TextMessage textMessage = new TextMessage();  
				textMessage.setToUserName(fromUserName);  
				textMessage.setFromUserName(toUserName);  
				textMessage.setCreateTime(new Date().getTime());  
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
				//4.1.获取回复消息的内容 ：消息的分类处理
				String replyContent=getReplyContentByMsgType(msgType, eventType, eventKey);
				textMessage.setContent(replyContent);  
				System.out.println("replyContent："+replyContent);

				//5.获取xml字符串： 将（被动回复消息型的）文本消息对象 转成  xml字符串
				replyMsg = MessageUtil.textMessageToXml(textMessage); 
			
			
				//4.2若是文本消息，则使用客服消息
			}else{
                //将消息转发到客服	
				replyMsg = CustomMessageService.transferToCustom(fromUserName, toUserName); 

			}



		} catch (Exception e) {
			e.printStackTrace();
		}  

		return replyMsg;
	}


	/**
	 * @desc ：3.处理消息：根据消息类型获取回复内容
	 *  
	 * @param msgType 消息类型
	 * @return String 回复内容
	 */
	public static  String getReplyContentByMsgType(String msgType,String eventType,String eventKey){
		String replyContent="";
		//1.文本消息  
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
			replyContent = "您发送的是文本消息！";  

		}  
		//2.图片消息  
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
			replyContent = "您发送的是图片消息！";  
		}  
		//3.地理位置消息  
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { 

			replyContent = "您发送的是地理位置消息 ！";  
		}  
		//4.链接消息  
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
			replyContent = "您发送的是链接消息！";  
		}  
		//5.音频消息  
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
			replyContent = "您发送的是音频消息！";  
		}
		//6.事件推送  
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { 
			replyContent=getReplyContentByEventType(eventType, eventKey);
		}
		//7.请求异常
		else {
			replyContent="请求处理异常，请稍候尝试！";
		}  

		return replyContent;
	}

	/**
	 * @desc ：5.处理消息：根据事件类型获取回复内容
	 *  
	 * @param eventType  事件类型
	 * @param eventKey  事件key值
	 * @return 
	 *   String
	 */
	public static String getReplyContentByEventType(String eventType,String eventKey){

		String respContent="";
		// 订阅  
		if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
			respContent = "欢迎关注！";  
		}  
		// 取消订阅  
		else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
			// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
		} 
		//上报地理位置事件
		else if(eventType.equals("LOCATION")){

		}
		// 自定义菜单点击事件  
		else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  

			if (eventKey.equals("12")) {  

			} else if (eventKey.equals("13")) {  
				respContent = "周边搜索菜单项被点击！";  
			} else if (eventKey.equals("14")) {  
				respContent = "历史上的今天菜单项被点击！";  
			} else if (eventKey.equals("21")) {  
				respContent = "歌曲点播菜单项被点击！";  
			} else if (eventKey.equals("22")) {  

				respContent = "经典游戏菜单项被点击！";  
			} else if (eventKey.equals("23")) {  
				respContent = "美女电台菜单项被点击！";  
			} else if (eventKey.equals("24")) {  
				respContent = "人脸识别菜单项被点击！";  
			} else if (eventKey.equals("25")) {  
				respContent = "聊天唠嗑菜单项被点击！";  
			} else if (eventKey.equals("31")) {  
				respContent = "Q友圈菜单项被点击！";  
			} else if (eventKey.equals("32")) {  
				respContent = "电影排行榜菜单项被点击！";  
			} else if (eventKey.equals("33")) {  
				respContent = "幽默笑话菜单项被点击！";  
			}  
		} 
		return respContent;
	}  








}
