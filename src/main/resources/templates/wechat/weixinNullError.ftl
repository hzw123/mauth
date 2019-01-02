<#include "../commons/macro.ftl">
<@commonHead/>
    <!-- apple devices fullscreen -->
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui.css?${now}" type="text/css" />
	<script src="${ctx}/widgets/bootstrap3/jquery.min.js"></script>
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <script src="${ctx}/widgets/easyvalidator/js/easy_validator.pack5.js"></script>
    
    <style type="text/css">
    </style>
<title>信息提示</title>
</head>
<body>
<div class="container" id="container"><div class="msg">
<div class="weui_msg">
    <div class="weui_icon_area"><i class="weui_icon_msg weui_icon_warn"></i></div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">操作失败</h2>
        <p class="weui_msg_desc">无关注客户信息，请退出后重新进入。</p>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <a href="javascript:;" class="weui_btn weui_btn_default" onclick="window.history.go(-1)">返回</a>
        </p>
    </div>
</div>
</div></div>
<br>
</body>
<script type="text/javascript">
</script>
</html>