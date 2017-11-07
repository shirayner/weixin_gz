package com.ray.weixin.gz.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;


/**
 * 微信公众号 Token、配置工具类
 * @desc  : AccessToken、Jsticket 、Jsapi
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午5:00:25
 */
public class AuthHelper {
	private static final Logger logger = LogManager.getLogger(AuthHelper.class);

	//1.获取access_token的接口地址,有效期为7200秒
	private static final String GET_ACCESSTOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; 
	//2.获取getJsapiTicket的接口地址,有效期为7200秒 
	private static final String GET_JSAPITICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"; 

	//3.通过code换取网页授权access_token
	private static final String GET_ACCESSTOKEN_BYCODE_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 


	/**
	 * @desc ：1.获取access_token 
	 *  
	 * @param appId  第三方用户唯一凭证
	 * @param appSecret  第三方用户唯一凭证密钥，即appsecret
	 * 
	 * @return
	 *      access_token	获取到的凭证
	 *      expires_in	凭证有效时间，单位：秒
	 * @throws Exception String
	 */
	public static String getAccessToken(String appId,String appSecret) throws Exception {
		//1.获取请求url
		String url=GET_ACCESSTOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		String accessToken="";  
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取accessToken
			}else {
				accessToken=jsonObject.getString("access_token");
			}  
		}  


		return accessToken;
	}


	/**
	 * @desc ：2.获取JsapiTicket
	 *  
	 * @param accessToken  有效凭证
	 * @return
	 * @throws Exception String
	 */
	public static String getJsapiTicket(String accessToken) throws Exception {
		//1.获取请求url
		String url=GET_JSAPITICKET_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		String jsapiTicket="";  
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取jsapiTicket
			}else {
				jsapiTicket=jsonObject.getString("ticket");
			}  
		}  


		return jsapiTicket;
	}

	/**
	 * @desc ： 3.通过code换取网页授权access_token
	 *  
	 * @param appId  第三方用户唯一凭证
	 * @param appSecret  第三方用户唯一凭证密钥，即appsecret
	 * @param Code  code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * 
	 * @return
	 * access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * expires_in	access_token接口调用凭证超时时间，单位（秒）
	 * refresh_token	用户刷新access_token
	 * openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	 * scope	用户授权的作用域，使用逗号（,）分隔
	 * 
	 * @throws Exception String
	 */
	public static JSONObject getAccessTokenByCode(String appId,String appSecret,String code) throws Exception {
		//1.获取请求url
		String url=GET_ACCESSTOKEN_BYCODE_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取accessToken
			}else {
				returnJsonObject=jsonObject;
			}  
		}  


		return returnJsonObject;
	}


	/**
	 * @desc ：4.获取前端jsapi需要的配置参数
	 *  
	 * @param request
	 * @return String
	 */
	public static String getJsapiConfig(HttpServletRequest request){  

		//1.准备好参与签名的字段
		//1.1 url
		/* 
		 *以http://localhost/test.do?a=b&c=d为例 
		 *request.getRequestURL的结果是http://localhost/test.do 
		 *request.getQueryString的返回值是a=b&c=d 
		 */  
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}

		//1.2 noncestr
		String nonceStr=UUID.randomUUID().toString();      //随机数
		//1.3 timestamp
		long timeStamp = System.currentTimeMillis() / 1000;     //时间戳参数  

		String signedUrl = url;

		String accessToken = null;
		String ticket = null;

		String signature = null;   	//签名


		try {  
			//1.4 jsapi_ticket
			accessToken=getAccessToken(Env.APP_ID, Env.APP_SECRET);  
			ticket=getJsapiTicket(accessToken);  

			//2.进行签名，获取signature
			signature=getSign(ticket,nonceStr,timeStamp,signedUrl);  


		} catch (Exception e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  

		logger.info("accessToken:"+accessToken);
		logger.info("ticket:"+ticket);
		logger.info("nonceStr:"+nonceStr);
		logger.info("timeStamp:"+timeStamp);
		logger.info("signedUrl:"+signedUrl);
		logger.info("signature:"+signature);
		logger.info("appId:"+Env.APP_ID);




		String configValue = "{signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
				+ timeStamp + "',appId:'" + Env.APP_ID + "'}";
		logger.info("configValue:"+configValue);

		return configValue;  
	}  


	/**
	 * @desc ： 3.生成签名的函数 
	 *  
	 * @param ticket jsticket
	 * @param nonceStr 随机串，自己定义
	 * @param timeStamp 生成签名用的时间戳 
	 * @param url 需要进行免登鉴权的页面地址，也就是执行dd.config的页面地址 
	 * @return
	 * @throws Exception String
	 */

	public static String getSign(String jsTicket, String nonceStr, Long timeStamp, String url) throws Exception {  
		String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;
		System.out.println(plainTex);
		try {  
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(plainTex.getBytes("UTF-8"));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception(e.getMessage());  
		} catch (UnsupportedEncodingException e) {  
			throw new Exception(e.getMessage());  
		}  
	}  

	//将bytes类型的数据转化为16进制类型  
	private static String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}







}

