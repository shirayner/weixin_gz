package com.ray.weixin.gz.util;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年10月17日 下午12:18:36
 */
public class CommonUtils {

	/**
	 * 1.获取文件名后缀
	 */
	public static String getFileNameExtension(String fileName) {
		//一.方法一
		//String ext=fileName.split("\\.")[0];
		//System.out.println(ext);

		//二、方法二
		//1.获取最后一个点的下一个位置
		int beginIndex=fileName.lastIndexOf(".")+1;     //  D:/img/2.png
		//2.获取后缀名
		String ext=fileName.substring(beginIndex);     //  png

		return ext;
	}

	
	
}
