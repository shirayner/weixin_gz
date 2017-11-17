package com.ray.weixin.gz.model.invoice;

/**@desc  : 发票具体内容
 * 
 * 见接口：8. 将电子发票卡券插入用户卡包
 * 
 * @author: shirayner
 * @date  : 2017年11月7日 下午3:05:47
 */
public class CardExt {

	//1.随机字符串，防止重复
	private String nonce_str;
	
	//2.用户信息结构体,包含一个invoice_user_data对象
	private UserCard user_card;

	
	
	
	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public UserCard getUser_card() {
		return user_card;
	}

	public void setUser_card(UserCard user_card) {
		this.user_card = user_card;
	}
	
	
	
}
