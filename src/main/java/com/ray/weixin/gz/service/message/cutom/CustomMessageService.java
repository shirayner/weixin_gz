package com.ray.weixin.gz.service.message.cutom;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.model.message.custom.Text;
import com.ray.weixin.gz.model.message.custom.TextMessage;
import com.ray.weixin.gz.model.message.response.CustomMessage;
import com.ray.weixin.gz.util.HttpHelper;
import com.ray.weixin.gz.util.MessageUtil;

/**@desc  :新版客服功能—客服消息
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:19:43
 */
public class CustomMessageService {
	private static final Logger log= LogManager.getLogger(CustomMessageService.class);

	//2.会话控制——创建会话
	private static final String CREATE_CONVERSATION_URL="https://api.weixin.qq.com/customservice/kfsession/create?access_token=ACCESS_TOKEN";
	//3.会话控制——关闭会话
	private static final String CLOSE_CONVERSATION_URL="https://api.weixin.qq.com/customservice/kfsession/close?access_token=ACCESS_TOKEN";
	//4.主动发消息
	private static final String SEND_CUSTOM_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * @desc ：1.将消息转发到客服
	 *  
	 * @param toUserName    接收方帐号（收到的OpenID）
	 * @param fromUserName  开发者微信号
	 * @return 
	 *   String   转发客服消息
	 *   
	 */
	public static String  transferToCustom(String toUserName,String fromUserName){

		CustomMessage customMessage=new CustomMessage();
		customMessage.setToUserName(toUserName);
		customMessage.setFromUserName(fromUserName);
		customMessage.setCreateTime(new Date().getTime());  
		customMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_CUSTOM);

		return  MessageUtil.customMessageToXml(customMessage);
	}

	/**
	 * @desc ：2.会话控制——创建会话
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount    完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param openId       粉丝的openid
	 *   void
	 */
	public static void createConversation(String accessToken,String kfAccount,String openId ) {
		//1.封装请求JSON
		JSONObject postData=new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("openid", openId);

		//2.拼接请求url
		String url=CREATE_CONVERSATION_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject=null;
		try {
			jsonObject = HttpHelper.doPost(url, postData);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//4.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//5.成功创建会话
				} else {
					log.info("创建会话成功！");
				} 
			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}


	/**
	 * @desc ：3.会话控制——关闭会话
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount    完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param openId       粉丝的openid
	 *   void
	 */
	public static void closeConversation(String accessToken,String kfAccount,String openId ) {
		//1.封装请求JSON
		JSONObject postData=new JSONObject();
		postData.put("kf_account", kfAccount);
		postData.put("openid", openId);

		//2.拼接请求url
		String url=CLOSE_CONVERSATION_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject=null;
		try {
			jsonObject = HttpHelper.doPost(url, postData);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//4.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//5.成功关闭会话
				} else {
					log.info("关闭会话成功！");
				} 
			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @desc ：4.主动发消息
	 *  
	 * @param accessToken  接口调用凭证
	 * @param touser   接收方帐号（收到的OpenID）
	 * @param content   文本消息内容
	 *   void
	 */
	public static void sendTextMessage(String accessToken,String touser,String content ) {
		//1.封装请求JSON
		TextMessage textMessage=new TextMessage();
		textMessage.setTouser(touser);
		textMessage.setMsgtype("text");
		Text text=new Text();
		text.setContent(content);
		textMessage.setText(text);

		Object postData=JSON.toJSON(textMessage);

		//2.拼接请求url
		String url=SEND_CUSTOM_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject=null;
		try {
			jsonObject = HttpHelper.doPost(url, postData);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//4.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//5.成功发送消息
				} else {
					log.info("发送消息成功！");
				} 
			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
