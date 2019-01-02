<#include "../../commons/macro.ftl">
<@commonHead/>

<title>优惠券模板</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		 <input type="hidden" name="couponMemberTemplate.dbid" id="dbid" value="${couponMemberTemplate.dbid }">
		 <c:if test="${empty(couponMemberTemplate) }">
			 <input type="hidden" name="couponMemberTemplate.type" id="type" value="2">
		 </c:if>
		 <c:if test="${!empty(couponMemberTemplate) }">
			 <input type="hidden" name="couponMemberTemplate.type" id="type" value="${couponMemberTemplate.type}">
		 </c:if>
		  <div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">名称<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="couponMemberTemplate.name" id="sn" value="${couponMemberTemplate.name }" lay-verify="title" autocomplete="off" placeholder="请输入名称" class="layui-input">
			    </div>
			</div>
		  	<div class="layui-inline">
			    <label class="layui-form-label">默认价格<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="couponMemberTemplate.price" id="price" value="${couponMemberTemplate.price }${empty(couponMemberTemplate)==true?'0.00':''}" lay-verify="required" autocomplete="off" placeholder="请输入默认价格" class="layui-input">
			    </div>
			 </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">序号</label>
		    <div class="layui-input-inline">
		      <input type="text" name="couponMemberTemplate.orderNum" id="orderNum"  value="${couponMemberTemplate.orderNum }" placeholder="请输入序号" class="layui-input">
		    </div>
		   <label class="layui-form-label">图片</label>
		    <div class="layui-input-inline">
		  		<input type="hidden" name="couponMemberTemplate.image" id="fileUpload" readonly="readonly"	value="${couponMemberTemplate.image }">
					<div class="layui-upload-list" style="float: left;">
					    <img class="layui-upload-img" id="demo1" width="100" height="60" src="${couponMemberTemplate.image }">
					    <p id="demoText"></p>
					 </div>
					<button type="button" class="layui-btn layui-btn-mini" id="test1" style="float: left;margin-top: 30px;margin-left: 5px">上传头像</button>
			</div>
		  </div>
		  <div class="layui-form-item">
		   	<label class="layui-form-label"></label>
		  	<textarea  class="input-xlarge" name="couponMemberTemplate.description" id="content" >${couponMemberTemplate.description }</textarea>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
	</form>
</body>

<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	var editor=CKEDITOR.replace("content");
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //自定义验证规则
	  form.verify({
	    title: function(value){
	      if(value.length < 2){
	        return '名称至少得2个字符啊';
	      }
	    }
	  });
	//普通图片上传
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
	      $("#fileUpload").val(res.url);
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
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  $('#content').val(CKEDITOR.instances.content.getData());
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/couponMemberTemplate/save',
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
						layer.msg(obj[0].message,{icon: 5});
						$("#submitBut").bind("onclick");
						return ;
					}else if(obj[0].mark==0){
						layer.msg(data[0].message,{icon: 1});
						if (target == "_self") {
							setTimeout(
									function() {
										window.location.href = obj[0].url
									}, 1000);
						}
						if (target == "_parent") {
						 setTimeout(
								function() {
									window.parent.frames["contentUrl"].location=obj[0].url;
									 parent.layer.closeAll();
								},1000) 
						}
						// 保存数据成功时页面需跳转到列表页面
					}
				},
				complete : function(jqXHR, textStatus){
					var jqXHR=jqXHR;
					var textStatus=textStatus;
				}, 
				beforeSend : function(jqXHR, configs){
					$("#submitBut").unbind("onclick");
					var jqXHR=jqXHR;
					var configs=configs;
				}, 
				error : function(jqXHR, textStatus, errorThrown){
						layer.msg("系统请求超时");
						$("#submitBut").bind("onclick");
				}
			});
	    return false;
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	 
	  
	});
</script>
</html>