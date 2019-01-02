
<#include "../../commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>操作管理</title>
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
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >操作日志管理</a>
</div>
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    <div class="layui-col-xs9" >
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs3" >
	     <form name="searchPageForm" id="searchPageForm" action="${ctx}/operateLog/queryList" method="post">
	     	<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
			<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
	    	 <div class="layui-form-item" style="margin-bottom: 2px;">
		  		<label class="layui-form-label" style="width: 60px;padding: 9px 5px;">操作人</label>
			    <div class="layui-input-inline" style="width: 100px;">
				   <input type="text" d="operator" name="operator" value="${operator}" autocomplete="off" class="layui-input"/>
			    </div>
			     <a id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search" onclick="document.getElementById('searchPageForm').submit()"><i class="layui-icon layui-btn-icon"></i>查询</a>
		  	</div>
		  </form>
    </div>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<th style="text-align: center;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></th>
			<th style="text-align: center;">操作人</th>
			<th style="text-align: center;">操作时间</th>
			<th style="text-align: center;">操作对象</th>
			<th style="text-align: center;">操作类型</th>
			<th style="text-align: center;">操作数据</th>
			<th style="text-align: center;">IP地址</th>
		</tr>
	</thead>
		<c:if test="${empty(page.result)||page.result==null }" var="status">
			<tr>
				<td colspan="7" style="text-align: center;">
					无数据
				</td>
			</tr>
		</c:if>
		<c:if test="${status==false }">
			<c:forEach var="operateLog" items="${page.result }">
				<tr height="32" align="center">
					<td><input type='checkbox' name="id" id="id1" value="${operateLog.dbid }"/></td>
					<td>${operateLog.operator }</td>
					<td>
						<fmt:formatDate value="${operateLog.operatedate }" pattern="yyyy-MM-dd HH:mm:ss"/>	</td>
					<td>${operateLog.operateobj }</td>
					<td>${operateLog.operatetype }</td>
					<td>${operateLog.operatefeild }</td>
					<td>${operateLog.ipAddress }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<div id="page"></div>
</body>

<script src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/widgets/utile/utile.js"></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
layui.use(['laypage', 'table','layer','form'], function(){
  var table = layui.table,laypage=layui.laypage;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  //监听表格复选框选择
  table.on('checkbox(demo)', function(obj){
    console.log(obj)
  });
  var $ = layui.$, active = {
		  setTop:function(){
			  deleteByIds('${ctx}/operateLog/delete');
		  }
	  };
	  $('.layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
	  
	 //触发事件
	  $('.layui-btn').on('click', function(){
		    var othis = $(this), method = othis.data('method');
		    active[method] ? active[method].call(this, othis) : '';
		});
  
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
</html>