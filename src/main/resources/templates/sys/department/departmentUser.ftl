<#include "../../commons/macro.ftl">
<@commonHead/>


<link href="${ctx}/css/depCom.css" type="text/css" rel="stylesheet"/>
<SCRIPT type="text/javascript">
		<!--
		var setting = {
				edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeDrag: beforeDrag,
					beforeDrop: beforeDrop
				}
			};
		function beforeDrag(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		}
		//
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {
			$.post("${ctx}/department/updateUser?dbid="+treeNodes[0].id+"&parentId="+targetNode.id+"&time="+new Date(),{},function (data){
				if(data=='error'){
					
				}else{
					//绑定查询结果
				}	
			})
			return targetNode ? targetNode.drop !== false : true;
		}

		var zTree, rMenu;
		$(document).ready(function(){
				//异步获取部门信息，每当点击右边功能菜单是自动刷新获取部门信息
				var userJson=$.parseJSON('${userJson}');
				$.fn.zTree.init($("#treeDemo"), setting, userJson);
		});

		//-->
	</SCRIPT>
	<style type="text/css">
	.ztree li span.button.switch.level0 {visibility:hidden; width:1px;line-height: 24px;}
	.ztree li ul.level0 {padding:0; background:none;}
	  ul, li{
		margin: 0;padding: 0;border: 0;outline: 0;line-height: 32px;
	}
	div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #4786C6;text-align: left;padding: 2px;}
	div#rMenu ul li{
		padding: 0;border: 0;outline: 0;
		margin: 1px 0;
		padding: 0 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: #66A0DF;
		color: white;
	}
	</style>
<title>部门维护</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);">部门管理</a>
</div>
 <!--location end-->
<div class="line2"></div>
<div class="alert alert-info" style="margin-top: 12px;">
	<strong>提示!</strong>从左边选择人员直接拖到右边对应的树中.
</div>
<div class="listOperate">
	<div class="operate">
   </div>
  	<div class="seracrhOperate">
  		 <form name="searchPageForm" id="searchPageForm" action="${ctx}/department/departmentUser" method="post">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
  	<%-- 	<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
				<td>
					部门:&nbsp;
					<select id="departmentId" name="departmentId" class="small text" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						${departmentSelect }
					</select>
				</td>
				<td>
					用户类型:
					<select id="userType" name="userType" class="small text" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						<option value="1" ${param.userType==1?'selected="selected"':'' } >一网用户</option>
						<option value="2" ${param.userType==2?'selected="selected"':'' } >二网用户</option>
					</select>
				</td>
				<td>
					启用状态&nbsp;
					<select id="userState" name="userState" class="small text" onchange="$('#searchPageForm')[0].submit()">
						<option value="0">请选择...</option>
						<option value="1" ${param.userState==1?'selected="selected"':'' } >启用</option>
						<option value="2" ${param.userState==2?'selected="selected"':'' } >停用</option>
					</select>
				</td>
  				<td><label>名称：</label></td>
  				<td><input type="text" id="userName" name="userName" class="text small" value="${param.userName}"></input></td>
  				<td><label>用户ID：</label></td>
  				<td><input type="text" id="userId" name="userId" class="text small" value="${param.userId}"></input></td>
  				<td><div href="javascript:void(-1)" onclick="$('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table> --%>
   		</form>
   	</div>
   		<div style="clear: both;"></div>
</div>

<div class="content_wrap"  style="margin-top: 20px;height: 480px;width: 100%;background-color: #F8F8F8">
	<div class="zTreeDemoBackground leftZ" style="width: 350px;width: 40%;">
		<ul id="treeDemo" class="ztree" style="width: 320px;"></ul>
	</div>
</div>
</body>
</html>