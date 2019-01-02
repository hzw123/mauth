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
<title>用户管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/user/queryList'">用户管理中心</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">

<div class="layui-row" >
    <div class="layui-col-xs6" >
		<a class="layui-btn" href="javascript:void();" onclick="window.location.href='${ctx}/user/add'">添加</a>
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs6" >
	     <form name="searchPageForm" id="searchPageForm" action="${ctx}/user/queryList" method="post">
	     	<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
			<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
	    	 <div class="layui-form-item" style="margin-bottom: 2px;">
		  		<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">	部门:&nbsp;</label>
			    <div class="layui-input-inline" style="width: 100px;">
					<select id="departmentId" name="departmentId" class="layui-input" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						${departmentSelect }
					</select>
			    </div>
		  		<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">	用户类型:&nbsp;</label>
			    <div class="layui-input-inline" style="width: 100px;">
					
					<select id="userType" name="userType" class="layui-input" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						<option value="1" ${param.userType==1?'selected="selected"':'' } >一网用户</option>
						<option value="2" ${param.userType==2?'selected="selected"':'' } >二网用户</option>
					</select>
			    </div>
		  		<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">	启用状态&nbsp;</label>
			    <div class="layui-input-inline" style="width: 100px;">
					<select id="userState" name="userState" class="layui-input" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						<option value="1" ${param.userState==1?'selected="selected"':'' } >启用</option>
						<option value="2" ${param.userState==2?'selected="selected"':'' } >停用</option>
					</select>
			    </div>
			  </div>
			   <div class="layui-form-item" style="margin-bottom: 2px;">
		  		<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">名称</label>
			    <div class="layui-input-inline" style="width: 100px;">
				   <input type="text" d="userName" name="userName" value="${param.userName}" autocomplete="off" class="layui-input"/>
			    </div>
		  		<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">用户ID：</label>
			    <div class="layui-input-inline" style="width: 100px;">
				   <input type="text" d="userId" name="userId" value="${param.userId}" autocomplete="off" class="layui-input"/>
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
		<td class="sn" style="text-align: center;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
		<td class="span2" style="text-align: center;">用户Id</td>
		<td class="span2" style="text-align: center;">名字</td>
		<td class="span2" style="text-align: center;">手机</td>
		<td class="span2" style="text-align: center;">部门</td>
		<td class="span2" style="text-align: center;">岗位</td>
		<td class="span2" style="text-align: center;">角色</td>
		<td class="span2" style="text-align: center;">用户状态</td>
		<td class="span3" style="text-align: center;">操作</td>
	</tr>
</thead>
<c:if test="${empty(page.result)||page.result==null }" var="status">
	<tr height="32" align="center">
		<td colspan="10">
			无用户信息
		</td>
	</tr>
</c:if>
<c:forEach var="user" items="${page.result }">
	<tr height="32" align="center">
		<td><input type='checkbox' name="id" id="id1" value="${user.dbid }"/></td>
		<td>${user.userId }</td>
		<td align="left" style="text-align: left;">${user.realName }&nbsp;&nbsp; 
			<c:if test="${!empty(user.staff.sex) }">
				(${user.staff.sex })	
			</c:if>
		</td>
		<td align="left">${user.mobilePhone }</td>
		<td align="left" style="text-align: left;">${user.department.name }</td>
		<td align="left" style="text-align: left;">${user.positionNames }</td>
		<td>
			<c:forEach var="role" items="${user.roles }">
				${role.name },
			</c:forEach>
		</td>
		<td align="left">
			<c:if test="${user.userState==1}">
				<span style="color:blue;">启用</span>
			</c:if>
			<c:if test="${user.userState==2}">
				<span style="color: red;">停用</span>
			</c:if>
		</td>
		<td><a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="window.location.href='${ctx}/user/edit?dbid=${user.dbid}'">编辑</a>
			<%-- <a href="javascript:void(-1)"  class="aedit"	onclick="$.utile.deleteById('${ctx}/stopOrStartUser?dbid=${user.dbid}','searchPageForm')">删除</a> --%>
			<c:if test="${user.userState==1}">
				<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/user/stopOrStartUser?dbid=${user.dbid}','searchPageForm','您确定【${user.realName}】停用该用户吗')">停用</a>
			</c:if>
			<c:if test="${user.userState==2}">
				<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/user/stopOrStartUser?dbid=${user.dbid}','searchPageForm','您确定【${user.realName}】启用该用户吗')">启用</a>
			</c:if>
			<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="window.location.href='${ctx}/user/userRole?dbid=${user.dbid}'">授权</a>
			<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/user/resetPassword?dbid=${user.dbid}&type=1','searchPageForm','您确定【${user.realName}】重置密码')">重置密码</a>
	</tr>
</c:forEach>
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
</body>
</html>