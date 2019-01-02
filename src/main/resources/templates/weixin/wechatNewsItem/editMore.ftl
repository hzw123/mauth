<#include "../../commons/macro.ftl">
<@commonHead/>
<title>公众账号设置添加</title>

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link href="${ctx}/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/wxcss/css/block.css"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/block-mul.css"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/global.css?da=${now}"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/font-awesome.min.css"/>
<style type="text/css">
.uploadImage{
	display:block;
	margin-top: 5px;
}
#cboxClose {
    background-color: #000;
    border: 2px solid #fff;
    border-radius: 32px;
    color: #fff;
    font-size: 21px;
    height: 28px;
    margin-left: -15px;
    position: absolute;
    top: 14px;
    width: 28px;
    display: none;
}
</style>
</head>
<body>
<h4 class="headline"><i class="fa fa-home"></i> 编辑多图文 <span class="line"></span> </h4>
<div class="alert alert-error" style="margin: 0 auto;width: 92%;">
	提示：
	<p>由于微信端对已经上传图文接口进行了限制，在编辑是请不要<span style="font-size: 18px;color: red;">增加</span>新的文章信息。</p>
</div>
<div class="block-wrap">
	<div class="pull-left msg-preview" style="width:330px">
      <div class="msg-item-wrapper">
      		<c:set value="" var="wechatNews"></c:set>
			<c:forEach var="WeixinNewsItem" items="${WeixinNewsTemplate.wechatNewsitems }" varStatus="i">
				<c:if test="${i.index==0 }" var="status">
			        <div id="appmsgItem1" class="appmsgItem msg-item">
				          <p class="msg-meta"> <span class="msg-date"></span> </p>
				          <div class="cover">
					            <p class="default-tip"  style="display:none">缩略图</p>
					            <h4 class="msg-t"> <span class="i-title">${WeixinNewsItem.title }</span> </h4>
					            <ul class="abs tc sub-msg-opr">
					              <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon edit-icon">编辑</a> </li>
					            </ul>
					            <img class="i-img" src="${WeixinNewsItem.thumbUrl }" >
				          </div>
				          <p class="msg-text"></p>
				          <input type="hidden" class="dbid" value="${WeixinNewsItem.dbid }">
				          <input type="hidden" class="title"  value="${WeixinNewsItem.title }">
				          <input type="hidden" class="author"  value="${WeixinNewsItem.author }">
				          <input type="hidden" class="cover"  value="${WeixinNewsItem.dbid }">
				          <input type="hidden" class="cover_url"  value="${WeixinNewsItem.thumbUrl }">
				          <input type="hidden" class="cover_show"  value="${WeixinNewsItem.show_cover_pic }">
				          <textarea class="content" style="display: none;">${WeixinNewsItem.content }</textarea>
				          <input type="hidden" class="from_url" value="${ WeixinNewsItem.content_source_url}">
				          <input type="hidden" class="thumbWechatUrl" value="${ WeixinNewsItem.thumbWechatUrl}">
				          <input type="hidden" class="thumb_media_id" value="${ WeixinNewsItem.thumb_media_id}">
				          <c:set var="wechatNews" value="${WeixinNewsItem }"></c:set>
			        </div>
		        </c:if>
		        <c:if test="${status==false }">
			        <div class="rel sub-msg-item appmsgItem"> 
			        	<span class="thumb"> 
			        		<span class="default-tip" style="display:none">缩略图</span> 
			        		<img src="${WeixinNewsItem.thumbUrl }" class="i-img" >
			        	</span>
			          <h4 class="msg-t"> 
			          	<span class="i-title">${WeixinNewsItem.title }</span>
			          </h4>
			          <ul class="abs tc sub-msg-opr">
			            <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon edit-icon">编辑</a> </li>
			            <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon del-icon">删除</a> </li>
			          </ul>
			          	  <input type="hidden" class="dbid" value="${WeixinNewsItem.dbid }">
				          <input type="hidden" class="title"  value="${WeixinNewsItem.title }">
				          <input type="hidden" class="author"  value="${WeixinNewsItem.author }">
				          <input type="hidden" class="cover"  value="${WeixinNewsItem.dbid }">
				          <input type="hidden" class="cover_url"  value="${WeixinNewsItem.thumbUrl }">
				          <input type="hidden" class="cover_show"   value="${WeixinNewsItem.show_cover_pic }">
				          <textarea class="content" style="display: none;">${WeixinNewsItem.content }</textarea>
			         	 <input type="hidden" class="from_url" value="${ WeixinNewsItem.content_source_url}">
			         	 <input type="hidden" class="thumbWechatUrl" value="${ WeixinNewsItem.thumbWechatUrl}">
				          <input type="hidden" class="thumb_media_id" value="${ WeixinNewsItem.thumb_media_id}">
			        </div>
		        </c:if>
		      </c:forEach>
        	<div class="sub-add"> <a href="javascript:;" class="block tc sub-add-btn"> <span class="vm dib sub-add-icon"></span>增加一条</a> </div>
      </div>
    </div>
    <div class="pull-left" >
      <div class="msg-editer-wrapper" style="width: 770px;">
        <div class="msg-editer">
          <form id="appmsg-form" method="post" class="form" enctype="multipart/form-data">
	           <input id="action" name="action" type="hidden" value="add" />
	           <input id="wechatTemplateDbid" name="wechatTemplateDbid" type="hidden" value="${WeixinNewsTemplate.dbid }" />
	           <input type="hidden" id="dbid" name="WeixinNewsItem.dbid" value="${WeixinNewsItem.dbid }">
		       <input type="hidden" id="newsItemTemplateId" name="newsItemTemplateId" value="${WeixinNewsItem.dbid }">
	           <input type="hidden" name="type" id="type" value="2">
	          <div class="msg-content" style="width: 100%;">
		            <label>标题<span class="maroon">*</span><span class="help-inline">(不能超过64个字)</span></label>
		            <div class="msg-item msg-input">
		                <input type="text" id="title" name="title" class="ui-input" value="${wechatNews.title }"/>
		            </div>
		            <label>作者<span class="help-inline">(选填)</span></label>
		            <div class="msg-item msg-input">
		                <input type="text" id="author" name="author" class="ui-input" value="${wechatNews.author }"/>
		            </div>
		            <label>封面<span class="maroon">*</span><span class="help-inline">(必须上传一张图片,图片大小150*150)</span></label>
		            <div class="msg-item msg-input">
		                <div class="cover-area">
		                  <div class="cover-hd">
		                      	<a  href="#modal-add-event" class="btn btn-success btn-mini " onclick="uploadFileWechat()"><i class="icon-plus icon-white"></i>上传图片</a>
								<input class="input-medium" style="display: none;" type="text" readonly="readonly" name="thumbUrl" id="thumbUrl" />
								<input class="input-medium" style="display: none;" type="text" readonly="readonly" name="thumb_media_id" id="thumb_media_id"  />
								<input class="input-medium" style="display: none;" type="text" readonly="readonly" name="thumbWechatUrl" id="thumbWechatUrl"  />
								<div id="imgArea" class="uploadImage">
									<img alt="" title="" class="cover_url" id="imgShowPciture" src="${WeixinNewsItem.thumbUrl  }" width="146" height="146"></img>
									<button type="button" id="cboxClose" class="cboxClose" title="点击删除图片">×</button>
								</div>
		                  </div>
		                  <p id="imgArea" class="cover-bd" style="display:none;">
		                    <img src="" id="img">
		                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
		                  </p>
		                  <label class="checkbox inline"><input type="checkbox" id="cover_show" name="cover_show" value="1" ${wechatNews.show_cover_pic==1?'checked="checked"':'' } ><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> </label>
		                </div>
		            </div>
		            <label>正文<span class="maroon">*</span><span class="help-inline">(不能超过20000个字)</span></label>
		            <div class="msg-item msg-text">
						<textarea name="contentArea" id="content" class="editor"><p>${wechatNews.content }</p></textarea>
		            </div>
		            <c:if test="${empty(wechatNews.content_source_url) }">
		            <a id="url-block-link" class="block mt10 mb10">添加原文链接</a>
		            <div id="url-block" style="display:none;">
		              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
		              <div class="msg-item msg-input">
		                  <input id="from_url" type="text" class="ui-input" name="from_url" ${wechatNews.content_source_url }/>
		              </div>
		            </div>
		            </c:if>
		            <c:if test="${!empty(wechatNews.content_source_url) }">
		            <div id="url-block" >
		              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
		              <div class="msg-item msg-input">
		                  <input id="from_url" type="text" class="ui-input" name="from_url" value="${wechatNews.content_source_url }" />
		              </div>
		            </div>
		            </c:if>
		            <div class="msg-item msg-button">
		                <input type="submit" class="btn btn-primary" id="save-btn" value="保  存">
			            <a class="btn btn-default"  id="preview">
							预览
						</a> 
			            <a class="btn btn-default"  id="saveAndSend">
							保存并发送
						</a> 
						<a class="btn btn-inverse" onclick="window.history.go(-1)">
							返回
						</a>
		            </div>
	          </div>
          </form>
        </div>
      </div>  
      <span class="abs msg-arrow a-out" style="margin-top: 0px;"></span> 
      <span class="abs msg-arrow a-in" style="margin-top: 0px;"></span>          
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
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.cookie.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.uniform.js"></script>
<script src="${ctx}/widgets/bootstrap/select2.min.js"></script>
<script src="${ctx}/widgets/bootstrap/unicorn.js"></script>

