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
<div class="layui-container" style="width:95%;line-height:40px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
   	<button class="layui-btn layui-btn-xs" data-method="changeMember">更换会员</button>
   	<button class="layui-btn layui-btn-xs" data-method="addMember">添加会员</button>
	<c:if test="${member.dbid!=1 }">
	   	<button class="layui-btn layui-btn-xs" onclick="detail()">充值</button>
   	</c:if>
   	<c:if test="${type==2 }">
	   	<div class="layui-inline">
	      <label class="layui-form-label">补录日期</label>
	      <div class="layui-input-inline">
	        <input type="text" name="date" id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input" lay-key="1">
	      </div>
	    </div>
   	</c:if>
   	<div class="layui-inline">
	      <label class="layui-form-label">消费人数:</label>
	      <div class="layui-input-inline">
		   	<span><input type="number" id="personNum" name="personNum" value="${onlineBooking.maleNum }" class="layui-input"/></span>
		  </div>
		  
		  
	</div>
	<div  class="layui-inline">
		<label class="layui-form-label">订单类型:</label>
		<div class="layui-input-block">
	      	<input type="radio" name="orderType" value="0" title="普通订单" checked > 普通订单  &nbsp;
	      	<input type="radio" name="orderType" value="1" title="点单" />点单
    	</div>
	</div>
</div>
<div class="layui-container" style="line-height:46px;border:1px solid #ddd;font-size:16px;border-radius:5px;margin: 0px auto;margin-top:20px;;">
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员编号:<span id="no">${member.no }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="memberName">${member.name }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="sex">${member.sex }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="mobilePhone">${member.mobilePhone }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	生日
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">
                	<span id="birthday">${member.birthday}</span>
                </div>
            </div>
        </div>
        <div class="layui-row" style="">
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">充值总额:
                	<span id="totalStormMoney">${member.consumeMoney }</span>
                </div>
            </div>
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">余额:
                	<span id="balance">${member.balance }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费次数：
                	<span id="totalBuy">${member.totalBuy }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">剩余积分：
                	<span id="overagePiont">${member.remainderPoint }</span>
                </div>
            </div>
        </div>
    </div>
