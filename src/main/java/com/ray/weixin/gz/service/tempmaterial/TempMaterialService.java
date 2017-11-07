package com.ray.weixin.gz.service.tempmaterial;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.gz.util.HttpHelper;


/**@desc  :  素材管理
 * 
 * @author: shirayner
 * @date  : 2017年11月1日 上午10:16:00
 */
public class TempMaterialService {
	private static final Logger logger = LogManager.getLogger(TempMaterialService.class);

	//1.新增临时素材
	private static final String UPLOAD_TEMPMATERIAL_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//2.获取临时素材 ( 即为原“下载多媒体文件”接口  )
	private static final String GET_TEMPMATERIAL_URL="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	//3.新增永久素材(上传)——上传永久图片——上传图文消息内的图片获取URL 
	private static final String UPLOAD_PERMANENT_IMG_URL="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	//4.新增永久素材(上传)——新增其他类型永久素材(image、voice、video、thumb)
	private static final String UPLOAD_PERMANENT_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	//5.获取永久素材列表
	private static final String LIST_PERMANENT_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	//6.获取永久素材(下载)
	private static final String GET_PERMANENT_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
    //7.删除永久素材
	private static final String DELETE_PERMANENT_MATERIAL_URL="https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";






	/** 1.新增临时素材
	 * 
	 * @desc ：
	 * 1、临时素材media_id是可复用的。
	 * 2、媒体文件在微信后台保存时间为3天，即3天后media_id失效。
	 * 3、上传临时素材的格式、大小限制与公众平台官网一致。
	 *    图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
	 *    语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
	 *    视频（video）：10MB，支持MP4格式
	 *    缩略图（thumb）：64KB，支持JPG格式
	 *     
	 * @param accessToken  有效凭证
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param fileDir  要上传文件所在路径
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject uploadTempMaterial(String accessToken,String type,String fileDir) throws Exception {
		//1.创建本地文件
		File file=new File(fileDir);

		//2.拼接请求url
		String url = UPLOAD_TEMPMATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		//3.调用接口，发送请求，上传文件到微信服务器
		JSONObject jsonObject=HttpHelper.uploadMedia(url, file);
		logger.info("JsonObject:"+jsonObject.toJSONString());

		//4.解析结果
		if (jsonObject != null) {
			if (jsonObject.getString("media_id") != null) {
				logger.info("上传" + type + "临时素材成功:"+jsonObject.get("media_id"));
				return jsonObject;

				//5.错误消息处理
			} else {
				logger.error("上传" + type + "临时素材成功失败");
			}
		}
		return null;

	}

	/**
	 * @desc ：2.获取临时素材
	 *  
	 * @param accessToken  调用接口凭证
	 * @param mediaId   媒体文件ID
	 * @param fileDir  文件下载路径（文件所在文件夹路径）,如 D:/img/download/,会与文件名拼接成文件下载路径
	 * @return
	 * @throws Exception File
	 */
	public static File getTempMaterial(String accessToken,String mediaId,String fileDir) throws Exception {
		//1.拼接请求url
		String url = GET_TEMPMATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);

		//2.调用接口，发送请求，下载文件到本地
		File file=HttpHelper.downloadMedia(url, fileDir);
		logger.info("fileName:"+file.getName());

