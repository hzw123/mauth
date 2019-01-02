<#include "../../commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

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
<title>项目管理</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png" style="margin-bottom: 5px;"/>
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/entItem/queryList'">项目管理</a>-
	<a href="javascript:void(-1);" onclick="">选择项目</a>
</div>
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    	<form name="searchPageForm" id="searchPageForm" action="${ctx}/entItem/queryItemList" method="post">
      	  项目类型：
		  <div class="layui-inline" style="width: 100px;">
		   <select id="itemTypeId" name="itemTypeId" class="layui-input" lay-filter="aihao" lay-verify="required" onchange="$('#searchPageForm')[0].submit()">
				<option value="0">请选择...</option>
				<c:forEach items="${itemTypes }" var="itemType">
					<option value="${itemType.dbid }" ${param.itemTypeId==itemType.dbid?'selected="selected"':'' } >${itemType.name }</option>
				</c:forEach>
			</select>
		  </div>
		  编号：
		  <div class="layui-inline">
		   <input type="text" name="no" id="no" autocomplete="off" class="layui-input" value="${param.no }" style="width: 100px;"/>
		  </div>
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" value="${param.name }" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" onchange="$('#searchPageForm')[0].submit()" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon" ></i>查询</button>
  		</form>
  </div>
  <br>
<form name="frmId" id="frmId" >
	<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
	    <colgroup>
	      <col>
	    </colgroup>
	    <thead>
			<tr>
				<th class="sn" style="text-align: center;width: 30px;">
					<input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" lay-skin="primary" ${all==true?'checked="checked"':'' }  />
				</th>
				<th  style="text-align: center;width: 120px;">编号</th>
				<th  style="text-align: center;width: 240px;">名称</th>
				<th  style="text-align: center;width: 120px;">类型</th>
				<th  style="text-align: center;width: 60px;">金额</th>
			</tr>
		</thead>
		<c:if test="${empty(items) }">
			<tr height="32" align="center">
				<td colspan="4">无数据</td>
			</tr>
		</c:if>
		<c:forEach var="item" items="${items }">
			<tr height="32" align="center">
				<td>
					<input type='checkbox' ${item.useStatus==1?'checked="checked"':'' } lay-skin="primary"   name="id" id="id1" value="${item.dbid }"/>
				</td>
				<td>${item.no }</td>
				<td>${item.name }</td>
				<td>${item.itemType.name }</td>
				<td>${item.price }</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
	      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
	    </div>
	  </div>
	</form>
</form>
</body>

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
	  
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/entItem/saveItems');
		  return false
	  });
	});
</script>
</html>