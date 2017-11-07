package com.ray.weixin.gz.config;

/**@desc  : 微信公众号接入配置
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午4:57:36
 */

public class Env {

    /**
     * 1. 企业应用接入秘钥相关
     */
   // public static final String APP_ID = "wx4dca3424bebef2cc";
    //public static final String APP_SECRET = "068e2599abf88ba78491a07906f3c56e";
  
	//测试号
    public static final String APP_ID = "wxa0064ea657f80062";
    public static final String APP_SECRET = "fcc960840df869ad1a46af7993784917";
    

    /**
     * 2.服务器配置：
     * 启用并设置服务器配置后，用户发给公众号的消息以及开发者需要的事件推送，将被微信转发到该URL中
     */
	public static final String TOKEN = "weixin";
	public static final String ENCODING_AES_KEY = "JvJ1Dww6tjUU2psC3pmokXvOHHfovfWP3LfX1xrriz1";
	
    
  

	
}
