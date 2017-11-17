package com.ray.weixin.gz.service.message.cutom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.message.cutom.CustomMessageService;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  :  发送消息-客服消息
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:47:45
 */
public class CustomMessageServiceTest {

	private static final Logger log = LogManager.getLogger(CustomMessageServiceTest.class);

	/**
	 * @desc ：2.会话控制——创建会话
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount    完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param openId       粉丝的openid
	 *   void
	 */
	@Test
	public void testCreateConversation() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

		String kfAccount="kf1@handhec";
        String openId="o1M7_1YPJI9YMaHiP54wN8nzuoDE";
		CustomMessageService.createConversation(accessToken, kfAccount, openId);

	}
	
	/**
	 * @desc ：3.会话控制——关闭会话
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount    完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param openId       粉丝的openid
	 *   void
	 */
	@Test
	public void testCloseConversation() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

		String kfAccount="kf1@handhec";
        String openId="o1M7_1YPJI9YMaHiP54wN8nzuoDE";
		CustomMessageService.closeConversation(accessToken, kfAccount, openId);;

	}
	
	/**
	 * @desc ：4.主动发消息
	 *  
	 * @param accessToken  接口调用凭证
	 * @param touser   接收方帐号（收到的OpenID）
	 * @param content   文本消息内容
	 *   void
	 */
	@Test
	public void testSendTextMessage() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

        String touser="o1M7_1YPJI9YMaHiP54wN8nzuoDE";
        //String touser="o1M7_1da9bXlZfrXS8LF046FcMHo";  //xinjialin
       //String content="亲爱的用户，感谢你关注本公众号，特送你100元以示奖励";
        String content="您账户4572于11月17日16:57POS消费人民币1273.30。1分钱喝冰镇饮料 cmbt.cn/a06 。[招商银行]";
        CustomMessageService.sendTextMessage(accessToken, touser, content);

	}

}
