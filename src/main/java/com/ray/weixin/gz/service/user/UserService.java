package com.ray.weixin.gz.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.util.HttpHelper;

/**@desc  : 用户管理
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午5:37:08
 */
public class UserService {
	private static final Logger logger = LogManager.getLogger(UserService.class);

	//1. 获取用户基本信息(UnionID机制)
	private static final String GET_USERINFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//2.1 获取用户列表(从指定的next_openid的下一个开始获取)
	private static final String LIST_USER_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	//2.2 获取所有用户列表
	private static final String LIST_ALLUSER_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	//3. 拉取用户信息(需scope为 snsapi_userinfo)——网页授权时                                  
	private static final String GET_SNS_USERINFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";




	/**
	 * @desc ：1.获取用户基本信息(UnionID机制)
	 *  
	 * @param accessToken  有效凭证
	 * @param openId 普通用户的标识，对当前公众号唯一
	 * 
	 * @return  用户详细信息
	 * subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 * openid	用户的标识，对当前公众号唯一
	 * nickname	用户的昵称
	 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 * city	用户所在城市
	 * country	用户所在国家
	 * province	用户所在省份
	 * language	用户的语言，简体中文为zh_CN
	 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
	 *              用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 * subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 * remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 * groupid	用户所在的分组ID（兼容旧的用户分组接口）
	 * tagid_list	用户被打上的标签ID列表
	 * 
	 * @throws Exception JSONObject
	 */
	public static JSONObject getUserInfo(String accessToken,String openId ) throws Exception {
		//1.获取请求url
		String url=GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject= jsonObject;
			} 
		}   

		return returnJsonObject;
	}

	/** 2.1  获取用户列表(从指定的next_openid的下一个开始获取)
	 * 
	 * @desc ：一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
	 *  
	 * @param accessToken 调用接口凭证
	 * @param nextOpenId  从指定的next_openid的下一个开始获取
	 * 
	 * @return
	 * total	关注该公众账号的总用户数
	 * count	拉取的OPENID个数，最大值为10000
	 * data	列表数据，OPENID的列表
	 * next_openid	拉取列表的最后一个用户的OPENID
	 *   
	 * @throws Exception JSONObject
	 */
	public static JSONObject listUser(String accessToken,String nextOpenId ) throws Exception {
		//1.获取请求url
		String url=LIST_USER_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject= jsonObject;
			} 
		}   

		return returnJsonObject;
	}

	/**2.2获取所有用户列表
	 * 
	 * @desc ：一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
	 *  
	 * @param accessToken  调用接口凭证
	 * 
	 * @return
	 * total	关注该公众账号的总用户数
	 * count	拉取的OPENID个数，最大值为10000
	 * data	列表数据，OPENID的列表
	 * next_openid	拉取列表的最后一个用户的OPENID
	 * 
	 * @throws Exception JSONObject
	 */
	public static JSONObject listAllUser(String accessToken) throws Exception {
		//1.获取请求url
		String url=LIST_ALLUSER_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject= jsonObject;
			} 
		}   

		return returnJsonObject;
	}


	/**
	 * @desc ：3. 拉取用户信息(需scope为 snsapi_userinfo)——网页授权时      
	 *  
	 * @param accessToken  网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId 用户的唯一标识
	 * 
	 * @return
	 * openid	用户的唯一标识
	 * nickname	用户昵称
	 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 * province	用户个人资料填写的省份
	 * city	普通用户个人资料填写的城市
	 * country	国家，如中国为CN
	 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 * privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 * 
	 * @throws Exception JSONObject
	 */
	public static JSONObject getSNSUserInfo(String accessToken,String openId ) throws Exception {
		//1.获取请求url
		String url=GET_SNS_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject= jsonObject;
			} 
		}   

		return returnJsonObject;


	}



}
