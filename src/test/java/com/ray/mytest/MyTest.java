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
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("s_pappid", "djwhei124");
		jsonObject.put("money", 123);
		
		System.out.println(jsonObject.toJSONString());
		System.out.println(JSON.toJSONString(jsonObject));
	}
	
	
}
