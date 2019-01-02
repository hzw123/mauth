<#include "../../commons/macro.ftl">
<@commonHead/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="${ctx}/css/global.css" rel="stylesheet" />
    <link href="${ctx}/css/main.css" rel="stylesheet" />
    <style type="text/css">
    	.grid-demo{
    		line-height: 30px;
    	}
    </style>
</head>
<body>
    <div class="location">
        <img src="../../images/homeIcon.png" /> &nbsp;
        <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
        <a href="javascript:void(-1);" >开单</a>
    </div>
    <br>
<div class="layui-container">
    <ul class="site-doc-icon">
	    <c:forEach var="room" items="${rooms }">
	    	<c:if test="${room.status==0}">
		      <li style="margin: 5px;border-radius:5px;border: 1px solid #009688;width: 200px;" class="" onclick="startWritingRoom(${room.dbid},'${room.name }')">
		        <a class="name" style="font-size: 18px;color: #009688" >
		        	${room.name }
		        </a>
			    <div class="code">&nbsp;</div>
		        <div class="code">${room.count }人间</div>
		      </li>
	    	</c:if>
    		<c:if test="${room.status==2 }">
    			<c:set value="${room.startWriting }" var="startWriting"></c:set>
    			 <li style="margin: 5px;border-radius:5px;border: 1px solid #fdc227;width: 200px;" onclick="window.location.href='${ctx}/startWriting/view?dbid=${startWriting.dbid }'">
			        <a class="name" style="font-size: 18px;color: #fdc227"> ${room.name }
			        	<c:if test="${startWriting.startStatus==1 }"> 
			        		<span class="layui-badge">
					       		<fmt:formatDate value="${startWriting.createTime }" pattern="HH:mm"/>待上钟
					       	</span>
			        	</c:if>
			        	<c:if test="${startWriting.startStatus==2 }"> 
			        		<span class="layui-badge">
					       		<fmt:formatDate value="${startWriting.start_time }" pattern="HH:mm"/>待下钟
					       	</span>
			        	</c:if>
			        	<c:if test="${startWriting.startStatus==0 }">
				       		<span class="layui-badge">
				       			<fmt:formatDate value="${startWriting.endTime }" pattern="HH:mm"/>已下钟	
				       		</span>
			        	</c:if>
			       </a>
			        <div class="code">
			        	<c:if test="${startWriting.state==2 }">待收</c:if>
			        	<c:if test="${startWriting.state==0}">已收 </c:if>
			        	<span style="color: red;">${room.startWriting.money} </span>元
			        </div>
			        <div class="code">${room.count }人间</div>
			      </li>
    		</c:if>
	    </c:forEach>
    </ul>  
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script>
var topParent=window.parent;
if(topParent==undefined){
	topParent=parent;
}
var layer;
layui.use(['table','layer'], function(){
  var table = layui.table,laydate=layui.laydate;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  //监听工具条
  table.on('tool(demo)', function(obj){
    var data = obj.data;
  });
  var $ = layui.$, active = {
		  startWritingRoom:function(){
			  
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
});
function startWritingRoom(roomId,roomName){
	layer.open({
        type: 2 //此处以iframe举例
        ,title: roomName+'开单'
         ,area: ['1280px', '640px']
         ,shade: 0.8
         ,maxmin: true
        ,content: '${ctx}/startWriting/startWritingRoom?roomId='+roomId+"&type=1&onlineBookingId=${param.onlineBookingId}"
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
        }
      ,btn: ['确定开单', '关闭']
      ,yes: function(index, layero){
    	  var body = layer.getChildFrame('body', index);
			var entItemNames= body.find("input[name=entItemName]");
			var entItemIds= body.find("input[name=entItemId]");
			var artificerIds= body.find("input[name=artificerId]");
			var partificerIds= body.find("input[name=partificerId]");
			var artificerNames= body.find("input[name=artificerName]");
			var serviceTypes=body.find("select[name='serviceType']");
			var orderType= body.find('input:radio[name="orderType"]:checked').val();
			var roomId= body.find("#roomId").val();
			var personNum= body.find("#personNum").val();
			var memberId= body.find("#memberId").val();
			var itemNums= body.find("input[name=num]");
			var onlineBookingId= "${param.onlineBookingId}";
			if(onlineBookingId==null||onlineBookingId==''){
				onlineBookingId="-1";
			}
			if(roomId==''||roomId==undefined){
				alert("开单失败，请选择房间");
				return ;
			}
			
			if(memberId==''||memberId==undefined){
				alert("开单失败，请选择会员");
				return ;
			}
			
			var arrayItemIds=new Array();
			var arrayArtificerIds=new Array();
			var arrayItemNums=new Array();
			var arrayServiceTypes=new Array();
			
			for(var i=0;i<entItemIds.length;i++){
				var entItemId=$(entItemIds[i]).val();
				if(entItemId!=''&&entItemId!=undefined){
					arrayItemIds.push(entItemId);
					var entItemName=$(entItemNames[i]).val();
					if(entItemName==''||entItemName==undefined){
						alert("项目/产品不能为空，请选择第【"+(i+1)+"】行项目");
						return ;
					}
					
					
					var artificerId=$(artificerIds[i]).val();
					if(artificerId==''||artificerId==undefined){
						artificerId=0;
					}
					arrayArtificerIds.push(artificerId);
					
					var itemNum=$(itemNums[i]).val();
					if(itemNum==''||itemNum==undefined){
						itemNum=1;
					}
					var num=parseInt(itemNum);
					if(num<=0){
						alert("项目数量不能小于0，请选输入【"+(i+1)+"】项目数量");
						return ;
					}
					arrayItemNums.push(num);
					
					var serviceType=$(serviceTypes[i]).val();
					if(serviceType==''||serviceType==undefined){
						serviceType=1;
					}
					arrayServiceTypes.push(serviceType);
				}
			}
			
			var url='${ctx}/startWriting/save';
	    	var params={"roomId":roomId,
	    				"memberId":memberId,
	    				"onlineBookingId":onlineBookingId,
	    				"personNum":personNum,
	    				"itemIds":arrayItemIds.toString(),
	    				"artificerIds":arrayArtificerIds.toString(),
	    				"itemNums":arrayItemNums.toString(),
	    				"orderType":orderType,
	    				"serviceTypes":arrayServiceTypes.toString()
	    				};
	    	$.post(url,params,function callBack(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					layer.msg(data[0].message);
					setTimeout(
							function() {
								layer.close(index);
								window.location.href = data[0].url;
							}, 1000);
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					layer.msg(data[0].message);
					// 保存失败时页面停留在数据编辑页面
					setTimeout(
							function() {
								layer.close(index);
							}, 1000);
				}
				return;
			});
      }
      ,btn2: function(index, layero){
    	  layer.close(index);
      }
      });
}
</script>
</body>
</html>
