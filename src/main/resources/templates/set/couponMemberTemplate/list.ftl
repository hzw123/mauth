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
.layui-table-cell{
	height: 60px;
	line-height: 60px;	
}
th .layui-table-cell{
	height: 32px;
	line-height: 32px;	
}
</style>
<title>优惠券</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png" style="margin-bottom: 5px;"/>
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">优惠券</a>
</div>
<hr class="layui-bg-red">
<br>
<div class="layui-row" >
    <div class="layui-col-xs6" >
	      <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
			  <button class="layui-btn" data-method="setTop">添加</button>
			  <button class="layui-btn" data-method="selectItem">设置可用产品/项目</button>
			  <button class="layui-btn layui-btn-danger" data-type="getCheckData">删除</button>
		  </div>
    </div>
    <div class="layui-col-xs6" >
      <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;" >
		  类型：
		  <div class="layui-inline" style="width: 100px;">
			<select name="type" id='type' lay-search="">
	    	 	<option value="">请选择...</option>
	    	 	<option value="1" ${couponMemberTemplate.type==1?'selected="selected"':'' } >代金券</option>
	    	 	<option value="2" ${couponMemberTemplate.type==2?'selected="selected"':'' }>免费券</option>
	    	 </select>		   
		  </div>
		  名称：
		  <div class="layui-inline">
		   <input type="text" name="name" id="name" autocomplete="off" class="layui-input" style="width: 100px;"/>
		  </div>
		  <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
  		</form>
    </div>
  </div>
 
<table class="layui-table" lay-data="{url:'${ctx}/couponMemberTemplate/queryJson', page:true, id:'idTest',limit:10,method:'post'}" lay-filter="demo">
  <thead>
    <tr >
      <th  lay-data="{checkbox:true, fixed: true}"></th>
      <th lay-data="{field:'barImage',align:'center', width:120,templet:'#barImage'}">图片</th>
      <th lay-data="{field:'name',align:'center', width:300}">名称</th>
      <th lay-data="{field:'type',align:'center', width:100,templet:'#typeTr'}">类型</th>
      <th lay-data="{field:'price',align:'center', width:100}">默认金额</th>
      <th lay-data="{field:'orderNum',align:'center', width:100}">序号</th>
      <th lay-data="{field:'createTime',align:'center', width:160}">创建时间</th>
      <th lay-data="{fixed: 'right', width:180, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
  </thead>
</table>
 
<script type="text/html" id="barImage">
 	 <img src="{{d.image}}" width="100" height="60"></img>
</script>
<script type="text/html" id="typeTr">
  {{# if(d.type===1){ }}
 	 <span>代金券</span>
  {{# } }}
  {{# if(d.type===2){ }}
 	 <span>免费券</span>
  {{# } }}
</script>
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  {{# if(d.state===0){ }}
 	 <a class="layui-btn layui-btn-mini" lay-event="stopOrStart">停用</a>
  {{# } }}
  {{# if(d.state===9997){ }}
 	 <a class="layui-btn layui-btn-mini" lay-event="stopOrStart">启用</a>
  {{# } }}
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
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
    	deleteById('${ctx}/couponMemberTemplate/delete',data.dbid);
        obj.del();
        layer.close(index);
      });
    }
    else if(obj.event === 'stopOrStart'){
    	var state=data.state;
    	var mess="";
    	if(state==0){
    		mess='确定要停用【'+data.name+'】优惠券吗';
    	}
    	if(state==9997){
    		mess='确定要启用【'+data.name+'】优惠券吗';
    	}
    	
        layer.confirm(mess, function(index){
        	stopOrStart('${ctx}/couponMemberTemplate/stopOrStart',data.dbid);
            layer.close(index);
          });
     }
    else if(obj.event === 'edit'){
   	 layer.open({
            type: 2 //此处以iframe举例
            ,title: '优惠券'
            ,area: ['560px', '600px']
            ,shade: 0.8
            ,maxmin: true
            ,content: '${ctx}/couponMemberTemplate/edit?dbid='+data.dbid
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
	      deleteById('${ctx}/couponMemberTemplate/delete',array.toString());
      });
    }
    ,selectItem: function(){ //获取选中数目
      var checkStatus = table.checkStatus('idTest')
      ,data = checkStatus.data;
      if(data.length<=0){
	      layer.msg('未选择操作数据');
	      return ;
      }
      if(data.length>1){
    	  layer.msg('只能选择一条数据操作');
	      return ;
      }
      var that = this;
      //多窗口模式，层叠置顶
      layer.open({
        type: 2 //此处以iframe举例
        ,title: '设置['+data[0].name+']可用项目'
        ,area: ['860px', '600px']
        ,shade: 0.8
        ,maxmin: true
        ,content: '${ctx}/couponMemberTemplate/selectItem?template_id='+data[0].dbid
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
        }
      });
    }
    ,setTop: function(){
        var that = this;
        //多窗口模式，层叠置顶
        layer.open({
          type: 2 //此处以iframe举例
          ,title: '优惠券'
          ,area: ['400px', '510px']
          ,content: '${ctx}/couponMemberTemplate/edit'
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
  			type: data.field.type
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
			  			type: $("#type").val()
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
			  			type: $("#type").val()
			  		}
			  	});
			}
		}
  }
});


</script>
</html>