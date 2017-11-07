package com.ray.weixin.gz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * HTTP请求封装，建议直接使用sdk的API
 */
public class HttpHelper {

	/**
	 * @desc ：1.发起GET请求
	 *  
	 * @param url
	 * @return JSONObject
	 * @throws Exception 
	 */
	public static JSONObject doGet(String url) throws Exception {

		//1.生成一个请求
		HttpGet httpGet = new HttpGet(url);
		//2.配置请求的属性
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//2000
		httpGet.setConfig(requestConfig);

		//3.发起请求，获取响应信息	
		//3.1 创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			//3.2 发起请求，获取响应信息	
			response = httpClient.execute(httpGet, new BasicHttpContext());

			//如果返回结果的code不等于200，说明出错了  
			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}
			//4.解析请求结果
			HttpEntity entity = response.getEntity();      //reponse返回的数据在entity中 
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");  //将数据转化为string格式  
				System.out.println("GET请求结果："+resultStr);
				JSONObject result = JSON.parseObject(resultStr);    //将String转换为 JSONObject

				if(result.getInteger("errcode")==null) {
					return result;
				}else if (0 == result.getInteger("errcode")) {
					return result;
				}else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();                     //释放资源

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}


	/** 2.发起POST请求
	 * @desc ：
	 *  
	 * @param url   请求url
	 * @param data  请求参数（json）
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject doPost(String url, Object data) throws Exception {
		//1.生成一个请求
		HttpPost httpPost = new HttpPost(url);

		//2.配置请求属性
		//2.1 设置请求超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		httpPost.setConfig(requestConfig);
		//2.2 设置数据传输格式-json
		httpPost.addHeader("Content-Type", "application/json");
		//2.3 设置请求参数
		StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
		httpPost.setEntity(requestEntity);

		//3.发起请求，获取响应信息	
		//3.1 创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		try {


			//3.3 发起请求，获取响应
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}

			//获取响应内容
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				System.out.println("POST请求结果："+resultStr);

				//解析响应内容
				JSONObject result = JSON.parseObject(resultStr);

				if(result.getInteger("errcode")==null) {
					return result;
				}else if (0 == result.getInteger("errcode")) {
					return result;
				}else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();              //释放资源

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * @desc ： 3.上传文件
	 *  
	 * @param url   请求url
	 * @param file  上传的文件
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject uploadMedia(String url, File file) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
				new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				//上传临时素材成功
				if (result.getString("errcode")== null) {
					// 成功
					//result.remove("errcode");
					//result.remove("errmsg");
					return result;
				} else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();                  //释放资源


			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * @desc ： 4.下载文件 -get
	 *  
	 * @param url  请求url
	 * @param fileDir  下载路径
	 * @return
	 * @throws Exception File
	 */
	public static File downloadMedia(String url, String fileDir) throws Exception  {
		//1.生成一个请求
		HttpGet httpGet = new HttpGet(url);
		//2.配置请求属性
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		httpGet.setConfig(requestConfig);

		//3.发起请求，获取响应信息	
		//3.1 创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		//4.设置本地保存的文件  
		//File file = new File(fileDir);
		File file = null;
		try {
			//5. 发起请求，获取响应信息	
			response = httpClient.execute(httpGet, new BasicHttpContext());
			System.out.println("HttpStatus.SC_OK:"+HttpStatus.SC_OK);  
			System.out.println("response.getStatusLine().getStatusCode():"+response.getStatusLine().getStatusCode());  
			System.out.println("http-header:"+JSON.toJSONString( response.getAllHeaders() ));  
			System.out.println("http-filename:"+getFileName(response) );  

			//请求成功  
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){  

				//6.取得请求内容  
				HttpEntity entity = response.getEntity();  

				if (entity != null) {  
					//这里可以得到文件的类型 如image/jpg /zip /tiff 等等 但是发现并不是十分有效，有时明明后缀是.rar但是取到的是null，这点特别说明  
					System.out.println(entity.getContentType());  
					//可以判断是否是文件数据流  
					System.out.println(entity.isStreaming());  

					//6.1 输出流
					//6.1.1获取文件名，拼接文件路径
					String fileName=getFileName(response);
					fileDir=fileDir+fileName;
					file = new File(fileDir);
					//6.1.2根据文件路径获取输出流
					FileOutputStream output = new FileOutputStream(file);  

					//6.2 输入流：从钉钉服务器返回的文件流，得到网络资源并写入文件  
					InputStream input = entity.getContent();  

					//6.3 将数据写入文件：将输入流中的数据写入到输出流
					byte b[] = new byte[1024];  
					int j = 0;  
					while( (j = input.read(b))!=-1){  
						output.write(b,0,j);  
					}  
					output.flush();  
					output.close();   
				}  
				if (entity != null) {  
					entity.consumeContent();  
				}  
			}  
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();                       //释放资源


			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}


	/**
	 * @desc ： 5.下载文件 - post
	 *  
	 * @param url  请求url
	 * @param data  post请求参数
	 * @param fileDir 文件下载路径
	 * @return
	 * @throws Exception File
	 */
	public static File downloadMedia(String url, Object data, String fileDir) throws Exception  {
		//1.生成一个请求
		HttpPost httpPost = new HttpPost(url);

		//2.配置请求属性
		//2.1 设置请求超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		httpPost.setConfig(requestConfig);
		//2.2 设置数据传输格式-json
		httpPost.addHeader("Content-Type", "application/json");
		//2.3 设置请求参数
		StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
		httpPost.setEntity(requestEntity);

		//3.发起请求，获取响应信息	
		//3.1 创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		//4.设置本地保存的文件  
		//File file = new File(fileDir);
		File file = null;
		try {
			//5. 发起请求，获取响应信息	
			response = httpClient.execute(httpPost, new BasicHttpContext());
			System.out.println("HttpStatus.SC_OK:"+HttpStatus.SC_OK);  
			System.out.println("response.getStatusLine().getStatusCode():"+response.getStatusLine().getStatusCode());  
			System.out.println("http-header:"+JSON.toJSONString( response.getAllHeaders() ));  
			System.out.println("http-filename:"+getFileName(response) );  

			//请求成功  
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){  

				//6.取得请求内容  
				HttpEntity entity = response.getEntity();  

				if (entity != null) {  
					//这里可以得到文件的类型 如image/jpg /zip /tiff 等等 但是发现并不是十分有效，有时明明后缀是.rar但是取到的是null，这点特别说明  
					System.out.println(entity.getContentType());  
					//可以判断是否是文件数据流  
					System.out.println(entity.isStreaming());  

					//6.1 输出流
					//6.1.1获取文件名，拼接文件路径
					String fileName=getFileName(response);
					fileDir=fileDir+fileName;
					file = new File(fileDir);
					//6.1.2根据文件路径获取输出流
					FileOutputStream output = new FileOutputStream(file);  

					//6.2 输入流：从钉钉服务器返回的文件流，得到网络资源并写入文件  
					InputStream input = entity.getContent();  

					//6.3 将数据写入文件：将输入流中的数据写入到输出流
					byte b[] = new byte[1024];  
					int j = 0;  
					while( (j = input.read(b))!=-1){  
						output.write(b,0,j);  
					}  
					output.flush();  
					output.close();   
				}  
				if (entity != null) {  
					entity.consumeContent();  
				}  
			}  
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();                       //释放资源


			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}


	/** 5. 获取response header中Content-Disposition中的filename值 
	 * @desc ：
	 *  
	 * @param response  响应
	 * @return String
	 */
	public static String getFileName(HttpResponse response) {  
		Header contentHeader = response.getFirstHeader("Content-Disposition");  
		String filename = null;  
		if (contentHeader != null) {  
			HeaderElement[] values = contentHeader.getElements();  
			if (values.length == 1) {  
				NameValuePair param = values[0].getParameterByName("filename");  
				if (param != null) {  
					try {  
						//filename = new String(param.getValue().toString().getBytes(), "utf-8");  
						//filename=URLDecoder.decode(param.getValue(),"utf-8");  
						filename = param.getValue();  
					} catch (Exception e) {  
						e.printStackTrace();  
					}  
				}  
			}  
		}  
		return filename;  
	}  



}
