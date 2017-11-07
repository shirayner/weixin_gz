package com.ray.weixin.gz.service.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.util.HttpHelper;

/**@desc  : 发送消息-客服消息
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:19:43
 */
public class CustomMessageService {
	private static final Logger logger = LogManager.getLogger(CustomMessageService.class);

	//1.添加客服帐号，每个公众号最多添加10个客服账号
	private static final String ADD_CUSTOM_URL="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN"; 


	/**
	 * @desc ：1.添加客服消息
	 *  
	 * @param accessToken    有效凭证
	 * @param kf_account  完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，
	 *            必须是英文、数字字符或者下划线，后缀为公众号微信号，长度不超过30个字符
	 * @param nickName    客服昵称，最长16个字
	 * @throws Exception void
	 */
	public static void addCustom(String accessToken,String kf_account,String nickName) throws Exception {
		//1.准备请求Json
		Map<String,String> customInfoMap=new HashMap<String,String>();
		customInfoMap.put("kf_account", kf_account);
		customInfoMap.put("nickname", nickName);

		Object data=JSON.toJSON(customInfoMap);
		logger.info(data.toString());
		//2.准备请求url
		String url=ADD_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject = HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toString());


		if (null != jsonObject) {  
			//4.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  

				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				
				//5.成功添加客服
			} else {
				logger.info("客服添加成功！");
			} 
		}   



	} 





}
