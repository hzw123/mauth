<#include "../../commons/macro.ftl">
<@commonHead/>
<title>公众账号设置添加</title>
<link rel="stylesheet" href="${ctx}/wxcss/css/block.css"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/block-mul.css"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/global.css?da=${now}"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/font-awesome.min.css"/>
</head>
<body>
<h4 class="headline"><i class="fa fa-home"></i> 新增单图文 <span class="line"></span> </h4>
<div class="block-wrap">
	<div class="pull-left msg-preview" style="width:330px;">
      <div class="msg-item-wrapper">
        <div id="appmsgItem1" class="msg-item">
          <h4 class="msg-t"> <span class="i-title">${WeixinNewsItem.title }</span> </h4>
          <p class="msg-meta"> <span class="msg-date">${WeixinNewsTemplate.addtime }</span> </p>
          <div class="cover">
          		<c:if test="${empty(WeixinNewsItem.thumbUrl) }">
	            	<p class="default-tip" style="">封面图片</p>
	            </c:if>
          		<c:if test="${!empty(WeixinNewsItem.thumbUrl) }">
	            	<p class="default-tip" style="display: none;">封面图片</p>
	            </c:if>
	            <img class="i-img" src="${WeixinNewsItem.thumbUrl }" id="fileUploadImage"> </div>
	          <p class="i-summary"></p>
        </div>
        <div class="msg-hover-mask"></div>
        <div class="msg-mask"> <span class="dib msg-selected-tip"></span> </div>
      </div>
    </div>
    <div class="pull-left" style="width: 60%;">
      <div class="msg-editer-wrapper" style="width: 100%;">
        <div class="msg-editer" style="width: 100%;">
          <form id="frmId" name="frmid" method="post">
          <input type="hidden" id="dbid" name="WeixinNewsItem.dbid" value="${WeixinNewsItem.dbid }">
          <input type="hidden" id="newsItemTemplateId" name="newsItemTemplateId" value="${WeixinNewsItem.dbid }">
          <input type="hidden" id="wechatTemplateDbid" name="wechatTemplateDbid" value="${WeixinNewsTemplate.dbid }">
          <input type="hidden" id="type" name="type" value="1"> 
          <div class="msg-content" style="width: 100%;">
	          <div class="control-group">
				<label class="control-label">
	            标题<span class="maroon">*</span><span class="help-inline">(不能超过64个字)</span></label>
	            <div class="controls">
	                <input type="text" id="title" name="WeixinNewsItem.title" class="ui-input" value="${ WeixinNewsItem.title}" checkType="string,1" tip="输入标题"/>
	            </div>
	          </div>
            <label>作者<span class="help-inline">(选填)</span></label>
            <div class="msg-item msg-input">
                <input type="text" id="author" name="WeixinNewsItem.author" value="${WeixinNewsItem.author }" class="ui-input" />
            </div>
            <label>封面<span class="maroon">*</span><span class="help-inline">(大图片建议尺寸：900像素 * 500像素)</span></label>
            <div class="msg-item msg-input">
                <div class="cover-area">
                  <div class="cover-hd">
                     	<a  href="#modal-add-event" class="btn btn-success btn-mini " onclick="uploadFileWechat()"><i class="icon-plus icon-white"></i>上传图片</a>
                     	<button type="button" class="layui-btn" id="test1">上传封面</button>
                     	<input id="fileUpload" type="hidden" name="WeixinNewsItem.thumbUrl" value="${WeixinNewsItem.thumbUrl }">
                     	<input id="thumbWechatUrl" type="hidden" name="WeixinNewsItem.thumbWechatUrl" value="${WeixinNewsItem.thumbWechatUrl }">
                     	<input id="thumb_media_id" type="hidden" name="WeixinNewsItem.thumb_media_id" value="${WeixinNewsItem.thumb_media_id }">
                  </div>
                  <div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
                  <!-- <p id="upload-tip" class="upload-tip">大图片建议尺寸：700像素 * 300像素</p> -->
                 <c:if test="${empty(WeixinNewsItem.thumbUrl) }">
	                  <p id="imgArea" class="cover-bd" style="display: none;border-top: none;">
	                    <img src="${WeixinNewsItem.thumbUrl }" id="cover_view">
	                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
	                  </p>
                  </c:if>
                 <c:if test="${!empty(WeixinNewsItem.thumbUrl) }">
	                  <p id="imgArea" class="cover-bd" style="border-top: none;">
	                    <img src="${WeixinNewsItem.thumbUrl }" id="cover_view">
	                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
	                  </p>
                  </c:if>
                 <c:if test="${empty(WeixinNewsItem)}">
        		  <label class="checkbox inline"><input type="checkbox" name="WeixinNewsItem.show_cover_pic" value="1" checked="checked"><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> </label>
        		 </c:if>
                 <c:if test="${!empty(WeixinNewsItem)}">
        		  <label class="checkbox inline"><input type="checkbox" name="WeixinNewsItem.show_cover_pic" value="1" ${WeixinNewsItem.show_cover_pic==1?'checked="checked"':''} ><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> </label>
        		 </c:if>
                </div>
            </div>
            <c:if test="${empty(WeixinNewsItem.digest) }">
	            <a id="desc-block-link" class="block mt10 mb10">添加摘要</a>
	            <div id="desc-block" style="display:none;">
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="digest" class="ui-input" name="WeixinNewsItem.digest" rows="3"></textarea>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(WeixinNewsItem.digest) }">
	            <div id="desc-block" >
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="digest" class="ui-input" name="WeixinNewsItem.digest" rows="3">${WeixinNewsItem.digest }</textarea>
	              </div>
	            </div>
            </c:if>
             <div class="control-group">
					<label class="control-label">
            		正文<span class="maroon">*</span><span class="help-inline">(不能超过20000个字)</span></label>
		            <div class="msg-item msg-text">
		                <textarea id="content" name="contentArea" checkType="string,1" tip="请输入正文">
		                	<c:if test="${empty(WeixinNewsItem.content) }">
				                <p>这是正文</p>
		                	</c:if>
		                	<c:if test="${!empty(WeixinNewsItem.content) }">
				                ${WeixinNewsItem.content }
		                	</c:if>
		               	</textarea>
		            </div>
		      </div>
            <c:if test="${empty(WeixinNewsItem.show_cover_pic) }">
	            <a id="url-block-link" class="block mt10 mb10">添加原文链接</a>
	            <div id="url-block" style="display:none;">
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="msg-item msg-input">
	                  <input type="text" class="ui-input" id="content_source_url" name="WeixinNewsItem.content_source_url" value="${WeixinNewsItem.content_source_url }"/>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(WeixinNewsItem.show_cover_pic) }">
	            <div id="url-block" >
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="msg-item msg-input">
	                  <input type="text" class="ui-input" name="WeixinNewsItem.content_source_url" value="${WeixinNewsItem.content_source_url }"/>
	              </div>
	            </div>
            </c:if>
            <div class="msg-item msg-button">
                <a type="submit" class="btn btn-primary" onclick="smbFm()" >保  存</a>
                <a class="btn btn-default" onclick="preview()">
					预览
				</a> 
                <a class="btn btn-default" onclick="smbFm(2)">
					保存并群发
				</a> 
                 <a class="btn btn-inverse" onclick="window.history.go(-1)">
					返回</a>
            </div>
          </div>
          </form>
        </div>  
        <span class="abs msg-arrow a-out" style="margin-top: 0px;"></span> 
        <span class="abs msg-arrow a-in" style="margin-top: 0px;"></span>          
      </div>
    </div>
