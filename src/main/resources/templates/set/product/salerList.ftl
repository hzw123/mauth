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
	<a href="javascript:void(-1);" onclick="">商品综合管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm"  action="${ctx}/product/querySalerList" method="post" >
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
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
  				<td><label>商品分类：</label></td>
  				<td>
  					<select class="midea text" id="categoryId" name="categoryId" onchange="$('#searchPageForm')[0].submit()" >
					<option value="">请选择...</option>
						${productCateGorySelect }
					</select>
				</td>
  				<td><label>名称：</label></td>
  				<td><input type="text" id="name" name="name" class="text midea" value="${param.name}"></input></td>
  				<td><div href="javascript:void(-1)" onclick="$('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table>
   		</form>
   	</div>
   		<div style="clear: both;"></div>
</div>

<c:if test="${empty(page.result)||page.result==null }" var="status">
	<div class="alert alert-info">
		<strong>提示!</strong> 当前未添加数据.
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td style="width: 20px;"><div class="checker" id="uniform-title-table-checkbox">
					<span><input type="checkbox" name="title-table-checkbox" id="title-table-checkbox" onclick="selectAll(this,'id')"></span>
				</div></td>
			<td style="width: 60px;">编号</td>
			<td style="width: 220px;">名称</td>
			<td style="width: 60px;">商品分类</td>
			<td style="width: 60px;">销售指导价</td>
			<td style="width:60px;">顾问结算价</td>
			 <td style="width: 50px;">库存</td>
			<td style="width: 80px;">创建时间</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result }" var="product">
		<tr>
			<td style="text-align: center;">
					<input type="checkbox"   name="id" id="id1" value="${product.dbid }">
			</td>
			<td>${product.sn }</td>
			<td>
					<c:if test="${fn:length(product.name)>12 }" var="status">
						${fn:substring(product.name,0,12) }...
					</c:if>
					<c:if test="${status==false }">
						${product.name }
					</c:if>
			</td>
			<td>
				${product.productcategory.name}
			</td>
			<td>
				${product.price}
			</td>
			<td>
				<fmt:formatNumber value="${product.salerCostPrice }"></fmt:formatNumber> 
			</td>
			<td>
				${product.stockNum}
			</td>
			<td>
				<fmt:formatDate value="${product.createTime }"/>
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
