<#include "../../commons/macro.ftl">
<@commonHead/>
<title>错误提示页面</title>
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
	<div class="page msg_warn js_show">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">操作失败</h2>
            <p class="weui-msg__desc">${error }</p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
            	<c:if test="${!empty(sessionScope.member) }">
	            	 <a href="${ctx}/memberWechat/memberCenter" class="weui-btn weui-btn_primary">会员中心</a>
            	</c:if>
                <a href="javascript:history.back();" class="weui-btn weui-btn_default">返回</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>