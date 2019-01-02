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
<title>资源排序</title>
</head>
<body class="bodycolor">
	<form action="" name="frmId" id="frmId"  target="_parent" class="layui-form" >
		<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;" >
		    <colgroup>
		      <col width="400">
		      <col width="200">
		    </colgroup>
		    <thead>
			<tr>
				<th class="span2" style="text-align: center;">名称</th>
				<th class="span2" style="text-align: center;">排序号</th>
			</tr>
		</thead>
		<c:forEach var="resource" items="${resources }">
			<tr height="32" align="center">
				<td>
				<input type="hidden" value="${resource.dbid }" name="dbid" id="dbid" >
				${resource.title }</td>
				<td>
					<input type="text" value="${resource.orderNo }" class="layui-input" name="orderNo" id="orderNo" >
				</td>
			</tr>
		</c:forEach>
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
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/resource/saveOrderNum');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>