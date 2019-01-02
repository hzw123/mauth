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
.layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
.layui-table  th{
	text-align: center;
}
</style>
<title>分店管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/enterprise/queryList'">分店管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<br>
<div class="alert alert-error">
	提示：删除分店，不会删除对应分店的管理员和部门信息
</div>
<br>
<div class="layui-row" >
    <div class="layui-col-xs9" >
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs3" >
	     <form name="searchPageForm" id="searchPageForm" action="${ctx}/enterprise/queryList" method="post">
	     	<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
			<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
	    	 <div class="layui-form-item" style="margin-bottom: 2px;">
		  		<label class="layui-form-label" style="width: 60px;padding: 9px 5px;">分店</label>
			    <div class="layui-input-inline" style="width: 100px;">
				   <input type="text" id="enterpriseName" name="enterpriseName" value="${enterpriseName}" autocomplete="off" class="layui-input"/>
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
			<th class="sn" style="width: 30px;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></th>
			<th style="width: 30px;">编码</th>
			<th style="width: 60px;">类型</th>
			<th style="width: 120px;">简称</th>
			<th style="width: 200px;">地址</th>
			<th style="width: 120px;">创始会员卡状态</th>
			<th style="width: 120px;">设置</th>
			<th style="width: 100px;" >操作</th>
		</tr>
	</thead>
	<c:if test="${empty(page.result)||page.result==null }" var="status">
		<tr>
			<td colspan="9" style="text-align: center;">
				无数据
			</td>
		</tr>
	</c:if>
	<c:forEach var="enterprise" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${enterprise.dbid }"/></td>
			<td align="left" >${enterprise.no }</td>
			<td align="left" >
				<c:if test="${enterprise.entType==1 }">
					直营店
				</c:if>
				<c:if test="${enterprise.entType==2 }">
					加盟店
				</c:if>
			</td>
			<td align="left" style="text-align: left;">${enterprise.name }</td>
			<td align="left" style="text-align: left;">${enterprise.address }</td>
			<td>
				 <c:if test="${enterprise.startMemberCardStatus==1 }">
				      	<span>待开启</span>
				 </c:if>
				  <c:if test="${enterprise.startMemberCardStatus==2 }">
				      	<span>已开启</span>
				  </c:if>
				  <c:if test="${enterprise.startMemberCardStatus==3 }">
				      	<span>已关闭</span>
				  </c:if>
			</td>
			<td>
			<form class="layui-form" action="">
				      <c:if test="${enterprise.startMemberCardStatus==1 }">
						<div class="layui-form-item">
					    <label class="layui-form-label">开启</label>
					    	<div class="layui-input-block">
						      <input type="checkbox" name="startMemberCardStatus" value="2" status="${enterprise.startMemberCardStatus }" enterpriseId="${enterprise.dbid }" lay-skin="switch" lay-filter="switchTest" lay-text="开启|取消">
						    </div>
					  	</div>
				      </c:if>
				      <c:if test="${enterprise.startMemberCardStatus==2 }">
				      <div class="layui-form-item">
					    <label class="layui-form-label">关闭</label>
					    	<div class="layui-input-block">
					      		<input type="checkbox" name="startMemberCardStatus" lay-skin="switch" value="3" status="${enterprise.startMemberCardStatus }"  enterpriseId="${enterprise.dbid }" lay-filter="switchTest" lay-text="关闭|取消">
					      </div>
					    </div>
				      </c:if>
				      <c:if test="${enterprise.startMemberCardStatus==3 }">
				      	<div class="layui-form-item">
					    <label class="layui-form-label">再次启用</label>
					    	<div class="layui-input-block">
					      		<input type="checkbox" name="startMemberCardStatus" lay-skin="switch" value="1" status="${enterprise.startMemberCardStatus }" enterpriseId="${enterprise.dbid }" lay-filter="switchTest" lay-text="再次启用|取消">
					      	</div>
					     </div>
				      </c:if>
			</form>
			</td>
			<td>
				<div class="layui-table-cell laytable-cell-1-10"> 
					<a class="layui-btn layui-btn-xs" onclick="window.location.href='${ctx}/enterprise/edit?dbid=${enterprise.dbid}&redirectType=2'" lay-event="edit">编辑</a>
					<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-method="del" url='${ctx}/enterprise/delete?dbids=${enterprise.dbid}'>删除</a>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>
<div id="page">
</div>
</body>

<script src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/widgets/utile/comm.js"></script>
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
  //监听指定开关
  form.on('switch(switchTest)', function(data){
    var value=$(this).attr("status");
    var enterpriseId=$(this).attr("enterpriseId");
    if(this.checked){
    	value=data.value;
    }
    $.post('${ctx}/enterprise/saveStartMemberCard',{"startMemberCardStatus":value,"enterpriseId":enterpriseId},function(data){
    	var obj;
		if(data.message!=undefined){
			obj=$.parseJSON(data.message);
		}else{
			obj=data;
		}
		if(obj[0].mark==1){
			  setTimeout(
						function() {
						  layer.msg(obj[0].message, {
						      offset: '6px'
						    });
						  window.location.href ="${ctx}/enterprise/queryList";
						}, 1000);
		}else if(obj[0].mark==0){
		 
		  setTimeout(
					function() {
						 layer.msg(obj[0].message, {
						      offset: '6px'
						    });
					  window.location.href ="${ctx}/enterprise/queryList";
					}, 1000);
		}
    })
  });
  var $ = layui.$, active = {
		  setTop:function(){
			  deleteByIds('${ctx}/enterprise/delete');
		  },
		  del:function(){
			  var url=$(this).attr("url");
			  deleteById(url);
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