package com.ray.weixin.gz.model.menu;  
  
/**
 *  @desc  : view类型的按钮
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:37:42
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