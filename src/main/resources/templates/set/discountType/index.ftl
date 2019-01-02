<#include "../../commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="${ctx}/css/global.css" rel="stylesheet" />
    <link href="${ctx}/css/main.css" rel="stylesheet" />
</head>
<body>
    <div class="location">
        <img src="../../images/homeIcon.png" /> &nbsp;
        <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/member/queryList'">折扣方案</a>
    </div>
  <div style="float:left;width:400px;padding-left:5px">
    <div class="layui-btn-container">
    	<button class="layui-btn layui-btn-sm" id="btnAdd">增加折扣方案</button>
    </div>
	<table class="layui-table" id="tTypes" lay-skin="line">
	  <colgroup>
	    <col width="150">
	    <col width="240">
	    <col>
	  </colgroup>
	  <tbody>
		  <c:forEach var="item" items="${discountTypes }">
			<tr id="${item.dbid}" onclick="changeType(${item.dbid},'${item.name}')">
				<td>${item.name }</td>
				<td>
		  			<a class="layui-btn layui-btn-mini" onclick="editType(${item.dbid})">编辑</a>
		  			<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteType(${item.dbid},'${item.name }')">删除</a>
		  			<a class="layui-btn layui-btn-mini" onclick="addItem('${item.name}',${item.dbid })">添加项目</a>
		  			<a class="layui-btn layui-btn-mini" onclick="changeState('${item.name}',${item.dbid },${item.state})">
		  				${item.state==0?'停用':'启用' }
		  			</a>
		  		</td>
			</tr>
		  </c:forEach>
	  </tbody>
	</table> 
  </div>
  
  <div style="margin-left:370px;padding-top:1px">
  	 <fieldset class="layui-elem-field layui-field-title" style="margin-top: 1px;">
  		<legend id="typeName">项目</legend>
	 </fieldset>
	<table class="layui-table" id="tItems" lay-data="{url:'${ctx}/discountType/queryItems', page:true, id:'tItems',limit:10,method:'post'}" lay-filter="tItems">
	  <thead>
	    <tr>
	      <th lay-data="{field:'itemName',align:'center', width:180}">项目名称</th>
	      <th lay-data="{field:'price',align:'center', width:100}">项目价格</th>
	      <th lay-data="{field:'discountPrice',align:'center', width:100}">方案价格</th>
	      <th lay-data="{field:'note',align:'center', width:200}">备注</th>
	      <th lay-data="{field:'createTime',align:'center', width:200}">创建时间</th>
	      <th lay-data="{fixed: 'right', width:180, align:'center', toolbar: '#barOperate'}"></th>
	    </tr>
	  </thead>
	</table>
  </div>
	<script type="text/html" id="barOperate">
  		<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="del">删除</a>
	</script>
  <script type="text/javascript" src="${ctx}/widgets/jquery-3.3.1.min.js"></script>

  <script type="text/javascript">
  var currentTypeId=0;
  var layer;
  var topParent=window.parent;
  if(parent==undefined){
  	topParent=parent;
  }
  
  layui.use(['laypage', 'table','layer','form'], function(){
	var table = layui.table;
    var form = layui.form;
    if(topParent==undefined){
  	 layer = layui.layer;
    }
    else{
  	 layer = topParent.layer;
    }
    
    reloadItems();
    table.on('tool(tItems)', function(obj){
    	if(obj.event === 'del'){
    		layer.confirm('确定要删除该项数据吗？', function(index){
    			var data = obj.data;
    			  $.post('${ctx}/discountType/DeleteItem?dbid='+data.dbid,callBack);
    				function callBack(data) {
    					if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
    						layer.alert(data[0].message, {icon: 5});
    					}
    					if (data[0].mark == 1) {// 删除数据失败时提示信息
    						layer.msg(data[0].message, {icon: 5});
    					}
    					if (data[0].mark == 0) {// 删除数据成功提示信息
    						layer.msg(data[0].message,{icon: 1});
    					}
    					reloadItems();
    				}
    		      });
    	    } else if(obj.event === 'edit'){
    	    	var data = obj.data;
    	    	layer.open({
    	            type: 2
    	            ,title: '折扣方案'
    	            ,area: ['460px', '360px']
    	            ,shade: 0.8
    	            ,content: '${ctx}/discountType/editItem?dbid='+data.dbid
    	            ,zIndex: layer.zIndex //重点1
    	            ,success: function(layero){
    	            	
    	            }
    	    		,end: function () {
    	    			reloadItems();
    	            }
    	          });
    	}
  });
  });

  //新增折扣方案
  $('#btnAdd').on("click",function(){
      layer.open({
        type: 2
        ,title: '折扣方案'
        ,area: ['460px', '360px']
        ,shade: 0.8
        ,content: '${ctx}/discountType/edit'
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
        }
        ,end: function () {
        	location.reload() 
        }
      });
	 });
  
  //编辑折扣方案
  function editType(id){
      layer.open({
          type: 2
          ,title: '折扣方案'
          ,area: ['460px', '360px']
          ,shade: 0.8
          ,content: '${ctx}/discountType/edit?typeId='+id
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
          }
	      ,end: function () {
	      	location.reload() 
	      }
        });
  }
  
  //删除折扣方案
  function deleteType(id,name){
	  layer.confirm('确定要删除【'+name+'】数据吗？', function(index){
		  $.post('${ctx}/discountType/DeleteType?typeId='+id,callBack);
			function callBack(data) {
				if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
					layer.alert(data[0].message, {icon: 5});
				}
				if (data[0].mark == 1) {// 删除数据失败时提示信息
					layer.msg(data[0].message, {icon: 5});
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					layer.msg(data[0].message,{icon: 1});
				}
				location.reload() 
			}
	      });
  }
  
  //删除折扣方案项
  function deleteItem(id,name){
	  layer.confirm('确定要删除【'+name+'】数据吗？', function(index){
		  $.post('${ctx}/discountType/DeleteType?typeId='+id,callBack);
			function callBack(data) {
				if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
					layer.alert(data[0].message, {icon: 5});
				}
				if (data[0].mark == 1) {// 删除数据失败时提示信息
					layer.msg(data[0].message, {icon: 5});
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					layer.msg(data[0].message,{icon: 1});
				}
				reloadItems();
			}
	      });
  }
  
  //新增折扣方案项
  function addItem(name,id){
      layer.open({
          type: 2
          ,title: '添加'+name+'项目'
          ,area: ['460px', '360px']
          ,shade: 0.8
          ,content: '${ctx}/discountType/addItem?typeId='+id
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
          }
	      ,end: function () {
	    	  reloadItems();
	      }
        });
  }
  
  function changeType(id,name){
	  if(currentTypeId==id){
		  return;
	  }
	  $("#tTypes tr").each(function(){
		  $(this).css("background","white");
		  if(this.id==id){
			  $(this).css("background","#bbffff");
		  }
		  });
	  $("#typeName").text(name+"项目");
	  currentTypeId=id;
	  reloadItems();
  }
  
  function reloadItems(){
	  if(currentTypeId==0){
		  var firstId=$("#tTypes tr:eq(0)").attr("id");
		  var name=$("#tTypes tr:eq(0) td:first").text();
		  changeType(firstId,name)
	  }
	  layui.table.reload("tItems", {where: {typeId: currentTypeId}});
	  return false;
  }
  
//编辑折扣方案
  function changeState(name,id,state){
	var stateName=state==0?"停用":"启用";
	state=state==0?9999:0;
	  layer.confirm('确定要'+stateName+'【'+name+'】折扣方案吗？', function(index){
		  $.post('${ctx}/discountType/updateState?typeId='+id+'&state='+state,callBack);
			function callBack(data) {
				if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
					layer.alert(data[0].message, {icon: 5});
				}
				if (data[0].mark == 1) {// 删除数据失败时提示信息
					layer.msg(data[0].message, {icon: 5});
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					layer.msg(data[0].message,{icon: 1});
				}
				location.reload() 
			}
	      });
  }
  
  </script>
</body>
</html>