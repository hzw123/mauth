<#include "../../commons/macro.ftl">
<@commonHead/>

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
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
<title>打印机管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">打印机管理</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<div class="listOperate">
	<div class="operate">
		<a class="layui-btn" id="btnAdd" href="javascript:void();">添加</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/role/queryList" metdod="get">
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<div class="layui-table">
	<table class="layui-hide" id="tData" lay-filter="tData"></table>
</div>

<script type="text/html" id="tmpState">
  {{# if(d.state===0){ }}
 	 <span >启用</span>
  {{# } }}
  {{# if(d.state===10001){ }}
 	 <span  >禁用</span>
  {{# } }}
</script>

<script type="text/html" id="barOpe">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>

 {{# if(d.state===0){ }}
 	 <a class="layui-btn layui-btn-mini" lay-event="changeState">禁用</a>
  {{# } }}
  {{# if(d.state===10001){ }}
 	 <a class="layui-btn layui-btn-mini" lay-event="changeState">启用</a>
  {{# } }}
</script>



<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script type="text/javascript">
layui.use(['form', 'table', 'jquery',"laydate"], function () {
    var table = layui.table;
    var laydate=layui.laydate;
    var form = layui.form;
    
    ///填充table中的数据
    table.render({
        elem: '#tData',
        method: 'post',
        url: '../printer/query',
        cols: [[
			  { field: 'name', title: '名称' , width: 220},
      	      { field: 'url', title: 'url' , width: 220},
      	      { field: 'user', title: '用户' , width: 180},
      	      { field: 'ukey', title: '密匙' , width: 120},
      	      { field: 'sn', title: 'sn' , width: 120},
      	      { field: 'state', title: '状态' , width: 80,templet:'#tmpState'},
      	      { field: 'timeLong', title: '操作' , width: 120,toolbar: '#barOpe'}
      	    ]],
        id: 'tData',
        page: false
    });
    
    ///注册table中的事件
    table.on('tool(tData)', function(obj){
        var data = obj.data;
		if(obj.event === 'del')
		{
          layer.confirm('确定要删除打印机:【'+data.name+'】吗？', function(index){
        	  location.href='${ctx}/printer/delete?dbid='+data.dbid;
        	});
        }
        else if(obj.event === 'changeState'){
        	var state=data.state;
        	var mess="";
        	if(state==0){
        		mess='确定要禁用打印机:【'+data.name+'】吗';
        		state=10001;
        	}
        	if(state==10001){
        		mess='确定要启用打印机:【'+data.name+'】吗';
        		state=0;
        	}
        	console.info(mess);
        	
            layer.confirm(mess, function(index){
            	location.href='${ctx}/printer/changeState?dbid='+data.dbid+'&state='+state;
              });
         }
      });
});

$(document).on('click', '#btnAdd', function () {
    var url = "edit";
    $.get(url, function (str) {
        pop_up = layui.layer.open({
            type: 1,
            title: ['添加打印机'],
            area: ['600px', '430px'],
            content: str,
        });
    });
});

$(document).on('click', 'a[data-fun="edit"]', function () {
	var url = "edit?dbid="+this.name;
    $.get(url, function (str) {
        pop_up = layui.layer.open({
            type: 1,
            title: ['编辑打印机信息'],
            area: ['600px', '400px'],
            content: str,
        });
    });
});

$(document).on('click', 'a[data-fun="delete"]', function () {
	$.utile.deleteById('${ctx}/room/delete?dbid='+this.name)
});
</script>
</body>
</html>