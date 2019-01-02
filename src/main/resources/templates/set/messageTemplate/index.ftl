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
<title>消息模板管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">消息模板管理</a>
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

<script type="text/html" id="tmpTemplateType">
{{# if(d.templateType===1){ }}
 	 <span >成为会员通知</span>
{{# } }}
{{# if(d.templateType===2){ }}
 	 <span  >充值成功通知</span>
{{# } }}
{{# if(d.templateType===3){ }}
 	 <span  >会员计次充值通知</span>
  {{# } }}
{{# if(d.templateType===4){ }}
 	 <span  >消费成功通知</span>
  {{# } }}
{{# if(d.templateType===6){ }}
 	 <span  >预约成功通知</span>
  {{# } }}
{{# if(d.templateType===7){ }}
 	 <span  >计次项目消费提醒</span>
  {{# } }}
{{# if(d.templateType===8){ }}
 	 <span  >积分提醒</span>
  {{# } }}
{{# if(d.templateType===9){ }}
 	 <span  >订单撤销通知</span>
  {{# } }}
{{# if(d.templateType===10){ }}
 	 <span  >会员卡余额修正提醒</span>
  {{# } }}
</script>

<script type="text/html" id="barOpe">
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
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
        url: '../messageTemplate/query',
        cols: [[
        	  { field: 'templateType', title: '模板类型' , width: 280,templet:'#tmpTemplateType'},
			  { field: 'templateId', title: '模板Id' , width: 220},
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
          layer.confirm('确定要删除模板吗？', function(index){
        	  location.href='${ctx}/messageTemplate/delete?dbid='+data.dbid;
        	});
        }
      });
});

$(document).on('click', '#btnAdd', function () {
    var url = "edit";
    $.get(url, function (str) {
        pop_up = layui.layer.open({
            type: 1,
            title: ['添加消息模板'],
            area: ['500px', '430px'],
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
</script>
</body>
</html>