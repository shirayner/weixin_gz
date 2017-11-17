package com.ray.weixin.gz.model.invoice.template;

/**@desc  : 发票模板对象
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午6:48:30
 */
public class InvoiceInfo {
	//是 发票卡券模板基础信息
	private BaseInvoice base_info;       
	
	//是 收款方（开票方）全称，显示在发票详情内。故建议一个收款方对应一个发票卡券模板
	private String payee; 
	
	//是 发票类型
	private String type;
	
	//是 描述
	private String detail;
	
	

	public BaseInvoice getBase_info() {
		return base_info;
	}

	public void setBase_info(BaseInvoice base_info) {
		this.base_info = base_info;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
