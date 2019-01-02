<#include "../../commons/macro.ftl">
<@commonHead/>






<title>资源排序</title>
</head>
<body class="bodycolor">
<div class="frmContent">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent">
		<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
		<thead class="TableHeader">
			<tr>
				<td class="span2">名称</td>
				<td class="span2">排序号</td>
			</tr>
		</thead> 
		<c:forEach var="resource" items="${resources }">
			<tr height="32" align="center">
				<td>
				<input type="hidden" value="${resource.dbid }" name="dbid" id="dbid" >
				${resource.title }</td>
				<td>
					<input type="text" value="${resource.orderNo }" class="text small" name="orderNo" id="orderNo" >
				</td>
			</tr>
		</c:forEach>
	</table>
	</form>
	<div class="formButton">
		<a href="javascript:void()"	onclick="$.utile.submitForm('frmId','${ctx}/resource/saveOrderNum')"	class="but butSave">保&nbsp;&nbsp;存</a>
	    <a href="javascript:void(-1)"	onclick="art.dialog.close()" class="but butCancle">取&nbsp;&nbsp;消</a>
	</div>
</div>
</body>
</html>