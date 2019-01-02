<#include "../../commons/macro.ftl">
<@commonHead/>

<style type="text/css">
	tr{
		height: 50px;
	}
	.formTableTdLeft{
		text-align: right;
		padding-right: 12px;
		width: 80px;
	}
</style>
<title>岗位信息编辑页面</title>
</head>
<body class="bodycolor">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent" class="layui-form" >
		<c:if test="${not empty(position) }">
			<input type="hidden" name="parentId" value="${position.parent.dbid }" id="parentId"></input>
		</c:if>
		<c:if test="${empty(position) }">
			<input type="hidden" name="parentId" value="${param.parentId }" id="parentId"></input>
		</c:if>
		<input type="hidden" name="position.dbid" id="dbid" value="${position.dbid }">

		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft">名称:&nbsp;</td>
				<td ><input type="text" name="position.name" id="name"
					value="${position.name }" class="layui-input" title="岗位名称"	lay-verify='required'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">序号:&nbsp;</td>
				<td ><input type="text" name="position.suqNo" id="suqNo"
					value="${position.suqNo }" class="layui-input" lay-verify='number'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">岗位职能:&nbsp;</td>
				<td ><textarea  name="position.discription" id="discription"
					 class="layui-input" title="用户名">${position.discription }</textarea></td>
			</tr>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
		      <a id="closeBut"  class="layui-btn layui-btn-primary" >关闭</a>
		    </div>
		  </div>
	</form>
</body>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
layui.use(['laypage', 'table','layer','form'], function(){
	  var table = layui.table,laypage=layui.laypage;
	  form = layui.form;
	  if(topParent==undefined){
		 layer = layui.layer;
	  }else{
		 layer = topParent.layer;
	  }
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/position/save')
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>