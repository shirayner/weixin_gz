package com.ray.weixin.gz.service.invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.model.invoice.Invoice;
import com.ray.weixin.gz.util.HttpHelper;

/**@desc  : 微信电子发票接口
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午4:31:45
 */
public class InvoiceService {
	private static final Logger logger = LogManager.getLogger(InvoiceService.class);

	//1.设置测试白名单
	private static final String SET_TESTWHITELIST_URL="https://api.weixin.qq.com/card/testwhitelist/set?access_token=ACCESS_TOKEN";
	//2.获取自身的开票平台识别码
	private static final String GET_SPAPPID_URL="https://api.weixin.qq.com/card/invoice/seturl?access_token=ACCESS_TOKEN";
    //3.创建发票卡券模板
	private static final String CREATE_INVOICE_TEMPLATE_URL="https://api.weixin.qq.com/card/invoice/platform/createcard?access_token=ACCESS_TOKEN";
	//4.更新发票卡券模板——更新发票卡券模板方法可参考一般卡券更新方法，见卡券更新接口文档。
   
	//5.解码code接口   (测试失败)
	private static final String GET_DECRYPT_CODE_URL="https://api.weixin.qq.com/card/code/decrypt?access_token=ACCESS_TOKEN";
	
	//6.获取授权页ticket
	private static final String GET_AUTHPAGE_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
	
	//7.获取授权页链接
	private static final String GET_AUTHPAGE_LINK_URL="https://api.weixin.qq.com/card/invoice/getauthurl?access_token=ACCESS_TOKEN";
	


	/**
	 * @desc ：1.设置测试白名单
	 *  
	 * @param accessToken  接口调用凭证
	 * @param openIdList   测试的openid列表。
	 * @param userNameList 测试的微信号列表。
	 * @throws Exception void
	 */
	public static void setTestWhiteList(String accessToken,List<String> openIdList ,List<String> userNameList) throws Exception {
		//1.准备好Json请求参数
		Map<String,List<String>>  postDataMap= new HashMap<String,List<String>>();
		postDataMap.put("openid", openIdList);
		postDataMap.put("username", userNameList);

		Object data=JSON.toJSON(postDataMap);
		String ded =JSON.toJSONString(postDataMap);
		//2.准备好请求url
		String url=SET_TESTWHITELIST_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} 
		}
	}


	/**
	 * @desc ：2.获取自身的开票平台识别码
	 *  
	 * @param accessToken  接口访问凭证
	 * @return
	 * invoice_url 该开票平台专用的授权链接。开票平台须将invoice_url内的s_pappid给到服务的商户，
	 *             商户在请求授权链接时会向微信传入该参数，标识所使用的开票平台是哪家
	 * s_pappid 开票平台识别码
	 * 
	 * @throws Exception String
	 */
	public static String getS_PAppId(String accessToken) throws Exception {
		//1.准备好Json请求参数
		Object data=new Object();

		//2.准备好请求url
		String url=GET_SPAPPID_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//4.解析结果，返回s_pappid
		String s_pappid = null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			}else {
				String invoiceUrl=jsonObject.getString("invoice_url");
				String BeginStr="s_pappid=";
				s_pappid = getSubByBeginStr(invoiceUrl, BeginStr);
				logger.info("s_pappid:"+s_pappid);
			}
		}
		return s_pappid;
	}

	/**
	 * @desc ：3.创建发票卡券模板
	 *  
	 * @param accessToken 接口调用凭证
	 * @param invoice  	发票模板对象
	 * 
	 * @return
	 * card_id  当错误码为0时，返回发票卡券模板的编号，
	 *          用于后续该商户发票生成后，作为必填参数在调用插卡接口时传入
	 * 
	 * @throws Exception String
	 */
	public static String createInvoiceTemplate(String accessToken,Invoice invoice) throws Exception {
		//1.准备好Json请求参数
		Object data=JSON.toJSON(invoice);
		logger.info("data:"+data.toString());
		//2.准备好请求url
		String url=CREATE_INVOICE_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//4.解析结果，返回s_pappid
		String cardId = null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			}else {
				cardId=jsonObject.getString("card_id");
				logger.info("cardId:"+cardId);
			}
		}
		return cardId;

	}
	
	/**
	 * @desc ：5.解码code接口 
	 *  
	 * @param accessToken 接口调用凭证
	 * @param encryptCode 在发票卡券发起访问外链的时候后缀的加密发票code，指向一张具体的发票卡券
	 * 
	 * @return
	 * code 解密后获取的真实发票卡券Code码
	 * @throws Exception String
	 */
	public static String getDecryptCode(String accessToken,String encryptCode) throws Exception {
		Map<String,String> postDataMap=new HashMap<String ,String>();
		postDataMap.put("encrypt_code", encryptCode);
		
		//1.准备好Json请求参数
		Object data=JSON.toJSON(postDataMap);
		logger.info("data:"+data.toString());
		//2.准备好请求url
		String url=GET_DECRYPT_CODE_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//4.解析结果，返回s_pappid
		String code = null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			}else {
				code=jsonObject.getString("code");
				logger.info("code:"+code);
			}
		}
		return code;

	}
	
	/**
	 * @desc ：6.获取授权页ticket
	 *  
	 * @param accessToken 接口调用凭证
	 * @return
	 * ticket 临时票据，用于在获取授权链接时作为参数传入
	 * @throws Exception String
	 */
	public static String getAuthPageTicket(String accessToken) throws Exception {
		//1.获取请求url
		String url=GET_AUTHPAGE_TICKET_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		String ticket=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				ticket= jsonObject.getString("ticket");
			} 
		}   

		return ticket;
	}
	
	/**
	 * @desc ：7.获取授权页链接
	 *  
	 * @param accessToken 接口调用凭证
	 * @param postData  JSONObject 
	 * s_pappid   String 是   开票平台在微信的标识号，商户需要找开票平台提供
	 * order_id   String 是   订单id，在商户内单笔开票请求的唯一识别号，
	 * money      int    是   订单金额，以分为单位
	 * timestamp  Int    是   时间戳
	 * source     String 是   开票来源，app：app开票，web：微信h5开票
	 * redirect_url   String 是   授权成功后跳转页面。本字段只有在source为H5的时候需要填写，引导用户在微信中进行下一步流程。
	 *       app开票因为从外部app拉起微信授权页，授权完成后自动回到原来的app，故无需填写。
	 * ticket     String 是   从上一环节中获取
	 * type       Int    是   授权类型，0：开票授权，1：填写字段开票授权，2：领票授权
	 * 
	 * @return
	 * @throws Exception String
	 */
	public static String getAuthPageLink(String accessToken,JSONObject postData) throws Exception {
		//1.获取请求url
		String url=GET_AUTHPAGE_LINK_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doPost(url, postData);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取菜单数据
		String authUrl=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				authUrl= jsonObject.getString("auth_url");
			} 
		}   

		return authUrl;
	}
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * @desc ： 获取一段字符串中从匹配字符串开始的子串
	 *  https://mp.weixin.qq.com/bizmall/authinvoice?action=list&s_pappid=d3hhMDA2NGVhNjU3ZjgwMDYyX0s22HY1myAuWWro7q-FsX8KWzrWiEgI8Ngqa3-W6dQ4
	 * @param str  完整字符串              "ABCDE"
	 * @param BeginStr   匹配字符串AB
	 * @return 
	 *   String  返回结果    CDE
	 */
	private static String getSubByBeginStr(String str,String BeginStr) {
		int beginIndex=str.indexOf(BeginStr)+BeginStr.length();

		String result=str.substring(beginIndex);

		return result;

	}



}
