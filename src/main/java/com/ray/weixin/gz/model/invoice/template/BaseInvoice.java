package com.ray.weixin.gz.model.invoice.template;

/**@desc  : 发票卡券模板基础信息
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午6:50:51
 */
public class BaseInvoice {
	// 是  发票商家LOGO，请参考上传图片接口(  TempMaterialService 3.新增永久素材(上传)——上传永久图片——上传图文消息内的图片获取URL  )
	private String logo_url;    
	// 是  收款方（显示在列表），上限为9个汉字，建议填入商户简称
	private String title;
	// 否 发票使用说明。可以介绍电子发票的背景、报销使用流程等
	private String description;
	// 否  开票平台自定义入口名称，与custom_url字段共同使用，长度限制在5个汉字内
	private String custom_url_name;
	// 否 开票平台自定义入口跳转外链的地址链接, 发票外跳的链接会带有发票参数，用于标识是从哪张发票跳出的链接，详情请见备注6.2。
	private String custom_url;
	// 否  营销场景的自定义入口
	private String promotion_url_name;
	// 否  入口跳转外链的地址链接，发票外跳的链接会带有发票参数，用于标识是从那张发票跳出的链接，详情请见备注6.2。
	private String promotion_url;
	// 否  显示在入口右侧的tips，长度限制在6个汉字内
	private String promotion_url_sub_title;
	
	
	
	
	
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCustom_url_name() {
		return custom_url_name;
	}
	public void setCustom_url_name(String custom_url_name) {
		this.custom_url_name = custom_url_name;
	}
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public String getPromotion_url_name() {
		return promotion_url_name;
	}
	public void setPromotion_url_name(String promotion_url_name) {
		this.promotion_url_name = promotion_url_name;
	}
	public String getPromotion_url() {
		return promotion_url;
	}
	public void setPromotion_url(String promotion_url) {
		this.promotion_url = promotion_url;
	}
	public String getPromotion_url_sub_title() {
		return promotion_url_sub_title;
	}
	public void setPromotion_url_sub_title(String promotion_url_sub_title) {
		this.promotion_url_sub_title = promotion_url_sub_title;
	}

}
