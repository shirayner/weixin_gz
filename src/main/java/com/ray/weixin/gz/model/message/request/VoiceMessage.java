package com.ray.weixin.gz.model.message.request;  
    
/**
 * @desc  :音频消息  
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:08:25
 */
public class VoiceMessage extends BaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}  