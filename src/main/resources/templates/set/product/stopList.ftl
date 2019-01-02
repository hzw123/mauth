<#include "../../commons/macro.ftl">
<@commonHead/>
<title>商品综合管理</title>


<title>操作管理</title>
<style type="text/css">
.location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
</style>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/product/queryList'">商品管理</a>
	<a href="javascript:void(-1);" onclick="">下架商品</a>
</div>
 <!--location end-->
<hr class="layui-bg-red">
<br>
 <!--location end-->
 <div class="layui-row" >
    <div class="layui-col-xs5" >
		<a class="layui-btn" href="javascript:void();" onclick="window.location.href='${ctx}/product/queryList'" style="margin-left: 5px;">返回</a>
		<a class="layui-btn layui-btn-danger" href="javascript:void(-1);" data-method="setTop">删除</a>
    </div>
    <div class="layui-col-xs7" >
    	<form name="searchPageForm" id="searchPageForm"  action="${ctx}/product/queryStopList" method="post" >
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<div class="layui-form-item" style="margin-bottom: 2px;">
				<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">	商品分类:&nbsp;</label>
			    <div class="layui-input-inline" style="width: 100px;">
  					<select class="layui-input" id="categoryId" name="categoryId" onchange="$('#searchPageForm')[0].submit()" >
						<option value="">请选择...</option>
						${productCateGorySelect }
					</select>
				</div>
  				<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">名称：</label>
  				<div class="layui-input-inline" style="width: 100px;">
  					<input type="text" id="name" name="name" class="layui-input" value="${param.name}"></input>
  				</div>
  				<label class="layui-form-label" style="width: 70px;padding: 9px 5px;">编号：</label>
  				<div class="layui-input-inline" style="width: 100px;">
  					<input type="text" id="sn" name="sn" class="layui-input" value="${param.sn}"></input>
  				</div>
  				<a id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search" onclick="$('#searchPageForm')[0].submit()"><i class="layui-icon layui-btn-icon"></i>查询</a>
   		</div>
   		</form>
    </div>
</div>

<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
	<thead>
		<tr>
			<td style="width: 20px;"><input type="checkbox" name="title-table-checkbox" id="title-table-checkbox" onclick="selectAll(this,'id')"></td>
			<td style="width: 60px;">编号</td>
			<td style="width: 220px;">名称</td>
			<td style="width: 100px;">商品分类</td>
			<td style="width: 50px;">销售价</td>
			<td style="width:50px;">成本价</td>
			<td style="width:50px;">结算价</td>
			<!-- <td style="width: 50px;">库存</td> -->
			<td style="width: 80px;">创建时间</td>
			<td style="width: 80px;">操作</td>
		</tr>
	</thead>
		<c:if test="${empty(page.result)||page.result==null }" var="status">
		<tr height="32" align="center">
			<td colspan="9">
				无数据
			</td>
		</tr>
	</c:if>
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
				${product.costPrice}
			</td>
			<td>
				<fmt:formatNumber value="${product.salerCostPrice }"></fmt:formatNumber> 
			</td>
			<td>
				<fmt:formatDate value="${product.createTime }"/>
			</td>
			<td style="text-align: center;">
			<a href="javascript:void(-1)" class="layui-btn  layui-btn-xs" onclick="$.utile.operatorDataByDbid('${ctx}/product/stopOrStart?dbid=${product.dbid}','searchPageForm','您确定上架【${product.name}】该商品吗')">上架</a>
			 <a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-xs" onclick="$.utile.deleteById('${ctx}/product/delete?dbids=${product.dbid}','searchPageForm')" title="删除">删除</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div id="page"></div>


<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
layui.use(['laypage', 'table','layer','form'], function(){
  var table = layui.table,laypage=layui.laypage;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  var $ = layui.$, active = {
	  setTop:function(){
		  deleteByIds('${ctx}/product/delete');
	  }
  };
  $('.layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
  
 //触发事件
  $('.layui-btn').on('click', function(){
	    var othis = $(this), method = othis.data('method');
	    active[method] ? active[method].call(this, othis) : '';
	});
  //完整功能
  laypage.render({
    elem: 'page',
    limit:${page.pageSize}
    ,count: ${page.totalCount}
    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    ,curr:${page.currentPageNo}
    ,jump: function(obj,first){
    	if(!first){
	      $("#currentPage").val(obj.curr);
	      $("#paramPageSize").val(obj.limit);
	      var qForm=$("#searchPageForm");
	      qForm.submit();
   	    }
    }
  });
});
</script>
</body>
</html>
