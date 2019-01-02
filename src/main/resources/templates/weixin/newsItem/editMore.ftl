<#include "../../commons/macro.ftl">
<@commonHead/>
<title>编辑多图文回复</title>
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
.msg-content .msg-input input {
    height: 38px; 
    line-height: 1.3; 
}
</style>
</head>
<body>
<h4 class="headline"><i class="fa fa-home"></i> 编辑多图文 <span class="line"></span> </h4>
<div class="block-wrap">
	<div class="pull-left msg-preview" style="width:330px">
      <div class="msg-item-wrapper">
      		<c:set value="" var="weixinNews"></c:set>
			<c:forEach var="weixinNewsitem" items="${weixinNewstemplate.weixinNewsitems }" varStatus="i">
				<c:if test="${i.index==0 }" var="status">
			        <div id="appmsgItem1" class="appmsgItem msg-item">
				          <p class="msg-meta"> <span class="msg-date"></span> </p>
				          <div class="cover">
					            <p class="default-tip"  style="display:none">缩略图</p>
					            <h4 class="msg-t"> <span class="i-title">${weixinNewsitem.title }</span> </h4>
					            <ul class="abs tc sub-msg-opr">
					              <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon edit-icon">编辑</a> </li>
					            </ul>
					            <img class="i-img" src="${weixinNewsitem.imagepath }" > 
				          </div>
				          <p class="msg-text"></p>
				          <input type="hidden" class="dbid" value="${weixinNewsitem.dbid }">
				          <input type="hidden" class="title"  value="${weixinNewsitem.title }">
				          <input type="hidden" class="author"  value="${weixinNewsitem.author }">
				          <input type="hidden" class="cover"  value="${weixinNewsitem.dbid }">
				          <input type="hidden" class="cover_url"  value="${weixinNewsitem.imagepath }">
				          <input type="hidden" class="cover_show" value="1"  value="${weixinNewsitem.coverShow }">
				          <textarea class="content" style="display: none;">${weixinNewsitem.content }</textarea>
				          <input type="hidden" class="from_url" value="${ weixinNewsitem.url}">
				          <c:set var="weixinNews" value="${weixinNewsitem }"></c:set>
			        </div>
		        </c:if>
		        <c:if test="${status==false }">
			        <div class="rel sub-msg-item appmsgItem"> 
			        	<span class="thumb"> 
			        		<span class="default-tip" style="display:none">缩略图</span> 
			        		<img src="${weixinNewsitem.imagepath }" class="i-img" > 
			        	</span>
			          <h4 class="msg-t"> 
			          	<span class="i-title">${weixinNewsitem.title }</span> 
			          </h4>
			          <ul class="abs tc sub-msg-opr">
			            <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon edit-icon">编辑</a> </li>
			            <li class="b-dib sub-msg-opr-item"> <a href="javascript:;" class="th opr-icon del-icon">删除</a> </li>
			          </ul>
			          	  <input type="hidden" class="dbid" value="${weixinNewsitem.dbid }">
				          <input type="hidden" class="title"  value="${weixinNewsitem.title }">
				          <input type="hidden" class="author"  value="${weixinNewsitem.author }">
				          <input type="hidden" class="cover"  value="${weixinNewsitem.dbid }">
				          <input type="hidden" class="cover_url"  value="${weixinNewsitem.imagepath }">
				          <input type="hidden" class="cover_show" value="1"  value="${weixinNewsitem.coverShow }">
				          <textarea class="content" style="display: none;">${weixinNewsitem.content }</textarea>
			         	 <input type="hidden" class="from_url" value="${ weixinNewsitem.url}">
			        </div>
		        </c:if>
		      </c:forEach>
        	<div class="sub-add"> <a href="javascript:;" class="block tc sub-add-btn"> <span class="vm dib sub-add-icon"></span>增加一条</a> </div>
      </div>
    </div>
    <div class="pull-left" >
      <div class="msg-editer-wrapper" style="width: 770px;">
        <div class="msg-editer">
          <form id="appmsg-form" method="post" class="layui-form" enctype="multipart/form-data">
	           <input id="action" name="action" type="hidden" value="add" />
	           <input id="weixinNewstemplateDbid" name="weixinNewstemplateDbid" type="hidden" value="${weixinNewstemplate.dbid }" />
	           <input type="hidden" name="type" id="type" value="2">
	          <div class="msg-content" style="width: 100%;">
		            <label>标题<span class="maroon">*</span><span class="help-inline">(不能超过64个字)</span></label>
		            <div class="msg-item msg-input">
		                <input type="text" id="title" name="title" class="layui-input" value="${weixinNews.title }"/>
		            </div>
		            <label>作者<span class="help-inline">(选填)</span></label>
		            <div class="msg-item msg-input">
		                <input type="text" id="author" name="author" class="layui-input" value="${weixinNews.author }"/>
		            </div>
		            <label>封面<span class="maroon">*</span><span class="help-inline">(必须上传一张图片,图片大小150*150)</span></label>
		            <div class="msg-item msg-input">
		                <div class="cover-area">
		                  <div class="cover-hd">
		                      	<button type="button" class="layui-btn" onclick="uploadFile('showPciture','imgShowPciture',false)">上传封面</button>
								<input class="input-medium" style="display: none;" type="text" readonly="readonly" name="product.showPciture" id="showPciture" value="${weixinNews.imagepath }" />
								<div id="imgArea" class="uploadImage">
									<img alt="" title="" class="cover_url" id="imgShowPciture" src="${weixinNews.imagepath }" width="146" height="146"></img>
									<button type="button" id="cboxClose" class="cboxClose" title="点击删除图片">×</button>
								</div>
		                  </div>
		                  <p id="imgArea" class="cover-bd" style="display:none;">
		                    <img src="" id="img">
		                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
		                  </p>
		                  <label class="checkbox inline"><input type="checkbox" id="cover_show" name="cover_show" value="1" ${weixinNews.coverShow==1?'checked="checked"':'' } ><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> </label>
		                </div>
		            </div>
		            <label>正文<span class="maroon">*</span><span class="help-inline">(不能超过20000个字)</span></label>
		            <div class="msg-item msg-text">
						<textarea name="contentArea" id="content" class="editor"><p>${weixinNews.content }</p></textarea>
		            </div>
		            <c:if test="${empty(weixinNews.url) }">
		            <a id="url-block-link" class="block mt10 mb10">添加原文链接</a>
		            <div id="url-block" style="display:none;">
		              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
		              <div class="msg-item msg-input">
		                  <input id="from_url" type="text" class="layui-input" name="from_url" ${weixinNews.url }/>
		              </div>
		            </div>
		            </c:if>
		            <c:if test="${!empty(weixinNews.url) }">
		            <div id="url-block" >
		              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
		              <div class="msg-item msg-input">
		                  <input id="from_url" type="text" class="layui-input" name="from_url" value="${weixinNews.url }" />
		              </div>
		            </div>
		            </c:if>
		            <br>
		            <div class="layui-form-item">
				    <div class="layui-input-block">
				      <button class="layui-btn" id="save-btn" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
				      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
				    </div>
				  </div>
	          </div>
          </form>
        </div>
      </div>  
      <span class="abs msg-arrow a-out" style="margin-top: 0px;"></span> 
      <span class="abs msg-arrow a-in" style="margin-top: 0px;"></span>          
    </div>
