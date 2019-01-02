<#include "../../commons/macro.ftl">
<@commonHead/>
<title>关注时回复</title>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
	class="skin-color" />
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx}/main/index" title="微商城中心" class="tip-bottom"><i
				class="icon-home"></i>微商城中心</a> <a href="javascript:void(-1)"
				class="current">文本消息</a>
		</div>

		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<p>
						<a class="btn btn-inverse" href="${ctx}/weixinSubscribe/add?parentMenu=${param.parentMenu}">
							<i	class="icon-plus-sign icon-white"></i>添加</a>
						<a class="btn btn-danger" href="javascript:void(-1)" onclick="$.utile.deleteIds('${ctx}/weixinSubscribe/delete','searchPageForm')">
							<i class="icon-remove icon-white"></i>删除
						</a>
					</p>
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/weixinSubscribe/queryList" method="post">
				     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
				     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
				     <input type="hidden" id="parentMenu" name="parentMenu" value='${param.parentMenu}'>
				     <%-- <table  cellpadding="0" cellspacing="0" >
						<tr>
							<td>商品名称：</td>
							<td><input type="text" class="input-large"  id="name" name="name" value="${param.name}" ></input></td>
							<td><input type="submit"  class="btn btn-success" value="查询" style="margin-left: 20px;"></input></td>
						</tr>
					 </table> --%>
					</form>
				</div>
				<div  style="clear: both;"></div>
			</div>
			<div class="widget-content">
				<c:if test="${empty(page.result)||page.result==null }" var="status">
					<div class="alert">
						<strong>提示!</strong> 当前未添加数据.
					</div>
				</c:if>
				<c:if test="${status==false }">
				<table class="table table-bordered table-striped with-check">
					<thead>
						<tr>
							<th style="width: 20px;"><div class="checker" id="uniform-title-table-checkbox">
									<span><input type="checkbox" name="title-table-checkbox" id="title-table-checkbox" style="opacity: 0;" onclick="selectAll(this,'id')"></span>
								</div></th>
							<th style="width: 140px;">名称</th>
							<th style="width: 120px;">类型</th>
							<th style="width: 120px;">状态</th>
							<th style="width: 120px;">创建时间</th>
							<th style="width: 120px;">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result }" var="weixinSubscribe">
						<tr>
							<td style="text-align: center;"><div class="checker" id="uniform-undefined">
									<span><input type="checkbox" style="opacity: 0;"  name="id" id="id1" value="${weixinSubscribe.dbid }"></span>
								</div>
							</td>
							<td>${weixinSubscribe.templateName }</td>
							<td>
			                      <c:if test="${weixinSubscribe.msgType=='text' }">
			                      	文本消息
			                      </c:if>
			                      <c:if test="${weixinSubscribe.msgType=='news' }">
			                      	图文消息
			                      </c:if>
							</td>
							<td>
			                      <c:if test="${weixinSubscribe.status==1 }">
			                      	<span style="color: green;">启用</span>
			                      </c:if>
			                      <c:if test="${weixinSubscribe.status==2 }">
			                      	<span style="color: red;">停用</span>
			                      </c:if>
							</td>
							<td>
								 ${fn:substring(weixinSubscribe.addTime,0,10)  }
							</td>
							<td style="text-align: center;">
							<a href="javascript:void(-1)" onclick="window.location.href='${ctx}/weixinSubscribe/edit?dbid=${weixinSubscribe.dbid}&parentMenu=${param.parentMenu }'">编辑</a> |
							<a href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/weixinSubscribe/delete?dbids=${weixinSubscribe.dbid}','searchPageForm')" title="删除">删除</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
			<div id="fanye">
				<%@ include file="../../commons/commonPagination.jsp" %>
			</div>
		</div>

	</div>

	<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
	<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.uniform.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.dataTables.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/unicorn.js"></script>
	<script src="${ctx}/widgets/bootstrap/unicorn.tables.js"></script>



</body>
</html>
