package com.ray.weixin.gz.model.message.response;  
   
/**
 * @desc  : 音乐消息 
 * 
 * @author: shirayner
 * @date  : 2017年11月13日 上午11:12:06
 */
public class MusicMessage extends BaseMessage {  
    // 音乐  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }  
}  