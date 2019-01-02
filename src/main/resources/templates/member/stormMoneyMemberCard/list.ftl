<#include "../../commons/macro.ftl">
<@commonHead/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="${ctx}/css/global.css" rel="stylesheet" />
    <link href="${ctx}/css/main.css" rel="stylesheet" />
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
	<a href="javascript:void(-1);" onclick="">会员储值记录</a>
</div>
<div class="layui-row" >
    <div class="layui-col-xs3" >
	  <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
		<button class="layui-btn" data-method="exportExcel"  onclick="exportExcel('frmId')">导出excel</button>
			  &#12288;
	  </div>
    </div>
    <div class="layui-col-xs9" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
      	   储值日期：
		  <div class="layui-inline" style="width: 200px;">
		   <input type="text" name="startTime" id="startTime" autocomplete="off" class="layui-input" />
		  </div>
		   订单编号：
		  <div class="layui-inline">
		   <input type="text" name="orderNo" id="orderNo" autocomplete="off" class="layui-input" style="width: 140px;"/>
		  </div>
			会员名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/stormMoneyMemberCard/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr >
      <th lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'orderNo',align:'center',height:60, width:140}">订单号</th>
      <th lay-data="{field:'memberName',align:'center', width:100}">会员</th>
      <th lay-data="{field:'typeName',align:'center', width:140}">类型</th>
      <th lay-data="{field:'createDate',align:'center', width:160}">储值时间</th>
      <th lay-data="{field:'cashierName',align:'center', width:100}">操作人</th>
      <th lay-data="{field:'money',align:'center', width:90,style:'color:red;'}">储值金额</th>
      <th lay-data="{field:'giveMoney',align:'center', width:90,style:'color:red;'}">赠送金额</th>
      <th lay-data="{field:'stateName',align:'center', width:100}">状态</th>
      <th lay-data="{field:'note',align:'center', width:200}">备注</th>
      <th lay-data="{fixed: 'right', width:120, align:'center', toolbar: '#barDemo'}"></th>
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
layui.use(['laypage', 'table','layer','form','laydate'], function(){
  var table = layui.table,laydate=layui.laydate;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  //日期范围
  laydate.render({
    elem: '#startTime'
    ,range: true
  });
  //监听工具条
  table.on('tool(demo)', function(obj){
    var data = obj.data;
    if(obj.event === 'view'){
    	layer.open({
            type: 2 //此处以iframe举例
            ,title: '会员卡储值明细'
            ,area: ['760px', '540px']
            ,shade: 0.8
            ,maxmin: true
            ,content: '${ctx}/stormMoneyMemberCard/view?stormMoneymember_card_id='+data.dbid+'&memberId='+data.memberId
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
            }
          });
    } 
    else if(obj.event==='cancel'){
    	layer.confirm('您确定撤销会员卡储值记录吗？',{icon: 5}, function(index){
    		$.post('/stormMoneyMemberCard/cancel?id='+data.dbid,{},function(data){
				  if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						layer.alert(data[0].message, {icon: 5});
					}
					if (data[0].mark == 1) {// 删除数据失败时提示信息
						layer.msg(data[0].message, {icon: 5});
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						layer.msg(data[0].message,{icon: 1});
					    setTimeout(
							function() {
								window.location.reload() ;
							}, 1000);
					}
			  });
    	});
    }
  });
  
  var $ = layui.$, active = {
    exportExcel:function(){
    	window.location.href='${ctx}/stormMoneyMemberCard/exportExcel';
    } };

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
  			mobilePhone: $("#mobilePhone").val(),
  			orderNo: $("#orderNo").val(),
  			startTime: $("#startTime").val()
  		}
  	});
      return false;
  });
});


</script>
</html>