<#include "../../commons/macro.ftl">
<@commonHead/>
<title>套餐添加</title>

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
   	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
   	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/product/queryList'">套餐类型</a>-
	<a href="javascript:void(-1)" class="current">
		<c:if test="${product.dbid>0 }" var="status">编辑套餐</c:if>
		<c:if test="${status==false }">添加套餐</c:if>
	</a>
</div>
<div class="frmContent">
   <form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
	<input type="hidden" value="${product.dbid }" id="dbid" name="product.dbid">
   <table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
		<tr>
			<td class="formTableTdLeft">适用类型:&nbsp;</td>
				<td>
					<c:if test="${empty(product.commType) }">
						<label>
							<input type="radio" id="commType" name="product.commType" checked="checked" value="1" onclick="showHide()">通用
						</label>
						<label>
							<input type="radio" id="commType" name="product.commType" value="2"  onclick="showHide()">专用
						</label>
					</c:if>
					<c:if test="${!empty(product.commType) }">
						<label>
							<input type="radio" id="commType" name="product.commType" ${product.commType==1?'checked="checked" ':'' }  value="1"  onclick="showHide()">通用
						</label>
						<label>
							<input type="radio" id="commType" name="product.commType" ${product.commType==2?'checked="checked" ':'' } value="2"  onclick="showHide()">专用
						</label>
					</c:if>
				<span style="color: red;">*</span>
				</td>
		</tr>
		<c:if test="${empty(product.commType)||product.commType==1 }">
			<tr id="brandTr" style="display: none;" >
				<td class="formTableTdLeft">品牌：</td>
				<td colspan="3"> 
					${brandCheck }
				</td>
			</tr>
		</c:if>
		<c:if test="${product.commType==2 }">
			<tr id="brandTr" >
				<td class="formTableTdLeft">品牌：</td>
				<td colspan="3"> 
					${brandCheck }
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="formTableTdLeft">套餐类型：</td>
			<td>
				<select class="largeX text" id="productCategoryDbid" name="productCategoryDbid" checkType="string,1,20"  tip="请选择套餐规格类型！" >
					<option value="">请选择...</option>
					${productCateGorySelect }
				</select>
			</td>
			<td class="formTableTdLeft">名称：</td>
			<td>
				<input type="text" class="largeX text" name="product.name"	id="name" value="${product.name }" onchange="pingyin.value = getCharsCode(this.value);" checkType="string,1,500"  tip="请输入套餐名称！字符长度必须为1-500个字符"  />
				<input type="hidden" name="product.pingyin" id="pingyin" value="${product.pingyin }">
			</td>
		</tr>
		<tr>
			<td class="formTableTdLeft">编码：</td>
			<td>
				<input  type="text" class="largeX text" name="product.sn" id="sn"  value="${product.sn }" checkType="string,1,20" placeholder="请输入套餐编码信息"  tip="请输入套餐编码！编码为1至20个字符"/>
			</td>
			<td class="formTableTdLeft">销售价格：</td>
			<td>
				<input type="text" class="largeX text" name="product.price" id="price"  value="${product.price }" checkType="float"  tip="请输入套餐销售价格！"/>
			</td>
		</tr>
		<tr>
			<td class="formTableTdLeft">成本价：</td>
			<td colspan="3">
				<input type="text" class="largeX text" name="product.costPrice" id="costPrice"  value="${product.costPrice }" checkType="float" canEmpty="Y" tip="请输入成本价格！可为空"/>
			</td>
		</tr>
		<tr>
				<td class="formTableTdLeft">可见范围：</td>
				<td colspan="3">
						<c:if test="${!empty(check) }">
							${check }
						</c:if>
				</td>
		</tr>
		<tr height="140" style="height: 140px;">
			<td class="formTableTdLeft">商品明细：</td>
			<td  colspan="3">
				<input type="hidden" id="packageIds" name="product.packageIds" value="${product.packageIds }">
				<textarea type="text" readonly="readonly" class="largeX text" style="height: 120px;width: 600px;" name="product.packageDetail" id="packageDetail" checkType="string,1" tip="请选择商品..">${product.packageDetail }</textarea>
				<a href="Javascript:void(-1)" class="aedit" style="color: #46A0DE" onclick="selectOperate()">请选择商品</a>
				<a href="Javascript:void(-1)" class="aedit" style="color: red;" onclick="$('packageIds').val('');$('#packageDetail').val('')">清除</a>
			</td>
		</tr>
		<tr height="80" style="height: 80px;">
			<td class="formTableTdLeft">备注：</td>
			<td  colspan="3">
				<textarea type="text" class="largeX text" style="height: 60px;width: 600px;" name="product.note" id="note"  value="">${product.note }</textarea>
			</td>
		</tr>
</table>
</form>                            
<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitForm('frmId','${ctx}/productPackage/save')"	class="but butSave">保&nbsp;&nbsp;存</a>
	    <a href="javascript:void(-1)"	onclick="goBack()" class="but butCancle">取&nbsp;&nbsp;消</a>
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
<script type="text/javascript">
	function showHide(){
		var value=$("input[name='product.commType']:checked").val();
		if(value==1){
			$("#brandTr").hide();
		}
		if(value==2){
			$("#brandTr").show();
		}
	}
</script>
</body>

</html>
