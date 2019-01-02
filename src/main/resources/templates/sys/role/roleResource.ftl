<#include "../../commons/macro.ftl">
<@commonHead/>
<link href="${ctx}/css/depCom.css" type="text/css" rel="stylesheet"/>
<SCRIPT type="text/javascript">
		<!--
		var setting = {
				view: {
					dblClickExpand: dblClickExpand
				},
				data: {
					simpleData: {
						enable: true
					}
				},
			check: {
				enable: true
			},
			callback: {
				beforeClick: function(treeId,treeNode){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.checkNode(treeNode, !treeNode.checked, null, true);
					return false;
				},
				onCheck: function(e, treeId, treeNode){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
					val="";
					
					for (var i=0, l=nodes.length; i<l; i++) {
						v += nodes[i].name + ",";
						val+=nodes[i].id+",";
					}
					if (v.length > 0 ) v = v.substring(0, v.length-1);
					if (val.length > 0 ) val = val.substring(0, val.length-1);
					$("#resourceIds").val(val);
				}
			}
		}
	
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}

		var zTree, rMenu;
		$(document).ready(function(){
				//异步获取部门信息，每当点击右边功能菜单是自动刷新获取部门信息
				$.post("${ctx}/role/roleResourceJson?dbid=${role.dbid}&timeStamp="+new Date(), { } ,function callback(json){
				if(null!=json&&json!=1){
					$.fn.zTree.init($("#treeDemo"), setting, json);
					zTree = $.fn.zTree.getZTreeObj("treeDemo");
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
		//-->
	</SCRIPT>
	<style type="text/css">
	.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
	.ztree li ul.level0 {padding:0; background:none;}
	  ul, li{
		margin: 0;padding: 0;border: 0;outline: 0;
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
<title>用户添加</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);">资源管理</a>
</div>
<hr class="layui-bg-red">
<div class="content_wrap" style="margin-left: 24px;height: 480px;">
	<div class="zTreeDemoBackground leftZ">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
			<form action="" id="frmId" name="frmId" method="post" target="_self">

				<input type="hidden" id="resourceIds" name="resourceIds" value="${resourceIds }"></input>
				<input type="hidden" id="roleId" name="roleId" value="${role.dbid}"></input>
				<div class="layui-form-item">
				    <div class="layui-input-block">
				      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
				      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
				    </div>
				  </div>
			</form>
	</div>
</div>
</body>


<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/role/saveResource');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>