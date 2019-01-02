<#include "../../commons/macro.ftl">
<@commonHead/>
<title>打印优惠券</title>
</head>
<link rel="stylesheet" href="${ctx}/css/print.css" />
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<style type="text/css" media="print">
.bar {
	display: none;
}
</style>
<style type="text/css">

table td, table th {
    border: 1px solid #000000;
    color: #000000;
    font-size: 18px;
    line-height: 36px;
    width: 100px;
    font-weight: normal;
    height: 50px;
    padding-left: 12px;
}
</style>
<script type="text/javascript">
$().ready(function() {
	var $print = $("#print");
	$print.click(function() {
		window.print();
		return false;
	});
});
</script>

</head>
<body >
<div class="bar">
	<c:if test="${param.type==1 }">
		<a href="javascript:;" id="print" class="btn btn-success " style="margin-left: 5px;">打 印</a>
	</c:if>
	<c:if test="${param.type==2 }">
		<a href="javascript:;" id="" class="btn btn-success " onclick="window.history.go(-1)" style="margin-left: 5px;">返 回</a>
	</c:if>
</div>
<table  border="1" cellpadding="0" cellspacing="0" style="margin-bottom: 12px;margin-top: 12px;font-size: 24px;width: 100%;" class="contentTable" >
<tr>
	<td colspan="2"  align="center" style="font-size: 20px;">优惠券</td>
</tr>
<tr>
   <td colspan="2">
       	优惠券名称：${couponMember.name }&nbsp;&nbsp;
		<c:if test="${couponMember.type==2 }">金额￥:
			<span style="color: red;font-size: 16px;">
				<fmt:formatNumber value="${ couponMember.money}"></fmt:formatNumber>
			</span>
		</c:if>
    </td>
</tr>
<tr>
    <td colspan="2">
       SN码：${couponMember.code }
    </td>
</tr>
<tr>
   <td colspan="2">
   	备注：
   	${couponMember.reason }
   </td>
</tr>
<tr>
   <td colspan="2">
   	卷面说明：
   	${couponMember.reason }
   </td>
</tr>

<tr>
	<td style="border-right: 0">
		创建人：${couponMember.creatorName }&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;
    </td>
  </tr>
<tr>
    <td style="border-right: 0">
    会员名称：${couponMember.member.name }
    </td>
   <td style="border-left: 0">
    会员ＩＤ：${couponMember.member.mobilePhone }
   </td>
</tr>
<tr>
    <td style="border-right: 0">领取日期：
       <fmt:formatDate value="${couponMember.createTime }" pattern="yyyy年MM月dd日"/>
    </td>
   <td style="border-left: 0">到期日期：<fmt:formatDate value="${couponMember.stopTime }" pattern="yyyy年MM月dd日"/>
    </td>
  </tr>
<tr>
    <td style="border-left: 0">
    	客户签字：
       	&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;
    </td>
  </tr>
  </table>
</body>
</html>