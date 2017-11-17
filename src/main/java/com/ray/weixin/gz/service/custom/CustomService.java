package com.ray.weixin.gz.service.custom;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.util.HttpHelper;


/**@desc  : 新版客服功能-客服管理
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:19:43
 */
public class CustomService {
	private static final Logger log= LogManager.getLogger(CustomService.class);

	//1.添加客服帐号，每个公众号最多添加10个客服账号
	private static final String ADD_CUSTOM_URL="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN"; 
	//2.获取客服基本信息
	private static final String LIST_CUSTOM_URL="https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN"; 
	//3.获取在线客服状态信息
	private static final String LIST_ONLINE_CUSTOM_URL="https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN"; 
	//4.邀请绑定客服帐号
	private static final String INVITE_WORKER_URL="https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=ACCESS_TOKEN"; 
	//5.修改客服信息
	private static final String UPDATE_CUSTOM_URL="https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN"; 
	//6.上传客服头像
	private static final String UPLOAD_HEADIMG_URL="https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT"; 
	//7.删除客服帐号
	private static final String DELETE_CUSTOM_URL="https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT"; 






	/**
	 * @desc ：1.添加客服消息
	 *  
	 * @param accessToken    有效凭证
	 * @param kf_account  完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，
	 *            必须是英文、数字字符或者下划线，后缀为公众号微信号，长度不超过30个字符
	 * @param nickName    客服昵称，最长16个字
	 * @throws Exception void
	 */
	public static void addCustom(String accessToken,String kf_account,String nickName) {
		//1.准备请求Json
		JSONObject postData=new JSONObject();
		postData.put("kf_account", kf_account);
		postData.put("nickname", nickName);

		//2.准备请求url
		String url=ADD_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);

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

					//5.成功添加客服
				} else {
					log.info("客服添加成功！");
				} 
			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	/**
	 * @desc ：2.获取客服基本信息
	 *  
	 * @param accessToken   接口调用凭证
	 * @return 
	 *   JSONObject
	 *   kf_account	        完整客服帐号，格式为：帐号前缀@公众号微信号
	 *   kf_nick	        客服昵称
	 *   kf_id	                 客服编号
	 *   kf_headimgurl  客服头像
	 *   kf_wx          如果客服帐号已绑定了客服人员微信号，则此处显示微信号
	 *   invite_wx	        如果客服帐号尚未绑定微信号，但是已经发起了一个绑定邀请，则此处显示绑定邀请的微信号
	 *   invite_expire_time	如果客服帐号尚未绑定微信号，但是已经发起过一个绑定邀请，邀请的过期时间，为unix 时间戳
	 *   invite_status  邀请的状态，有等待确认“waiting”，被拒绝“rejected”，过期“expired”
	 *     
	 */
	public static JSONObject listCustom(String accessToken) {
		//1.准备请求url
		String url=LIST_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起POST请求，获取返回结果
		JSONObject jsonObject=null;
		JSONObject returnJson=null;
		try {
			jsonObject = HttpHelper.doGet(url);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//3.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//4.成功添加客服
				} else {
					returnJson=jsonObject;
				} 
			}   

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnJson;
	} 


	/**
	 * @desc ：3.获取在线客服状态信息
	 *  
	 * @param accessToken 接口调用凭证
	 * @return 
	 *   JSONObject
	 *   kf_account	完整客服帐号，格式为：帐号前缀@公众号微信号
	 *   status	客服在线状态，目前为：1、web 在线
	 *   kf_id	客服编号
	 *   accepted_case	客服当前正在接待的会话数
	 *   
	 */
	public static JSONObject listOnlineCustom(String accessToken) {
		//1.准备请求url
		String url=LIST_ONLINE_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起POST请求，获取返回结果
		JSONObject jsonObject=null;
		JSONObject returnJson=null;
		try {
			jsonObject = HttpHelper.doGet(url);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//3.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//4.成功添加客服
				} else {
					returnJson=jsonObject;
				} 
			}   

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnJson;
	} 

	/**
	 * @desc ：4.邀请绑定客服帐号
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kf_account   完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param invite_wx    接收绑定邀请的客服微信号

	 *   void
	 */
	public static void inviteWorker(String accessToken,String kf_account,String invite_wx) {
		//1.拼装请求JSON
		JSONObject postData=new JSONObject();
		postData.put("kf_account", kf_account);
		postData.put("invite_wx", invite_wx);

		//2.准备请求url
		String url=INVITE_WORKER_URL.replace("ACCESS_TOKEN", accessToken);

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

					//4.成功邀请绑定客服帐号
				} else {
					log.info("成功邀请绑定客服帐号");
				} 
			}   

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 


	/**
	 * @desc ： 5.修改客服信息
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kf_account   完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param nickName     客服昵称，最长16个字
	 *   void
	 */
	public static void updateCustom(String accessToken,String kf_account,String nickName) {
		//1.准备请求Json
		JSONObject postData=new JSONObject();
		postData.put("kf_account", kf_account);
		postData.put("nickname", nickName);

		//2.准备请求url
		String url=UPDATE_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);

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

					//5.成功添加客服
				} else {
					log.info("客服修改成功！");
				} 
			}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 


	/**
	 * @desc ：6.上传客服头像
	 *  
	 * @param accessToken   接口调用凭证
	 * @param fileDir  图片路径
	 *   void
	 */
	public static void uploadHeadImg(String accessToken,String kfAccount,String fileDir) {
		//1.创建本地文件
		File file=new File(fileDir);

		//2.准备请求url
		String url=UPLOAD_HEADIMG_URL.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", kfAccount);

		//3.发起HTTP请求，上传头像
		try {
			JSONObject jsonObject = HttpHelper.uploadMedia(url, file);
			log.info("JsonObject:"+jsonObject.toJSONString());

			//4.解析结果
			if (jsonObject != null) {

				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg);

					//5.成功上传客服头像
				} else {
					log.info("客服头像上传成功！");
				} 

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	/**
	 * @desc ：7.删除客服帐号
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount  客服账号
	 *   void
	 */
	public static void deleteCustom(String accessToken, String kfAccount) {
		//1.准备好请求url
		String url=DELETE_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", kfAccount);

		//2.发起GET请求，执行删除操作
		//2.发起POST请求，获取返回结果
		JSONObject jsonObject=null;

		try {
			jsonObject = HttpHelper.doGet(url);
			log.info("jsonObject:"+jsonObject.toString());

			if (null != jsonObject) {  
				//3.错误消息处理
				if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  

					int errCode = jsonObject.getInteger("errcode");
					String errMsg = jsonObject.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 

					//4.成功删除客服
				} else {
					log.info("成功删除客服");
				} 
			}   

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 




}
