package com.ray.weixin.gz.model.invoice;

/**@desc  : 用户信息结构体,包含一个invoice_user_data对象
 * 
 * @author: shirayner
 * @date  : 2017年11月7日 下午3:09:39
 */
public class UserCard {
	//1.发票详细信息
	private InvoiceUserData invoice_user_data;

	
	
	public InvoiceUserData getInvoice_user_data() {
		return invoice_user_data;
	}

	public void setInvoice_user_data(InvoiceUserData invoice_user_data) {
		this.invoice_user_data = invoice_user_data;
	}
	
	
	
}
