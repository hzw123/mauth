<#include "../../commons/macro.ftl">
<@commonHead/>
    <title>选择包间</title>
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
<div class="layui-container">
	<form class="layui-form" >
	    <ul class="site-doc-icon" id="online">
		    <c:forEach var="room" items="${rooms }">
		      <li style="margin: 5px;border-radius:5px;border: 1px solid #009688;cursor: pointer;" >
		        <div style="text-align: right;line-height: 20px;margin-top: -20px;">
		        	<input type="checkbox" id="roomId" name="roomId" value="${room.dbid }" style="display:inline;" lay-skin="primary" >
		        </div>
		        <div class="name" style="font-size: 18px;color: #009688">${room.name }</div>
		        <div class="code">${room.count }人间</div>
		      </li>
		    </c:forEach>
	    </ul>  
    </form>
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var addMoreState=false;
	$(".site-doc-icon li").each(function(i,v){
		$(this).bind("click",function(){
		 	var checkeds = $("input[type='checkbox'][name='roomId']");
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			checkbox.checked=false;
		  		}
		  	});
		  	var value=$(this).find("input[type='checkbox'][name='roomId']")[0];
		  	value.checked=true;
		});
		});
})
</script>
</body>
</html>
