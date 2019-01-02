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
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
<title>角色管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">角色管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="listOperate">
	<div class="operate">
		<a class="layui-btn " href="javascript:void();" onclick="window.location.href='${ctx}/role/edit'">添加</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/role/queryList" metdod="get">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<th class="sn" style="text-align: center;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></th>
			<th class="span2" style="text-align: center;">角色名称</th>
			<th class="span2" style="text-align: center;">适用用户类型</th>
			<th class="span2" style="text-align: center;">状态</th>
			<th class="span4" style="text-align: center;">备注</th>
			<th class="span2" style="text-align: center;">操作</th>
		</tr>
	</thead>
	<c:forEach var="role" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${role.dbid }"/></td>
			<td>${role.name }</td>
			<td>
				<c:if test="${role.userType==1 }">管理员</c:if>
				<c:if test="${role.userType==2 }">普通用户</c:if>
			</td>
			<td>
				<c:if test="${role.state==1 }"><span style="color: blue;">启用</span></c:if>
				<c:if test="${role.state==0 }"><span style="color: red;">未启用</span></c:if>
			</td>
			<td style="text-align: left;">${role.note} </td>
			<td><a href="#" class="layui-btn layui-btn-xs"
				onclick="window.location.href='${ctx}/role/edit?dbid=${role.dbid}'">编辑</a>
				<a href="#" class="layui-btn layui-btn-danger layui-btn-xs"
				onclick="$.utile.deleteById('${ctx}/role/delete?dbid=${role.dbid}')">删除</a>
				<a href="#" class="layui-btn layui-btn-xs"
				onclick="window.location.href='${ctx}/role/roleResource?dbid=${role.dbid}'">分配权限</a>
		</tr>
	</c:forEach>
</table>
<div id="page">
</div>


<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
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
  //完整功能
  laypage.render({
    elem: 'page',
    limit:${page.pageSize}
    ,count: ${page.totalCount}
    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    ,curr:${page.currentPageNo}
    ,jump: function(obj,first){
    	if(!first){
	      $("#currentPage").val(obj.curr);
	      $("#paramPageSize").val(obj.limit);
	      var qForm=$("#searchPageForm");
	      qForm.submit();
   	    }
    }
  });
});
</script>
</body>
</html>