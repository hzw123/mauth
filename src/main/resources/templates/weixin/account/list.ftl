<#include "../../commons/macro.ftl">
<@commonHead/>
<title>公众账号设置</title>

 <style type="text/css">
 .location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
.layui-table th{
	text-align: center;
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
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >公众账号设置</a>
</div>
<hr class="layui-bg-red">
<div class="layui-row" >
    <div class="layui-col-xs6" style="padding-left: 12px;">
		<%-- <a class="layui-btn" href="javascript:void();" onclick="window.location.href='${ctx}/user/add'">添加</a> --%>
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs6" >
     	<form name="searchPageForm" id="searchPageForm" action="${ctx}/weixinAccount/queryList" method="post">
	     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
	     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		</form>
	</div>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<td class="sn" style="text-align: center;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<th >名称</th>
			<th >微信号</th>
			<th >原始ID</th>
			<th >公众号类型</th>
			<th style="width: 120px;">邮箱</th>
			<th style="width: 120px;">帐号APPID</th>
			<th style="width: 120px;">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${empty(page.result)||page.result==null }" var="status">
		<tr height="32" align="center">
			<td colspan="9">
				无数据
			</td>
		</tr>
	</c:if>
		<c:forEach items="${page.result }" var="weixinAccount">
		<tr>
			<td style="text-align: center;"><input type='checkbox' name="id" id="id1" value="${weixinAccount.dbid }"/></td>
			<td>${weixinAccount.accountname }</td>
			<td>${weixinAccount.accountnumber }</td>
			<td>${weixinAccount.weixinAccountid }</td>
			<td>
				<c:if test="${weixinAccount.accounttype=='1' }">
					服务号
				</c:if>
				<c:if test="${weixinAccount.accounttype=='2' }">
					订阅号
				</c:if>
			</td>
			<td>${weixinAccount.accountemail }</td>
			<td>${weixinAccount.accountappid }</td>
			<td style="text-align: center;">
				<a href="javascript:void(-1)" class="layui-btn layui-btn-xs" onclick="window.location.href='${ctx}/weixinAccount/edit?dbid=${weixinAccount.dbid}&parentMenu=${param.parentMenu }'">编辑</a>
				<a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-xs" onclick="$.utile.deleteById('${ctx}/weixinAccount/delete?dbids=${weixinAccount.dbid}','searchPageForm')" title="删除">删除</a>
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
<div id="page"></div>


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
  var $ = layui.$, active = {
	  setTop:function(){
		  deleteByIds('${ctx}/weixinAccount/delete');
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
</body>
</html>
