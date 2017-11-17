package com.ray.weixin.gz.service.custom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  :  新版客服功能-客服管理
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午3:47:45
 */
public class CustomServiceTest {

	private static final Logger log= LogManager.getLogger(CustomServiceTest.class);

	/**
	 * @desc ：1.添加客服消息
	 *  
	 * @param accessToken    有效凭证
	 * @param kf_account  完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，
	 *            必须是英文、数字字符或者下划线，后缀为公众号微信号，长度不超过30个字符
	 * @param nickName    客服昵称，最长16个字
	 * @throws Exception void
	 */
	@Test
	public void testAddCustom() {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String kf_account="kf1@handhec";
		String nickName="客服1";
		
		CustomService.addCustom(accessToken, kf_account, nickName);
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
	@Test
	public void testlistCustom() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

		JSONObject customList=CustomService.listCustom(accessToken);
	
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
	@Test
	public void testlistOnlineCustom(){
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

		JSONObject customList=CustomService.listOnlineCustom(accessToken);
	
	}
	
	
	/**
	 * @desc ：4.邀请绑定客服帐号
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kf_account   完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param invite_wx    接收绑定邀请的客服微信号

	 *   void
	 */
	@Test
	public void testInviteWorker() {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

		String kf_account="kf1@handhec";
		String invite_wx="shiray1994";
		CustomService.inviteWorker(accessToken, kf_account, invite_wx);
	
	}
	
	/**
	 * @desc ： 5.修改客服信息
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kf_account   完整客服帐号，格式为：帐号前缀@公众号微信号
	 * @param nickName     客服昵称，最长16个字
	 *   void
	 */
	@Test
	public void testUpdateCustom() {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String kf_account="kf1@handhec";
		String nickName="客服111";
		
		CustomService.updateCustom(accessToken, kf_account, nickName);;
	}
	
	
	/**
	 * @desc ：6.上传客服头像
	 *  
	 * @param accessToken   接口调用凭证
	 * @param fileDir  图片路径
	 *   void
	 */
	@Test
	public void testUploadHeadImg() {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		String kfAccount="kf1@handhec";
		String fileDir="D:/img/kfHeadImg/1littlePrince.jpg";
		
		CustomService.uploadHeadImg(accessToken,kfAccount, fileDir);
	}
	
	/**
	 * @desc ：7.删除客服帐号
	 *  
	 * @param accessToken  接口调用凭证
	 * @param kfAccount  客服账号
	 *   void
	 */
	@Test
	public void testDeleteCustom() {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		String kfAccount="kf1@handhec";

		CustomService.deleteCustom(accessToken, kfAccount);
	}
	
	
}
