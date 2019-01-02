<#include "../../commons/macro.ftl">
<@commonHead/>

<script type="text/javascript">
/** 获取checkBox的value* */
function getCheckBoxproduct() {
	var array = new Array();
	var arrayNames = new Array();
	var checkeds = $("input[type='checkbox'][name='id']");
	$.each(checkeds, function(i, checkbox) {
		if (checkbox.checked) {
			array.push(checkbox.value);
			arrayNames.push($(checkbox).attr("tip"));
		}
	});
	$("#targets").val(array.toString()+"|"+arrayNames.toString());
}
</script>
<title>商品选择列表</title>
</head>
<body class="bodycolor">
 <!--location end-->
<div class="listOperate">
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/product/productSelect" method="post">
  		<input type="hidden" id="targets" name="targets">
		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<td><label>品牌：</label></td>
  				<td>
  					<select class="text midea" id="brandId" name="brandId"  onchange="$('#searchPageForm')[0].submit()">
						<option value="0" >请选择...</option>
						<c:forEach var="brand" items="${brands }">
							<option value="${brand.dbid }" ${param.brandId==brand.dbid?'selected="selected"':'' } >${brand.name }</option>
						</c:forEach>
					</select>
  				</td>
  				<td><label>类型：</label></td>
  				<td>
  					<select class="text midea" id="categoryId" name="categoryId"  onchange="$('#searchPageForm')[0].submit()">
						<option value="0" >请选择...</option>
						${productCateGorySelect }
					</select>
  				</td>
  				<td><label>名称：</label></td>
  				<td><input type="text" id="name" name="name" class="text midea" value="${param.name}"></input></td>
  				<td><div href="javascript:void(-1)" onclick=" $('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table>
   		</form>
   	</div>
   		<div style="clear: both;"></div>
</div>
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id');getCheckBoxproduct()" /></td>
			<td class="span2">类型</td>
			<td class="span2">适用类型</td>
			<td class="span2">名称</td>
		</tr>
	</thead>
	<c:forEach var="product" items="${products }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" tip="${product.name }" value="${product.dbid }" onclick="getCheckBoxproduct()"/></td>
			<td>
					${product.productcategory.name }
			</td>
			<td>
				<c:if test="${product.commType==1 }">
					通用
				</c:if>
				<c:if test="${product.commType==2 }">
					专用
				</c:if>
			</td>
			<td>${product.name }</td>
		</tr>
	</c:forEach>
</table>
</body>
<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>




</html>