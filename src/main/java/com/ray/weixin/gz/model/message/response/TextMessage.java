package com.ray.weixin.gz.model.message.response;  
   
/**
 * @desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:10:58
 */
public class TextMessage extends BaseMessage {  
    // 回复的消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  