<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack2.js"></script>


<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>

<script src="${ctx}/wxcss/js/global.js"></script>
<script src="${ctx}/wxcss/js/wechat.block.mul.js?da=1212"></script>
<script src="${ctx}/wxcss/js/jquery.json-2.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//富文本插件
	var editor=CKEDITOR.replace("content",{filebrowserImageUploadUrl:"/wechatMedia/uploadImages?type=image"});
	$("#cboxClose").bind("click",function(){
		$(".default-tip", window.appmsg).show();
		$("#imgArea").hide();
		$(".cover_url", window.appmsg).val("");
		$(".i-img", window.appmsg).hide();
		$(".cboxClose", window.appmsg).hide();
	})
});
/* $(window).bind('beforeunload',function(){
    return '您输入的内容尚未保存，确定离开此页面吗？';
}); */
function uploadFileWechat(){
	var path="";
	art.dialog.open(path+'/wechatMedia/uploadConpentWechat', {
		title: '上传图片',
		width:"690px",height:"400px",
		init: function () {
			var iframe = this.iframe.contentWindow;
			var top = art.dialog.top;// 引用顶层页面window对象
		},
		ok: function () {
			var iframe = this.iframe.contentWindow;
			if (!iframe.document.body) {
				alert('iframe还没加载完毕呢');
				return false;
			};
			var json= iframe.document.getElementById('fileUpload').value;
			
			if(null!=json&&json.length>0){
				var obj=$.parseJSON(json);
				$("#thumbUrl").val(obj.thumbUrl);
				$("#thumb_media_id").val(obj.mediaId);
				$("#thumbWechatUrl").val(obj.thumbWechatUrl);
				$("#cboxClose").show();
    			 $(".i-img", window.appmsg).attr("src", obj.thumbUrl).show();
    			 $(".default-tip", window.appmsg).hide();
    			$(".cover_url", window.appmsg).val(obj.thumbUrl); 
    			$(".thumb_media_id", window.appmsg).val(obj.mediaId);
    			$(".thumbWechatUrl", window.appmsg).val(obj.thumbWechatUrl); 
    			$("#imgArea").show().find("#imgShowPciture").attr("src", obj.thumbUrl);
				return true;
			}else{
				alert("请选择图片后点击【确定】按钮");
				return false;
			}
		},
		cancel: true
	});
}
function uploadFile(fileUrl,imgeUrl,numState){
	var path="";
	var state=false;
	if(numState==true){
		state=true;
	}
	art.dialog.open(path+'/compoent/uploadConpent?numState='+state, {
	    title: '上传图片',
	    width:"690px",height:"400px",
	    init: function () {
	    	var iframe = this.iframe.contentWindow;
	    	var top = art.dialog.top;// 引用顶层页面window对象
	    },
	    ok: function () {
	    	var iframe = this.iframe.contentWindow;
	    	if (!iframe.document.body) {
	        	alert('iframe还没加载完毕呢')
	        	return false;
	        };
	       var fileUpload= iframe.document.getElementById('fileUpload').value;
	       if(null!=fileUpload&&fileUpload.length>0){
		        $("#"+fileUrl).val(fileUpload);
		        if(null!=imgeUrl&&imgeUrl!=undefined){
		        	 //关闭
			        $("#cboxClose").show();
			        $(".default-tip", window.appmsg).hide();
	    			$(".i-img", window.appmsg).attr("src", fileUpload).show();
	    			$(".default-tip", window.appmsg).hide();
	    			$(".i-img", window.appmsg).attr("src", fileUpload).show();
	    			$("#imgArea").show().find("#imgShowPciture").attr("src", fileUpload);
	    			$(".cover_url", window.appmsg).val(fileUpload);
		        }
		       	return true;
	       }else{
	    	   alert("请选择图片后点击【确定】按钮");
	    	   return false;
	       }
	    },
	    cancel: true
	});
}
</script>
</body>
</html>
