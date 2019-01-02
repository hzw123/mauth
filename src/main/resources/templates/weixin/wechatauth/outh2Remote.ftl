<#include "../../commons/macro.ftl">
<@commonHead/>
</head>

<body>
<%
	out.print("获取权限成功...");
	response.sendRedirect((String)request.getSession().getAttribute("resultUrl"));
	//out.print((String)request.getSession().getAttribute("resultUrl"));
	request.getSession().setAttribute("resultUrl", null);
%>
</body>
</html>