<#include "commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>系统管理员客户</title>
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
</head>
<body>
<br>
 <blockquote class="layui-elem-quote">
  	欢迎：<span style="color: #FFB800">${user.realName }</span>,登陆系统
  </blockquote>
</body>

<script type="text/javascript">
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
      layer.confirm('确定要撤销【'+data.name+'】客户归档吗？', function(index){
    	  turnFlowerToCustomer('${ctx}/customerCreditExam/saveFileToCustomer',data.dbid);
        obj.del();
        layer.close(index);
      });
    }else if(obj.event === 'edit'){
       		window.location.href='/customerCreditExam/customerDetail?customerId='+data.dbid
    }
  });
  
  var $ = layui.$, active = {
	turnBack: function(){ //获取选中数据
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
      layer.confirm('确定要撤销【'+data.length+'】条客户归档吗？', function(index){
    	  turnFlowerToCustomer('${ctx}/customerCreditExam/saveFileToCustomer',array.toString());
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
	  			id: data.field.id,
	  			customerProcessStatus: data.field.customerProcessStatus,
	  			loanSubmitType: data.field.loanSubmitType,
	  			name: data.field.name,
	  			loanPlatformId: data.field.loanPlatformId,
	  			custStatus: data.field.custStatus,
	  			finProductId: data.field.finProductId,
	  			mobilePhone: data.field.mobilePhone,
	  			cardNo: data.field.cardNo,
	  			distributorName: data.field.distributorName,
	  			salerDepName: data.field.salerDepName,
	  			salerName: data.field.salerName,
	  			startCreateDate: data.field.startCreateDate,
	  			endCreateDate: data.field.endCreateDate,
	  			startLoanDate: data.field.startLoanDate,
	  			endLoanDate: data.field.endLoanDate,
	  			startPaymentDatetime: data.field.startPaymentDatetime,
	  			endPaymentDatetime: data.field.endPaymentDatetime,
	  			startFileDate: data.field.startFileDate,
	  			endFileDate: data.field.endFileDate
	  		}
	  	});
	      return false;
	  });
	  form.on('select(loanPlatformId)', function(data){
		  $.post('${ctx}/finProduct/ajaxByLoanPlatformIdId?loanPlatformId='+data.value+'&datePar='+new Date(),{},function(dataselect){
				$("#finProductId").empty();
				$("#finProductId").append(dataselect);
				form.render('select')
		  })
	  })
  function turnFlowerToCustomer(url,param){
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
