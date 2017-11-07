<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="com.ray.weixin.gz.config.*"%>
<%@page language="java" import="com.alibaba.fastjson.JSONObject"%>
<%@page language="java" import="com.ray.weixin.gz.util.*"%>
<%@page language="java" import="com.ray.weixin.gz.service.user.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>身份认证</title>
<script src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

</head>
<body>



<%
         //1.接收code和state
         String code= request.getParameter("code");
         String state=request.getParameter("state");
         
         //2.获取网页授权时的accessToken 以及 openId
         JSONObject jsonObject = AuthHelper.getAccessTokenByCode(Env.APP_ID,Env.APP_SECRET, code);
         String accessToken=jsonObject.getString("access_token");
         String openId=jsonObject.getString("openid");
         
         //3.拉取用户信息
         JSONObject userInfoJsonObject =UserService.getSNSUserInfo(accessToken, openId);
         
   
%>

hello，这里是第三方应用

code=<%= code%>    <br>
state=<%= state%>      <br>
nickname=<%= userInfoJsonObject.getString("nickname") %>    <br><br>
sex=<%= userInfoJsonObject.getString("sex") %>    <br><br>
headimgurl=<%= userInfoJsonObject.getString("headimgurl") %>    <br><br>

</body>
</html>