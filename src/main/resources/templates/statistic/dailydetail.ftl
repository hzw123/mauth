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
        url: '../statistic/queryDailyDetail',
        cols: [[
      	      { field: 'date', title: '日期' , width: 160},
      	      { field: 'count', title: '上客量' , width: 80},
      	      { field: 'actualMoney', title: '营业额' , width: 80},
      	      { field: 'paywayCash', title: '现金', width: 80},
      	      { field: 'paywayBank', title: '银行卡' , width: 80},
      	      { field: 'paywayAlipay', title: '支付宝' , width: 80},
      	      { field: 'paywayWechat', title: '微信' , width: 80},
    	      { field: 'paywayCard', title: '会员卡' , width: 80},
    	      { field: 'paywayGroup', title: '团购', width: 80},
    	      { field: 'paywayKoubei', title: '口碑' , width: 80},

      	      { field: 'customerPaywayCash', title: '(顾客)现金', width: 80},
      	      { field: 'customerPaywayBank', title: '(顾客)银行卡' , width: 80},
      	      { field: 'customerPaywayAlipay', title: '(顾客)支付宝' , width: 80},
      	      { field: 'customerPaywayWechat', title: '(顾客)微信' , width: 80},
    	      { field: 'customerPaywayCard', title: '(顾客)会员卡' , width: 80},
    	      { field: 'customerPaywayStartCard', title: '(顾客)团购', width: 80},
    	      { field: 'customerPaywayGroup', title: '(顾客)口碑' , width: 80},
    	      
    	      { field: 'qingyi', title: '轻逸', width: 80},
      	      { field: 'pujin', title: '璞境' , width: 80},
      	      { field: 'shuchang', title: '舒畅' , width: 80},
      	      { field: 'guben', title: '固本' , width: 80},
    	      { field: 'wentong', title: '温通' , width: 80},
    	      { field: 'spa', title: 'spa', width: 80},
    	      { field: 'jiuyang', title: '九阳' , width: 80},
    	      { field: 'kaibei', title: '开背', width: 80},
      	      { field: 'xiujiao', title: '修脚' , width: 80},
      	      { field: 'caier', title: '采耳', width: 80},
    	      { field: 'yanhu', title: '眼护' , width: 80},
      	      { field: 'guasha', title: '刮痧' , width: 80},
    	      { field: 'huoguan', title: '火罐' , width: 80},
    	      
    	      { field: 'firstStorm1', title: '办卡1千', width: 80},
      	      { field: 'firstStorm2', title: '办卡2千' , width: 80},
      	      { field: 'firstStorm3', title: '办卡3千' , width: 80},
      	      { field: 'firstStorm4', title: '办卡4千' , width: 80},
    	      { field: 'firstStorm5', title: '办卡5千' , width: 80},
    	      { field: 'firstStorm6', title: '办卡6千', width: 80},
    	      { field: 'firstStorm7', title: '办卡7千' , width: 80},
    	      { field: 'firstStorm8', title: '办卡8千', width: 80},
      	      { field: 'firstStorm9', title: '办卡9千' , width: 80},
      	      { field: 'firstStorm10', title: '办卡1万', width: 80},
      	      { field: 'totalFirstStorm', title: '办卡总计', width: 80},
      	      
      	      { field: 'followStorm1', title: '续卡1千', width: 80},
    	      { field: 'followStorm2', title: '续卡2千' , width: 80},
    	      { field: 'followStorm3', title: '续卡3千' , width: 80},
    	      { field: 'followStorm4', title: '续卡4千' , width: 80},
  	      	  { field: 'followStorm5', title: '续卡5千' , width: 80},
  	          { field: 'followStorm6', title: '续卡6千', width: 80},
  	          { field: 'followStorm7', title: '续卡7千' , width: 80},
  	          { field: 'followStorm8', title: '续卡8千', width: 80},
    	      { field: 'followStorm9', title: '续卡9千' , width: 80},
    	      { field: 'followStorm10', title: '续卡1万', width: 80},
    	      { field: 'totalFollowStorm', title: '续卡总计', width: 80},
    	      
    	      { field: 's59', title: '赠券59' , width: 80},
    	      { field: 's128', title: '赠券128' , width: 80},
    	      { field: 's169', title: '赠券169' , width: 80},
    	      { field: 's189', title: '赠券189' , width: 80},
    	      { field: 's219', title: '赠券219' , width: 80},
    	      { field: 's269', title: '赠券269' , width: 80},
    	      { field: 'c59', title: '用券59' , width: 80},
    	      { field: 'c128', title: '用券128' , width: 80},
    	      { field: 'c169', title: '用券169' , width: 80},
    	      { field: 'c189', title: '用券189' , width: 80},
    	      { field: 'c219', title: '用券219' , width: 80},
    	      { field: 'c269', title: '用券269' , width: 80}
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
