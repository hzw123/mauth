<#include "../../commons/macro.ftl">
<@commonHead/>
<title>评价打赏</title>
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
	<form class="layui-form" action="${ctx}/cashWechat/saveTip" id="frmId" name="frmId" method="post" target="_self">
		<input type="hidden" name="startWritingId" value="${startWriting.dbid }"/>
		<div class="container" id="container">
			<div class="weui-cells weui-cells_form">
	            <div class="weui-cell" >
	                <div class="weui-cell__hd"><label class="weui-label">评价</label></div>
	                <div class="weui-cell__bd">
	                    <div style="float: left;width: 32%;">
	                    	<label><input type="radio" checked="checked" name="startWriting.evaluate" value="3" style="float:left;margin-top: 4px;"/>好评</label>
	                    </div>
	                    <div style="float: left;width: 32%;">
	                    	<label><input type="radio" name="startWriting.evaluate" value="2" style="float:left;margin-top: 4px;"/>中评</label>
	                    </div>
	                    <div style="float: left;width: 32%;">
	                    	<label><input type="radio" name="startWriting.evaluate" value="1" style="float:left;margin-top: 4px;"/>差评</label>
	                    </div>
	                    <div style="clear: both;"></div>
	                </div>
	            </div>
	            <div class="weui-cell" >
	                <div class="weui-cell__hd"><label class="weui-label">打赏</label></div>
	                <div class="weui-cell__bd">
	                	<input class="weui-input" id="tip" type="number" name="startWriting.tipMoney" value="" placeholder="打赏金额"/>
	                </div>
	            </div>
	         </div>
            <div class="weui-cells__tips">打赏金额将从会员卡余额中扣除</div>
            <br>
            <div class="weui-cells weui-cells_form" style="margin-top: 0;">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea name="startWriting.note" id="doctorIntroduction" class="weui-textarea" placeholder="请写下对本次服务的感受吧" rows="3"></textarea>
					</div>
				</div>
            </div>
            <br>
            <br>
            <br>
            <a href="javascript:;" onclick="if(vali()){$('#frmId')[0].submit()}"  class="weui-btn weui-btn_primary">评价</a>
		</div>
	</form>
       <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="${ctx}/widgets/jquery.min.js"></script>
		<script src="${ctx}/weui/example/zepto.min.js"></script>
		<script src="${ctx}/weui/example/router.min.js"></script>
		<script type="text/javascript">
			function vali(){
				var tip=$("#tip").val();
				if(tip==""){
					return true;
				}
				if(tip<0){
					alert("打赏金额不能小于0");
					$("#tip").foucs();
					return false;
				}
				if(tip>10000){
					alert("打赏金额太多了，消受不起");
					$("#tip").foucs();
					return false;
				}
				return true;
			}
		</script>
	</body>

</html>