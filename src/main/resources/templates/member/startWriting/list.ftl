<#include "../../commons/macro.ftl">
<@commonHead/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="${ctx}/css/global.css" rel="stylesheet" />
    <link href="${ctx}/css/main.css" rel="stylesheet" />
    <style type="text/css">
    	
	.layui-table-cell {
	    padding: 0px 5px;
	}
    </style>
</head>
<body>
    <div class="location">
        <img src="../../images/homeIcon.png" /> &nbsp;
        <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/startWriting/queryList'">开单记录</a>
    </div>
    <br>
<div class="layui-row" >
    <div class="layui-col-xs6" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="setTop">补录订单</button>
			  
			  <button class="layui-btn" data-method="setTop1"  onclick="exportExcel('frmId')">导出Excel</button>
		  </div>
    </div>
    <div class="layui-col-xs6" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		  开单日期：
		  <div class="layui-inline" style="width: 200px;">
		   <input type="text" name="startTime" id="startTime" autocomplete="off" class="layui-input" />
		  </div>
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="memName" id="memName" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/startWriting/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr>
      <th lay-data="{field:'orderNo',align:'center', width:120}" >订单号</th>
      <th lay-data="{field:'memName',align:'center', width:100,templet:'#nameTemp'}" >会员</th>
      <th lay-data="{field:'roomName',align:'center', width:90}" >包间</th>
      <th lay-data="{field:'createTime',align:'center', width:160}" >开单时间</th>
      <th lay-data="{field:'money',align:'center', width:100}" >订单金额</th>
      <th lay-data="{field:'discountMoney',align:'center', width:90}" >折扣金额</th>
      <th lay-data="{field:'actualMoney',align:'center', width:90}" >实收金额</th>
      <th lay-data="{field:'stateName',align:'center', width:90}" >订单状态</th>
      <th lay-data="{field:'personNum',align:'center', width:100}" >人数</th>
      <th lay-data="{field:'creator',align:'center', width:100}" >操作人</th>
      <th lay-data="{fixed: 'right', width:180, align:'left', toolbar: '#barDemo'}" ></th>
    </tr>
  </thead>
</table>
 
<script type="text/html" id="nameTemp">
	 <a href='${ctx}/member/detail?dbid={{d.memId}}' class='layui-table-link'>{{d.memName}}</a>
</script>

<script type="text/html" id="barDemo">
   <a class="layui-btn layui-btn-mini" lay-event="view">查看</a>
   {{# if(d.state===2){ }}
 	 <a class="layui-btn layui-btn-mini" lay-event="cash">收银</a>
  	 <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="view">取消订单</a>
  {{# } }}
  {{# if(d.state===0){ }}
 	<a class="layui-btn layui-btn-mini" lay-event="print">补打小票</a>
  {{# } }}
</script>

<script>
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var tcurr=1;
layui.use(['laypage', 'table','layer','form','laydate'], function(){
  var table = layui.table,laydate=layui.laydate,page=layui.laypage;
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
  //日期范围
  laydate.render({
    elem: '#startTime'
    ,range: true
  });
  //监听工具条
  table.on('tool(demo)', function(obj){
    var data = obj.data;
    if(obj.event === 'detail'){
      layer.msg('ID：'+ data.dbid + ' 的查看操作');
    } else if(obj.event === 'del'){
      layer.confirm('删除操作数据不可恢复，您确定要删除【'+data.orderNo+'】数据吗？', function(index){
    	deleteById('${ctx}/startWriting/delete',data.dbid);
        obj.del();
        layer.close(index);
      });
    }
    else if(obj.event === 'cancel'){
    	var curr1=$($(".layui-laypage-skip").find("input")[0]).val();
        layer.confirm('您确定取消【'+data.orderNo+'】订单吗', function(index){
        	 $.post("${ctx}/startWriting/cancel?dbid=" + data.dbid + "&datetime=" + new Date(),
	     		function callBack(data) {
	     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
	     				layer.alert(data[0].message, {icon: 5});
	     			}
	     			if (data[0].mark == 1) {// 删除数据失败时提示信息
	     				layer.msg(data[0].message, {icon: 5});
	     			}
	     			if (data[0].mark == 0) {// 删除数据成功提示信息
	     				layer.msg(data[0].message,{icon: 1});
	     				table.reload('idTest',{
	    			  		where: {
	    			  			memName: $("#memName").val(),
	    			  			startWritingTypeId: $("#startWritingTypeId").val()
	    			  		}
	    			  		,page:{
	    			  			curr:curr1
	    			  			,limit:20
	    			  		}
	    			  		,done:function(res,curr,count){
	    		                console.log(count);
	    		            }
	    			  	});
	     			}
	     		})
            layer.close(index);
          });
     }
    else if(obj.event === 'print'){
       	 $.post("${ctx}/startWriting/print?startWritingId=" + data.dbid + "&datetime=" + new Date(),
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
     		})
     }
    else if(obj.event === 'view'){
   		window.location.href= '${ctx}/startWriting/view?dbid='+data.dbid
    }
    else if(obj.event === 'cash'){
			var that = this;
	        //多窗口模式，层叠置顶
	        layer.open({
	          type: 2 //此处以iframe举例
	          ,title: data.roomName+'收银'
	          ,area: ['1280px', '640px']
	          ,shade: 0.8
	          ,maxmin: true
	          ,content: '${ctx}/startWriting/cash?startWritingId='+data.dbid
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
	      deleteById('${ctx}/startWriting/delete',array.toString());
      });
    }
    ,sendCoupstartWriting: function(){ //获取选中数目
    	var checkStatus = table.checkStatus('idTest')
        ,data = checkStatus.data;
        if(data.length<=0){
      	  layer.msg('请选择数据后在操作');
      	  return ;
        }
        var array = new Array();
        var arrayName = new Array();
        $.each(data, function(i, da) {
    		array.push(da.dbid);
    		arrayName.push(da.name);
    	   });
        layer.confirm('确定为【'+data.length+'】位会员发放代金券吗？', function(index){
        	names=encodeURIComponent(encodeURIComponent(arrayName.toString()));
        	window.location.href='${ctx}/couponstartWriting/sendMore?startWritingIds='+array.toString()+'&startWritingNames='+names;
        	 layer.close(index);
        });
    }
    ,isAll: function(){ //验证是否全选
      var checkStatus = table.checkStatus('idTest');
      layer.msg(checkStatus.isAll ? '全选': '未全选')
    },
    setTop: function(){
        window.location.href='${ctx}/startWriting/makeupRoom'
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
  			memName: data.field.memName,
  			startTime: data.field.start_time
  		}
  	});
      return false;
  });
  function deleteById(url,param){
	  $.post(url + "?dbid=" + param + "&datetime=" + new Date(),callBack);
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
			  			memName: $("#memName").val(),
			  			startWritingTypeId: $("#startWritingTypeId").val()
			  		}
			  	});
			}
		}
  }
  function cancel(url,param){
	  $.post(url + "?dbid=" + param + "&datetime=" + new Date(),callBack);
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
			  			memName: $("#memName").val(),
			  			startWritingTypeId: $("#startWritingTypeId").val()
			  		}
			  	});
			}
		}
  }
});

function exportExcel(searchFrm){
 	window.location.href='${ctx}/startWriting/exportExcel';
 }
</script>
</body>
</html>
