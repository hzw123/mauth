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
<title>用户添加</title>
</head>
<body class="bodycolor">
	<form action="" name="frmId" id="frmId"  target="_parent"  class="layui-form" >
		<c:if test="${not empty(resource) }">
			<input type="hidden" name="parentId" value="${resource.parent.dbid }" id="parentId"></input>
		</c:if>
		<c:if test="${empty(resource) }">
			<input type="hidden" name="parentId" value="${param.parentId }" id="parentId"></input>
		</c:if>
		<input type="hidden" name="resource.dbid" id="dbid" value="${resource.dbid }">
		<input type="hidden" name="resource.indexStatus" id="indexStatus" value="1">

		<br>
		<br>
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">连接类型:&nbsp;</td>
				<c:if test="${not empty(param.menu) }">
				<td>
					<select id="menu" name="resource.menu"  class="layui-input" lay-verify='required' >
						<option value="">请选择连接类型</option>
						<option value="0" ${param.menu==0?'selected="selected"':'' } >模块</option>
						<option value="1" ${param.menu==1?'selected="selected"':'' } >菜单</option>
						<option value="2" ${param.menu==2?'selected="selected"':'' }>列表</option>
						<option value="3" ${param.menu==3?'selected="selected"':'' }>功能</option>
					</select></td>
				</c:if>
				<c:if test="${empty(param.menu) }">
				<td>
					<select id="menu" name="resource.menu"  class="layui-input" lay-verify='required'>
						<option value="">请选择连接类型</option>
						<option value="0" ${resource.menu==0?'selected="selected"':'' } >模块</option>
						<option value="1" ${resource.menu==1?'selected="selected"':'' }>菜单</option>
						<option value="2" ${resource.menu==2?'selected="selected"':'' }>列表</option>
						<option value="3" ${resource.menu==3?'selected="selected"':'' }>功能</option>
					</select></td>
				</c:if>
				
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">标题:&nbsp;</td>
				<td ><input type="text" name="resource.title" id="title"
					value="${resource.title }" class="layui-input" title="标题"	lay-verify='required'></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">url连接:&nbsp;</td>
				<td ><input type="text" name="resource.content" id="content"
					value="${resource.content }" class="layui-input" title="url连接"></td>
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
		  submitFrm('${ctx}/resource/save');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>