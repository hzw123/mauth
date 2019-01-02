<#include "../../commons/macro.ftl">
<@commonHead/>
<title>文本消息添加</title>

 <style type="text/css">
 .location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
.layui-table th{
	text-align: center;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >
		<c:if test="${weixinTexttemplate.dbid>0 }" var="status">编辑文本消息</c:if>
		<c:if test="${status==false }">添加文本消息</c:if>
	</a>
</div>
<hr class="layui-bg-red">
<form class="layui-form" method="post" action="" 	name="frmId" id="frmId">
	<input type="hidden" value="${weixinTexttemplate.dbid }" id="dbid" name="weixinTexttemplate.dbid">
	<input type="hidden" value="${weixinTexttemplate.addtime }" id="addtime" name="weixinTexttemplate.addtime">
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">模板名称：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinTexttemplate.templatename"  lay-verify="required" 	id="templatename" value="${weixinTexttemplate.templatename }" checkType="string,1,50"  tip="请输入模板名称"  style="width: 60%;"/>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">模板内容：</label>
		<div class="layui-input-block">
			<textarea  class="layui-input" style="width: 60%;min-height: 320px;" name="weixinTexttemplate.content"	lay-verify="required" >${weixinTexttemplate.content }</textarea>
		</div>
	</div>
	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
	      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
	    </div>
	  </div>
</form>


<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/widgets/charscode.js"></script>

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
	//时间选择器
	  laydate.render({
	    elem: '#birthday'
	    ,type: 'date'
	  });
	  
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/weixinTexttemplate/save');
		  return false
	  });
	});
</script>
</body>
</html>
