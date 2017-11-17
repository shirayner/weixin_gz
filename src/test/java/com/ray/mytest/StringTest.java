package com.ray.mytest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年11月8日 下午5:30:25
 */
public class StringTest {
	private static final Logger log = LogManager.getLogger(StringTest.class);

	@Test
	public void testString_contains () {
		String str="<xml><ToUserName><![CDATA[ww92f5da92bb24696e]]></ToUserName><Encrypt><![CDATA[YOvPfwOW6XBiNG/nhg+7nWu3juNF6St0cXekXwwQ4IrVJw9DX3EK+fHkjp8HDDXw2QupoyrANX2n/lRPYDh9Aa0awDAtvCmOSq0DhMODVyetPVzPZAQCo+iprfzi3u4NfbTc7vzqdACmdgSo2L8135y8fReJUNeTyHmy1Otk0g6G8bJDqRa/tEEpM1PFWHZx1M8r8k4SQ4EhYI17bbqivyYhbMlAh1WKW3lid4xzxb7PpGXt7Y/1n2XvnVTQRBQuagF6S40gX+taSo+oKFnlBGklegznpdXbj/j7qgMRd1ZwEpeSgsGz9OqfFDA6rFy9sFn1czlwCzeEABy2r7vGO2jIv/GDyGRyrBxVhHeFk+CiqAFtVIEjHiZXzK7f2v15NIp+X8/V0sYnKjfEbEPiG/FA7RwvBAp6GHNmjqpHbco=]]></Encrypt><AgentID><![CDATA[1000002]]></AgentID></xml>";
		if(str.contains("Encrypt")) {
			log.info("ture");
		}
	}

	@Test
	public void testString_2 () {
		String res="[{\"card_id\":\"pEVWfuPFrMuU3fkx5iWQeSBSefTg\",\"encrypt_code\":\"O\\/mPnGTpBu22a1szmK2ogzhFPBh9eYzv2p70L8yzyymmmDDmLBJfmhaRYC2+Ac1QqztRDyQ9SVEU5gdknT+Dks8RPfvOVTVRVykyQ8pe+bA9vWT04nL0w\\/YDmtAnfZ4+Rtvt55ZfNt5zQuiukRmETg==\",\"app_id\":\"wxbc0799a47795854a\"}]";
		System.out.println(res);

		String str="\"[{\\\"card_id\\\":\\\"pEVWfuPFrMuU3fkx5iWQeSBSefTg\\\",\\\"encrypt_code\\\":\\\"O\\\\/mPnGTpBu22a1szmK2ogzhFPBh9eYzv2p70L8yzyymmmDDmLBJfmhaRYC2+Ac1QqztRDyQ9SVEU5gdknT+Dks8RPfvOVTVRVykyQ8pe+bA9vWT04nL0w\\\\/YDmtAnfZ4+Rtvt55ZfNt5zQuiukRmETg==\\\",\\\"app_id\\\":\\\"wxbc0799a47795854a\\\"}]\"";
		System.out.println(str);

		String str2=StringEscapeUtils.unescapeJava(str);
		System.out.println(str2);

		String str3=str2.substring(1,str2.length() - 1);
		System.out.println(str3);

		//JSONObject js=JSON.parseObject(str3);
		JSONArray js=JSON.parseArray(str3);
		JSONArray js2=new JSONArray();
		for(int i=0;i<js.size();i++) {

			JSONObject js3=JSON.parseObject(js.get(i).toString());
			js3.remove("app_id");
			System.out.println("js3:"+js3);
			js2.add(js3);
		}
		
		System.out.println("js2:"+js2);








	}



}
