<#include "../../commons/macro.ftl">
<@commonHead/>
	<script src="${ctx}/widgets/jquery.min.js"></script>
<title>文件上传组件</title>
</head>
<style>
 .imagea{
 	display: block;margin: 6px;border: 3px solid #FFF;cursor: pointer;
 }
 .imagea:HOVER {
	border:3px solid #1094fa;
}

#online {
    padding: 10px 0 0;
    width: 100%;
}
#online #imageList {
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    position: relative;
    width: 100%;
}

#online ul {
    display: block;
    list-style: outside none none;
    margin: 0;
    padding: 0;
}

#online li {
    background-color: #eee;
    cursor: pointer;
    display: block;
    float: left;
    height: 120px;
    list-style: outside none none;
    margin: 0 0 9px 9px;
    overflow: hidden;
    padding: 0;
    position: relative;
    width: 120px;
}
#online li{
	border: 3px solid #FFF;cursor: pointer;
}
#online li:HOVER{
	border:3px solid #1094fa;
}
#online li.selected .icon {
    background-image: url("${ctx}/pages/compoent/success.png");
    background-position: 80px 80px;
}
#online li .icon {
    background-repeat: no-repeat;
    border: 0 none;
    cursor: pointer;
    height: 120px;
    left: 0;
    position: absolute;
    top: 0;
    width: 120px;
    z-index: 2;
}
</style>
<body>

<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
      <ul class="layui-tab-title">
	    <li class="layui-this">上传本地文件</li>
	  </ul>
     <div class="layui-tab-content">
	      <div class="layui-tab-item layui-show">
	        	<input type="hidden" name="fileUpload" id="fileUpload" readonly="readonly"	>
				<button type="button" class="layui-btn" id="test1">上传图片</button>
				<div class="layui-upload-list">
				    <img class="layui-upload-img" id="demo1" width="300" height="180" >
				    <p id="uploadState"></p>
				    <p id="method" style="display: none;">删除</p>
				    <p id="demoText"></p>
				 </div>
	      </div>
	   </div>
</div>

<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
	layui.use(['form', 'layedit', 'element','upload'], function(){
	  var form = layui.form,upload = layui.upload,element = layui.element;;
	  if(topParent==undefined){
			 layer = layui.layer;
		  }else{
			 layer = topParent.layer;
		  }
	  var $ = layui.$;
	  var uploadInst = upload.render({
		    elem: '#test1'
		    ,url: '${ctx}/wechatMedia/uploadFileMidia'
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
		      $("#fileUpload").val(res);
		      $("#imgArea").show();
				$(".default-tip").hide();
				$("#fileUploadImage").show()
				$("#fileUpload").val(res);
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
	});
</script>	
</body>
</html>