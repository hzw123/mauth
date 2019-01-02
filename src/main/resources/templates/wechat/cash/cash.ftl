<#include "../../commons/macro.ftl">
<@commonHead/>
<title>${room.name}收银</title>
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
<form class="layui-form" action="${ctx}/cashWechat/saveCash" id="frmId" name="frmId" method="post" target="_self">
<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
<input type="hidden" id="money" name="money" value="${startWriting.money }">
<input type="hidden" id="acturePrice" name="acturePrice" value="${startWriting.money }">
<input type="hidden" id="startWritingId" name="startWritingId" value="${startWriting.dbid }">
<input type="hidden" id="discountItemJson" name="discountItemJson" value="">
<input type="hidden" name="payWayId" id="payWayId" value="5" >
<div class="container" id="container">
	<div class="weui-form-preview">
	      <div class="weui-form-preview__hd">
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">会员编号</label>
	              <em class="weui-form-preview__value">${member.memberCardNo }</em>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">会员卡</label>
	              <em class="weui-form-preview__value">${member.memberCard.name }</em>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">余额</label>
	              <em class="weui-form-preview__value">${member.balance }</em>
	          </div>
	      </div>
	</div>
	<br>
	<% int gindex=1; %>
	<% int oindex=1; %>
	<c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
		<input type="hidden" id="startWritingItemId" name="startWritingItemId" value="${startWritingItem.dbid }" >
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
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">折扣</label>
                    <span class="weui-form-preview__value">${startWritingItem.discountMoney }</span>
                </div>
                 <div class="weui-form-preview__item">	
		            <div class="weui-cell weui-cell_select">
		                <label for="" class="weui-label" style="width:20px;float: left;margin-right: 1em; min-width: 4em;color: #808080;text-align: justify; text-align-last: justify;">优惠</label>
		                	<div class="weui-cell__bd">
			                  <select class="weui-select" lay-filter="discount" data-oldDiscountType="1" data-oldForginId="<%=gindex++ %>" data-key="1_${startWritingItem.itemId}" data-price="${startWritingItem.money}" data-cnt="${startWritingItem.num}" data-type='1' data-dbid="${startWritingItem.dbid }">
								<option disabled selected value style="display:none"></option>
								<!-- 会员卡赠送 -->
								<c:if test="${!empty(memberCard)}" var="memberCardStatus">
									<c:if test="${(!empty(memberCardDisItems)&&fn:length(memberCardDisItems)>0)}" var="memdisStatus">
										<c:forEach var="memberCardDisItem" items="${memberCardDisItems }">
											<c:if test="${startWritingItem.itemId == memberCardDisItem.itemId}">
												<option data-index=<%=oindex++ %> lay-filter="discount"
													data-discountType="3" data-point="10"
													data-forginId="${memberCardDisItem.index}"
													data-totalCnt="${memberCardDisItem.num}"
													data-cnt= "0"
													data-money="10000"
													data-vipprice="0" 
													data-price="${startWritingItem.money}"
													data-vipprice="0" data-num="${startWritingItem.num}">会员卡赠送
												</option>
											</c:if>
										</c:forEach>
									</c:if>
								</c:if>
								<!--优惠券-->
								<c:if test="${(!empty(couponMemberTemplateItems)&&fn:length(couponMemberTemplateItems)>0)}" var="couponStatus">
									<c:forEach var="userCoupon" items="${couponMemberTemplateItems }">
										<c:if test="${startWritingItem.itemId == userCoupon.itemId}">
											<option data-index=<%=oindex++ %> lay-filter="discount"
												data-discountType="${userCoupon.type eq 1?'6':'5'}"
												data-point="10"
												data-forginId="${userCoupon.dbid}"
												data-totalCnt="${userCoupon.remainder}"
												data-cnt= "0"
												data-money="${userCoupon.type == 2?10000:userCoupon.price}"
												data-vipprice="0" data-price="${startWritingItem.money}"
												data-num="${startWritingItem.num}">${userCoupon.type == 1?'代金券':'免费券'}${userCoupon.price }</option>
										</c:if>
									</c:forEach>
								</c:if>
								<!--次卡-->
								<c:if test="${!empty(stormMoneyOnceEntItemCards)&&fn:length(stormMoneyOnceEntItemCards)>0 }" var="onceStatus">
									<c:forEach var="stormMoneyOnceEntItemCard" items="${stormMoneyOnceEntItemCards }">
										<c:if test="${startWritingItem.itemId == stormMoneyOnceEntItemCard.item.dbid }">
											<option data-index=<%=oindex++ %> lay-filter="discount"
												data-discountType="4" 
												data-point="10"
												data-forginId="${stormMoneyOnceEntItemCard.dbid }"
												data-totalCnt= "${stormMoneyOnceEntItemCard.remainder}"
												data-cnt= "0"
												data-money="10000" data-price="${startWritingItem.money}"
												data-vipprice="0" data-num="${startWritingItem.num}">次卡[剩余:${stormMoneyOnceEntItemCard.remainder}次]
											</option>
										</c:if>
									</c:forEach>
								</c:if>
								<!-- 会员卡折扣 -->
								<c:if test="${!empty(memberCard)&&memberCard.discount<10&&!empty(memberCardDiscountItems)&&fn:length(memberCardDiscountItems)>0 }">
									<c:forEach var="cardDiscountItem" items="${memberCardDiscountItems }">
										<c:if test="${startWritingItem.itemId == cardDiscountItem.itemId}">
											<option data-index=<%=oindex++ %> lay-filter="discount"
												data-discountType="2" 
												data-point="${memberCard.discount }"
												data-forginId="<%=gindex++ %>" 
												data-totalCnt= "0"
												data-cnt="0"
												data-money="0" 
												data-price="${startWritingItem.money}"
												data-vipprice="0" 
												data-num="${startWritingItem.num}">${memberCard.name }${memberCard.discount }折
											</option>
										</c:if>
										</c:forEach>
										
									</c:if>
									
								<!-- 固定折扣 -->
								<c:if test="${!empty(fixedDiscountItems)&&fn:length(fixedDiscountItems)>0 }">
									<c:forEach var="fixedDiscountItem" items="${fixedDiscountItems }">
										<c:if test="${startWritingItem.itemId == fixedDiscountItem.itemId}">
											<option data-index=<%=oindex++ %> lay-filter="discount"
												data-discountType="10" 
												data-point="${fixedDiscountItem.fixedDiscount }"
												data-forginId="<%=gindex++ %>" 
												data-totalCnt= "0"
												data-cnt="0"
												data-money="${startWritingItem.num}" 
												data-price="${startWritingItem.money}"
												data-vipprice="0" 
												data-num="${startWritingItem.num}">${fixedDiscountItem.itemName }${fixedDiscountItem.fixedDiscount }折</option>
										</c:if>
									</c:forEach>
								</c:if>

									<!-- 会员价 -->
								<c:if test="${!empty(vippriceItems)&&fn:length(vippriceItems)>0 }">
									<c:forEach var="vipItem" items="${vippriceItems }">
										<c:if test="${startWritingItem.itemId == vipItem.itemId}">
											<option data-index=<%=oindex++ %> lay-filter="discount"
												data-discountType="9" data-point="10"
												data-forginId="<%=gindex++ %>" 
												data-totalCnt= "10000"
												data-cnt="0"
												data-money="10000" 
												data-vipprice="${vipItem.vipprice }"
												data-price="${startWritingItem.money}"
												data-num="${startWritingItem.num}">会员价${vipItem.vipprice}</option>
										</c:if>
									</c:forEach>
								</c:if>
									<!-- 无优惠 -->
								<option data-index=<%=oindex++ %> data-discountType="1"
									data-point="10" 
									data-forginId="<%=gindex++ %>"
									data-totalCnt="0" 
									data-cnt="0" 
									data-money="0"
									data-price="${startWritingItem.money}" 
									data-vipprice="0"
									data-num="${startWritingItem.num}">无优惠
								</option>
							</select>
		                </div>
		            </div>
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
          <p id="itemTotalPrice" class="weui-tabbar__label" style="line-height: 40px;font-size: 14px;text-align: right;padding-right: 12px;">合计：￥${startWriting.money }</p>
      </a>
      <a href="javascript:;" onclick="conf()" class="weui-tabbar__item  weui-btn_primary" style="height: 40px;line-height: 40px;font-size: 20px;color: white;">
         结算
      </a>
  </div>
  <div class="js_dialog" id="iosDialog1" style="display: none;">
       <div class="weui-mask"></div>
       <div class="weui-dialog">
           <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
           <div class="weui-dialog__bd">当前订单待付款金额：<span style="color: red;font-size: 18px;" id="showItemTotalPrice">￥${startWriting.money }</span></div>
           <div class="weui-dialog__bd">付款方式：会员卡余额支付</div>
           <div class="weui-dialog__ft">
               <a href="javascript:$('#iosDialog1').hide()" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
               <a href="javascript:" onclick="$('#frmId')[0].submit()" class="weui-dialog__btn weui-dialog__btn_primary">确定付款</a>
           </div>
       </div>
   </div>
