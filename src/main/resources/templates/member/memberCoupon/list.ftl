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
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
</style>
<title>代金券管理</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png" style="margin-bottom: 5px;"/>
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">代金券管理</a>
</div>
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    <div class="layui-col-xs3" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="exportExcel">导出excel</button>
		  </div>
    </div>
    <div class="layui-col-xs9" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		劵名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="memberName" id="memberName" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
			电话：
		  <div class="layui-inline">
		   <input type="text" name="mobilePhone" id="mobilePhone" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/couponMember/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr >
      <th lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'name',align:'center',height:60, width:120}">名称</th>
      <th lay-data="{field:'code',align:'center',height:60, width:140}">编号</th>
      <th lay-data="{field:'name',align:'center', width:70,templet:'<div>{{d.member.name}}</div>'}">姓名</th>
      <th lay-data="{field:'name',align:'center', width:120,templet:'<div>{{d.member.mobilePhone}}</div>'}">电话</th>
      <th lay-data="{field:'money',align:'center', width:60,style:'color:red;'}">金额</th>
      <th lay-data="{field:'count',align:'center', width:80}">总次数</th>
      <th lay-data="{field:'remainder',align:'center', width:80}">剩余次数</th>
      <th lay-data="{field:'orderNum',align:'center', width:120,templet:'<div>{{d.stopTime}}</div>'}">有效期</th>
      <th lay-data="{field:'createTime',align:'center', width:120}">发放时间</th>
      <th lay-data="{field:'stateName',align:'center', width:80}">状态</th>
      <th lay-data="{fixed: 'right', width:180, align:'center', toolbar: '#barDemo'}">操作</th>
    </tr>
  </thead>
</table>

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="view">查看</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="cancel">撤销</a>
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
    } else if(obj.event === 'cancel'){
      layer.confirm('确定要撤销【'+data.name+'】数据吗？', function(index){
    	deleteById('${ctx}/couponMember/cancel',data.dbid);
        obj.del();
        layer.close(index);
      });
    }
    else if(obj.event === 'view'){
    	window.location.href='${ctx}/couponMember/printCode?dbid='+data.dbid+'&type=2';
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
	      deleteById('${ctx}/couponMember/delete',array.toString());
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
          ,title: '发优惠券'
          ,area: ['860px', '600px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/couponMember/edit'
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
            //layer.setTop(layero); //重点2
          }
        });
    },
    exportExcel:function(){
    	window.location.href='${ctx}/couponMember/export';
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
  			name: $("#name").val(),
  			isUse: $("#isUse").val(),
  			memberName: $("#memberName").val(),
  			mobilePhone: $("#mobilePhone").val()
  		}
  	});
      return false;
  });
  function deleteById(url,param){
	  $.post(url + "?dbids=" + param ,callBack);
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
			  			name: $("#name").val(),
			  			memberName: $("#memberName").val(),
			  			mobilePhone: $("#mobilePhone").val()
			  		}
			  	});
			}
		}
  }
});


</script>
</html>