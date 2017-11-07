package com.ray.weixin.gz.model.menu;  
  
/** 
 * view类型的菜单 
 *  
 * @author liuyq 
 * @date 2013-04-10 
 */  
public class ViewButton extends Button {  
    private String type;  
    private String url;  
  
    public String getType() {  
        return type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
}  