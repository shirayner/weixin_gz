package com.ray.weixin.gz.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.menu.MenuServiceTest;
import com.ray.weixin.gz.service.user.UserService;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  :  用户管理
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 下午6:14:20
 */
public class UserServiceTest {

	private static final Logger logger = LogManager.getLogger(MenuServiceTest.class);
 
	/**
	 * @desc ：1.获取用户基本信息(UnionID机制)
	 *   void
	 */
	@Test
	public void testGetUserInfo() {
		try {
			String openId="oDLefxDvQajibXhSWIWHrkiO1n2o";
			String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

			JSONObject jsonObject=UserService.getUserInfo(accessToken, openId);
            logger.info("jsonObject:"+jsonObject.toJSONString());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * @desc ：2.获取用户列表(只能获取指定的那个)
	 * 
	 * 
	 * 
	 *   void
	 */
	@Test
	public void testListUser() {
		try {
			String nextOpenId="oDLefxDvQajibXhSWIWHrkiO1n2o";
			//String nextOpenId="oDLefxJLf6PIiiw7v2pVKKDVuyc8";
			String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

			JSONObject jsonObject=UserService.listUser(accessToken, nextOpenId);
            logger.info("jsonObject:"+jsonObject.toJSONString());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * @desc ：3.获取所有用户列表
	 *   void
	 */
	@Test
	public void testListAllUser() {
		try {
		
			String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);

			JSONObject jsonObject=UserService.listAllUser(accessToken);
            logger.info("jsonObject:"+jsonObject.toJSONString());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
