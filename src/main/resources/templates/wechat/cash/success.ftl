<#include "../../commons/macro.ftl">
<@commonHead/>
<title>付款成功提示</title>
<link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
	<div class="page msg_success js_show">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">操作成功</h2>
            <p class="weui-msg__desc">已经付款成功</p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="${ctx}/memberWechat/memberCenter" class="weui-btn weui-btn_primary">会员中心</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>