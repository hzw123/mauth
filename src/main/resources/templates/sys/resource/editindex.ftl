<#include "../../commons/macro.ftl">
<@commonHead/>

<style type="text/css">
.location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
tr{
	height: 50px;
}
.formTableTdLeft{
		text-align: right;
		padding-right: 12px;
		width: 80px;
	}
</style>
<title>主页管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/resource/queryIndexList'">主页管理</a>-
	<a href="javascript:void(-1);">
		<c:if test="${empty(resource) }">添加主页</c:if>
		<c:if test="${!empty(resource) }">编辑主页</c:if>
	</a>
	<hr class="layui-bg-red">
</div>
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" class="layui-form" >
		<input type="hidden" name="resource.dbid" id="dbid" value="${resource.dbid }">
		<input type="hidden" name="resource.indexStatus" id="indexStatus" value="2">

		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">标题:&nbsp;</td>
				<td ><input type="text" name="resource.title" id="title"
					value="${resource.title }" class="layui-input" title="用户名"	lay-verify='required'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">url连接:&nbsp;</td>
				<td ><input type="text" name="resource.content" id="content"
					value="${resource.content }" class="layui-input" title="用户名" lay-verify='required'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">序号:&nbsp;</td>
				<td ><input type="text" name="resource.orderNo" id="orderNo"
					value="${resource.orderNo }" class="layui-input" ></td>
			</tr>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
		      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
		    </div>
		  </div>
	</form>
</body>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/resource/saveIndex');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>