<#include "../../commons/macro.ftl">
<@commonHead/>
</head>
<body onload="">
<%
	//out.println(request.getAttribute("weixinUrl"));
	//out.println(request.getAttribute("weixinUrl"));
	out.println("获取微信权限...");
	response.sendRedirect((String)request.getAttribute("weixinUrl"));
%>
</body>
</html>