<#include "../../commons/macro.ftl">
<@commonHead/>
<title>角色赋权限</title>
</head>
<body class="bodycolor">
<div class="location">
   	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
   	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
   	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/userBussi/queryBussiList'">账号管理</a>-
	<a href="javascript:void(-1);" onclick="">微信企业号设置</a>
</div>
<hr class="layui-bg-red">
<div id="result">
	<form name="frmId" id="frmId" method="post" target="_self">

	<input type="hidden" name="dbid" value="${user2.dbid }" id="dbid">
	<div class="frmTitle" onclick="showOrHiden('contactTable')">微信状态</div>
	<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
			<thead class="TableHeader">
				<tr>
					<td class="span2">用户Id</td>
					<td class="span2">名字</td>
					<td class="span2">部门</td>
					<td class="span2">手机</td>
					<td class="span2">邮箱</td>
					<td class="span2">微信Id</td>
					<td class="span2">同步状态</td>
					<td class="span2">关注状态</td>
					<td class="span2">操作</td>
				</tr>
			</thead>
				<tr height="32" align="center">
					<td>${user.userId }</td>
					<td align="left" style="text-align: left;">${user.realName }&nbsp;&nbsp; ${user.staff.sex }</td>
					<td align="left" style="text-align: left;">${user.department.name }</td>
					<td align="left">${user.mobilePhone }</td>
					<td align="left">${user.email }</td>
					<td align="left">${user.wechatId }</td>
					<td align="left">
						<c:if test="${user.sysWeixinStatus==1}">
							<span style="color: red;">未同步</span>
						</c:if>
						<c:if test="${user.sysWeixinStatus==2}">
							<span style="color: green;">已同步</span>
						</c:if>
					</td>
					<td align="left">
						<c:if test="${user.attentionStatus==1}">
							<span style="color: red;">未关注</span>
						</c:if>
						<c:if test="${user.attentionStatus==2}">
							<span style="color: green;">已关注</span>
						</c:if>
						<c:if test="${user.attentionStatus==3}">
							<span style="color: red;">已禁用</span>
						</c:if>
					</td>
					<td>
						<c:if test="${!empty(user.wechatId) }">
							<a href="javascript:void(-1)" class="aedit" onclick="$.utile.operatorDataByDbid('${ctx}/address/synSingleUser?dbid=${user.dbid }','searchPageForm','确定同步用户资料信息吗？')">同步</a>
							<c:if test="${user.sysWeixinStatus==2}">
								|
								<a href="javascript:void(-1)" class="aedit" onclick="$.utile.operatorDataByDbid('${ctx}/address/synSingleAtt?dbid=${user.dbid }','searchPageForm','确定更新关注状态？')">更新状态</a>
							</c:if>
						</c:if>
					</td>
				</tr>
			</table>
	<div class="frmTitle" onclick="showOrHiden('contactTable')">红包权限状态</div>
	<c:if test="${empty(appUser) }">
		还未配置用户红包权限。
		<a href="JavaScript:void(-1)" style="color: #2b7dbc" onclick="window.open('${ctx}}/app/queryList')">同步应用用户</a>
		|
		<a href="JavaScript:void(-1)" style="color: #2b7dbc" onclick="window.open('https://qy.weixin.qq.com/cgi-bin/loginpage')">微信企业号</a>
	</c:if>
	<c:if test="${!empty(appUser) }">
		<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
		<thead  class="TableHeader">
			<tr>
				<td class="span2">用户Id</td>
				<td class="span2">名字</td>
				<td class="span2">部门</td>
				<td class="span2">手机</td>
				<td class="span2">邮箱</td>
				<td class="span2">微信Id</td>
				<td class="span2">openId</td>
				<td class="span2">appId</td>
				<td class="span2">状态</td>
				<td class="span2">操作</td>
			</tr>
		</thead>
		<tr height="32" align="center">
			<td>${user.userId }</td>
			<td align="left" style="text-align: left;">${user.realName }&nbsp;&nbsp; ${user.staff.sex }</td>
			<td align="left" style="text-align: left;">${user.department.name }</td>
			<td align="left">${user.mobilePhone }</td>
			<td align="left">${user.email }</td>
			<td align="left">${user.wechatId }</td>
			<td style="text-align: center;">${appUser.openId} </td>
			<td style="text-align: center;">${appUser.redBagAppId} </td>
			<td style="text-align: center;">
				<c:if test="${appUser.status==1 }">
					<span style="color: red">未生成</span>
				</c:if>
				<c:if test="${appUser.status==2 }">
					<span style="color: green">已生成</span>
				</c:if>
			</td>
			<td>
				<c:if test="${appUser.status==1 }">
					<a href="javascript:void(-1)" class="aedit" onclick="$.utile.operatorDataByDbid('${ctx}/appUser/saveOpenId?appId=${app.dbid}&appUserId=${appUser.dbid }','searchPageForm','确定生成OPENID？')">生成openId</a>
				</c:if>
				<c:if test="${appUser.status==2 }">
					<a href="javascript:void(-1)" class="aedit" onclick="$.utile.operatorDataByDbid('${ctx}/appUser/saveOpenId?appId=${app.dbid}&appUserId=${appUser.dbid }','searchPageForm','确定生成OPENID？')">更新openId</a>
				</c:if>
			</td>
		</tr>
	</table>	
	</c:if>
		
	</form>
</div>
<div class="formButton">
    <a class="but butSave" href="javascript:void(-1)" onclick="window.location.href='${ctx}/appUserRole/editSingleRole?userId=${user.dbid}'">继续设置红包奖励</a>
 	<a href="javascript:void(-1)"	onclick="window.history.go(-1)" class="but butCancle">返回</a>
</div>
</body>
<script type="text/javascript">
/*
 * 1、删除一条数据 2、url格式为：${ctx}/user/deleteById?dbid=1
 */
$.utile.operatorDataByDbid = function(url,searchFrm,conf) {
	var content="您确定删除选择数据吗？";
	if(null!=conf&&conf!=undefined){
		content=conf;
	}
	var params;
	if(null!=searchFrm&&searchFrm!=undefined&&searchFrm!=''){
		params=$("#"+searchFrm).serialize();
	}
	window.top.art.dialog({
		content : content,
		icon : 'question',
		width:"250px",
		height:"80px",
		window : 'top',
		lock : true,
		ok : function() {// 点击去定按钮后执行方法
			$.post(url + "&datetime=" + new Date(),params,function callBack(data) {
				if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
					
					window.top.art.dialog({
						content : data[0].message,
						icon : 'warning',
						window : 'top',
						width:"250px",
						height:"80px",
						lock : true,
						ok : function() {// 点击去定按钮后执行方法
							$.utile.close();
							return;
						}
					});
					
				}
				if (data[0].mark == 1) {// 删除数据失败时提示信息
					/* $.cqtaUtile.errMessage(data[0].message); */
					$.utile.tips(data[0].message);
					return ;
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					/* $.cqtaUtile.okDeleteMessage(data[0].message); */
					$.utile.tips(data[0].message);
					setTimeout(function() {
						window.location.reload();
					}, 1000);
					return ;
				}
				// 页面跳转到列表页面
				
			});
		},
		cancel : true
	});
}
	
</script>
</html>