<div class="layui-container">
	<form class="layui-form" >
	    <input type="hidden" id="totalMoney" name="totalMoney" value="0">
	    <input type="hidden" id="roomId" name="roomId" value="${room.dbid }">
	    <input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
	    <input type="hidden" id="type" name="type" value="${type }">
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
			        <th>项目</th>
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
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
var form;
layui.use(['laypage', 'table','layer','form','laydate'], function(){
  var table = layui.table;
  var laypage=layui.laypage;
  var laydate=layui.laydate;
	form = layui.form;
	  laydate.render({
	    elem: '#date'
	    ,max: '${date}'
	  });
	  if(topParent==undefined){
		 layer = layui.layer;
	  }else{
		 layer = topParent.layer;
	  }
	  var $ = layui.$, active = {
			 changeMember:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        layer.open({
			          type: 2 //此处以iframe举例
			          ,title: '更改会员'
			          ,area: ['1280px', '640px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/member/changeMember'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			          }
			        ,btn: ['确定', '关闭']
			        ,yes: function(index, layero){
			        	var body = layer.getChildFrame('body', index);
			        	var memberid= body.find("input[name=id]:checked").val();
			        	var memberData= body.find("#memberData").val();
			        	if(memberid==''||memberid==undefined){
			        		alert("请选择会员后在操作");
			        		return ;
			        	}
			        	var data=$.parseJSON(memberData);
			        	$("#no").text(data.no);
			        	$("#memberName").text(data.name);
			        	$("#sex").text(data.sex);
			        	$("#memberId").val(data.dbid);
			        	$("#mobilePhone").text(data.mobilePhone);
			        	$("#birthday").text(data.birthday);
			        	$("#totalStormMoney").text(data.totalStormMoney);
			        	$("#balance").text(data.balance);
			        	$("#totalBuy").text(data.totalBuy);
			        	$("#totalMoney").text(data.totalMoney);
			        	$("#overagePiont").text(data.overagePiont);
			        	$("#consumpiontPoint").text(data.consumpiontPoint);
						layer.close(index);
						 //window.location.href='${ctx}/startWriting/startWritingRoom?roomId=${room.dbid}&memberId='+memberid+"&type=${type}";
			        }
			        ,btn2: function(index, layero){
			        	layer.close(index);
			          //return false 开启该代码可禁止点击该按钮关闭
			        }
			        });
		},
		addMember:function(){
		  var that = this;
	        //多窗口模式，层叠置顶
	        layer.open({
	          type: 2 //此处以iframe举例
	          ,title: '创建会员卡'
	          ,area: ['760px', '560px']
	          ,shade: 0.8
	          ,maxmin: true
	          ,content: '${ctx}/member/editRoom?roomId=${room.dbid}'
	          ,zIndex: layer.zIndex //重点1
	          ,success: function(layero){
	          }
	        ,btn: ['确定', '关闭']
	        ,yes: function(index, layero){
	        	var body = layer.getChildFrame('body', index);
				var roomId= body.find("#roomId").val();
				var no= body.find("#no").val();
				var sex= body.find("input[name=member.sex]:checked").val();
				var name= body.find("#name").val();
				var mobilePhone= body.find("#mobilePhone").val();
				var birthday= body.find("#birthday").val();
				var note= body.find("#note").val();
				if(roomId==''&&roomId==undefined){
					alert("保存错误，无房间信息");
					return ;
				}
				if(no==''||no==undefined){
					alert("请输入会员编号");
					return ;
				}
				if(name==''||name==undefined){
					alert("请输入会员姓名");
					return ;
				}
				if(mobilePhone==''||mobilePhone==undefined){
					alert("请输入会员电话");
					return ;
				}
				var stat=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/.test(mobilePhone);
			      if(stat==false){
			    	  alert("请输入正确的手机号码格式");
					return ;
		    	  }
				var url='${ctx}/member/saveRoom';
		    	var params={
		    				"roomId":roomId,
		    				"member.no":no,
		    				"member.name":name,
		    				"member.sex":sex,
		    				"member.mobilePhone":mobilePhone,
		    				"member.birthday":birthday,
		    				"member.note":note
		    				};
		    	$.post(url,params,function callBack(data) {
		    		var data=$.parseJSON(data);
		        	$("#no").text(data.no);
		        	$("#memberName").text(data.name);
		        	$("#sex").text(data.sex);
		        	$("#memberId").val(data.dbid);
		        	$("#mobilePhone").text(data.mobilePhone);
		        	$("#birthday").text(data.birthday);
		        	$("#totalStormMoney").text(data.totalStormMoney);
		        	$("#balance").text(data.balance);
		        	$("#totalBuy").text(data.totalBuy);
		        	$("#totalMoney").text(data.totalMoney);
		        	$("#overagePiont").text(data.overagePiont);
		        	$("#consumpiontPoint").text(data.consumpiontPoint);
		        	layer.close(index);
					return;
				});
	        }
	        ,btn2: function(index, layero){	layer.close(index);}
	        });
	  }
	 }
	  $('.layui-container .layui-btn').on('click', function(){
		  var othis = $(this), method = othis.data('method');
		  active[method] ? active[method].call(this, othis) : '';
	  });
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
			var names=$("#itemTable input[name=entItemName]");
				$("#entItemName"+sn).val(data.name);
				$("#entItemId"+sn).val(data.dbid);
				$("#num"+sn).val(1);
				$("#price"+sn).val(data.price);
				itemPrice();
		});
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
			extraParams:{type:"${type}"},
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

function orderTotalPrice(){
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
	$("#orderTotalPrice").text(formatFloat(totalA));
	$("#totalMoney").text(formatFloat(totalA));
	
	
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

function detail(){
	var memberId=$('#memberId').val();
	window.open('${ctx}/member/detail?dbid='+memberId);
}

</script>
</body>
</html>
