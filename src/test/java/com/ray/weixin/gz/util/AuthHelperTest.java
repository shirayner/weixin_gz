package com.ray.weixin.gz.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年10月30日 下午8:22:35
 */
public class AuthHelperTest {
	private static final Logger logger = LogManager.getLogger(AuthHelperTest.class);
	
	@Test
	public void testGetAccessToken() throws Exception {

		String appId=Env.APP_ID;
		String appSecret=Env.APP_SECRET;
		String accessToken=AuthHelper.getAccessToken(appId, appSecret);
		logger.info("accessToken:"+accessToken);
	}
	
	
	@Test
	public void testGetJsapiTicket() throws Exception {

		String appId=Env.APP_ID;
		String appSecret=Env.APP_SECRET;
		String accessToken=AuthHelper.getAccessToken(appId, appSecret);
	    String jsapiTicket=AuthHelper.getJsapiTicket(accessToken);
		logger.info("accessToken:"+accessToken);
		logger.info("jsapiTicket:"+jsapiTicket);
	}
	
	
	
}
