
<!DOCTYPE html>
<html>
<head>



 <style type="text/css">
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
<title>用户管理</title>
<link href="${ctx}/css/depCom.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?1"></script>





<SCRIPT type="text/javascript">
		var setting = {
				edit: {
					drag: {
						autoExpandTrigger: true,
						prev: dropPrev,
						inner: dropInner,
						next: dropNext
					},
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				view: {
					dblClickExpand: false,
					addHoverDom:addHoverDom,
					updateDom:updateDom,
					fontCss: setFontCss
				},
				data: {
					simpleData: {
						enable: true
					}
				}
,
			check: {
				enable: false
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeDragOpen: beforeDragOpen,
				onDrag: onDrag,
				onDrop: onDrop,
				onExpand: onExpand,
				onClick: onClick,
				onRightClick: OnRightClick
			}
		};

		function onClick(event, treeId, treeNode, clickFlag) {
			if(null!=treeNode&&treeNode!=undefined){
				if(treeNode.level>0){
					$("#departmentId").val(treeNode.id);
					$('#searchPageForm')[0].submit();
				}else{
					$("#departmentId").val("");
					$('#searchPageForm')[0].submit();
				}
			}
		}
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu(treeNode, event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu(treeNode, event.clientX, event.clientY);
			}
		}
		function setFontCss(treeId, treeNode) {
			var departmentId=$("#departmentId").val();
			if(null!=departmentId&&departmentId!=undefined&&departmentId!=''){
				var obj={'color':'red','border':'1px #FFB951 solid','background-color':'#FFE6B0'} ;
				  return treeNode.id==departmentId ? obj : {};
			}
		}
		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			/* if (type.menu==0) { */
				if(type.root!=undefined &&type.root=="root"){
					$("#m_add").show();
					$("#m_order").show();
					$("#m_delete").hide();
					$("#m_edit").hide();
				}else if(type.root==undefined){
					$("#m_delete").show();
					$("#m_edit").show();
					$("#m_add").show();
					$("#m_order").show();
				}
			/* } else if(type.menu==1){
				$("#m_delete").show();
				$("#m_edit").show();
				$("#m_add").show();
			}else if(type.menu=2){
				$("#m_delete").show();
				$("#m_edit").show();
				$("#m_add").hide();
			} */
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		var addCount = 1;
		
		function add() {
			var node=zTree.getSelectedNodes()[0];
			var parentId=0,menu=0;
			if(null==node){
				parentId=0;
			}else{
				parentId=node.id;
			}
			if(node.menu==0){
				if(node.root!=undefined&&node.root=="root"){
					menu=0;
				}else{
					menu=1;
				}
			}
			else if(node.menu==1){
				menu=2;
			}
			$.utile.openDialog('${ctx}/department/edit?parentId='+parentId,'添加',760,420);
			hideRMenu();
			/* var newNode = { name:"增加" + (addCount++)};
			if (zTree.getSelectedNodes()[0]) {
				newNode.checked = zTree.getSelectedNodes()[0].checked;
				zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
			} else {
				zTree.addNodes(null, newNode);
			} */
		}
		function edit() {
			var nodes = zTree.getSelectedNodes()[0];
			$.utile.openDialog('${ctx}/department/edit?dbid='+nodes.id,'编辑',760,500);
			hideRMenu();
		}
		function order() {
			var nodes = zTree.getSelectedNodes()[0];
			$.utile.openDialog('${ctx}/department/orderNum?parentId='+nodes.id,'排序',720,500);
			hideRMenu();
		}
		function deleteId(checked) {
			var node=zTree.getSelectedNodes()[0];
			var childrens=node.children;
			//删除部门信息时
			//1、先判断是否为最后一个部门节点
			//2、确定删除数据
			//3、ajax提交选择删除数据，返回删除状态信息
			//4、提示删除是否成功
			if(null!=childrens&&childrens.length>0){
				layer.msg( "请先删除『"+node.name+"』的子部门！", {icon: 5});
			}
			if(childrens.length==0){
				layer.confirm("确定删除选择数据吗？", function(index){
					 $.post("${ctx}/department/delete?dbid="+node.id,{},callBack);
						function callBack(data) {
							if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
								layer.alert(data[0].message, {icon: 5});
							}
							if (data[0].mark == 1) {// 删除数据失败时提示信息
								layer.msg(data[0].message, {icon: 5});
							}
							if (data[0].mark == 0) {// 删除数据成功提示信息
								layer.msg(data[0].message,{icon: 1});
								zTree.removeNode(node);
								layer.closeAll();
							}
						}
					})
			}
			hideRMenu();
		}
		function resetTree() {
			hideRMenu();
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
		function expandLevel(treeObj,node)  
        {  
			var parent=node.getParentNode();
			if(null!=parent){
                expandLevel(treeObj,parent);  
	            treeObj.expandNode(node, true, false, false);  
			}else{
	            treeObj.expandNode(node, true, false, false);  
			}
        }  
		var zTree, rMenu;
		$(document).ready(function(){
				//异步获取部门信息，每当点击右边功能菜单是自动刷新获取部门信息
				$.post("${ctx}/department/editResourceJson?timeStamp="+new Date(), { } ,function callback(json){
				if(null!=json&&json!=1){
					$.fn.zTree.init($("#treeDemo"), setting, json);
					zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var departmentId=$("#departmentId").val();
					var node2=zTree.getNodesByParam("id", departmentId, null);
					if(null!=node2&&node2!=undefined){
						var node=node2[0];
						if(node!=undefined){
							var id=node.id;
							if(id!=''&&id!=undefined){
								expandLevel(zTree,node);
							}
							zTree.updateNode(node);  
						}
					}
					rMenu = $("#rMenu");
				}else{
					var zNodes =[];
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					zTree = $.fn.zTree.getZTreeObj("treeDemo");
					rMenu = $("#rMenu");
					$("#treeDemo").append("<li>暂无部门信息！<br>点击右键添加部门信息！</li>");
				}
			});
		});
		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function addHoverDom(treeId, treeNode,child) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.addNodes(treeNode, child);
			return false;
		};
		function updateDom(treeId, node) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.updateNode(node);
			return false;
		};

		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}

		var log, className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes." );
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
			showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "+ (isCopy==null? "cancel" : isCopy ? "copy" : "move"));
			return true;
		}
		function onDrag(event, treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes." );
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			var tree=treeNodes[0];
			var npNode=treeNodes[0].getParentNode();
			$.post("${ctx}/department/updateParent?dbid="+tree.id+"&parentId="+npNode.id+"&timeStamp="+new Date(),{},function (data){
				if(data[0].mark==0){//删除数据成功
					$.utile.tips(data[0].message);
					return ;
				}
				else if(data[0].mark==1){
					$.utile.tips(data[0].message);
					return ;
				}
			})
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
				showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
			}
		}

		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		function setTrigger() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
		}
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
<title>用户管理</title>
</head>
<body style="background: #FFF;">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/userBussi/queryBussiList'">用户管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="content_wrap"  style="height: 550px;width: 100%;margin-top: 40px;">
	<div class="zTreeDemoBackground leftZ" style="position: absolute;bottom: 0;top: 50px;width: 13.5%;height: 450px;" >
		<ul id="treeDemo" class="ztree" style="width: 100%;height: 438px;"></ul>
	</div>
	<div style="left: 15%;position: absolute;width: 85%;bottom: 0;top: 50px;">
		<div class="layui-row" >
		    <div class="layui-col-xs6" >
				<a class="layui-btn" href="javascript:void(-1);" onclick="window.location.href='${ctx}/userBussi/add'">添加管理员</a>
				<a class="layui-btn" href="javascript:void(-1);" onclick="window.location.href='${ctx}/userBussi/addComm'">普通用户</a>
				<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
		    </div>
		    <div class="layui-col-xs6" >
			     <form name="searchPageForm" id="searchPageForm" action="${ctx}/userBussi/queryBussiList" method="post">
			     	<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
					<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
					<div class="layui-form-item" style="margin-bottom: 2px;">
			    	   <label class="layui-form-label" style="width: 60px;padding: 9px 5px;">用户类型:</label>
					    <div class="layui-input-inline" style="width: 120px;">
					        <select id="adminType" name="adminType" lay-filter='adminType' class="layui-input">
								<option value="0">请选择...</option>
								<option value="1" ${param.adminType==1?'selected="selected"':'' } >管理员用户</option>
								<option value="2" ${param.adminType==2?'selected="selected"':'' } >普通用户</option>
							</select>
					    </div>
					     <label class="layui-form-label" style="width: 60px;padding: 9px 5px;">启用状态</label>
					    <div class="layui-input-inline" style="width: 120px;">
						  	 <select id="userState" name="userState"  lay-filter='userState' class="layui-input">
								<option value="0">请选择...</option>
								<option value="1" ${param.userState==1?'selected="selected"':'' } >启用</option>
								<option value="2" ${param.userState==2?'selected="selected"':'' } >停用</option>
							</select>
					    </div>
					  </div>
			    	 <div class="layui-form-item" style="margin-bottom: 2px;">
				  		<label class="layui-form-label" style="width: 60px;padding: 9px 5px;">名称</label>
					    <div class="layui-input-inline" style="width: 120px;">
						   <input type="text" id="userName" name="userName" value="${param.userName}" autocomplete="off" class="layui-input"/>
					    </div>
				  		<label class="layui-form-label" style="width: 60px;padding: 9px 5px;">用户ID</label>
					    <div class="layui-input-inline" style="width: 120px;">
						   <input type="text" id="userId" name="userId" value="${param.userId}" autocomplete="off" class="layui-input"/>
					    </div>
					     <a id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search" onclick="document.getElementById('searchPageForm').submit()"><i class="layui-icon layui-btn-icon"></i>查询</a>
				  	</div>
				  </form>
		    </div>
		</div>
		<c:if test="${empty(page.result)||page.result==null }" var="status">
			<div class="alert alert-info">
				<strong>提示!</strong> 无用户信息！请点击“添加”按钮进行添加数据操作
			</div>
		</c:if>
		<c:if test="${status==false }">
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;" lay-size="sm">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<th class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></th>
			<th class="span2" style="text-align: center;">用户Id</th>
			<th class="span3" style="text-align: center;">姓名</th>
			<th class="span2" style="text-align: center;">角色</th>
			<th class="span2" style="text-align: center;">用户类型</th>
			<th class="span2" style="text-align: center;">部门</th>
			<th class="span2" style="text-align: center;">用户状态</th>
			<th class="span3" style="text-align: center;">操作</th>
		</tr>
		</thead>
		<c:forEach var="user" items="${page.result }">
			<tr height="32" align="center">
				<td><input type='checkbox' name="id" id="id1" value="${user.dbid }"/></td>
				<td>${user.userId }</td>
				<td>${user.realName }/${user.mobilePhone }</td>
				<td>
					<c:forEach var="role" items="${user.roles }">
						${role.name },
					</c:forEach>
				</td>
				<td>
					<c:if test="${user.adminType==1 }">
						管理员用户
					</c:if>
					<c:if test="${user.adminType==2 }">
						普通用户
					</c:if>
				</td>
				<td>${user.department.name }</td>
				<td >
					<c:if test="${user.userState==1}">
						<span style="color:blue;">启用</span>
					</c:if>
					<c:if test="${user.userState==2}">
						<span style="color: red;">停用</span>
					</c:if>
				</td>
				<td>
					<c:if test="${user.adminType==1 }">
						<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="window.location.href='${ctx}/userBussi/edit?dbid=${user.dbid}'">编辑</a>
					</c:if>
					<c:if test="${user.adminType==2 }">
						<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="window.location.href='${ctx}/userBussi/editComm?dbid=${user.dbid}'">编辑</a>
					</c:if>
					<c:if test="${user.userState==1}">
						<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/userBussi/stopOrStartUser?dbid=${user.dbid}&type=2','searchPageForm','您确定【${user.realName}】停用该用吗')">停用</a>
					</c:if>
					<c:if test="${user.userState==2}">
						<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/userBussi/stopOrStartUser?dbid=${user.dbid}&type=2','searchPageForm','您确定【${user.realName}】启用该用吗')">启用</a>
					</c:if>
					<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="window.location.href='${ctx}/userBussi/userRole?dbid=${user.dbid}'">授权</a>
					<a href="javascript:void(-1)" class="layui-btn layui-btn-xs"	onclick="$.utile.operatorDataByDbid('${ctx}/user/resetPassword?dbid=${user.dbid}&type=2','searchPageForm','您确定【${user.realName}】重置密码')">重置密码</a>
			</tr>
		</c:forEach>
		</table>
		<div id="page">
		</div>
		</c:if>
	</div>
</div>
	<div id="rMenu">
	<ul>
		<li id="m_add" onclick="add();">增加</li>
		<li id="m_edit" onclick="edit();">编辑</li>
		<li id="m_delete" onclick="deleteId();">删除</li>
		<li id="m_order" onclick="order();">排序</li>
	</ul>
</div>

<script src="${ctx}/widgets/utile/comm.js"></script>
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
  //监听表格复选框选择
  table.on('checkbox(demo)', function(obj){
    console.log(obj)
  });
  form.on('select(adminType)', function(data){
	  var qForm=$("#searchPageForm");
       qForm.submit();
  })
  form.on('select(userState)', function(data){
	  var qForm=$("#searchPageForm");
       qForm.submit();
  })
  var $ = layui.$, active = {
	  setTop:function(){
		  deleteByIds('${ctx}/userBussi/delete');
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