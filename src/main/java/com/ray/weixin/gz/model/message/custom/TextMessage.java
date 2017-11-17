package com.ray.weixin.gz.model.message.custom;

/**@desc  :  文本消息
 * 
 * @author: shirayner
 * @date  : 2017年11月17日 下午4:32:45
 */
public class TextMessage extends BaseMessage {
	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}



}