</form>
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
        calcTotalPrice();
        $("select[lay-filter='discount']").on("change",function(){
    		calcTotalPrice();
        });
    });

    function calcTotalPrice(){
    	var items = $("select[lay-filter='discount']");
			var customPrice = 0.0; //项目，商品折扣前的金额
			var totalPrice = 0; //实际支付的金额
			var success=true;
			var discountItemJson = "[";
			$(items).each(function(i) {
				var oldDiscountType = $(this).attr("data-olddiscounttype");//上一次选中的优惠方式
				var oldForginId = $(this).attr("data-oldforginId"); //上一次优惠方式的forginid
				var selected = $(this).find("option:selected");
				var type = $(this).attr("data-type"); //商品项目类型
				var dbid = $(this).attr("data-dbid"); //订单商品Id/订单项目ID

				if ($(selected).prop("disabled")) {
					selected = $(this).find("option:enabled").first()
					selected.attr('selected', 'selected');
				}

				var itemPrice = 0.0; //项目、商品折扣后的金额
				var discountPrice = 0.0;  //折扣金额
				var oindex =parseInt(selected.attr("data-index"));//获取当前选项的全局index
				var point = selected.attr("data-point"); //折扣
				var price = selected.attr("data-price"); //商品/项目价格
				var money = selected.attr("data-money"); //优惠券金额
				var totalCnt=selected.attr("data-totalCnt");  //优惠券的总数量
				var cnt = parseInt(selected.attr("data-cnt")); //使用优惠券数量
				var num = parseInt(selected.attr("data-num")); //消费数量
				var vipprice = selected.attr("data-vipprice");//会员价
				var discountType = selected.attr("data-discountType"); //订单商品Id/订单项目ID
				var forginId = selected.attr("data-forginId"); //代金券ID/次卡ID/免费券Id

				$("option[data-discountType='" + oldDiscountType+ "'][data-forginId='" + oldForginId+ "']").removeAttr('disabled');
				
				var selectedItems= $("option:selected[data-discountType='" + discountType+ "'][data-forginId='" + forginId+ "']");
				var selectedItemNum=0;  //选中的数量
				var otherSelectedItemNum=0;//其它选中的项
				var otherSelectedItemCnt=0; //其它使用的数量
				$(selectedItems).each(function(i,element){
					var itemNum=$(element).attr("data-num");  //消费数量
					var itemCnt=$(element).attr("data-cnt");  //选用优惠券的数量
					selectedItemNum+=parseInt(itemNum);
					var eoindex=parseInt($(element).attr("data-index"));
					if(eoindex!=oindex){
						otherSelectedItemNum+=parseInt(itemNum);
						otherSelectedItemCnt+=parseInt(itemCnt);
					}	
				});
				
				if(selectedItemNum>0&& selectedItemNum>=totalCnt){
					$("option:not(:selected)[data-discountType='" + discountType+ "'][data-forginId='" + forginId+ "']").attr("disabled", "disabled");
				}
				
	 			if(otherSelectedItemNum>0&&otherSelectedItemNum>totalCnt){
					$(selected).attr("disabled", "disabled");
					calcTotalPrice();
					success=false;
					return false;
				} 				
				cnt=totalCnt-otherSelectedItemCnt;
				cnt=cnt>num?num:cnt;
				selected.attr('data-cnt', cnt);
				
				//折扣金额
				var cost = (num - cnt) * price * (point / 10.0);
				cost = cost < 0 ? 0 : cost;
				//使用优惠券后的金额
				var couponCost = 0.0;
				couponCost = (price - money) * cnt;
				couponCost = couponCost < 0 ? 0.0 : couponCost;				
				var vipCost=0.0;
				vipCost=vipprice*num;
				vipCost=vipCost<0?0.0:vipCost;
				//消费金额
				customPrice += price * num;
				//实付金额
				itemPrice = parseInt(cost + couponCost+vipCost + 0.5);	
				//折扣金额
				discountPrice = price * num - itemPrice;				
				totalPrice = totalPrice + itemPrice;
				//consoleconsole.info( "totalCnt:"+totalCnt+",num:"+num+",cnt:"+cnt+",price:"+price+",point:"+point+",money:"+money+",vipprice:"+vipprice);
				var tr = $(this).parent().parent();
				$(tr).children('td').eq(5).text(discountPrice);
				$(tr).children('td').eq(6).text(itemPrice);
				var tr=$(this).parent().parent().parent().prev().find(".weui-form-preview__value");
	    		$(tr).text(discountPrice);
				discountItemJson = discountItemJson + '{"dbid":' + dbid
						+ ',"type":' + type + ',"discountType":'
						+ discountType + ',"point":' + point
						+ ',"price":' + price + ',"money":' + money
						+ ',"cnt":' + cnt + ',"num":' + num
						+ ',"forginId":' + forginId + ',"vipprice":'+vipprice+'},';
				$(this).attr("data-olddiscounttype", discountType);
				$(this).attr("data-oldforginid", forginId);
			});
			
 			if(!success){
				return false;
			} 
			
			discountItemJson = discountItemJson.substring(0,(discountItemJson.length - 1))
			discountItemJson = discountItemJson + "]";

    	$("#discountItemJson").val(discountItemJson);
    	$("#itemTotalPrice").text("￥"+totalPrice);
    	$("#showItemTotalPrice").text("￥"+totalPrice);
    	$("#money").val(totalPrice);
    	$("#acturePrice").val(totalPrice);
    	$("#submitBut").text("确定收银（￥"+totalPrice+")");
    }
    function conf(){
    	var balance="${member.balance}";
    	if(balance==''||balance==undefined){
    		balance=0;
    	}
    	balance=parseInt(balance);
    	var totalPrice=$("#money").val();
    	if(totalPrice==''||totalPrice==undefined){
    		totalPrice=0;
    	}
    	totalPrice=parseInt(totalPrice);
    	if(balance<totalPrice){
    		alert("余额不足，不能付款");
    		return ;
    	}
    	var $iosDialog2 = $('#iosDialog1');
    	 $iosDialog2.fadeIn(200);
    }
</script>
</html>