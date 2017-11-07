package com.ray.weixin.gz.model.message.request;  
  
/** 
 * 图片消息 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class ImageMessage extends BaseMessage {  
    // 图片链接  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}  