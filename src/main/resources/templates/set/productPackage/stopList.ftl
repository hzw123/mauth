<#include "../../commons/macro.ftl">
<@commonHead/>
<title>套餐综合管理</title>


<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>



<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">套餐综合管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/productPackage/queryList'">返回</a>
		<a class="but butCancle" href="javascript:void();" onclick="$.utile.deleteIds('${ctx}/productPackage/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm"  action="${ctx}/productPackage/queryStopList" method="post" >
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
  				<td><label>套餐分类：</label></td>
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
			<td style="width: 120px;">名称</td>
			<td style="width: 100px;">套餐分类</td>
			<td style="width: 140px;">商品明细</td>
			<td style="width: 50px;">销售价</td>
			<td style="width:50px;">成本价</td>
			<!-- <td style="width: 50px;">库存</td> -->
			<td style="width: 80px;">创建时间</td>
			<td style="width: 80px;">操作</td>
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
			<td class="tip" tip="商品明细：${product.packageDetail}">
				<a href="javascript:void(-1)" class="" style="color: #46A0DE;">${fn:substring(product.packageDetail,0,12) }</a>
			</td>
			<td>
				${product.price}
			</td>
			<td>
				${product.costPrice}
			</td>
		<%-- 	<td>${product.stockNum }</td> --%>
			<td>
				<fmt:formatDate value="${product.createTime }"/>
			</td>
			<td style="text-align: center;">
			<a href="javascript:void(-1)" class="aedit" onclick="$.utile.operatorDataByDbid('${ctx}/productPackage/stopOrStart?dbid=${product.dbid}','searchPageForm','您确定【${product.name}】启用该套餐吗')">启用</a>|
			<a href="javascript:void(-1)" class="aedit" onclick="$.utile.deleteById('${ctx}/productPackage/delete?dbids=${product.dbid}','searchPageForm')" title="删除">删除</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</c:if>
<div id="fanye">
	<#include "../../commons/commonPagination.ftl">
</div>
</body>
</html>
