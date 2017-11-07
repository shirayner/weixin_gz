package com.ray.weixin.gz.service.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.message.CustomMessageService;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  :  发送消息-客服消息
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:47:45
 */
public class CustomMessageServiceTest {

	private static final Logger logger = LogManager.getLogger(CustomMessageServiceTest.class);

	@Test
	public void testAddCustom() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String kf_account="test1@saofapiao";
		String nickName="客服1";
		
		CustomMessageService.addCustom(accessToken, kf_account, nickName);
	}
	
}
