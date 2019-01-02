$().ready(function (K) {
	// 全局变量
	window.appmsgIndex = 0;
	window.appmsg = $("#appmsgItem1");
	window.delResId = [];
	
	// 删除封面图
	$("#delImg").click(function() {
		$(".default-tip", window.appmsg).show();
		$("#imgArea").hide();
		$(".cover_url", window.appmsg).val("");
		$(".thumb_media_id", window.appmsg).val("");
		$(".thumbWechatUrl", window.appmsg).val(""); 
		$(".i-img", window.appmsg).hide();
	});

	// 标题
	$(".msg-editer #title").bind("keyup", function() {
		$(".i-title", window.appmsg).text($(this).val());
		$(".title", window.appmsg).val($(this).val());
	});
	
	// 作者
	$(".msg-editer #author").bind("keyup", function() {
		$(".author", window.appmsg).val($(this).val());
	});
	
	// 是否显示封面
	$(".msg-editer #cover_show").bind("mousedown",function() {
		var cover_show_val = $(this).attr("checked") == "checked" ? 0 : 1;
		console.log(cover_show_val);
		$(".cover_show", window.appmsg).val(cover_show_val);
	});
	
	// 添加文章来源
	$("#url-block-link").click(function() {
		$("#url-block").show();
		$(this).hide();
	});
	
	// 文章来源
	$(".msg-editer #from_url").bind("keyup", function() {
		$(".from_url", window.appmsg).val($(this).val());
	});
	
	
	// 添加蒙版样式
	$("#appmsgItem1, .sub-msg-item").live({
		mouseover: function() {
			$(this).addClass("sub-msg-opr-show");
		},
		mouseout: function() {
			$(this).removeClass("sub-msg-opr-show");
		}
	});
	
	// 增加子图文
	$(".sub-add-btn").click(function() {
		var len = $(".sub-msg-item").size();
		if (len >= 7) {
			alert("最多只能加入8条图文信息")
			return
		}
		var $lastItem = $(".sub-msg-item:last");
		var $newItem = $lastItem.clone();
		$("input, textarea", $newItem).val("");
		$(".i-title", $newItem).text("");
		$(".default-tip", $newItem).css("display", "block");
		$(".cover_url", $newItem).val("");
		$(".thumb_media_id", $newItem).val("");
		$(".thumbWechatUrl", $newItem).val("");
		$(".content", $newItem).val("");
		$(".i-img", $newItem).hide();
		$(".dbid", $newItem).remove();
		$lastItem.after($newItem);
	});
	
	// item 编辑
	$(".sub-msg-opr .edit-icon").live("click", function() {
		
		window.appmsg.find(".content").val(CKEDITOR.instances.content.getData());
		var $msgItem = $(this).closest(".appmsgItem");
		var index = $(".appmsgItem").index($msgItem);
		
		window.appmsgIndex = index;
		window.appmsg = $msgItem;
		// 标题
		$("#title").val($(".title", $msgItem).val());
		
		// 作者
		$("#author").val($(".author", $msgItem).val());
		

		// 封面
		if ($(".cover_url", $msgItem).val() == "") {
			$("#imgArea").hide();
		} else {
			$("#imgArea").show().find("#imgShowPciture").attr("src", $(".cover_url", $msgItem).val());
		}
		
		// 封面是否显示
		if($(".cover_show", $msgItem).val() == 1){
			$("#cover_show").attr("checked", true);
		} else {
			$("#cover_show").attr("checked", false);
		}
		
		// 加载内容
		//CKEDITOR.instances.content.getData()
		CKEDITOR.instances.content.setData($(".content", $msgItem).val());

		// 文章来源
		if ($(".from_url", $msgItem).val() == "") {
			$("#url-block-link").show();
			$("#url-block").hide().find("#from_url").val("")
		} else {
			$("#url-block-link").hide();
			$("#url-block").show().find("#from_url").val($(".from_url", $msgItem).val())
		}
		
		// 位置
		if (index == 0) {
			$(".msg-editer-wrapper").css("margin-top", "0px")
		} else {
			var top = 110 + $(".sub-msg-item").eq(0).outerHeight(true) * index;
			$(".msg-editer-wrapper").css("margin-top", top + "px")
		}
	});
	
	// item 删除
	$(".sub-msg-opr .del-icon").live("click", function() {
		var len = $(".appmsgItem").size();
		
		if (len <= 2) {
			$.utile.tips("无法删除，多条图文至少需要2条消息。")
			return
		}

		if (confirm("确认删除此消息？")) {
			var $msgItem = $(this).closest(".sub-msg-item");
			var dbid=$(".dbid", $msgItem).val();
			if(null!=dbid){
				$.post("/WeixinNewsItem/deleteNewsItem?dbid="+dbid+"&date="+new Date(),{},function (data){
					
				})
			}
			if ($(".dbid", $msgItem).size() > 0) {
				window.delResId.push($(".dbid", $msgItem).val())
			}
			$msgItem.remove()
		}
	});
	
	function smt($submit,url,preview){
		var valid = true;
		var $msgItem;
		var jsonData = [];
		
		$(".appmsgItem").each(function(index, msgItem) {
			
			$msgItem = $(msgItem);
			//标题
			var title = $("input.title", $msgItem).val();
			//作者
			var author = $("input.author", $msgItem).val();
			//封面
			var cover_url = $("input.cover_url", $msgItem).val();
			//是否在正文问展示封面
			var cover_show = $("input.cover_show", $msgItem).val();
			//内容
			var content = $("textarea.content", $msgItem).val();
			//微信公众号远程多媒体ID
			var thumb_media_id = $("input.thumb_media_id", $msgItem).val();
			//微信公众号远程连接地址
			var thumbWechatUrl = $("input.thumbWechatUrl", $msgItem).val();
			//原文链接
			var from_url = $("input.from_url", $msgItem).val();
			
			if (title == "") {
				$.utile.tips("标题不能为空")
				valid = false;
				return false
			}

			if (cover_url == "") {
				$.utile.tips("必须上传一个封面图片")
				valid = false;
				return false
			}

			if (content == "") {
				$.utile.tips("正文不能为空")
				valid = false;
				return false
			}

			jsonData[index] = {
				title: title,
				author: author,
				thumbUrl: cover_url,
				show_cover_pic: cover_show,
				content: content,
				content_source_url: from_url,
				thumb_media_id: thumb_media_id,
				thumbWechatUrl: thumbWechatUrl
			};
			
			if ($(".dbid", $msgItem).size() > 0) {
				jsonData[index].dbid = $(".dbid", $msgItem).val()
			}
		});

		if (!valid) {
		 	$(".edit-icon", $msgItem).click();
		 	return false
		}

        $submit.attr("disabled", true);
		var sumbitData = {
			wechatTemplateDbid: $("input[name='wechatTemplateDbid']").val(),
			jsonData: $.toJSON(jsonData),
			  
		};
		 
		if (window.delResId.length > 0) {
		 	sumbitData.delResId = $.toJSON(window.delResId)
		}
		if(preview==1||preview==3){
			$.post(url, sumbitData, function(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					$.utile.tips(data[0].message);
					setTimeout(
							function() {
								window.location.href = data[0].url
							}, 1000);
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					$.utile.tips(data[0].message);
					$submit.attr("disabled", false);
					// 保存失败时页面停留在数据编辑页面
				}
			},"json")
		}
		if(preview==2){
			$.post(url, sumbitData, function(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					var templateIdValue=data[0].url;
					$("#newsItemTemplateId").val(templateIdValue);
					$("#wechatTemplateDbid").val(templateIdValue);
					// 保存数据成功时页面需跳转到列表页面
					top.art.dialog({
					    title: '设置图片名称',
					    content: document.getElementById('templateId'),
					    lock : true,
						fixed : true,
					    ok: function () {
					    	var wechatId=window.parent.document.getElementById("wechatId").value;
					    	var newsItemTemplateId=window.document.getElementById("newsItemTemplateId").value;
					    	if(null==wechatId||wechatId==''){
					    		$(window.parent.document.getElementById("imageTr")).css("color","red");
					    		$(window.parent.document.getElementById("imageTr")).find("input").css("border-color","red");
					    		$(window.parent.document.getElementById("messageError")).show();
					    		return false;
					    	}
					    	var url='${ctx}/wechatSendMessage/preview';
					    	var params={"wechatId":wechatId,"newsItemTemplateId":newsItemTemplateId};
					    	$.post(url,params,function callBack(data) {
								if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
									$.utile.tips(data[0].message);
									 $submit.attr("disabled", true);
									return true;
								}
								if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
									$.utile.tips(data[0].message);
									// 保存失败时页面停留在数据编辑页面
								}
							});
					    },
					    cancel:function(){
							return true;
					    }
				})
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					$.utile.tips(data[0].message);
					$submit.attr("disabled", false);
					// 保存失败时页面停留在数据编辑页面
				}
			},"json")
		}
		return false;
	}
	
	
	$("#save-btn").click(function() {
		var $submit = $(this);
		var url="/WeixinNewsItem/saveMore";
		smt($submit,url,1);
	});
	$("#preview").click(function() {
		var $submit = $(this);
		var url="/WeixinNewsItem/saveMore?previewStatus=1";
		smt($submit,url,2);
	});
	$("#saveAndSend").click(function() {
		var $submit = $(this);
		var url="/WeixinNewsItem/saveMore?previewStatus=2";
		smt($submit,url,3);
	});
	
});