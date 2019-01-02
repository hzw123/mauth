<#include "../../commons/macro.ftl">
<@commonHead/>
<title>${room.name }收银</title>
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
<form class="layui-form" action="${ctx}/cashWechat/saveOnceCash" id="frmId" name="frmId" method="post" target="_self">
<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
<input type="hidden" id="startWritingId" name="startWritingId" value="${startWriting.dbid }">
<input type="hidden" id="discountItemJson" name="discountItemJson" >
<input type="hidden" id="money" name="money" >
<input type="hidden" name="payWayId" id="payWayId" value="6" >
<div class="container" id="container">
	<div class="weui-form-preview">
		<div class="weui-form-preview__hd">
		   <div class="weui-form-preview__item">
		      <label class="weui-form-preview__label">创始会员卡</label>
		      <em class="weui-form-preview__value">余额:${member.startBalance}</em>
		   </div>
		</div>
	</div>
	<br>
	<c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
		<input type="hidden" id="startWritingItemId" name="startWritingItem" data-money=${startWritingItem.money} data-num="${startWritingItem.num}" value="${startWritingItem.dbid }" >
		<div class="weui-form-preview">
            <div class="weui-form-preview__bd" style="margin-bottom: 5px;">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品/项目</label>
                    <span class="weui-form-preview__value">${startWritingItem.itemName } </span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">金额</label>
                    <span class="weui-form-preview__value">￥<span style="color: red;">${startWritingItem.money }</span> x ${startWritingItem.num }</span>
                    <span class="weui-form-preview__value"></span>
                </div>
            </div>
        </div>
    </c:forEach>
    <br>
    <br>
    <br>
    
</div>
<div class="weui-tabbar" style="position: fixed;">
      <a href="javascript:;" class="weui-tabbar__item">
          <p class="weui-tabbar__label" style="line-height: 40px;font-size: 14px;text-align: right;padding-right: 12px;">合计：￥${startWriting.money }</p>
      </a>
      <a href="javascript:;" onclick="conf()" class="weui-tabbar__item  weui-btn_primary" style="height: 40px;line-height: 40px;font-size: 20px;color: white;">
         结算
      </a>
  </div>
</form>

<div class="js_dialog" id="iosDialog1" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
                <div class="weui-dialog__bd">当前订单待付款金额：<span style="color: red;font-size: 18px;">￥${startWriting.money }</span></div>
                <div class="weui-dialog__bd">付款方式：创始会员卡支付</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:$('#iosDialog1').hide()" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
                    <a href="javascript:" onclick="$('#frmId')[0].submit()" class="weui-dialog__btn weui-dialog__btn_primary">确定付款</a>
                </div>
            </div>
        </div>

</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctx}/widgets/jquery.min.js"></script>
<script src="${ctx}/weui/example/zepto.min.js"></script>
<script src="${ctx}/weui/example/router.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('.weui-tabbar__item').on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
        });
        
        var discountItemJson="[";
        var money=0;
        var items = $("input[name='startWritingItem']");
        $(items).each(function(i) {
        	var itemId = $(this).val();//上一次选中的优惠方式
        	var price = $(this).attr("data-money"); 
        	var num = $(this).attr("data-num"); 
        	money=money+price*num;
        	discountItemJson = discountItemJson + '{"dbid":' + itemId
    		+ ',"type":1,"discountType":1,"point":10,"price":' + price + ',"money":0,"cnt":0,"num":' + num
    		+ ',"forginId":0,"vipprice":0},';
        });
        discountItemJson = discountItemJson.substring(0,(discountItemJson.length - 1))
		discountItemJson = discountItemJson + "]";
        $("#discountItemJson").val(discountItemJson);
        $("#money").val(money);
    });
    function conf(){
    	var $iosDialog2 = $('#iosDialog1');
    	 $iosDialog2.fadeIn(200);
    }
</script>
</html>