<#include "../../commons/macro.ftl">
<@commonHead/>
<title>权限验证错误提示</title>
</head>
<body>
<%
	out.println("<span>获取微信权限发生错误</span>");
	out.println("<span style='color:red;'>"+(String)request.getAttribute("error")+"</span>");
%>
</body>
</html>