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
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/member/queryList'">会员管理</a>
    </div>
    <br>
<div class="layui-row" >
    <div class="layui-col-xs5" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="setTop">添加</button>
			  <button class="layui-btn" data-method="sendCoupMember">发放代金券</button>
			 <!--  <button class="layui-btn" data-method="import" >导入</button> -->
			  <button class="layui-btn" data-method="importExcel" >批量导入</button>
			  <button class="layui-btn" data-method="export" onclick="exportExcel('frmId')">导出</button>
			  <button class="layui-btn layui-btn-danger" data-type="getCheckData">删除</button>
		  </div>
    </div>
    <div class="layui-col-xs7" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		  生日：
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
 
<table class="layui-table" lay-data="{url:'${ctx}/member/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr>
      <th  lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'memberCardName',align:'center', width:100,templet:'<div>{{d.memberCard.name}}</div>'}">会员类型</th>
      <th lay-data="{field:'memberCardNo',align:'center', width:120}">卡号</th>
      <th lay-data="{field:'name',align:'center', width:120,templet:'#nameTemp'}">姓名</th>
      <th lay-data="{field:'sex',align:'center', width:60}">性别</th>
      <th lay-data="{field:'cancelMemStatus',align:'center', width:60,templet:'#state'}">状态</th>
      <th lay-data="{field:'balance',align:'center', width:100}">余额</th>
      <th lay-data="{field:'remainderPoint',align:'center', width:80}">积分</th>
      <th lay-data="{field:'birthday',align:'center', width:110}">生日</th>
      <th lay-data="{field:'mobilePhone',align:'center', width:120}">手机号码</th>
      <th lay-data="{field:'consumeMoney',align:'center', width:90}">累计消费</th>
      <th lay-data="{field:'totalBuy',align:'center', width:90}">累计次数</th>
      <th lay-data="{field:'totalCardMoney',align:'center', width:90}">累计充值</th>
      <th lay-data="{field:'lastBuyDate',align:'center', width:160}">最近消费时间</th>
      <th lay-data="{field:'createTime',align:'center', width:160}">创建时间</th>
      <th lay-data="{field:'creator',align:'center', width:80}">创建人</th>
      <th lay-data="{fixed: 'right', width:150, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
  </thead>
</table>
 
<script type="text/html" id="nameTemp">
	 <a href='${ctx}/member/detail?dbid={{d.dbid}}' class='layui-table-link'>{{d.name}}</a>
</script>
<script type="text/html" id="bussiTypeTemp">
  {{# if(d.bussiType===1){ }}
 	 <span >储值卡</span>
  {{# } }}
  {{# if(d.bussiType===2){ }}
 	 <span  >创始会员卡</span>
  {{# } }}
</script>
<script type="text/html" id="state">
  {{# if(d.state===0){ }}
 	 <span >正常</span>
  {{# } }}
  {{# if(d.state===9998){ }}
 	 <span  >冻结</span>
  {{# } }}
  {{# if(d.state===9999){ }}
 	 <span  >注销</span>
  {{# } }}
</script>
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="crash">充值</a>
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
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
      layer.confirm('确定要删除【'+data.name+'】数据吗？', function(index){
    	deleteById('${ctx}/member/delete',data.dbid);
        layer.close(index);
      });
    }
    else if(obj.event === 'stopOrStart'){
    	var stopStatus=data.stopStatus;
    	var mess="";
    	if(stopStatus==1){
    		mess='确定要下架【'+data.name+'】项目吗';
    	}
    	if(stopStatus==2){
    		mess='确定要上架【'+data.name+'】项目吗';
    	}
    	
        layer.confirm(mess, function(index){
        	stopOrStart('${ctx}/member/stopOrStart',data.dbid);
            layer.close(index);
          });
     }
    else if(obj.event === 'edit'){
   	 layer.open({
            type: 2 //此处以iframe举例
            ,title: '编辑会员卡'
            ,area: ['760px', '560px']
            ,shade: 0.8
            ,maxmin: true
            ,content: '${ctx}/member/edit?dbid='+data.dbid
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
              //layer.setTop(layero); //重点2
            }
          });
    }
    else if(obj.event === 'crash'){
   	 layer.open({
            type: 2 //此处以iframe举例
            ,title: '会员充值'
            ,area: ['760px', '560px']
            ,shade: 0.8
            ,maxmin: true
            ,content: '${ctx}/stormMoneyMemberCard/edit?memberId='+data.dbid+'&type=2'
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
	      deleteById('${ctx}/member/delete',array.toString());
      });
    }
    ,sendCoupMember: function(){ //获取选中数目
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
        	window.location.href='${ctx}/couponMember/sendMore?memberIds='+array.toString()+'&memberNames='+names;
        	 layer.close(index);
        });
    }
    ,isAll: function(){ //验证是否全选
      var checkStatus = table.checkStatus('idTest');
      layer.msg(checkStatus.isAll ? '全选': '未全选')
    },
    importExcel:function(){
    	 var that = this;
         
         //多窗口模式，层叠置顶
         layer.open({
           type: 2 //此处以iframe举例
           ,title: '批量导入'
           ,area: ['760px', '560px']
           ,shade: 0.8
           ,maxmin: true
           ,content: '${ctx}/member/importExcel'
           ,zIndex: layer.zIndex //重点1
           ,success: function(layero){
             //layer.setTop(layero); //重点2
           }
         });
    }
    ,
    setTop: function(){
        var that = this;
        
        //多窗口模式，层叠置顶
        layer.open({
          type: 2 //此处以iframe举例
          ,title: '创建会员卡'
          ,area: ['760px', '560px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/member/edit'
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
  			name: data.field.name,
  			mobilePhone: data.field.mobilePhone,
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
			  			mobilePhone: $("#mobilePhone").val(),
			  			memberTypeId: $("#memberTypeId").val()
			  		}
			  	});
			}
		}
  }
  function stopOrStart(url,param){
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
			  			name: $("#name").val(),
			  			mobilePhone: $("#mobilePhone").val(),
			  			memberTypeId: $("#memberTypeId").val()
			  		}
			  	});
			}
		}
  }
});

function exportExcel(searchFrm){
 	window.location.href='${ctx}/member/exportExcel';
 }


</script>
</body>
</html>
