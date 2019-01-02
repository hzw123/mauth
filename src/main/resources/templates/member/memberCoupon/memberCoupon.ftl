<#include "../../commons/macro.ftl">
<@commonHead/>

<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>

<title>会员管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">定向券管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.history.go(-1)">返回</a>
   </div>
  	<div class="seracrhOperate">
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(couponMembers)||couponMembers==null }" var="status">
	<div class="alert alert-info">
		<strong>提示!</strong> 会员未添加优惠券
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span2">名称</td>
			<td class="span2">sn码</td>
			<td class="span1">类型</td>
			<td class="span1">折扣/金额</td>
			<td class="span1">姓名</td>
			<td class="span1">电话</td>
			<td class="span2">有效期</td>
			<td class="span2">创建人</td>
			<td class="span1">是否启用</td>
			<td class="span2">是否使用</td>
		</tr>
	</thead>
	<c:forEach var="couponMember" items="${couponMembers }">
			<tr>
				<td style="text-align: center;">
						<input type="checkbox"   name="id" id="id1" value="${couponMember.dbid }">
				</td>
				<td>${couponMember.name }</td>
				<td style="text-align: center;">
					${couponMember.code }
				</td>
				<td style="text-align: center;">
					<c:if test="${couponMember.type==1 }">
						折扣券							
					</c:if>
					<c:if test="${couponMember.type==2 }">
						代金券
					</c:if>
				</td>
				<td>
					<c:if test="${couponMember.showHiden==true }">
						<c:if test="${couponMember.type==1 }">
							<span style="color: red;font-size: 16px;">${ couponMember.moneyOrRabatt} 折</span>
						</c:if>
						<c:if test="${couponMember.type==2 }">
							<span style="color: red;font-size: 16px;">
								￥<fmt:formatNumber value="${ couponMember.moneyOrRabatt}"></fmt:formatNumber>
							</span>
						</c:if>
					</c:if>
				</td>
			    <td style="text-align: center;">
					${couponMember.member.name }
				</td>
			    <td style="text-align: center;">
					${couponMember.member.mobilePhone }
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${couponMember.start_time}" pattern="yyyy-MM-dd"/> ~
					<fmt:formatDate value="${couponMember.stopTime }" pattern="yyyy-MM-dd"/>
				</td>
				 <td style="text-align: center;">
					${couponMember.creatorName }
				</td>
				<td align="center" style="text-align: center;">
					<c:if test="${couponMember.enabled==true }" var="status">
						<span style="color: blue;">是</span>
					</c:if>
					<c:if test="${status==false }">
						<span style="color: red;">否</span>
					</c:if>
				</td>
				<td align="center" style="text-align: center;">
						<c:if test="${couponMember.isUsed==true }" var="status">
							<span style="color: blue;">
								${couponMember.usedPersonName } &nbsp;&nbsp;
								<fmt:formatDate value="${couponMember.usedDate }" pattern="yyyy-MM-dd HH:mm"/>
							</span>
						</c:if>
						<c:if test="${status==false }">
							<span style="color: red;">否</span>
							<c:if test="${now<coupon.start_time }">
						    	<a href="javascript:void(-1)" class="btn_get" style="position:relative; margin-right: 2px;color: #E2E0E0">活动未开始</a>
						    </c:if>
						    <c:if test="${now>coupon.stopTime }">
						    	<a href="javascript:void(-1)" class="btn_get" style="position:relative;margin-right: 2px;color: #E2E0E0">已过期</a>
						    </c:if>
						</c:if>
				</td>
			</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>