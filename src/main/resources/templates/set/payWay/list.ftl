<#include "../../commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

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
</style>
<title>支付方式管理</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png" style="margin-bottom: 5px;"/>
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">支付方式</a>
</div>
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    <div class="layui-col-xs6" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="setTop">添加</button>
			 <!--  <button class="layui-btn layui-btn-danger" data-type="getCheckData">删除</button> -->
		  </div>
    </div>
    <div class="layui-col-xs6" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/payWay/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr>
      <th lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'name',align:'center', width:400}">名称</th>
      <th lay-data="{field:'type',align:'center', width:200,templet:'#sysTypeTemp'}">类型</th>
      <th lay-data="{field:'orderNum',align:'center', width:120}">排序</th>
      <th lay-data="{field:'createTime',align:'center', width:160}">创建时间</th>
      <th lay-data="{fixed: 'right', width:200, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
  </thead>
</table>
 <script type="text/html" id="sysTypeTemp">
  {{# if(d.type===1){ }}
 	 <span style='color:#C9C9C9'>系统默认</span>
  {{# } }}
  {{# if(d.type===2){ }}
 	 <span  >管理创建</span>
  {{# } }}
</script>
<script type="text/html" id="barDemo">
   <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  {{# if(d.type===2){ }}
   <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
  {{# } }}
</script>

<script>
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
layui.use(['laypage', 'table','layer','form'], function(){
  var table = layui.table;
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
  //监听工具条
  table.on('tool(demo)', function(obj){
    var data = obj.data;
    if(obj.event === 'detail'){
      layer.msg('ID：'+ data.dbid + ' 的查看操作');
    } else if(obj.event === 'del'){
      layer.confirm('确定要删除【'+data.name+'】数据吗？', function(index){
    	deleteById('${ctx}/payWay/delete',data.dbid);
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
   	 layer.open({
            type: 2 //此处以iframe举例
            ,title: '支付方式'
            ,area: ['760px', '260px']
            ,shade: 0.8
            ,maxmin: true
            ,content: '${ctx}/payWay/edit?dbid='+data.dbid
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
              //layer.setTop(layero); //重点2
            }
          });
    }
  });
  
  var $ = layui.$, active = {
    getCheckData: function(){ //获取选中数据
      var checkStatus = table.checkStatus('idTest')
      ,data = checkStatus.data;
      if(data.length<=0){
    	  layer.msg('请选择数据后在操作');
    	  return ;
      }
      var array = new Array();
      $.each(data, function(i, da) {
  		array.push(da.dbid);
  	   });
      layer.confirm('确定要删除选择【'+data.length+'】条数据吗？', function(index){
    	  /* $.each(data, function(i, obj) {
    		  obj.del(); 
    	  }) */
	      deleteById('${ctx}/payWay/delete',array.toString());
      });
    }
    ,getCheckLength: function(){ //获取选中数目
      var checkStatus = table.checkStatus('idTest')
      ,data = checkStatus.data;
      layer.msg('选中了：'+ data.length + ' 个');
    }
    ,isAll: function(){ //验证是否全选
      var checkStatus = table.checkStatus('idTest');
      layer.msg(checkStatus.isAll ? '全选': '未全选')
    },
    setTop: function(){
        var that = this;
        
        //多窗口模式，层叠置顶
        layer.open({
          type: 2 //此处以iframe举例
          ,title: '支付方式'
          ,area: ['760px', '260px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/payWay/edit'
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
            //layer.setTop(layero); //重点2
          }
        });
      }
  };
  
  $('.demoTable .layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
  
 //触发事件
  $('#layerDemo .layui-btn').on('click', function(){
	    var othis = $(this), method = othis.data('method');
	    active[method] ? active[method].call(this, othis) : '';
	  });
 
  form.on('submit(Search)', function (data) {
  	table.reload('idTest', {
  		where: {
  			loanPlatformId: data.field.loanPlatformId,
  			name: data.field.name
  		}
  	});
      return false;
  });
  function deleteById(url,param){
	  $.post(url + "?dbids=" + param + "&datetime=" + new Date(),callBack);
		function callBack(data) {
			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				layer.alert(data[0].message, {icon: 5});
			}
			if (data[0].mark == 1) {// 删除数据失败时提示信息
				layer.msg(data[0].message, {icon: 5});
			}
			if (data[0].mark == 0) {// 删除数据成功提示信息
				layer.msg(data[0].message,{icon: 1});
				table.reload('idTest', {
			  		where: {
			  			name: $("#name").val()
			  		}
			  	});
			}
		}
  }
});


</script>
</html>