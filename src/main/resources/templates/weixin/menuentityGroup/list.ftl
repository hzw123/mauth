<#include "../../commons/macro.ftl">
<@commonHead/>
<title>菜单管理</title>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">菜单管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="layui-row" >
    <div class="layui-col-xs6" >
		<a class="layui-btn" style="margin-left: 12px;" href="javascript:void();" onclick="$.utile.openDialog('${ctx}/weixinMenuentityGroup/edit','添加个性化菜单',1024,560)">添加</a>
    </div>
	<div class="layui-col-xs6" >
	    <form name="searchPageForm" id="searchPageForm" action="${ctx}/weixinMenuentityGroup/queryList" method="post">
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
			<th style="width: 120px;text-align: center;">名称</th>
			<th style="width: 80px;text-align: center;">类型</th>
			<th style="width: 80px;text-align: center;">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${empty(page)||page==null }" var="status">
		<tr height="32" align="center">
			<td colspan="3">
				无用数据
			</td>
		</tr>
	</c:if>
		<c:forEach items="${page.result }" var="weixinMenuentityGroup">
			<c:set value="${weixinMenuentityGroup.weixinMenuentityGroupMatchRule }" var="weixinMenuentityGroupMatchRule"></c:set>
			<tr>
				<td style="text-align: left;">${weixinMenuentityGroup.name }</td>
				<td>
					<c:if test="${weixinMenuentityGroup.type==1 }">
						默认菜单
					</c:if>
					<c:if test="${weixinMenuentityGroup.type==2 }">
						个性菜单
					</c:if>
				</td>
				<td style="text-align: center;">
					<a href="javascript:void(-1)" class="layui-btn layui-btn-xs" onclick="window.location.href='${ctx}/weixinMenuentity/queryList?groupId=${weixinMenuentityGroup.dbid}&parentMenu=1'">菜单管理</a>
					<c:if test="${weixinMenuentityGroup.type==2 }">
						<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs" onclick="$.utile.openDialog('${ctx}/weixinMenuentityGroup/edit?dbid=${weixinMenuentityGroup.dbid}','添加菜单',1024,560)">编辑</a>
						<a href="javascript:void(-1)" class="layui-btn layui-btn-xs layui-btn-danger" onclick="$.utile.deleteById('${ctx}/weixinMenuentityGroup/delete?dbids=${weixinMenuentityGroup.dbid}','searchPageForm')" title="删除">删除</a>
					</c:if>
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
		  deleteByIds('${ctx}/user/delete');
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
<script type="text/javascript">
	function synDataDepart(){
		$.post('${ctx}/weixinMenuentity/sameMenu',{},function (data){
			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
				$.utile.tips(data[0].message);
			}
			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
				$.utile.tips(data[0].message);
				// 保存失败时页面停留在数据编辑页面
			}
			return;
		})
	}
	function deleteWechatMenu(){
		window.top.art.dialog({
			content : '删除微信端菜删除微信端菜单，本地数据不被删除，您确定删除微信端菜单吗？',
			icon : 'question',
			width:"250px",
			height:"80px",
			lock : true,
			ok : function() {// 点击去定按钮后执行方法
				var param = getCheckBox();
				$.post("${ctx}/weixinMenuentity/deleteWechatMenu?datetime=" + new Date(),{},callBack);
				function callBack(data) {
					if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						window.top.art.dialog({
							content : data[0].message,
							icon : 'warning',
							window : 'top',
							lock : true,
							width:"200px",
							height:"80px",
							ok : function() {// 点击去定按钮后执行方法
								$.utile.close();
								return;
							}
						});

					}

					if (data[0].mark == 1) {// 删除数据失败时提示信息
						$.utile.tips(data[0].message);
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						$.utile.tips(data[0].message);
					}
					// 页面跳转到列表页面
					setTimeout(function() {
						window.location.href = data[0].url
					}, 1000);
				}
			},
			cancel : true
		});
	}
</script>
</body>
</html>
