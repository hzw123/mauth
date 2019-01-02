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
</style>

<title>角色赋权限</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/user/queryList'">用户管理中心</a>
</div>
<hr class="layui-bg-red">
	<form name="frmId" id="frmId" method="post" target="_self">

	<input type="hidden" name="dbid" value="${user2.dbid }" id="dbid">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
	  <legend>角色权限</legend>
	</fieldset>
	 <table class="layui-table">
	    <colgroup>
	      <col width="150">
	      <col width="150">
	      <col>
	    </colgroup>
	    <thead>
	      <tr>
				<th class="sn" style="text-align: left;padding-left:12px;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" />全选</th>
				<th class="span5">角色名称</th>
			</tr>
		</thead>
		<tbody id="TableBody">
		</tbody>
	</table>
	<%-- <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
	  <legend>其他权限</legend>
	</fieldset>
	<table class="layui-table">
			<tr height='32'>
				<td style="text-align: left;">
					<label>是否拥有合同流失权限:<input type="checkbox" id="approvalCpidStatus"  name="approvalCpidStatus" ${user2.approvalCpidStatus==2?'checked="checked"':'' }  value="2"></label>
				</td>
			</tr>
		</tbody>
	</table> --%>
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
<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/widgets/charscode.js"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?-1"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/comm.js"></script>
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
		  submitFrm('${ctx}/user/saveUserRole')
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
<script type="text/javascript">
	$(document).ready(function (){
		var result=null;
		$.post("${ctx}/user/userRoleJson?dbid=${user2.dbid}&timeStamp="+new Date(), { } ,function callback(json){
			if(null!=json&&json.length>0){
				var length=json.length;
				for(var i=0;i<length;i++){
					var obj=json[i];
					var append="<tr height='32' align='left'>";
					if(obj.checked==true){
						append=append+"<td style='text-align: left;padding-left:12px;'><input type='checkbox' name='id' id='id1' value='"+obj.dbid+"' checked='checked'/></td>"
					}else{
						append=append+"<td style='text-align: left;padding-left:12px;'><input type='checkbox' name='id' id='id1' value='"+obj.dbid+"'/></td>"
					}
					append=append+"<td style='text-align: left;'>"+obj.name+"</td></tr>"
					result=result+append;	
				}
				$("#TableBody").append(result);
			}
		});
	});
</script>
</html>