<#include "../../commons/macro.ftl">
<@commonHead/>
<title>商品综合管理</title>


<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>




<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/product/queryList'">商品综合管理</a>
	-
	<a href="javascript:void(-1);" onclick="">库存预警报告</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but butCancle" href="javascript:void();" onclick="window.history.go(-1)">返回</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm"  action="${ctx}/product/warmStockNum" method="post" >
		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<td><label>状态：</label></td>
  				<td>
  					<select class="midea text" id="stockStatus" name="stockStatus" onchange="$('#searchPageForm')[0].submit()" >
					<option value="0">请选择...</option>
					<option value="2" ${param.stockStatus==2?'selected="selected"':'' } >超出</option>
					<option value="3" ${param.stockStatus==3?'selected="selected"':'' } >短缺</option>
					</select>
				</td>
  				<td><div href="javascript:void(-1)" onclick="$('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table>
   		</form>
   	</div>
   		<div style="clear: both;"></div>
</div>

<c:if test="${empty(products)||products==null }" var="status">
	<div class="alert alert-info">
		<strong>提示!</strong> 当前未添加数据.
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td style="width: 40px;">行号</td>
			<td style="width: 160px;">名称</td>
			<td style="width: 100px;">商品分类</td>
			<td style="width:80px;">最高库存量</td>
			<td style="width:80px;">最低库存量</td>
			<td style="width:80px;">当前存量</td>
			<td style="width: 80px;">状态</td>
			<td style="width: 80px;">超出/短缺库存量</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${products }" var="product" varStatus="i">
		<tr>
			<td>${i.index+1 }</td>
			<td style="text-align: left;">
						${product.name }
			</td>
			<td>
				${product.productcategory.name}
			</td>
			<td>
				${product.maxStockNum}
			</td>
			<td>
				${product.minStockNum}
			</td>
			<td>
				${product.stockNum}
			</td>
			<td>
				<c:if test="${product.stockStatus==2 }">
					<span style="color: green">超出</span>
				</c:if>
				<c:if test="${product.stockStatus==3 }">
					<span style="color: red">短缺</span>
				</c:if>
			</td>
			<td>
				<c:if test="${product.stockStatus==2 }">
					<span style="color: green">
						${product.stockNum-product.maxStockNum }
					</span>
				</c:if>
				<c:if test="${product.stockStatus==3 }">
					<span style="color: red">
						${product.stockNum-product.minStockNum }
					</span>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>
