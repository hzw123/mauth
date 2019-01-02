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
        <a href="javascript:void(-1);" >补录订单</a>
    </div>
    <br>
<div class="layui-container">
    <ul class="site-doc-icon">
	    <c:forEach var="room" items="${rooms }">
		      <li style="margin: 5px;border-radius:5px;border: 1px solid #009688;width: 200px;" onclick="startWritingRoom(${room.dbid},'${room.name }')">
		        <a class="name" style="font-size: 18px;color: #009688" >
		        	${room.name }
		        </a>
			    <div class="code">&nbsp;</div>
		        <div class="code">${room.count }人间</div>
		      </li>
	    </c:forEach>
    </ul>  
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script>
var topParent=window.parent;
if(parent==undefined){
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
        ,title: "补录订单"+roomName+'开单'
         ,area: ['1280px', '640px']
         ,shade: 0.8
         ,maxmin: true
        ,content: '${ctx}/startWriting/startWritingRoom?roomId='+roomId+"&type=2"
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
          //layer.setTop(layero); //重点2
        }
      ,btn: ['确定开单', '关闭']
      ,yes: function(index, layero){
    	  var body = layer.getChildFrame('body', index);
			var entItemNames= body.find("input[name=entItemName]");
			var entItemIds= body.find("input[name=entItemId]");
			var artificerIds= body.find("input[name=artificerId]");
			var artificerNames= body.find("input[name=artificerName]");
			var roomId= body.find("#roomId").val();;
			var memberId= body.find("#memberId").val();
			var personNum= body.find("#personNum").val();
			var type= body.find("#type").val();
			var date= body.find("#date").val();
			var itemNums= body.find("input[name=num]");
			if(roomId==''||roomId==undefined){
				alert("开单失败，请选择房间");
				return ;
			}
			if(memberId==''||memberId==undefined){
				alert("开单失败，请选择会员");
				return ;
			}
			if(date==''||date==undefined){
				alert("开单失败，请选择补录时间");
				return ;
			}
			var entStatus=false,productStatus=false;
			var arrayItemIds=new Array(),arrayArtificerIds=new Array(),arrayItemNums=new Array();
			for(var i=0;i<entItemIds.length;i++){
				var entItemId=$(entItemIds[i]).val();
				if(entItemId!=''&&entItemId!=undefined){
					arrayItemIds.push(entItemId);
					entStatus=true;
					var entItemName=$(entItemNames[i]).val();
					if(entItemName==''||entItemName==undefined){
						alert("项目不能为空，请选择第【"+(i+1)+"】行项目");
						return ;
					}
					var artificerId=$(artificerIds[i]).val();
					if(artificerId==''||artificerId==undefined){
						alert("技师不能为空，请选择第【"+(i+1)+"】技师");
						return ;
					}
					arrayArtificerIds.push(artificerId);
					var itemNum=$(itemNums[i]).val();
					if(itemNum==''||itemNum==undefined){
						alert("项目数量不能为空，请选输入【"+(i+1)+"】项目数量");
						return ;
					}
					var num=parseInt(itemNum);
					if(num<=0){
						alert("项目数量不能小于0，请选输入【"+(i+1)+"】项目数量");
						return ;
					}
					arrayItemNums.push(num);
				}
			}
			
			
			var entProductIds= body.find("input[name=entProductId]");
			var entProductNames= body.find("input[name=entProductName]");
			var productNums= body.find("input[name=productNum]");
			var arrayProductIds=new Array(),arrayProductNums=new Array();
			for(var i=0;i<entProductIds.length;i++){
				var entItemId=$(entProductIds[i]).val();
				if(entItemId!=''&&entItemId!=undefined){
					arrayProductIds.push(entItemId);
					productStatus=true;
					var entProductName=$(entProductNames[i]).val();
					if(entProductName==''||entProductName==undefined){
						alert("产品不能为空，请选择第【"+(i+1)+"】行产品");
						return ;
					}
					var productNum=$(productNums[i]).val();
					if(productNum==''||productNum==undefined){
						alert("产品数量不能为空，请选输入【"+(i+1)+"】产品数量");
						return ;
					}
					var num=parseInt(productNum);
					if(num<=0){
						alert("产品数量不能小于0，请选输入【"+(i+1)+"】产品数量");
						return ;
					}
					arrayProductNums.push(num);
				}
			}
			if(productStatus==false&&entStatus==false){
				alert("请添加项目或者产品信息");
				return ;
			}
			var url='${ctx}/startWriting/save';
	    	var params={"roomId":roomId,
	    				"memberId":memberId,
	    				"personNum":personNum,
	    				"type":type,
	    				"date":date,
	    				"itemIds":arrayItemIds.toString(),
	    				"artificerIds":arrayArtificerIds.toString(),
	    				"itemNums":arrayItemNums.toString(),
	    				"entProductIds":arrayProductIds.toString(),
	    				"productNums":arrayProductNums.toString()
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
        //return false 开启该代码可禁止点击该按钮关闭
      }
      });
}
</script>
</body>
</html>