</div>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?date=1"></script>
<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>

<script src="${ctx}/wxcss/js/global.js"></script>
<script src="${ctx}/wxcss/js/weixin.block.mul.js"></script>
<script src="${ctx}/wxcss/js/jquery.json-2.4.min.js"></script>
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
	 /*  form.on('submit(submitButton)', function(data){
		  var fileUpload=$("#fileUpload").val();
			var url=$("#url").val();
			if(null==fileUpload||fileUpload==""){
				alert("请先上传封面图片！");
				return false;
			}
		  //$.utile.submitForm('frmId','',true);
		  submitFrm('${ctx}/weixinNewsItem/save',true);
		  return false
	  }); */
	});
</script>
<script type="text/javascript">
$(document).ready(function(){
	//富文本插件
	var editor=CKEDITOR.replace("content");
	$("#cboxClose").bind("click",function(){
		$(".default-tip", window.appmsg).show();
		$("#imgArea").hide();
		$(".cover_url", window.appmsg).val("");
		$(".i-img", window.appmsg).hide();
		$(".cboxClose", window.appmsg).hide();
	})
});

function uploadFile(fileUrl,imgeUrl,numState){
	var path="";
	var state=false;
	if(numState==true){
		state=true;
	}
	layer.open({
        type: 2 //此处以iframe举例
        ,title: '选择文件'
        ,area: ['760px', '460px']
        ,shade: 0.8
        ,maxmin: true
        ,content: path+'/compoent/uploadConpent?numState='+state
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
          //layer.setTop(layero); //重点2
        }
        ,btn: ['确定', '关闭']
        ,yes: function(index, layero){
        	var body = layer.getChildFrame('body', index);
			var fileUpload= body.find('#fileUpload').val();
			if(null!=fileUpload&&fileUpload.length>0){
		        $("#"+fileUrl).val(fileUpload);
		        if(null!=imgeUrl&&imgeUrl!=undefined){
		        	$("#"+imgeUrl).attr("src",fileUpload);
		        	 //关闭
			        $("#cboxClose").show();
			        $(".default-tip", window.appmsg).hide();
	    			$(".i-img", window.appmsg).attr("src", fileUpload).show();
	    			$(".default-tip", window.appmsg).hide();
	    			$(".i-img", window.appmsg).attr("src", fileUpload).show();
	    			$("#imgArea").show().find("#imgShowPciture").attr("src", fileUpload);
	    			$(".cover_url", window.appmsg).val(fileUpload);
		        }
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
</body>
</html>
