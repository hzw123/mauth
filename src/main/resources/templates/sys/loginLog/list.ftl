<#include "../../commons/macro.ftl">
<@commonHead/>

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
<title>登录日志管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >登录日志管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    <div class="layui-col-xs9" >
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs3" >
    	<form name="searchPageForm" id="searchPageForm" action="${ctx}/loginLog/queryList" method="post">
			 <input type="hidden" id="currentPage" name="page" value='${page.page}'>
			 <input type="hidden" id="paramPageSize" name="size" value='${page.size}'>
			 <table>
				<tr>
					<td>登录用户：</td>
					<td><input type="text" id="userName" name="userName" class="input-small field"/></td>
					<td><input type="submit" value="查询"/></td>
				</tr>
			 </table>
		</form>
    </div>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<td class="span1" style="width: 30px;text-align: center;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span1" style="text-align: center;">登录ID</td>
			<td class="span2" style="text-align: center;">登录人</td>
			<td class="span2" style="text-align: center;">登录时间</td>
			<td class="span2" style="text-align: center;">登录IP</td>
			<td class="span4" style="text-align: center;">登录地点</td>
			<td class="span3" style="text-align: center;">sessionId</td>
		</tr>
	</thead>
	<#if !(page.result??)>
	    <tr>
            <td colspan="7" style="text-align: center;">
                无数据
            </td>
        </tr>
	</#if>
	<#if status==false>
	    <#list page.result as loginLog>
	        <tr height="32" align="center">
                <td><input type='checkbox' name="id" id="id1" value="${loginLog.dbid}"/></td>
                <td>${loginLog.userId}</td>
                <td>${loginLog.userName}</td>
                <td>${(loginLog.loginDate?string('yyyy-MM-dd HH:mm:ss'))}</td>
                <td>${loginLog.ipAddress}</td>
                <td>${loginLog.loginAddress}</td>
                <td>${loginLog.sessionId}</td>
            </tr>
	    </#list>
	</#if>
	</table>
	<div id="page">
	</div>
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
			  deleteByIds('${ctx}/loginLog/delete');
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