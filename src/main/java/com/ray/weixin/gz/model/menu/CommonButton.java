package com.ray.weixin.gz.model.menu;  
  
/** 
 * 普通按钮（子按钮） 
 *  
 * @author liufeng 
 * @date 2013-08-08 
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