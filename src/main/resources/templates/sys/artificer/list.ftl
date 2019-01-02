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
<title>房间管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">技师管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="listOperate">
	<div class="operate">
		<a class="layui-btn" id="btnAdd">添加</a>
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
				<th class="sn" style="text-align: center;">
					<input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" />
				</th>
				<th class="span2" style="text-align: center;">工号</th>
				<th class="span2" style="text-align: center;">姓名</th>
				<th class="span4" style="text-align: center;">性别</th>
				<th class="span2" style="text-align: center;">手机号</th>
				<th class="span4" style="text-align: center;">备注</th>
				<th class="span2" style="text-align: center;">操作</th>
			</tr>
		</thead>
	<c:forEach var="artificer" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${artificer.dbid }"/></td>
			<td>${artificer.no }</td>
			<td>${artificer.name}</td>
			<td>${artificer.sex}</td>
			<td>${artificer.phone}</td>
			<td style="text-align: left;">${room.note} </td>
			<td>
				<a class="layui-btn layui-btn-xs" data-fun="edit" name="${artificer.dbid}">编辑</a>
				<a class="layui-btn layui-btn-danger layui-btn-xs" data-fun="delete"  name="${artificer.dbid}">删除</a>
			</td>
				
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

$(document).on('click', '#btnAdd', function () {
    var url = "edit";
    $.get(url, function (str) {
        pop_up = layui.layer.open({
            type: 1,
            title: ['添加技师'],
            area: ['600px', '450px'],
            content: str,
        });
    });
});

$(document).on('click', 'a[data-fun="edit"]', function () {
	var url = "edit?dbid="+this.name;
    $.get(url, function (str) {
        pop_up = layui.layer.open({
            type: 1,
            title: ['编辑技师'],
            area: ['600px', '450px'],
            content: str,
        });
    });
});

$(document).on('click', 'a[data-fun="delete"]', function () {
	$.utile.deleteById('${ctx}/artificer/delete?dbid='+this.name)
});
</script>
</body>
</html>