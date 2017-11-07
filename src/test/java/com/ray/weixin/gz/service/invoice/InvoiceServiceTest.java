package com.ray.weixin.gz.service.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.model.invoice.BaseInvoice;
import com.ray.weixin.gz.model.invoice.Invoice;
import com.ray.weixin.gz.model.invoice.InvoiceInfo;
import com.ray.weixin.gz.util.AuthHelper;

/**@desc  : 微信电子发票接口
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午4:56:22
 */
public class InvoiceServiceTest {
	private static final Logger logger = LogManager.getLogger(InvoiceServiceTest.class);
	 
	/**
	 * @desc ：1.设置测试白名单
	 *  
	 * @throws Exception void
	 */
	@Test
	public void setTestWhiteList() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		List<String> openIdList=new ArrayList<String>();
		openIdList.add("oDLefxDvQajibXhSWIWHrkiO1n2o");
		
		List<String> userNameList=new ArrayList<String>();
		userNameList.add("shiray1994");
		
		InvoiceService.setTestWhiteList(accessToken, openIdList, userNameList);

	}
	
	/**
	 * @desc ：2.获取自身的开票平台识别码
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetS_PAppId() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String s_pappid=InvoiceService.getS_PAppId(accessToken);
	
		logger.info("s_pappid:"+s_pappid);
		
		
	}
	
	/**
	 * @desc ：3.创建发票卡券模板
	 *  
	 * @throws Exception void
	 * "errcode":72031,"errmsg":"invalid params hint     JSON数据格式不对
	 * 
	 */
	@Test
	public void testCreateInvoiceTemplate() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		Invoice invoice=new Invoice();
		
		InvoiceInfo invoice_info=new InvoiceInfo();
		invoice_info.setPayee("测试-收款方");
		invoice_info.setType("广东省增值税普通发票");

		BaseInvoice base_info=new BaseInvoice();
		base_info.setLogo_url("http://mmbiz.qpic.cn/mmbiz_png/2LubW5xIOiaLU0yl6F1icsdAWRs71OWlZTicFE3KickMJwAUU9lCqNgia6tnjFAvqmLy8BaOHud8xqicLVL4FdrqmgGQ/0");;
		base_info.setTitle("上海百度公司");
		
		invoice_info.setBase_info(base_info);
		
		invoice.setInvoice_info(invoice_info);
		
		
		String cardId=InvoiceService.createInvoiceTemplate(accessToken, invoice);
	
		logger.info("cardId:"+cardId);
		
		
	}
	
	/**
	 * @desc ：5.解码code接口
	 *  
	 * @throws Exception void    测试失败
	 * {"errcode":40075,"errmsg":"invalid encrypt code hint:
	 * 
	 */
	@Test
	public void testGetDecryptCode() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
	
		String encryptCode="XXIzTtMqCxwOaawoE91+VJdsFmv7b8g0VZIZkqf4GWA60Fzpc8ksZ/5ZZ0DVkXdE";
		
		String code =InvoiceService.getDecryptCode(accessToken, encryptCode);
		
	
		logger.info("code:"+code);
		
		
	}
	
	/**
	 * @desc ：6.获取授权页ticket
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetAuthPageTicket() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		String ticket=InvoiceService.getAuthPageTicket(accessToken);
		
		logger.info("ticket:"+ticket);
		
		
	}
	
	/**
	 * @desc ：7.获取授权页链接
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetAuthPageLink() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String s_pappid=InvoiceService.getS_PAppId(accessToken);
		long timeStamp=System.currentTimeMillis() / 1000;
		String ticket=InvoiceService.getAuthPageTicket(accessToken);
		
		
		JSONObject postData=new JSONObject();
		postData.put("s_pappid", s_pappid);
		postData.put("order_id", "1234");
		postData.put("money", 11);
		postData.put("timestamp", timeStamp);
		postData.put("source", "web");
		postData.put("ticket", ticket);
		postData.put("type", 1);
		postData.put("redirect_url","https://mp.weixin.qq.com");
		
		String authUrl=InvoiceService.getAuthPageLink(accessToken, postData);
		
		logger.info("authUrl:"+authUrl);
		
		
	}
	
	

	
}
