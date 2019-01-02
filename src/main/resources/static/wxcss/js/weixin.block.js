$().ready(function (K) {

	$('button.select_img').live("click", function (e) {
        window.editor.loadPlugin('smimage', function () {
            window.editor.plugin.imageDialog({
                imageUrl: $(e.target).parent().prevAll("input[type=hidden]").val(),
                clickFn: function (url, title, width, height, border, align) {
                    $(".cover .i-img").attr("src", url).show();
					$("#imgArea").show().find(" #cover_view").attr("src", url);
					$("input[name='cover_url']").val(url);
					$(".default-tip").hide();
                    $(e.target).text("重新选择")
                    window.editor.hideDialog();
                }
            });
        });
    });

	// 标题预览
	$(".msg-editer #title").bind("keyup", function() {
		$(".i-title").text($(this).val())
	});

	// 描述预览
	$(".msg-editer #summary").bind("keyup", function() {
		$(".i-summary").text($(this).val())
	});

	// 添加描述
	$("#desc-block-link").click(function() {
		$("#desc-block").show();
		$(this).hide()
	});

	// 添加链接
	$("#url-block-link").click(function() {
		$("#url-block").show();
		$(this).hide()
	});

	// 删除图片
	$("#delImg").click(function() {
		$(".default-tip").show();
		$("#imgArea").hide();
		$("#fileUpload").val("");
		$(".cover .i-img").hide()
		$("#uploadFileContent").hide()
	});

	
	ActBlock = {
		postSubmit: function(form, ajax_url, redirect_url) {

			var $form = $(form);
            var $submit = $form.find('input[type=submit]');

            if ($("#cover_url").val() == "") {
				YS.ui.tips.info("必须上传一张图片")
				return false
			}
			var editorContent = window.editor.html();
			if (editorContent.length <= 0 || editorContent > 20000) {
				YS.ui.tips.info("正文的内容必须填写且不能超过20000个字")
				return false
			}

            $submit.attr("disabled", true);

			var submitData = {
				
				// 新增 还是 编辑 
				action: $("input[name='action']", $form).val(),
				
				// 类型
				typeid: $("input[name='typeid']", $form).val(),
				
				// 标题
				title: $("input[name='title']", $form).val(),
				
				// 作者
				author: $("input[name='author']", $form).val(),
				
				// 封面图片地址
				cover_url: $("input[name='cover_url']", $form).val(),
				
				// 封面是否显示
				cover_show: $("input[name='cover_show']:checked", $form).val(),
				
				// 描述
				summary: $("textarea[name='summary']", $form).val(),
				
				// 编辑器-正文
				content: editorContent,
				
				// 原文链接
				from_url: $("input[name='from_url']", $form).val(),
				
				// 文章地址---编辑时
				link: $("input[name='link']", $form).val(),
				
				// 文章id
				aid: $("input[name='aid']", $form).val(),
				
				// block-id
				bid: $("input[name='bid']", $form).val() 
			};
	
			$.post(ajax_url, submitData, function(data) {
				if (data.code == '100') {
					YS.ui.tips.suc("保存成功", redirect_url)
				} else {
					if (data.msg) {
						YS.ui.tips.info(data.msg)
					} else {
						YS.ui.tips.err("保存失败")
                        $submit.attr("disabled", false);
					}
				}
			}, 'json');
	
			return false;
		}
	}
});