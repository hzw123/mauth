<#include "../../commons/macro.ftl">
<@commonHead/>

<title>部门选择择器</title>
<SCRIPT type="text/javascript">
<!--
	var setting = {
		view : {
			selectedMulti : false,
			showIcon: false
		},
		check : {
			enable : true,
			chkboxType : {
				"Y" : "",
				"N" : ""
			}
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onCheck : onCheck
		}
	};

	var clearFlag = false;
	function onCheck(e, treeId, treeNode) {
		selectedNode(treeId,treeNode);
	}
	function clearCheckedOldNodes() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getChangeCheckedNodes();
		for ( var i = 0, l = nodes.length; i < l; i++) {
			nodes[i].checkedOld = nodes[i].checked;
		}
	}
	function selectedNode(treeId, TreeNode) {
		if(TreeNode.checked==true){
			var result='<li id="li'+TreeNode.id+'"><a href="javascript:;" class="select_item" nid="u'+TreeNode.id+'"'+ 
	 		'alias="u'+TreeNode.id+'" title="'+TreeNode.name+'"><span class="icon_diff icon_party"></span>'+TreeNode.name+
	 		'<span	class="addr_del" id="span'+TreeNode.id+'" ck="del"  fornid="u'+TreeNode.id+'" bparty="true"	alias="u'+TreeNode.id+'"></span></a></li>';
	 		$("#treeDemo2").append(result);	
	 		$("#span"+TreeNode.id).bind("click",{TreeNode:TreeNode,treeId:treeId},function(){
	 			deleteDep(treeId, TreeNode);
	 		})
		}else{
			$("#li"+TreeNode.id).remove();
		}
		ok();
	}
	function deleteDep(treeId, treeNode){
		$("#li"+treeNode.id).remove();
		if(treeNode.checked==true){
			treeNode.checked=false;
			$("#"+treeNode.tId+"_check").attr('class','button chk checkbox_false_full');
		}
		ok();
	}
	function count() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var checkedNodes = zTree.getCheckedNodes(true);
		if(checkedNodes.length>0){
			for(var i=0;i<checkedNodes.length;i++){
				var node=checkedNodes[i];
				alert(node.id+" "+node.name);
			}
		}

	}
	function ok(){
		var resultIds="";
		var resultNames="";
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var checkedNodes = zTree.getCheckedNodes(true);
		if(checkedNodes.length>0){
			for(var i=0;i<checkedNodes.length;i++){
				var node=checkedNodes[i];
				resultIds=resultIds+node.id+",";
				resultNames=resultNames+node.name+",";
			}
		}
		resultIds=resultIds.substring(0,resultIds.length-1);
		resultNames=resultNames.substring(0,resultNames.length-1);
		var data=resultIds+"|"+resultNames;
		$("#departments").val(data);
	}
	var zTree, rMenu;
	$(document).ready(
			function() {
				//异步获取部门信息，每当点击右边功能菜单是自动刷新获取部门信息
				$.post("${ctx}/department/editResourceJson?timeStamp="
						+ new Date(), {}, function callback(json) {
					if (null != json && json != 1) {
						$.fn.zTree.init($("#treeDemo"), setting, json);
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
						rMenu = $("#rMenu");
					} else {
						var zNodes = [];
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
						rMenu = $("#rMenu");
						$("#treeDemo")
								.append("<li>暂无部门信息！<br>点击右键添加部门信息！</li>");
					}
				});
			});
//-->
</SCRIPT>
<style type="text/css">
.ztree li span.button.switch.level0 {
	visibility: hidden;
	width: 1px;
}

.ztree li ul.level0 {
	padding: 0;
	background: none;
}

ul,li {
	margin: 0;
	padding: 0;
	border: 0;
	outline: 0;
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #4786C6;
	text-align: left;
	padding: 2px;
}

div#rMenu ul li {
	padding: 0;
	border: 0;
	outline: 0;
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #66A0DF;
	color: white;
}
#treeDemo{
	height: 350px;
}
.select_item{
	font-size: 14px;
	font-family: 微软雅黑,Verdana,Arial,Helvetica,AppleGothic,sans-serif;
	line-height: 16px;
    margin-left: 5px;
    color: #5b5555;
    text-decoration: none;
}
 a:HOVER {
	color: #5b5555;
    text-decoration: none;
    background: none;
}
</style>
</head>
<body class="bodycolor">
<input type="hidden" value="" id="departments" name="departments">
	<div style="width: 510px; height: 360px;margin: 0 auto;">
		<div style="width: 232px; float: left; margin-left: 12px;height: 360px;">
			<div>选择员工所在部门</div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div class="left ico_arrow_left"></div>
		<div style="width: 232px; float: left; margin-left: 12px;">
			<div>员工将属于一下部门</div>
			<ul id="treeDemo2"  style="line-height: 20px;border: 1px solid #629DDD;height: 355px; margin-top: 5px;  overflow-x: auto;  width: 232px;padding-top: 5px;">
			</ul>
		</div>
	</div>
</body>
</html>