		return file;

	}

	/** 3.新增永久素材——上传永久图片——上传图文消息内的图片获取URL 
	 * @desc ：
	 *  
	 * @param accessToken  
	 * @param fileDir
	 * @return
	 * @throws Exception String
	 */
	public static String uploadPermanentImg(String accessToken,String fileDir) throws Exception {
		//1.创建本地文件
		File file=new File(fileDir);

		//2.拼接请求url
		String url = UPLOAD_PERMANENT_IMG_URL.replace("ACCESS_TOKEN", accessToken);

		//3.调用接口，发送请求，上传文件到微信服务器
		JSONObject jsonObject=HttpHelper.uploadMedia(url, file);
		logger.info("JsonObject:"+jsonObject.toJSONString());

		String ImgUrl=null;
		//4.解析结果
		if (jsonObject != null) {
			if (jsonObject.getString("url") != null) {

				ImgUrl=jsonObject.getString("url");

				logger.info("新增永久素材成功:"+ImgUrl);

				//5.错误消息处理
			} else {
				logger.info("新增永久素材失败");
			}
		}
		return ImgUrl;
	}



	/**
	 * @desc ： 4.新增永久素材——新增其他类型永久素材(image、voice、thumb)
	 *  
	 * @param accessToken  调用接口凭证
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param fileDir 本地图片路径
	 * 
	 * @return
	 * media_id	新增的永久素材的media_id
	 * url	新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
	 * 
	 * @throws Exception String
	 */
	public static JSONObject uploadPermanentMaterial(String accessToken,String type,String fileDir) throws Exception {
		//1.创建本地文件
		File file=new File(fileDir);

		//2.拼接请求url
		String url = UPLOAD_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		//3.调用接口，发送请求，上传文件到微信服务器
		JSONObject jsonObject=HttpHelper.uploadMedia(url, file);
		logger.info("JsonObject:"+jsonObject.toJSONString());

		//4.解析结果
		JSONObject returnJsonObject=null;
		if (jsonObject != null) {
			if (jsonObject.getString("media_id") != null) {

				logger.info("新增永久素材成功:"+jsonObject.getString("media_id"));
				returnJsonObject= jsonObject;
				//5.错误消息处理
			} else {
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("新增永久素材失败"+" errcode:"+errCode+", errmsg:"+errMsg);
			}
		}
		return returnJsonObject;
	}

	/**
	 * @desc ：5.获取永久素材列表
	 *  
	 * @param accessToken 调用接口凭证
	 * @param type  素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset  从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count 返回素材的数量，取值在1到20之间
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject listPermanentMaterial(String accessToken, String type, String offset,String count) throws Exception {
		//1.准备好json请求参数
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("type", type);
		paramMap.put("offset", offset);
		paramMap.put("count", count);

		Object data=JSON.toJSON(paramMap);

		//2.准备好请求url
		String url=LIST_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起HTTP请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doPost(url, data); 
		logger.info("jsonObject:"+jsonObject.toJSONString());


		//4.解析结果
		JSONObject returnJsonObject=null;
		if (jsonObject != null) {

			//4.1 错误消息处理
			if (jsonObject.getInteger("errcode") != null) {
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("获取永久素材列表失败 "+"errcode:"+errCode+", errmsg:"+errMsg);		

				//4.2 新增成功
			} else {
				logger.info("获取永久素材列表成功 ");
				returnJsonObject= jsonObject;
			}
		}
		return returnJsonObject;
	}
 
    /**
     * @desc ：6.获取永久素材
     *  
     * @param accessToken 调用接口凭证
     * @param mediaId  媒体文件ID
     * @param fileDir  文件下载路径（文件所在文件夹路径）,如 D:/img/download/,会与文件名拼接成文件下载路径
     * @return
     * @throws Exception File
     */
	public static File getPermanentMaterial(String accessToken, String mediaId,String fileDir) throws Exception {
		//1.准备好json请求参数
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("media_id", mediaId);

		Object data=JSON.toJSON(paramMap);

		//2.准备好请求url
		String url=GET_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);

		//3.调用接口，发送HTTP请求，下载文件到本地
		File file=HttpHelper.downloadMedia(url, data,fileDir);
		logger.info("fileName:"+file.getName());

		
		return file;
	}


	/**
	 * @desc ：7.删除永久素材
	 *  
	 * @param accessToken 调用接口凭证
     * @param mediaId  媒体文件ID
	 * @return
	 * 
	 * @throws Exception JSONObject
	 */
	public static JSONObject deletePermanentMaterial(String accessToken, String mediaId) throws Exception {
		//1.准备好json请求参数
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("media_id", mediaId);

		Object data=JSON.toJSON(paramMap);

		//2.准备好请求url
		String url=DELETE_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发起HTTP请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doPost(url, data); 
		logger.info("jsonObject:"+jsonObject.toJSONString());


		//4.解析结果
		JSONObject returnJsonObject=null;
		if (jsonObject != null) {

			//4.1 错误消息处理
			if (jsonObject.getInteger("errcode") != 0) {
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("删除永久素材失败 "+"errcode:"+errCode+", errmsg:"+errMsg);		

				//4.2 新增成功
			} else {
				logger.info("删除永久素材成功 ");
				returnJsonObject= jsonObject;
			}
		}
		return returnJsonObject;
	}

}
