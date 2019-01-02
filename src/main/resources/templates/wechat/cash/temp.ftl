<#include "../../commons/macro.ftl">
<@commonHead/>
<title>微信权限验证成功</title>
</head>
<body>
<%
	String url=(String)request.getAttribute("url");
	response.sendRedirect(url);
	//out.print(url);
%>
</body>
</html>