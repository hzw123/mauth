<#include "../../commons/macro.ftl">
<@commonHead/>
<title>收银</title>
<link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
	<div class="container" id="container"></div>
	  <div class="js_dialog" id="iosDialog1" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__hd"><strong class="weui-dialog__title">敬告</strong></div>
                <div class="weui-dialog__bd">您当前收银的房间号是<span style="color: red;font-size: 18px;">${room.name }</span>,请确认无误后在进行付款操作。</div>
	                <div class="page__bd" style="text-align: left;margin-bottom: 8px;">
	        			<div class="weui-cells__title">支付方式</div>
	                	<div class="weui-cells weui-cells_radio">
	                		<label class="weui-cell weui-check__label" for="x12">
				                <div class="weui-cell__bd">
				                    <p>会员卡余额支付</p>
				                </div>
				                <div class="weui-cell__ft">
				                    <input type="radio" name="radio1" class="weui-check" value="1" id="x12" checked="checked">
				                    <span class="weui-icon-checked"></span>
				                </div>
				            </label>
			                <c:if test="${member.startBalance>=startWriting.money}">
					            <label class="weui-cell weui-check__label" for="x11">
					                <div class="weui-cell__bd">
					                    <p>创始会员卡支付</p>
					                </div>
					                <div class="weui-cell__ft">
					                    <input type="radio" class="weui-check" value="2" name="radio1" id="x11">
					                    <span class="weui-icon-checked"></span>
					                </div>
					            </label>
			                </c:if>
				        </div>
	                </div>
                <div class="weui-dialog__ft">
                   <!--   <a href="javascript:history.back();" class="weui-dialog__btn weui-dialog__btn_default">取消</a>-->
                    <a href="javascript:" onclick="pay()" class="weui-dialog__btn weui-dialog__btn_primary">收银</a>
                </div>
            </div>
        </div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/weui/example/zepto.min.js"></script>
<script src="${ctx}/weui/example/router.min.js"></script>
<script src="${ctx}/weui/example/example.js"></script>
<script type="text/javascript">
var $iosDialog2 = $('#iosDialog1');
$(document).ready(function(){
    $iosDialog2.fadeIn(200);
})
function pay(){
	var type = $("input[type='radio'][name='radio1']:checked").val();
	if(type==1){
		window.location.href="${ctx}/cashWechat/cash?roomId=${room.dbid }&startWritingId=${startWriting.dbid}&memberId=${member.dbid}";
	}
	if(type==2){
		window.location.href="${ctx}/cashWechat/onceCash?roomId=${room.dbid }&startWritingId=${startWriting.dbid}&memberId=${member.dbid}";
	}
	
}
</script>
</html>