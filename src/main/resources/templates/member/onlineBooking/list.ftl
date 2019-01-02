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
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/onlineBooking/queryList'">预约记录</a>
    </div>
<div class="layui-row" >
    <div class="layui-col-xs4" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="add">添加</button>
			  <button class="layui-btn layui-btn-danger" data-type="getCheckData">删除</button>
		  </div>
    </div>
    <div class="layui-col-xs8" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		  预约时间：
		  <div class="layui-inline" style="width: 200px;">
		   <input type="text" name="startTime" id="startTime" autocomplete="off" class="layui-input" />
		  </div>
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  电话：
		  <div class="layui-inline">
		   <input type="text" name="mobilePhone" id="mobilePhone" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/onlineBooking/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr>
      <th  lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'memName',align:'center', width:120,templet:'#nameTemp'}">会员姓名</th>
      <th lay-data="{field:'artificerName',align:'center', width:120}">技师</th>
      <th lay-data="{field:'mobilePhone',align:'center', width:120}">联系电话</th>
      <th lay-data="{field:'bookingDate',align:'center', width:120}">预约日期</th>
      <th lay-data="{field:'bookingTime',align:'center', width:120}">预约时间</th>
      <th lay-data="{field:'dealStatus',align:'center', width:100,templet:'#dealStatus'}">处理状态</th>
      <th lay-data="{field:'startWritingStatus',align:'center', width:100,templet:'#startWritingStatus'}">开单状态</th>
      <th lay-data="{field:'createTime',align:'center', width:160}">创建时间</th>
      <th lay-data="{field:'creator',align:'center', width:120}">创建人</th>
      <th lay-data="{fixed: 'right', width:150, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
  </thead>
</table>
 
<script type="text/html" id="nameTemp">
	 <a ${ctx}/member/detail?dbid={{d.memId}}' class='layui-table-link'>{{d.memName}}</a>
</script>
<script type="text/html" id="dealStatus">
  {{# if(d.dealStatus===1){ }}
 	 <span >未处理</span>
  {{# } }}
  {{# if(d.dealStatus===2){ }}
 	 <span  >已处理</span>
  {{# } }}
</script>
<script type="text/html" id="startWritingStatus">
  {{# if(d.startWritingStatus===1){ }}
 	 <span >预约中</span>
  {{# } }}
  {{# if(d.startWritingStatus===2){ }}
 	 <span  >已开单</span>
  {{# } }}
  {{# if(d.startWritingStatus===3){ }}
 	 <span  >已拒绝</span>
  {{# } }}
  {{# if(d.startWritingStatus===4){ }}
 	 <span  >已取消</span>
  {{# } }}
</script>
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-mini" lay-event="handle">处理</a>
{{# if(d.startWritingStatus===1){ }}
 <a class="layui-btn layui-btn-mini" lay-event="startWriting">开单</a>
{{# } }}
 <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<script>
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
layui.use(['laypage', 'table','layer','form','laydate'], function(){
   var table = layui.table;
   var laydate=layui.laydate;
   var form = layui.form;
   var $ = layui.$;
  
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  //监听表格复选框选择
  table.on('tool(demo)', 
		  function(obj){
		    var data = obj.data;
		    if(obj.event === 'handle')
		    {
		    	var that = this;
		        layer.open({
		          type: 2 ,
		          title: '处理预约',
		          area: ['600px', '510px'],
		          shade: 0.8,
		          maxmin: true,
		          content: '/onlineBooking/handle?dbid='+data.dbid,
		          zIndex: layer.zIndex ,
		          success: function(layero){
		          }
		        });
		    }
		    else if(obj.event === 'startWriting'){
		    	window.location.href="${ctx}/startWriting/startWriting?onlineBookingId="+data.dbid;
		    }
		    else if(obj.event === 'del'){
		        layer.confirm('确定要删除【'+data.memName+'】预约记录数据吗？', function(index){
		      		deleteById('${ctx}/onlineBooking/delete',data.dbid);
		          	obj.del();
		          	layer.close(index);
		        });
		      }
    	});
  //日期范围
  laydate.render({
    elem: '#startTime'
    ,range: true
  });

  

  var active = {
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
	      deleteById('${ctx}/onlineBooking/delete',array.toString());
      });
    },
    isAll: function(){ //验证是否全选
      var checkStatus = table.checkStatus('idTest');
      layer.msg(checkStatus.isAll ? '全选': '未全选')
    },
    add: function(){
        var that = this;
        layer.open({
          type: 2 //此处以iframe举例
          ,title: '添加预约'
          ,area: ['600px', '510px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/onlineBooking/edit'
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
          }
        });
      }
    ,
    
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
  			name: data.field.name,
  			startTime: data.field.start_time
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
			  			name: $("#name").val(),
			  			memberTypeId: $("#memberTypeId").val()
			  		}
			  	});
			}
		}
  }
});
  
  

</script>
</body>
</html>
