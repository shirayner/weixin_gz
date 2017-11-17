package com.ray.weixin.gz.service.invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.model.invoice.CardExt;
import com.ray.weixin.gz.model.invoice.InvoiceUserData;
import com.ray.weixin.gz.model.invoice.UserCard;
import com.ray.weixin.gz.model.invoice.template.BaseInvoice;
import com.ray.weixin.gz.model.invoice.template.Invoice;
import com.ray.weixin.gz.model.invoice.template.InvoiceInfo;
import com.ray.weixin.gz.service.tempmaterial.TempMaterialService;
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
	
		logger.info("cardId:"+cardId);  //cardId=pDLefxG5ZPv-uzSySh7hwAX6JfoY
		
		
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
	
	/**
	 * @throws Exception 
	 * @desc ：8.上传pdf
	 *   
	 *   void
	 */
	@Test
	public void testUploadPDF() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String  fileDir="D:/fp762.pdf";  //bdARqt5NClDYbP_og5NwBRwO4sCIIwF1ZeVQQKTvB1bkn2rL9Yq52Y6S656lTxf1

		InvoiceService.uploadPDF(accessToken, fileDir);
	}
	
	/**
	 * @desc ：9.将电子发票卡券插入用户卡包
	 *  
	 * @throws Exception 
	 *   void
	 */
	@Test
	public void testInsertInvoice() throws Exception {
		//1.获取accessToken
		String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		//2.准备好发票具体内容
		//2.1
		int billing_time=(int)System.currentTimeMillis() / 1000;  
		
		InvoiceUserData invoice_user_data=new InvoiceUserData();
		invoice_user_data.setFee(4);
		invoice_user_data.setTitle("上海百度公司");
		invoice_user_data.setBilling_time(billing_time);
		invoice_user_data.setBilling_no("847574");
		invoice_user_data.setBilling_code("437463");
		invoice_user_data.setFee_without_tax(3);
		invoice_user_data.setTax(1);
		invoice_user_data.setS_pdf_media_id(""); //
		invoice_user_data.setCheck_code("123456");
		
		
		//2.2准备好用户信息结构体——UserCard
		UserCard user_card=new UserCard();
		user_card.setInvoice_user_data(invoice_user_data);
		
		//2.3 准备好发票具体内容——CardExt
		String nonce_str=UUID.randomUUID().toString();
		CardExt card_ext=new CardExt();
		card_ext.setNonce_str(nonce_str);
		card_ext.setUser_card(user_card);
		
		//3..准备好JSON请求参数
		JSONObject postData=new JSONObject();
		postData.put("order_id", "1234");  //获取授权页的时候设置的，GetAuthPageLink
		postData.put("card_id", "pDLefxG5ZPv-uzSySh7hwAX6JfoY");  //创建发票卡券模板生成的card_id将在创建发票卡券时被引用
		postData.put("appid", Env.APP_ID);
		postData.put("card_ext", card_ext);
		
		InvoiceService.insertInvoice(accessToken, postData);
		
	}
	

	
}
