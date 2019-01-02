<#include "../../commons/macro.ftl">
<@commonHead/>
<title>套餐查看</title>

<link href="${ctx}/css/common.css" type="text/css" rel="stylesheet">
<link  href="${ctx}/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>





<script type="text/javascript" src="${ctx}/widgets/charscode.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<script type="text/javascript">
	function showOrHidn(varCheck,id){
		var va=varCheck.checked;
		if(va){
			$("#"+id).attr("disabled",false);
		}else{
			$("#"+id).attr("disabled",true);
		}
	}
</script>
<body class="bodycolor">
<div class="location">
	<a href="javascript:void(-1)" class="current">
		统装包明细
	</a>
</div>
<div class="frmContent">
   <form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
	<input type="hidden" value="${product.dbid }" id="dbid" name="product.dbid">
   <table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
		<tr>
			<td class="formTableTdLeft">车型：</td>
			<td id="carModelLabel">
				${product.carSeriy.name }
			</td>
		</tr>
		<tr>
			<td class="formTableTdLeft">套餐类型：</td>
			<td>
				${product.productcategory.name }
			</td>
			<td class="formTableTdLeft">名称：</td>
			<td>
				${product.name }
			</td>
		</tr>
		<tr>
			<td class="formTableTdLeft">编码：</td>
			<td>
				${product.sn }
			</td>
			<td class="formTableTdLeft">销售价格：</td>
			<td>
				${product.price }
			</td>
		</tr>
		<tr height="140" >
			<td class="formTableTdLeft">商品明细：</td>
			<td  colspan="3">
				${product.packageDetail }
			</td>
		</tr>
		<tr height="80" >
			<td class="formTableTdLeft">备注：</td>
			<td  colspan="3">
				${product.note }
			</td>
		</tr>
</table>
</form>                            
<div class="formButton" style="text-align: center;">
	    <a href="javascript:void(-1)"	onclick="art.dialog.close()" class="but butCancle">关闭</a>
	</div>
</div>
<script type="text/javascript">
function ajaxCarSeriy(val){
	if(null==val||val==''){
		alert("请选择品牌");
		return false;
	}
	$("#carSeriyId").remove();
	$.post("${ctx}/carSeriy/ajaxCarSeriy?brandId="+val+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			$("#carModelLabel").append(data);
		}
	});
}
function selectOperate(){
	var brandId=$("#brandId").val();
	commonSelect('选择商品','${ctx}/product/productSelect?brandId='+brandId,'packageIds','packageDetail','targets',720,450,true);
}
</script>
</body>

</html>
