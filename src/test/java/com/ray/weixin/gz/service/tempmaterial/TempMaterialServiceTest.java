package com.ray.weixin.gz.service.tempmaterial;


import org.junit.Test;

import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.tempmaterial.TempMaterialService;
import com.ray.weixin.gz.util.AuthHelper;


/**@desc  : 素材管理
 * 
 * @author: shirayner
 * @date  : 2017年11月1日 上午10:30:13
 */
public class TempMaterialServiceTest {
	
	/**
	 * @desc ： 1.新增临时素材
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testUploadTempMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String  type="image";
		//String  fileDir="D:/img/1.jpg";  //5BXY7DI-uz3N-m8HuZP3Lqzy-WrtegzUKW04OcLNlUjMBcyEyCdgorBsotQqpH0r

		String  fileDir="D:/img/2.png";  //bdARqt5NClDYbP_og5NwBRwO4sCIIwF1ZeVQQKTvB1bkn2rL9Yq52Y6S656lTxf1

		TempMaterialService.uploadTempMaterial(accessToken, type, fileDir);


	}

	/**
	 * @desc ： 2.获取临时素材
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetTempMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		// String  mediaId="5BXY7DI-uz3N-m8HuZP3Lqzy-WrtegzUKW04OcLNlUjMBcyEyCdgorBsotQqpH0r"; // D:/img/1.jpg
		String  mediaId="bdARqt5NClDYbP_og5NwBRwO4sCIIwF1ZeVQQKTvB1bkn2rL9Yq52Y6S656lTxf1"; // D:/img/2.png
		String  fileDir="D:/img/download/";  

		TempMaterialService.getTempMaterial(accessToken, mediaId, fileDir);


	}
	
	/**
	 * @desc ： 3.新增永久素材——上传永久图片——上传图文消息内的图片获取URL 
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testUploadPermanentImg() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		
		//String  fileDir="D:/img/1.jpg"; 
		String  fileDir="D:/img/2.png";

		TempMaterialService.uploadPermanentImg(accessToken, fileDir);

	}
	
	
	/**
	 * @desc ： 4.新增永久素材——新增其他类型永久素材(image、voice、thumb)
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testUploadPermanentMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String type="image";
		//String  fileDir="D:/img/1.jpg"; 
		String  fileDir="D:/img/2.png";

		TempMaterialService.uploadPermanentMaterial(accessToken, type, fileDir);

	}
	
	/**
	 * @desc ： 5.获取永久素材列表 
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testListPermanentMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String type="image";
		String offset="0";
		String count="2";

		TempMaterialService.listPermanentMaterial(accessToken, type, offset, count);

	}
	
	/**
	 * @desc ：6.获取永久素材
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testGetPermanentMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String mediaId="NFREZRuTaNgMSgnxT5agYkff8xLCKRjZPMXhS-lT6aE";
		String fileDir="D:/img/download/";

		TempMaterialService.getPermanentMaterial(accessToken, mediaId, fileDir);

	}
	
	/**
	 * @desc ：7.删除永久素材
	 *  
	 * @throws Exception void
	 */
	@Test
	public void testDeletePermanentMaterial() throws Exception {
		String  accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
		String mediaId="NFREZRuTaNgMSgnxT5agYkff8xLCKRjZPMXhS-lT6aE";

		TempMaterialService.deletePermanentMaterial(accessToken, mediaId);

	}
	
	
	

}
