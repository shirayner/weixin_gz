package com.ray.weixin.gz.model.message.request;  
   
/**
 * @desc  : 图片消息 
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:04:33
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