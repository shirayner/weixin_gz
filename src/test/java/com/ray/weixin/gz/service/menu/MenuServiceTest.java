package com.ray.weixin.gz.service.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.model.menu.Button;
import com.ray.weixin.gz.model.menu.CommonButton;
import com.ray.weixin.gz.model.menu.ComplexButton;
import com.ray.weixin.gz.model.menu.Menu;
import com.ray.weixin.gz.model.menu.ViewButton;
import com.ray.weixin.gz.service.menu.MenuService;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  : 菜单测试类
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 上午9:53:00
 */
public class MenuServiceTest {
	
	private static final Logger logger = LogManager.getLogger(MenuServiceTest.class);

	
	
	/**
	 * @desc ：1. 创建菜单
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testCreateMenu() throws Exception {
		//1.准备好请求参数
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		Menu menu=getMenu();
		
		//2.调用接口，执行请求
		MenuService.createMenu(menu, accessToken);

	}

	/**
	 * @desc ：2.查询菜单数据
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetMenu() throws Exception {
		//1.准备好请求参数
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		//2.调用接口，执行请求
		JSONObject jsonObject=MenuService.getMenu(accessToken);
        logger.info("菜单数据："+jsonObject.toJSONString());
	}

	/**
	 * @desc ：3.删除菜单
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testDeleteMenu() throws Exception {
		//1.准备好请求参数
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		//2.调用接口，执行请求
		MenuService.deleteMenu(accessToken);
       
	}
	
	
	/**
	 * @desc ：辅助1.组装菜单数据 
	 *  
	 * @return Menu
	 */
	private static Menu getMenu() {  
		ViewButton btn11 = new ViewButton();  
		btn11.setName("移动审批");  
		btn11.setType("view");  
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0064ea657f80062&redirect_uri=http%3A%2F%2Frayner.nat300.top%2Fweixin_gz%2FIDAuthentication.jsp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");  
		//btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0064ea657f80062&redirect_uri=http%3A%2F%2Frayner.nat300.top%2Fweixingz_hec%2Fweixingz.screen&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");  

		
		ViewButton btn12 = new ViewButton();  
		btn12.setName("上传图片");  
		btn12.setType("view");  
		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa0064ea657f80062&redirect_uri=http%3A%2F%2Frayner.nat300.top%2Fweixin_gz%2FuploadImg.jsp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");  

		ViewButton btn13 = new ViewButton();  
		btn13.setName("申请开票");  
		btn13.setType("view");  
		btn13.setUrl("https://mp.weixin.qq.com/bizmall/authinvoice?action=list&s_pappid=d3hhMDA2NGVhNjU3ZjgwMDYyX0s22HY1myAuWWro7q-FsX8KWzrWiEgI8Ngqa3-W6dQ4&appid=wxa0064ea657f80062&num=1&o1=1234&m1=11&t1=1509942717&source=web&type=1&redirect_url=https%3A%2F%2Fmp.weixin.qq.com&signature=3940aae89b04a0fd05f8f231b74821939f0d18df#wechat_redirect");  


		ViewButton btn14 = new ViewButton();  
		btn14.setName("获取发票信息");  
		btn14.setType("view");  
		btn14.setUrl("http://rayner.nat300.top/weixin_gz/showInvoice.jsp");  

		ViewButton btn15 = new ViewButton();  
		btn15.setName("上传图片2");  
		btn15.setType("view");  
		btn15.setUrl("http://5hcn2d.natappfree.cc/WeiXin_SanFenBaiXue/index2.jsp");  

		ViewButton btn21 = new ViewButton();  
		btn21.setName("index");  
		btn21.setType("view");  
		btn21.setUrl("http://rayner.nat300.top/weixin_gz/index.jsp");  

		CommonButton btn22 = new CommonButton();  
		btn22.setName("经典游戏");  
		btn22.setType("click");  
		btn22.setKey("22");  

		CommonButton btn23 = new CommonButton();  
		btn23.setName("美女电台");  
		btn23.setType("click");  
		btn23.setKey("23");  

		CommonButton btn24 = new CommonButton();  
		btn24.setName("人脸识别");  
		btn24.setType("click");  
		btn24.setKey("24");  

		CommonButton btn25 = new CommonButton();  
		btn25.setName("聊天唠嗑");  
		btn25.setType("click");  
		btn25.setKey("25");  

		CommonButton btn31 = new CommonButton();  
		btn31.setName("Q友圈");  
		btn31.setType("click");  
		btn31.setKey("31");  

		CommonButton btn33 = new CommonButton();  
		btn33.setName("幽默笑话");  
		btn33.setType("click");  
		btn33.setKey("33");  

		CommonButton btn34 = new CommonButton();  
		btn34.setName("用户反馈");  
		btn34.setType("click");  
		btn34.setKey("34");  

		CommonButton btn35 = new CommonButton();  
		btn35.setName("关于我们");  
		btn35.setType("click");  
		btn35.setKey("35");  

		ViewButton btn32 = new ViewButton();  
		btn32.setName("周边搜索");  
		btn32.setType("view");  
		btn32.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");  

		ComplexButton mainBtn1 = new ComplexButton();  
		mainBtn1.setName("汉得测试");  
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });  

		ComplexButton mainBtn2 = new ComplexButton();  
		mainBtn2.setName("休闲驿站");  
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  

		ComplexButton mainBtn3 = new ComplexButton();  
		mainBtn3.setName("更多");  
		mainBtn3.setSub_button(new Button[] { btn31, btn33, btn34, btn35, btn32 });  

		/** 
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
		 *  
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
		 */  
		Menu menu = new Menu();  
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  

		return menu;  
	}  
}
