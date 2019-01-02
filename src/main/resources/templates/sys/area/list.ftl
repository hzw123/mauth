<#include "../../commons/macro.ftl">
<@commonHead/>
<title>区域管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/user/queryList'">用户管理中心</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="${ctx}/area/add?parentMenu=${param.parentMenu}&parentId=${parent.dbid}" >添加</a>
		<c:if test="${!empty(parent) }">
			<a class="but button" href="${ctx}/area/queryList?parentMenu=${param.parentMenu}&parentId=${parent.area.dbid}">返回上一级</a>
		</c:if>
   </div>
</div>
<c:if test="${empty(areas)||areas==null }" var="status">
	<div class="alert alert-info" >
		<strong>提示!</strong>[${parent.name}] 当前未添加数据.
	</div>
</c:if>
<c:if test="${status==false }">
	<ul class="mainTable">
		<c:forEach var="area" items="${areas }">
			<li style="float: left;min-width: 120px;">
				<div class="comments">
					<p>
						<a href="javascript:void(-1)" class="aedit" title="${area.name }" onclick="window.location.href='${ctx}/area/queryList?parentMenu=${param.parentMenu}&parentId=${area.dbid }'">${area.name }</a>
					</p>
					<a class="butMini button" href="javascript:void(-1)" onclick="window.location.href='${ctx}/area/edit?parentMenu=${param.parentMenu}&dbid=${area.dbid }'">编辑</a>
					<a class="butMini button" href="javascript:void(-1)" onclick="$.utile.operatorDataByDbid('${ctx}/area/delete?dbid=${area.dbid}&parentId=${parent.dbid}','searchPageForm','提示：删除数据同时已将删除子级数据！你确定要删除吗？')">删除</a>
				</div>
			</li>
		</c:forEach>
		<li style="clear: both;"></li>
			<div style="height: 60px;width: 100%;background-color: #F8F8F8"></div>
	</ul>
</c:if>
</body>
</html>
