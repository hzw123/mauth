<#include "../../commons/macro.ftl">
<@commonHead/>
<title>添加图文回复</title>
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
          <h4 class="msg-t"> <span class="i-title">${weixinNewsitem.title }</span> </h4>
          <p class="msg-meta"> <span class="msg-date">${weixinNewsitem.createDate }</span> </p>
          <div class="cover">
          		<c:if test="${empty(weixinNewsitem.imagepath) }">
	            	<p class="default-tip" style="">封面图片</p>
	            </c:if>
          		<c:if test="${!empty(weixinNewsitem.imagepath) }">
	            	<p class="default-tip" style="display: none;">封面图片</p>
	            </c:if>
	            <img class="i-img" src="${weixinNewsitem.imagepath }" id="fileUploadImage"> </div>
	          <p class="i-summary"></p>
        </div>
        <div class="msg-hover-mask"></div>
        <div class="msg-mask"> <span class="dib msg-selected-tip"></span> </div>
      </div>
    </div>
    <div class="pull-left" style="width: 60%;">
      <div class="msg-editer-wrapper" style="width: 100%;">
        <div class="msg-editer" style="width: 100%;">
          <form id="frmId" name="frmId" method="post" class="layui-form">
          <input type="hidden" id="dbid" name="weixinNewsitem.dbid" value="${weixinNewsitem.dbid }"> 
           <input type="hidden" id="readNum" name="weixinNewsitem.readNum" value="${weixinNewsitem.readNum }"> 
          <input type="hidden" id="dbid" name="weixinNewsitem.createDate" value="${weixinNewsitem.createDate }"> 
          <input type="hidden" id="weixinNewstemplateDbid" name="weixinNewstemplateDbid" value="${weixinNewstemplate.dbid }"> 
          <input type="hidden" id="type" name="type" value="1"> 
          <div class="msg-content" style="width: 100%;">
	          <div class="control-group">
				<label class="control-label">
	            标题<span class="maroon">*</span><span class="help-inline">(不能超过64个字)</span></label>
	            <div class="controls">
	                <input type="text" id="title" name="weixinNewsitem.title" class="layui-input" value="${ weixinNewsitem.title}" lay-verify="required" tip="输入标题"/>
	            </div>
	          </div>
	        <div class="control-group">
	            <label>作者<span class="help-inline">(选填)</span></label>
	            <div class="controls">
	                <input type="text" id="author" name="weixinNewsitem.author" value="${weixinNewsitem.author }" class="layui-input" />
	            </div>
            </div>
            <label>封面<span class="maroon">*</span><span class="help-inline">(大图片建议尺寸：900像素 * 500像素)</span></label>
            <div class="msg-item msg-input">
                <div class="cover-area">
                  <div class="cover-hd">
                  		<span id="spanButtonPlaceholder1"></span> <br />
                     	<input id="fileUpload" type="hidden" name="weixinNewsitem.imagepath" value="${weixinNewsitem.imagepath }">
                  </div>
                  <button type="button" class="layui-btn" id="test1">上传封面</button>
                  <p id="demoText"></p>
                  <p id="uploadState"></p>
                  <a id="method" href="javascript:void(-1)" style="display: none;">删除</a>
                  <!-- <p id="upload-tip" class="upload-tip">大图片建议尺寸：700像素 * 300像素</p> -->
                 <c:if test="${empty(weixinNewsitem.imagepath) }">
	                  <p id="imgArea" class="cover-bd" style="display: none;">
	                    <img src="${weixinNewsitem.imagepath }" id="cover_view">
	                  </p>
                  </c:if>
                 <c:if test="${!empty(weixinNewsitem.imagepath) }">
	                  <p id="imgArea" class="cover-bd" >
	                    <img src="${weixinNewsitem.imagepath }" id="cover_view">
	                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
	                  </p>
                  </c:if>
        		  <label class="checkbox inline"><input type="checkbox" name="cover_show" value="1" checked="checked"><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> </label>
                </div>
            </div>
            <c:if test="${empty(weixinNewsitem.description) }">
	            <a id="desc-block-link" class="block mt10 mb10">添加摘要</a>
	            <div id="desc-block" style="display:none;">
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="summary" class="layui-input" name="weixinNewsitem.description" rows="3"></textarea>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(weixinNewsitem.description) }">
	            <div id="desc-block" >
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="summary" class="layui-input" name="weixinNewsitem.description" rows="3">${weixinNewsitem.description }</textarea>
	              </div>
	            </div>
            </c:if>
             <div class="control-group">
					<label class="control-label">
            		正文<span class="maroon">*</span><span class="help-inline">(不能超过20000个字)</span></label>
		            <div class="msg-item msg-text">
		                <textarea id="content" name="contentArea" checkType="string,1" tip="请输入正文">
		                	<c:if test="${empty(weixinNewsitem.content) }">
				                <p>这是正文</p>
		                	</c:if>
		                	<c:if test="${!empty(weixinNewsitem.content) }">
				                ${weixinNewsitem.content }
		                	</c:if>
		               	</textarea>
		            </div>
		      </div>
            <a id="url-block-link" class="block mt10 mb10">添加原文链接</a>
            <c:if test="${empty(weixinNewsitem.url) }">
	            <div id="url-block" style="display:none;">
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="controls">
	                  <input type="text" class="layui-input" id="url" name="weixinNewsitem.url" value="${weixinNewsitem.url }"/>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(weixinNewsitem.url) }">
	            <div id="url-block" >
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="controls">
	                  <input type="text" class="layui-input" name="weixinNewsitem.url" value="${weixinNewsitem.url }"/>
	              </div>
	            </div>
            </c:if>
             <div id="url-block" >
	              <label>排序<span class="help-inline">(请输入排序号)</span></label>
	              <div class="controls">
	                  <input type="text" class="layui-input" id="orders" name="weixinNewsitem.orders" value="${weixinNewsitem.orders }"/>
	              </div>
	         </div>
	         <br>
	         <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
			      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
			    </div>
			  </div>
          </div>
          </form>
        </div>  
        <span class="abs msg-arrow a-out" style="margin-top: 0px;"></span> 
        <span class="abs msg-arrow a-in" style="margin-top: 0px;"></span>          
      </div>
    </div>
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
	var editor=CKEDITOR.replace("content");
});
function checkWebUrl(obj){
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
	    ,url: '/swfUpload/uploadFileLayui'
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
