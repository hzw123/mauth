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
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/startWriting/queryCousumptionList'">项目统计</a>
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
        url: '../statistic/queryDailyConsume',
        cols: [[
      	      { field: 'odate', title: '日期' , width: 160},
      	      { field: 'count', title: '单数' , width: 80},
      	      { field: 'personNum', title: '人数' , width: 80},
      	      { field: 'money', title: '账单金额' , width: 80},
      	      { field: 'payment', title: '实付', width: 80},
      	      { field: 'vipDiscount', title: '会员卡折扣' , width: 80},
      	      { field: 'vipFree', title: '会员卡赠送' , width: 80},
      	      { field: 'countCard', title: '次卡' , width: 80},
    	      { field: 'freeTicket', title: '项目劵' , width: 80},
    	      { field: 'moneyTicket', title: '代金券', width: 80},
    	      { field: 'give', title: '赠单' , width: 80},
    	      { field: 'free', title: '免单' , width: 80},
    	      { field: 'vipprice', title: '会员价', width: 80},
    	      { field: 'fixedDiscount', title: '固定折扣' , width: 80},
    	      { field: 'otherDiscount', title: '其它优惠' , width: 80},
    	      { field: 'tipMoney', title: '小费' , width: 80},
    	      { field: 'discountPrice', title: '整单优惠' , width: 80}
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
