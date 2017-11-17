package com.ray.weixin.gz.model.message.custom;

/**@desc  : 消息基类
 * 
 * @author: shirayner
 * @date  : 2017年11月17日 下午4:26:48
 */
public class BaseMessage {
	// 接收方帐号（收到的OpenID）
	private String touser;
	//消息类型
	private String msgtype;
	
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	
}
