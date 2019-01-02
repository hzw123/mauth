<#include "../commons/macro.ftl">
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
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/startWriting/queryCousumptionList'">充值统计</a>
    </div>
    <div class="layui-row">
        <div class="layui-col-xs6">
            <div class="demoTable" id="layerDemo" style="margin-left: 12px;">
                &nbsp;
            </div>
        </div>
        <div class="layui-col-xs6">
            <form class="layui-form layui-input-inline" id="searchFrm" lay-filter="layform" method="post" style="float: right;">
                	时间范围：
                <div class="layui-inline" style="width: 200px;">
                    <input type="text" name="startTime" id="startTime" autocomplete="off" class="layui-input" />
                </div>
                <button id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search"><i class="layui-icon layui-btn-icon"></i>查询</button>
            </form>
        </div>
    </div>
	<div class="layui-table">
	    <table class="layui-hide" id="tData"></table>
	</div>
	
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
layui.use(['form', 'table', 'jquery',"laydate"], function () {
    var table = layui.table;
    var laydate=layui.laydate;
    var form = layui.form;
    table.render({
        elem: '#tData',
        method: 'post',
        url: '../statistic/queryArtificer',
        cols: [[
      	      { field: 'artificerName', title: '姓名' , width: 120},
      	      { field: 'money', title: '金额' , width: 120},
      	      { field: 'itemMoney', title: '项目金额' , width: 80},
      	      { field: 'productMoney', title: '产品金额' , width: 80},
      	      { field: 'commissionNum', title: '提成' , width: 80},
      	      { field: 'timeLong', title: '服务时长' , width: 80},
      	      { field: 'countItem', title: '项目数量' , width: 120},
      	      { field: 'countProduct', title: '产品数量', width: 80}
      	    ]],
        id: 'tData',
        page: false
    });

    laydate.render({
        elem: '#startTime'
        ,range: true
      });
    
    form.on('submit(Search)', function (data) {
      	table.reload('tData', {
      		where: {
      			startTime: data.field.start_time
      		}
      	});
          return false;
      });
});
	
</script>
</body>
</html>
