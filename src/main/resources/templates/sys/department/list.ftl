<#include "../../commons/macro.ftl">
<@commonHead/>

<link href="${ctx}/css/depCom.css" type="text/css" rel="stylesheet"/>

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
				onRightClick: OnRightClick
			}
		};

		function onClick(event, treeId, treeNode, clickFlag) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.expandNode(treeNode, null, null, null, true);
			if(null!=treeNode&&treeNode!=undefined){
				if(treeNode.level>0){
					$.post("${ctx}/department/getDepartmentByDbid?dbid="+treeNode.id+"&time="+new Date(),{},function (data){
						if(data=='error'){
							
						}else{
							//绑定查询结果
							$("#name").val(data.name);
							$("#phone").val(data.phone);
							$("#fax").val(data.fax);
							$("#manager").val(data.manager);
							$("#suqNo").val(data.suqNo);
							$("#discription").val(data.discription);
						}	
					})
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
			$.utile.openDialog('${ctx}/department/edit?parentId='+parentId,'添加',720,420);
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
			$.utile.openDialog('${ctx}/department/edit?dbid='+nodes.id,'编辑',720,420);
			hideRMenu();
		}
		function order() {
			var nodes = zTree.getSelectedNodes()[0];
			$.utile.openDialog('${ctx}/department/orderNum?parentId='+nodes.id,'排序',720,400);
			hideRMenu();
		}
		function deleteById(checked) {
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
	<strong>提示!</strong>选择部门节点，点击鼠标右键进行部门操作！
</div>
<div class="content_wrap"  style="margin-top: 20px;height: 480px;width: 100%;background-color: #F8F8F8">
	<div class="zTreeDemoBackground leftZ">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<input type="hidden" id="departmentId" name="departmentId" value='${param.parentId}'>
	<div class="right">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;margin-top: 5px;" >
			<tr height="42">
				<td class="formTableTdLeft" style="width: 100px">名称:&nbsp;</td>
				<td ><input type="text" name="department.name" id="name" readonly="readonly"	value="${department.name }" class="large text" title="部门名称"	></td>
			</tr>
			<tr height="42" >
				<td class="formTableTdLeft" style="width: 100px">电话:&nbsp;</td>
				<td ><input type="text" name="department.phone" id="phone" readonly="readonly"
					value="${department.phone }" class="large text" title="电话"	></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 100px">传真:&nbsp;</td>
				<td ><input type="text" name="department.fax" id="fax" readonly="readonly"
					value="${department.fax }" class="large text" title="传真"	></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 100px">部门主管:&nbsp;</td>
				<td ><input type="text" name="department.manager" id="manager" readonly="readonly"
					value="${department.manager }" class="large text" title="部门主管" readonly="readonly"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 100px">序号:&nbsp;</td>
				<td ><input type="text" name="department.suqNo" id="suqNo" readonly="readonly"
					value="${department.suqNo }" class="input-small text" title="序号">	</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 100px">部门职能:&nbsp;</td>
				<td ><textarea readonly="readonly" name="department.discription" id="discription"
					 class="textarea text" title="用户名" cols="60" rows="8">${department.discription }</textarea></td>
			</tr>
		</table>
	</div>
</div>
	<div id="rMenu">
	<ul>
		<li id="m_add" onclick="add();">增加</li>
		<li id="m_edit" onclick="edit();">编辑</li>
		<li id="m_delete" onclick="deleteById();">删除</li>
		<li id="m_order" onclick="order();">排序</li>
	</ul>
</div>
</body>

<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
layui.use(['layer'], function(){
  var table = layui.table,laypage=layui.laypage;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
});
</script>
</html>