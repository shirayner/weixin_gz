package com.ray.weixin.gz.model.menu;  
  
/**
 *  @desc  : 普通按钮（子按钮） 
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:37:18
 */
public class CommonButton extends Button {  
    private String type;  
    private String key;  
  
    public String getType() {  
        return type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String getKey() {  
        return key;  
    }  
  
    public void setKey(String key) {  
        this.key = key;  
    }  
}  