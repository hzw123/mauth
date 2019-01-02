<#include "../../commons/macro.ftl">
<@commonHead/>
    <title>添加产品或项目</title>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${ctx}/widgets/auto/jquery.autocomplete.css" />
    <style type="text/css">
    	.grid-demo{
    		line-height: 30px;
    	}
    	.layui-table th{
    		text-align: center;
    	}
    	.layui-table td{
    		padding: 5px 5px;
    		text-align: center;
    	}
    </style>
</head>
<body>
<div class="layui-container">
	<form class="layui-form" >
	   <input type="hidden" id="totalMoney" name="totalMoney" value="0">
		<div class="layui-row" style="margin-top: 12px;">
		    <div class="layui-col-xs12" style="border-right: 1px solid #eee;height: 500px;padding-right: 5px;">
		      <div class="" style="text-align: center;font-size: 18px;">项目</div>
		      <table class="layui-table" id="itemTable">
			    <colgroup>
			      <col width="150">
			      <col width="120">
			      <col width="50">
			      <col width="50">
			      <col width="50">
			      <col width="50">
			    </colgroup>
			    <thead>
			      <tr>
			        <th>项目/产品</th>
			        <th>技师</th>
			        <th>数量</th>
			        <th>金额</th>
			        <th>类型</th>
			        <th>操作</th>
			      </tr> 
			    </thead>
			    <tbody>
			    <tr height="32" style="font-size: 16px;font-weight: bold;">
					<td  colspan="2" style="text-align: left;padding-right: 12px;border-right: 0;font-size: 16px;font-weight: normal;" >
						合计:<span style="color: #FFB800;" id="itemNum">0</span> 项
					</td>
					<td  colspan="3" style="text-align: right;padding-right: 12px;border-left: 0;font-size: 16px;font-weight: normal;" >
						项目总金额：<span style="color: #FF5722" id="itemTotalPrice">￥0.00</span>
					</td>
				</tr>
			    </tbody>
			  </table>
		    </div>
		  </div>
    </form>
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript"	src="${ctx}/javascript/startWriting.js"></script>
<script type="text/javascript">
var layer;
var form;
layui.use(['form', 'layedit', 'laydate'], function(){
	  form = layui.form;
	  layer = layui.layer;
	  createEntItemTr(form);
	  createEntItemTr(form);
});
function autoItemByName(id){
	var id1 = "#"+id;
		$(id1).autocomplete("${ctx}/entItem/ajaxEntItem",{
			max: 20,      
	        width: 200,
	        matchSubset:false,  
	        matchContains: true,  
			dataType: "json",
			parse: function(data) {   
		    	var rows = [];      
		        for(var i=0; i<data.length; i++){      
		           rows[rows.length] = {       
		               data:data[i]       
		           };       
		        }       
		   		return rows;   
		    }, 
			formatItem: function(row, i, total) {  
				return "<span>名称："+row.name+"&nbsp;&nbsp;价格："+row.price+"&nbsp;&nbsp;时长："+row.timeLong+"分钟 </span>";  
		    },   
		    formatMatch: function(row, i, total) {   
		       return row.name;   
		    },   
		    formatResult: function(row) {   
		       return row.name;   
		    }		
		});
		$(id1).result(function(event, data, formatted){
			var id=$(event.currentTarget).attr("id");
			var sn=id.substring(11,id.length);
			//判断是否有重复的项目
			var names=$("#itemTable input[name=entItemName]");
				$("#entItemName"+sn).val(data.name);
				$("#entItemId"+sn).val(data.dbid);
				$("#num"+sn).val(1);
				$("#price"+sn).val(data.price);
				itemPrice();

		});
	//计算总金额
}
function itemPrice(){
	var prices=$("#itemTable input[name=price]");
	var nums=$("#itemTable input[name=num]");
	var totalA=0.0;
	var lenght=0;
	for(var i=0;i<prices.length;i++){
		var price=$(prices[i]).val();
		var num=$(nums[i]).val();
		if(null!=price&&price!=''){
			price=parseInt(price);
			num=parseInt(num);
			if(!isNaN(price)){
				totalA=totalA+price*num;
			}else{
				totalA=totalA+0;
			}
			lenght=lenght+1;
		}
	}
	$("#itemTotalPrice").text("￥"+formatFloat(totalA));
	$("#itemNum").text(lenght);
	orderTotalPrice();
}
function autoArtificerByName(id){
	var id1 = "#"+id;
		$(id1).autocomplete("${ctx}/artificer/ajaxArtificer",{
			max: 20,      
			extraParams:{type:"${startWriting.infromType}"},
	        width: 130,    
	        matchSubset:false,   
	        matchContains: true,  
			dataType: "json",
			parse: function(data) {   
		    	var rows = [];      
		        for(var i=0; i<data.length; i++){      
		           rows[rows.length] = {       
		               data:data[i]       
		           };       
		        }       
		   		return rows;   
		    }, 
			formatItem: function(row, i, total) {   
				 return "<span>名称："+row.name+"&nbsp;&nbsp;编号："+row.no+"&nbsp;&nbsp; </span>";  
		    },   
		    formatMatch: function(row, i, total) {   
		       return row.name;   
		    },   
		    formatResult: function(row) {   
		       return row.name;   
		    }		
		});
		$(id1).result(function(event, data, formatted){
			var id=$(event.currentTarget).attr("id");
			var sn=id.substring(13,id.length);
			$("#artificerName"+sn).val(data.name);
			$("#artificerId"+sn).val(data.dbid);
		});
}
//添加项目部分
function createEntItem(v){
	var value=$(v).val();
	if(null==value||value.length<=0){
		return ;
	}
	var id=$(v).parent().parent().attr("id");
	if(id==$("#itemTable tr").eq(-2).attr("id")){
		createEntItemTr();
	}
}

function orderTotalPrice(){
	var productPrices=$("#productTable input[name=productPrice]");
	var productNums=$("#productTable input[name=productNum]");
	var ptotalA=0.0;
	for(var i=0;i<productPrices.length;i++){
		var price=$(productPrices[i]).val();
		var num=$(productNums[i]).val();
		if(null!=price&&price!=''){
			price=parseInt(price);
			num=parseInt(num);
			if(!isNaN(price)){
				ptotalA=ptotalA+price*num;
			}else{
				ptotalA=ptotalA+0;
			}
		}
	}
	
	var prices=$("#itemTable input[name=price]");
	var nums=$("#itemTable input[name=num]");
	var totalA=0.0;
	for(var i=0;i<prices.length;i++){
		var price=$(prices[i]).val();
		var num=$(nums[i]).val();
		if(null!=price&&price!=''){
			price=parseInt(price);
			num=parseInt(num);
			if(!isNaN(price)){
				totalA=totalA+price*num;
			}else{
				totalA=totalA+0;
			}
		}
	}
	$("#orderTotalPrice").text(formatFloat(ptotalA+totalA));
	$("#totalMoney").text(formatFloat(ptotalA+totalA));
	
	
}

function formatFloat(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        return 0;
    }
    var f_x = Math.round(x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}
</script>
</body>
</html>