</div>
<div style="display: none; width: 540px;" id="templateId">
	<table id="noLine" border="0" align="center" cellpadding="0" cellspacing="0" style="width: 320px;margin-top: 5px;">
		<tr style="height: 40px;" height="20">
			<td id="messageError" colspan="3" style="text-align: left;color: red;" width="280">
				关注公众号后，才能接收图文消息预览
			</td>
		</tr>
		<tr style="height: 40px;" height="30" id="imageTr">
			<td colspan="3" style="text-align: left;" width="280">
				<input type="text" id="wechatId" name="wechatId" value="" placeholder="请输入微信号/QQ号/手机号" style="width: 320px;">
			</td>
		</tr>
		<tr style="height: 20px;" height="20">
			<td id="messageError" colspan="3" style="text-align: left;display: none;color: red;" width="280">
				填写名称错误！
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?date=1"></script>
<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script src="${ctx}/wxcss/js/global.js"></script>
<script src="${ctx}/wxcss/js/weixin.block.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//富文本插件
	var editor=CKEDITOR.replace("content",{filebrowserImageUploadUrl:"/wechatMediaImage/uploadImages?type=image"});
});
function smbFm(previewStatus){
	var fileUpload=$("#fileUpload").val();
	var title=$("#title").val();
	var url=$("#thumbUrl").val();
	if(null==title||title==""){
		alert("请填写标题！");
		return false;
	}
	if(null==fileUpload||fileUpload==""){
		alert("请先上传封面图片！");
		return false;
	}
	var url="";
	if(null!=previewStatus&&previewStatus==2){
		url="${ctx}/WeixinNewsItem/save?previewStatus="+previewStatus;
	}else{
		url="${ctx}/WeixinNewsItem/save";
	}
	$.utile.submitForm('frmId',url,true);
}
function checkWebUrl(obj){
	return true;
	var value = obj.attr("value");
	if (obj.attr('canEmpty') == "Y" && value.length == 0)
		return true;
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"  
		        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
		        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
		        + "|" // 允许IP和DOMAIN（域名） 
		       + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
		        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
		        + "[a-z]{2,6})" // first level domain- .com or .museum  
		        + "(:[0-9]{1,4})?" // 端口- :80  
		        + "((/?)|" // a slash isn't required if there is no file name  
		        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
	var re=new RegExp(strRegex); 
     if (!re.test(value)) {
 		return false;
     }
 } 
