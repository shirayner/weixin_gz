//1.jsapi签名校验
wx.config({
	debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId: _config.appId, // 必填，公众号的唯一标识
	timestamp: _config.timeStamp, // 必填，生成签名的时间戳
	nonceStr: _config.nonceStr, // 必填，生成签名的随机串
	signature: _config.signature,// 必填，签名，见附录1
	jsApiList: [ 'checkJsApi', 'onMenuShareAppMessage',
		'onMenuShareWechat', 'startRecord', 'stopRecord',
		'onVoiceRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice',
		'uploadVoice', 'downloadVoice', 'chooseImage',
		'previewImage', 'uploadImage', 'downloadImage',
		'getNetworkType', 'openLocation', 'getLocation',
		'hideOptionMenu', 'showOptionMenu', 'hideMenuItems',
		'showMenuItems', 'hideAllNonBaseMenuItem',
		'showAllNonBaseMenuItem', 'closeWindow', 'scanQRCode',
		'previewFile', 'openEnterpriseChat',
		'selectEnterpriseContact','chooseInvoice'

		]// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

//2.jsapi签名校验成功后执行ready
wx.ready(function(){
	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

	//2.1 提示jsapi签名验证成功
	$("#yanzheng").html("验证成功");  

	$("#ceshi").click(function(){
		alert("ceshiaaa");

	});


	//2.2 上传图片
	var images = {
			localId : [],
			serverId : []
	};
	$("#uploadImg").click(function(){
		//2.2.1拍照或从手机相册中选图
		wx.chooseImage({
			success : function(res) {
				images.localId = res.localIds;
				alert('已选择 ' + res.localIds.length + ' 张图片');
				//2.2.2 上传图片
				uploadImg();
			}
		});
	});

	// 2.2.2 上传图片
	function uploadImg() {
		if (images.localId.length == 0) {
			alert('请先使用 chooseImage 接口选择图片');
			return;
		}
		var i = 0, length = images.localId.length;
		images.serverId = [];

		function upload() {
			wx.uploadImage({
				localId : images.localId[i],
				success : function(res) {
					i++;
					alert('已上传：' + i + '/' + length);
					images.serverId.push(res.serverId);
					//将serverId上传至服务器
					alert("ajax请求即将执行--");

					$.ajax({
						type : "POST",
						url : "http://se9mxs.natappfree.cc/weixin_gz/uploadimg",
						data : {
							serverId : res.serverId
						},
						dataType : "text",
						success : function(data) {
							alert(data);
						}

					});


					if (i < length) {
						upload();
					}
				},
				fail : function(res) {
					alert(JSON.stringify(res));
				}
			});
		}
		upload();
	};

	//2.3 扫一扫
	$("#qrcode").click(function(){
		wx.scanQRCode({
			needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				alert(result);

			},
			fail:function (res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				alert(result);

			}
		});

	});


	//2.4 拉起发票列表
	$("#showInvoice").click(function(){
		wx.invoke('chooseInvoice', {
			'timestamp': 1489030247, //卡券签名时间戳
			'nonceStr': "p(6N&7WOAF", //卡券签名随机串
			'signType': 'SHA1', //签名方式，默认'SHA1'
			'cardSign': "a72043eed36c74300000000000000000" //卡券签名
		}, function(res) {
			alert(JSON.stringify(res));

		});

	});






});


//2.jsapi签名校验失败后执行error
wx.error(function(err){
	alert('wx error: ' + JSON.stringify(err));  
	// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});




