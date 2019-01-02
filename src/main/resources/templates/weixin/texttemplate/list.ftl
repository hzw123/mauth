<#include "../../commons/macro.ftl">
<@commonHead/>
<title>关注时回复</title>

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
	<a href="javascript:void(-1);" >文本消息</a>
</div>
<hr class="layui-bg-red">
<div class="layui-row" >
    <div class="layui-col-xs6" style="padding-left: 12px;">
		<a class="layui-btn" href="javascript:void();" onclick="window.location.href='${ctx}/weixinTexttemplate/add'">添加</a>
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);"  data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs6" >
     	<form name="searchPageForm" id="searchPageForm" action="${ctx}/weixinTexttemplate/queryList" method="post">
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
		<td class="sn" style="text-align: center;width: 40px;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
		<th style="width: 140px;">名称</th>
		<th >内容</th>
		<th style="width: 120px;">创建时间</th>
		<th style="width: 100px;">操作</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${empty(page.result)||page.result==null }" var="status">
		<tr height="32" align="center">
			<td colspan="5">
				无数据
			</td>
		</tr>
	</c:if>
	<c:forEach items="${page.result }" var="weixinTexttemplate">
		<tr>
			<td style="text-align: center;"><input type='checkbox' name="id" id="id1" value="${weixinTexttemplate.dbid }"/></td>
			<td style="text-align: center;">${weixinTexttemplate.templatename }</td>
			<td>${weixinTexttemplate.content }</td>
			<td style="text-align: center;">
				 ${fn:substring(weixinTexttemplate.addtime,0,10)  }
			</td>
			<td style="text-align: center;">
			<a href="javascript:void(-1)" class="layui-btn layui-btn-xs" onclick="window.location.href='${ctx}/weixinTexttemplate/edit?dbid=${weixinTexttemplate.dbid}&parentMenu=${param.parentMenu }'">编辑</a>
			<a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-xs" onclick="$.utile.deleteById('${ctx}/weixinTexttemplate/delete?dbids=${weixinTexttemplate.dbid}','searchPageForm')" title="删除">删除</a></td>
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
		  deleteByIds('${ctx}/weixinTexttemplate/delete');
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
