package com.ray.weixin.gz.model.invoice;

import java.util.List;

/**@desc  : 发票具体信息
 * 
 * @author: shirayner
 * @date  : 2017年11月7日 下午3:11:37
 */
public class InvoiceUserData {

	//1.是 发票的金额，以分为单位
	private int fee;

	//2.是 发票的抬头
	private String title;

	//3.是 发票的开票时间，为10位时间戳（utc+8）
	private int billing_time;

	//4.是 发票的发票代码
	private String billing_no;

	//5.是 发票的发票号码
	private String billing_code;

	//6.是 不含税金额，以分为单位
	private int fee_without_tax;

	//7.是 税额，以分为单位
	private int tax;

	//8.是 发票pdf文件上传到微信发票平台后，会生成一个发票s_media_id，
	//该s_media_id可以直接用于关联发票PDF和发票卡券。发票上传参考“上传PDF”一节
	private String s_pdf_media_id;

	//9.是 校验码
	private String check_code;

	//10.否 其它消费附件的PDF，如行程单、水单等，PDF上传方式参考“上传PDF”一节
	private String s_trip_pdf_media_id;
	
	//11.否 商品详情结构，见下方
	private List<GoodInfo> info;

	//12.否 购买方纳税人识别号
	private String buyer_number;
	
	//13.否 购买方地址、电话
	private String buyer_address_and_phone;
	
	//14.否 购买方开户行及账号
	private String buyer_bank_account;
	
	//15.否 销售方纳税人识别号
	private String seller_number;
	
	//16.否 销售方地址、电话
	private String seller_address_and_phone;
	
	//17.否 销售方开户行及账号
	private String seller_bank_account;
	
	//18.否 备注，发票右下角初
	private String remarks;
	
	//19.否 收款人，发票左下角处
	private String cashier;
	
	//20.否 开票人，发票下方处
	private String maker;

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBilling_time() {
		return billing_time;
	}

	public void setBilling_time(int billing_time) {
		this.billing_time = billing_time;
	}

	public String getBilling_no() {
		return billing_no;
	}

	public void setBilling_no(String billing_no) {
		this.billing_no = billing_no;
	}

	public String getBilling_code() {
		return billing_code;
	}

	public void setBilling_code(String billing_code) {
		this.billing_code = billing_code;
	}

	public int getFee_without_tax() {
		return fee_without_tax;
	}

	public void setFee_without_tax(int fee_without_tax) {
		this.fee_without_tax = fee_without_tax;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public String getS_pdf_media_id() {
		return s_pdf_media_id;
	}

	public void setS_pdf_media_id(String s_pdf_media_id) {
		this.s_pdf_media_id = s_pdf_media_id;
	}

	public String getCheck_code() {
		return check_code;
	}

	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}

	public String getS_trip_pdf_media_id() {
		return s_trip_pdf_media_id;
	}

	public void setS_trip_pdf_media_id(String s_trip_pdf_media_id) {
		this.s_trip_pdf_media_id = s_trip_pdf_media_id;
	}

	public List<GoodInfo> getInfo() {
		return info;
	}

	public void setInfo(List<GoodInfo> info) {
		this.info = info;
	}

	public String getBuyer_number() {
		return buyer_number;
	}

	public void setBuyer_number(String buyer_number) {
		this.buyer_number = buyer_number;
	}

	public String getBuyer_address_and_phone() {
		return buyer_address_and_phone;
	}

	public void setBuyer_address_and_phone(String buyer_address_and_phone) {
		this.buyer_address_and_phone = buyer_address_and_phone;
	}

	public String getBuyer_bank_account() {
		return buyer_bank_account;
	}

	public void setBuyer_bank_account(String buyer_bank_account) {
		this.buyer_bank_account = buyer_bank_account;
	}

	public String getSeller_number() {
		return seller_number;
	}

	public void setSeller_number(String seller_number) {
		this.seller_number = seller_number;
	}

	public String getSeller_address_and_phone() {
		return seller_address_and_phone;
	}

	public void setSeller_address_and_phone(String seller_address_and_phone) {
		this.seller_address_and_phone = seller_address_and_phone;
	}

	public String getSeller_bank_account() {
		return seller_bank_account;
	}

	public void setSeller_bank_account(String seller_bank_account) {
		this.seller_bank_account = seller_bank_account;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}


	
}
