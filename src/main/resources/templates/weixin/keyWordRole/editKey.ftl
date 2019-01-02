<#include "../../commons/macro.ftl">
<@commonHead/>

<link  href="${ctx}/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack4.js"></script>
<title>参数二维码设置关键词</title>
</head>
<body >
	<br>
	<form action="" name="frmId" id="frmId"  target="_self">

		<input type="hidden" name="weixinKeyWordRoleId" id="weixinKeyWordRoleId" value="${param.weixinKeyWordRoleId }">
		<input type="hidden" name="weixinKeyWord.dbid" id="dbid" value="${weixinKeyWord.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="width: 60px;">关键词:&nbsp;</td>
				<td ><input type="text" name="weixinKeyWord.keyword" id="keyword"
					value="${weixinKeyWord.keyword }" class="media text" title="关键词"	checkType="string,2,12" tip="长度在2到12个字符之间"><span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="42">
				
				<td class="formTableTdLeft" style="width: 60px;">规则:&nbsp;</td>
				<td >
					<c:if test="${empty(weixinKeyWord) }">
						<label><input type="radio" value="1" id="matchingType" name="weixinKeyWord.matchingType" checked="checked">全匹配</label> 
						<label><input type="radio" value="2" id="matchingType2" name="weixinKeyWord.matchingType" >模糊匹配</label> 
					</c:if>
					<c:if test="${!empty(weixinKeyWord) }">
						<label><input type="radio" value="1" id="matchingType" name="weixinKeyWord.matchingType" ${weixinKeyWord.matchingType==1?'checked="checked"':'' } >全匹配</label> 
						<label><input type="radio" value="2" id="matchingType2" name="weixinKeyWord.matchingType" ${weixinKeyWord.matchingType==2?'checked="checked"':'' } >模糊匹配</label> 
					</c:if>
					<span style="color: red;">*</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>