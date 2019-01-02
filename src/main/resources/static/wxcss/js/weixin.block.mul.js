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
			if ($(".dbid", $msgItem).size() > 0) {
				window.delResId.push($(".dbid", $msgItem).val())
			}
			$msgItem.remove()
		}
	});
	
	
	$("#save-btn").click(function() {

		var $submit = $(this);
		var valid = true;
		var $msgItem;
		var jsonData = [];
		
		$(".appmsgItem").each(function(index, msgItem) {
			
			$msgItem = $(msgItem);
			
			var title = $("input.title", $msgItem).val();
			var author = $("input.author", $msgItem).val();
			var cover_url = $("input.cover_url", $msgItem).val();
			var cover_show = $("input.cover_show", $msgItem).val();
			var content = $("textarea.content", $msgItem).val();
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
				imagepath: cover_url,
				cover_show: cover_show,
				content: content,
				url: from_url
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
			weixinNewstemplateDbid: $("input[name='weixinNewstemplateDbid']").val(),
			jsonData: $.toJSON(jsonData),
			  
		};
		 
		if (window.delResId.length > 0) {
		 	sumbitData.delResId = $.toJSON(window.delResId)
		}
		$.post('/weixinNewsItem/saveMore', sumbitData, function(data) {
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
		return false;
	});
});