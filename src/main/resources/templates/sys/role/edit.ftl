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
<title>用户角色</title>
</head>
<body class="bodycolor">
<div class="location">
     	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
     	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/role/queryList'">角色管理</a>-
<a href="javascript:void(-1);">
	<c:if test="${empty(role) }">添加角色</c:if>
	<c:if test="${!empty(role) }">编辑角色</c:if>
</a>
</div>
<hr class="layui-bg-red">
<div class="frmContent">
	<form action="" name="frmId" id="frmId"  target="_self" class="layui-form" >

		<input type="hidden" name="role.dbid" id="dbid" value="${role.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">名称:&nbsp;</td>
				<td ><input type="text" name="role.name" id="name"
					value="${role.name }" class="layui-input" title="名称"	lay-verify='required'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">适用用户类型:&nbsp;</td>
				<td >
					<select id="userType" name="role.userType" class="layui-input" lay-verify='required'>
						<option value="">请选择....</option>
						<option value="1"  ${role.userType==1?'selected="selected"':''}>管理员</option>
						<option  value="2" ${role.userType==2?'selected="selected"':''}>普通用户</option>
					</select>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" style="color: red;">状态:&nbsp;</td>
				<td>
					<select class="layui-input" id="state" name="role.state" lay-verify='required'>
						<option value="">请选择....</option>
						<option value="1" ${role.state==1?'selected="selected"':'' } >启用</option>
						<option value="0" ${role.state==0?'selected="selected"':'' }>停用</option>
					</select>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">备注:&nbsp;</td>
				<td>
					<textarea class="layui-input" rows="8" cols="60" name="role.note" id="note">${role.note }</textarea>
				</td>
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
	</div>
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
		  submitFrm('${ctx}/role/save');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>