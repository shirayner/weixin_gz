package com.ray.mytest;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午5:59:26
 */
public class MyTest {

	@Test
	public void testJson1() {
		Object ob=new Object();
		String data=JSON.toJSONString(ob);
		System.out.println(data);
	}

	
	/** 
{
    "money": 123, 
    "s_pappid": "djwhei124"
}
	 * @desc ：测试构造json
	 *   void
	 */
	@Test
	public void testJson2() {
		Object ob=new Object();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("s_pappid", "djwhei124");
		jsonObject.put("money", ob);
		
		System.out.println(jsonObject.toJSONString());
		System.out.println(JSON.toJSONString(jsonObject));
	}
	
	@Test
	public void testJson3() {

		String str="{ " + 
				"  \"openid\": [ " + 
				"      \"o1Pj9jmZvwSyyyyyyBa4aULW2mA\",  " + 
				"      \"o1Pj9jmZvxxxxxxxxxULW2mA\" " + 
				"               ], " + 
				"  \"username\": [ " + 
				"      \"afdvvf\", " + 
				"      \"abcd\" " + 
				"                ] " + 
				" }";
		
		JSONObject jsonObject=JSON.parseObject(str);
		System.out.println(jsonObject.toJSONString());
	}
	
	@Test
	public void testOpenIdLengths() {

		String str1="oDLefxDvQajibXhSWIWHrkiO1n2o";
		String str2="ocYxcuAEy30bX0NXmGn4ypqx3tI0";
		String str3="ocYxcuBt0mRugKZ7tGAHPnUaOW7Y";
		String str4="ocYxcuBt0mRugKZ7tGAHPnUaOW7Y";
		
		System.out.println(str1.length());
		System.out.println(str2.length());
		System.out.println(str3.length());
		System.out.println(str4.length());

	}
	
}
