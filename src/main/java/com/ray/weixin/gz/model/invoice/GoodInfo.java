package com.ray.weixin.gz.model.invoice;

/**@desc  : 商品详情
 * 
 * @author: shirayner
 * @date  : 2017年11月7日 下午3:30:24
 */
public class GoodInfo {

	//1.是 项目的名称
	private String name;

	//2.是 项目的数量
	private int num;

	//3.是 项目的单位，如个
	private String unit;

	//4.是 项目的单价
	private int price;



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}



}
