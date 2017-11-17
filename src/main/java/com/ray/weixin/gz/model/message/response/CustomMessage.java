package com.ray.weixin.gz.model.message.response;  
   
/**
 * @desc  : 新版客服功能——将消息转发到客服
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:10:58
 */
public class CustomMessage extends BaseMessage {  
    // 回复的消息内容  
    private TransInfo TransInfo;

	public TransInfo getTransInfo() {
		return TransInfo;
	}

	public void setTransInfo(TransInfo transInfo) {
		TransInfo = transInfo;
	}  
  
    
    
}  