function getParam2(frmId, state) {
	// 富文本编辑器取数据
	var ckeditorState = state || false;
	if (ckeditorState == true) {
		$('#content').val(CKEDITOR.instances.content.getData());
	}
	var params = $("#" + frmId).serialize();
	return params;
}
function preview(){
	var fileUpload=$("#fileUpload").val();
	var url=$("#thumbUrl").val();
	if(null==fileUpload||fileUpload==""){
		alert("请先上传封面图片！");
		return false;
	}
	if(null!=url&&url!=""){
		var stu=checkWebUrl(url);
		if(stu==false){
			alert("请输正确的超链接格式！");
			return false;
		}
	}
	var params = getParam2("frmId", true);
	var url2="${ctx}/WeixinNewsItem/save?previewStatus=1";
	$.ajax({	
		url : "${ctx}/WeixinNewsItem/save?previewStatus=1",
		data : params, 
		async : false, 
		timeout : 20000, 
		dataType : "json",
		type:"post",
		success : function(data, textStatus, jqXHR){
			//alert(data.message);
			var obj;
			if(data.message!=undefined){
				obj=$.parseJSON(data.message);
			}else{
				obj=data;
			}
			if(obj[0].mark==1){
				//错误
				$.utile.tips(obj[0].message);
				$(this).find(".btn-default").attr("onclick",url2);
				return ;
			}else if(obj[0].mark==0){
				var templateIdValue=obj[0].url;
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
				});
			}
		},
		complete : function(jqXHR, textStatus){
			$(this).find(".btn-default").attr("onclick",url2);
			var jqXHR=jqXHR;
			var textStatus=textStatus;
		}, 
		beforeSend : function(jqXHR, configs){
			url2=$(".butSave").attr("onclick");
			$(this).find(".btn-default").attr("onclick",url2);
			var jqXHR=jqXHR;
			var configs=configs;
		}, 
		error : function(jqXHR, textStatus, errorThrown){
				$.utile.tips("系统请求超时");
				$(this).find(".btn-default").attr("onclick",url2);
		}
	});
}
function uploadFileWechat(){
	var path="";
	layer.open({
        type: 2 //此处以iframe举例
        ,title: '选择文件'
        ,area: ['760px', '460px']
        ,shade: 0.8
        ,maxmin: true
        ,content: path+'/wechatMedia/uploadConpentWechat'
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
          //layer.setTop(layero); //重点2
        }
        ,btn: ['确定', '关闭']
        ,yes: function(index, layero){
        	var body = layer.getChildFrame('body', index);
			var json= body.find('#fileUpload').val();
			if(null!=json&&json.length>0){
				var obj=$.parseJSON(json);
				/* $("#thumbUrl").val(obj.thumbUrl);
				$("#thumb_media_id").val(obj.mediaId);
				$("#thumbWechatUrl").val(obj.thumbWechatUrl);
				$("#cboxClose").show();
    			 $(".i-img", window.appmsg).attr("src", obj.thumbUrl).show();
    			 $(".default-tip", window.appmsg).hide();
    			$(".cover_url", window.appmsg).val(obj.thumbUrl); 
    			$(".thumb_media_id", window.appmsg).val(obj.mediaId);
    			$(".thumbWechatUrl", window.appmsg).val(obj.thumbWechatUrl); 
    			$("#imgArea").show().find("#imgShowPciture").attr("src", obj.thumbUrl); */
    			
    			$("#imgArea").show();
				$(".default-tip").hide();
				$("#fileUploadImage").show()
				$("#fileUpload").val(obj.thumbUrl);
				$("#thumb_media_id").val(obj.mediaId);
				$("#thumbWechatUrl").val(obj.thumbWechatUrl);
				$("#fileUploadImage").attr("src",obj.thumbUrl);
				$("#cover_view").attr("src",obj.thumbUrl);
		        $("#cboxClose").show();
		       	layer.close(index);
	       }else{
	    	   alert("请选择图片后点击【确定】按钮");
	    	   layer.close(index);
	       }
        }
        ,btn2: function(index, layero){
        	layer.close(index);
          //return false 开启该代码可禁止点击该按钮关闭
        }
      });
}
</script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate;
	  if(topParent==undefined){
			 layer = layui.layer;
		  }else{
			 layer = topParent.layer;
		  }
	  var $ = layui.$;
	  var uploadInst = upload.render({
	    elem: '#test1'
	    ,url: '/wechatMedia/uploadConpentWechat'
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo1').attr('src', result); //图片链接（base64）
	      });
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	        return layer.msg('上传失败');
	      }
	      //上传成功
	      	//$("#fileUpload").val(res.url);
		  	$("#imgArea").show();
			$(".default-tip").hide();
			$("#fileUploadImage").show()
			$("#fileUpload").val(res.url);
			$("#fileUploadImage").attr("src",res.url);
			$("#cover_view").attr("src",res.url);
			
			var uploadState="<span id='state' class='stateSuccess'>上传成功</span>";
			$("#uploadState").text("");
			$("#uploadState").append(uploadState);
			$("#method").show("fast");
			//从新绑定删除方法
			$("#method").unbind("click");
			$("#method").bind("click",function(e){
				$.post('${ctx}/swfUpload/deleteFile?fileUrl='+encodeURI(encodeURI(res.url))+"&timeStamp="+new Date(),function(data){
					if(data=="success"){
						$(".default-tip").show();
						$("#imgArea").hide();
						$("#fileUpload").val("");
						$(".cover .i-img").hide()
						$("#uploadState").slideUp('fast');
						$("#method").slideUp('fast');
					}if(data=="failed"){
						var uploadState="<span id='state' class='stateSuccess'>文件删除失败</span>";
						$("#uploadState").text("");
						$("#uploadState").append(uploadState);
						//绑定上传文件队列删除方法
						$("#method").unbind("click");
					}
				});
			});
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
	      });
	    }
	  });
	  //自定义验证规则
	  form.verify({
		 userId: function(value){
		      if(value.length < 3){
		        return '用户ID不能为空,并且3-20个字符';
		      }
		      if(value.length >20){
		        return '用户ID不能为空,并且3-20个字符';
		      }
	    },
	    realName: function(value){
		      if(value.length < 2){
		        return '真实姓名不能为空,并且2-5个字符';
		      }
		      if(value.length >5){
		        return '真实姓名不能为空,并且2-5个字符';
		      }
	    },
	    integer:function(value){
	    	if(value!=""){
		    	if (!(/^([-]){0,1}([0-9]){1,}$/.test(value))) {
		    		return "序号必须为数字";
		    	}
	    	}
	    }
	  });
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var fileUpload=$("#fileUpload").val();
			var url=$("#url").val();
			if(null==fileUpload||fileUpload==""){
				alert("请先上传封面图片！");
				return false;
			}
		  //$.utile.submitForm('frmId','',true);
		  submitFrm('${ctx}/weixinNewsItem/save',true);
		  return false
	  });
	});
</script>
</body>
</html>
