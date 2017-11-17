<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信内拉起发票列表</title>
<script src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

<script type="text/javascript">

	var _config =
<%=com.ray.weixin.gz.util.AuthHelper.getJsapiConfig(request)%>
	;
	var invoice_config =
		<%=com.ray.weixin.gz.util.AuthHelper.getInvoiceConfig()%>
			;
	
</script>

<script type="text/javascript" src="js/auth.js"></script>


</head>
<body>

	<div align="center">
		<span class="desc">是否验证成功</span>
		<button class="btn btn_primary" id="yanzheng">验证</button>
	</div>

	<div align="center">
		<span class="desc">测试按钮</span>
		<button class="btn btn_primary" id="ceshi">测试</button>
	</div>

	<div align="center">
		<span class="desc">checkJsApi</span>
		<button class="btn btn_primary" id="checkJsApi">checkJsApi</button>
	</div>
	
	<div align="center">
		<span class="desc">上传图片按钮</span>
		<button class="btn btn_primary" id="uploadImg">上传图片</button>
	</div>

	<div align="center">
		<span class="desc">拍照上传图片按钮</span>
		<button class="btn btn_primary" id="uploadImgFromCamera">拍照上传</button>
	</div>

	<div align="center">
		<span class="desc">扫码按钮</span>
		<button class="btn btn_primary" id="qrcode">扫码</button>
	</div>

	
	<div align="center">
		<span class="desc">拉起发票列表</span>
		<button class="btn btn_primary" id="showInvoice">拉起发票列表</button>
	</div>
	


</body>
</html>