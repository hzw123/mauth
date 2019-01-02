<#include "../../commons/macro.ftl">
<@commonHead/>

<link  href="${ctx}/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack4.js"></script>
<title>规则添加</title>
</head>
<body style="margin-bottom: 0px">
	<br>
	<form action="" name="frmId" id="frmId"  target="_self">

		<input type="hidden" name="weixinKeyWordRole.dbid" id="dbid" value="${weixinKeyWordRole.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;height: 60px;">
			<tr height="42">
				<td class="formTableTdLeft">名称:&nbsp;</td>
				<td ><input type="text" name="weixinKeyWordRole.name" id="name"
					value="${weixinKeyWordRole.name }" class="media text" title="名称"	checkType="string,2,12" tip="长度在2到12个字符之间，不能与已有渠道重复"><span style="color: red;">*</span></td>
			</tr>
		</table>
	</form>
</body>
</html>