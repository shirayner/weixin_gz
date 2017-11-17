package com.ray.weixin.gz.model.message.request;  
  
/**
 * @desc  : 文本消息 
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:04:09
 */
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  