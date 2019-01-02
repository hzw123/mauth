<#include "../../commons/macro.ftl">
<@commonHead/>
<title>${room.name}收银</title>
<link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
</head>
<body>
<form class="layui-form" id="frmId" name="frmId" method="post" target="_parent">
<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
<input type="hidden" id="startWritingId" name="startWritingId" value="${startWriting.dbid }">
<input type="hidden" id="discountItemJson" name="discountItemJson" value="">
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
	<c:if test="${(member.startBalance)>=startWriting.money}">
		<div class="weui-form-preview">
		      <div class="weui-form-preview__hd">
		          <div class="weui-form-preview__item">
		              <label class="weui-form-preview__label">创始会员卡</label>
		              <em class="weui-form-preview__value">${member.startMemberCardNo }</em>
		          </div>
		          <div class="weui-form-preview__item">
		              <label class="weui-form-preview__label">会员卡</label>
		              <em class="weui-form-preview__value">${member.startMemberCard.name }</em>
		          </div>
		          <div class="weui-form-preview__item">
		              <label class="weui-form-preview__label">余额</label>
		              <em class="weui-form-preview__value">${member.startBalance}</em>
		          </div>
		      </div>
		</div>
		<a href="javascript:;" class="weui-btn weui-btn_primary">创始会员卡支付</a>
	 </c:if>
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
			                  <select class="weui-select" lay-filter="discount" data-oldDiscountType="1" data-oldForginId="<%=gindex++ %>" data-key="1_${startWritingItem.item.dbid}" data-price="${startWritingItem.money}" data-cnt="${startWritingItem.num}" data-type='1' data-dbid="${startWritingItem.dbid }">
					        	<option data-index=<%=oindex++ %> data-discountType="1" data-point="10" data-forginId="<%=gindex %>" data-cnt="0" data-money="0" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}" selected>无优惠</option>
								<c:if test="${!empty(memberCard)}" var="memberCardStatus">
							  		<c:if test="${(!empty(memberCardDisItems)&&fn:length(memberCardDisItems)>0)}" var="memdisStatus">
									    <c:forEach var="memberCardDisItem" items="${memberCardDisItems }">
									    	<c:if test= "${startWritingItem.item.dbid == memberCardDisItem.itemId}">
					        					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="3" data-point="10" data-forginId="${memberCardDisItem.index}" data-cnt="0" data-money="10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">会员卡赠送</option>
				        					</c:if>
									    </c:forEach>
								    </c:if>
						     	</c:if>
						     	
						     	<c:if test="${(!empty(couponMemberTemplateItems)&&fn:length(couponMemberTemplateItems)>0)||(!empty(couponMemberTemplateProducts)&&fn:length(couponMemberTemplateProducts)>0) }" var="couponStatus">
								    <c:forEach var="couponMemberTemplateItem" items="${couponMemberTemplateItems }">
									    <c:if test= "${startWritingItem.item.dbid == couponMemberTemplateItem.itemId}">
									    <c:set value="${couponMemberTemplateItem.couponMemberTemplate }" var="couponMemberTemplate"></c:set>
								    	 <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${couponMemberTemplate.type eq 1?'6':'5'}" data-point="10" data-forginId="${couponMemberTemplate.dbid}" data-cnt="1" data-money= "${couponMemberTemplate.type == 2?1000:couponMemberTemplate.price}" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${couponMemberTemplate.type == 1?'代金券':'免费券'}${couponMemberTemplate.price }</option>
							    	</c:if>
								    </c:forEach>
			     				</c:if>
			     				
			     				<c:if test="${!empty(stormMoneyOnceEntItemCards)&&fn:length(stormMoneyOnceEntItemCards)>0 }" var="onceStatus">
							    	 <c:forEach var="stormMoneyOnceEntItemCard" items="${stormMoneyOnceEntItemCards }">
								    	 <c:if test="${startWritingItem.item.dbid == stormMoneyOnceEntItemCard.item.dbid }" >
										    <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="4" data-point="10" data-forginId="${stormMoneyOnceEntItemCard.dbid }" data-cnt="${stormMoneyOnceEntItemCard.num-stormMoneyOnceEntItemCard.consumpiontNum}" data-money="10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">次卡[剩余: ${stormMoneyOnceEntItemCard.num-stormMoneyOnceEntItemCard.consumpiontNum}次]</option>
									     </c:if>
							    	 </c:forEach>
			     				</c:if>
			     				<c:forEach var="discountType" items="${discountTypes }">
			     					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${discountType.dbid }" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="${startWritingItem.num}" data-money= "10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${discountType.name }</option>
				        		</c:forEach>
			     				
			     				<c:if test="${!empty(memberCard)&&memberCard.discount<10 }" var="memDiscStatus">
								    <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="2" data-point="${memberCard.discount }" data-forginId="<%=gindex++ %>" data-cnt="0" data-money= "10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${memberCard.name }${memberCard.discount }折</option>
			     				</c:if>
				        	</select>
		                </div>
		            </div>
		        </div>
            </div>
        </div>
    </c:forEach>
	 <c:forEach var="startWritingProduct" items="${startWritingProducts }" varStatus="i">
	 <input type="hidden" id="startWritingProductId" name="startWritingProductId" value="${startWritingProduct.dbid }" >
		<div class="weui-form-preview">
            <div class="weui-form-preview__bd" style="margin-bottom: 5px;">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品/项目</label>
                    <span class="weui-form-preview__value">${startWritingProduct.prodcutName } </span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">金额</label>
                    <span class="weui-form-preview__value">￥<span style="color: red;">${startWritingProduct.money }</span> x ${startWritingProduct.num }</span>
                    <span class="weui-form-preview__value"></span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">折扣</label>
                    <span class="weui-form-preview__value">${startWritingProduct.discountMoney }</span>
                </div>
	            <div class="weui-form-preview__item">	
		              	<div class="weui-cell weui-cell_select">
		                <label for="" class="weui-label" style="width:20px;float: left;margin-right: 1em; min-width: 4em;color: #808080;text-align: justify; text-align-last: justify;">优惠</label>
		                <div class="weui-cell__bd">
		                   <select class="weui-select" lay-filter="discount"  data-oldDiscountType="1" data-oldForginId="<%=gindex++ %>" data-key="2_${startWritingProduct.product.dbid}" data-price="${startWritingProduct.money}" data-cnt="${startWritingProduct.num}" data-type='2' data-dbid="${startWritingProduct.dbid }">
			        		<option data-index=<%=oindex++ %>  data-discountType="1" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="0" data-money="<%=gindex++ %>" data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}" selected>无优惠</option>
							<c:if test="${!empty(memberCard)}" var="memberCardStatus">
						  		<c:if test="${(!empty(memberCardDisProducts)&&fn:length(memberCardDisProducts)>0) }" var="memdisStatus">
								    <c:forEach var="memberCardDisProduct" items="${memberCardDisProducts }">
								    	<c:if test= "${startWritingProduct.product.dbid == memberCardDisProduct.productId}">
				        					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="3" data-point="10" data-forginId="${memberCardDisProduct.index}" data-cnt="1" data-money="10000"  data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}" >会员卡赠送</option>
			        					</c:if>
					    	 </c:forEach>
							    </c:if>
					     	</c:if>
					     	
					     	<c:if test="${(!empty(couponMemberTemplateProducts)&&fn:length(couponMemberTemplateProducts)>0) }" var="couponStatus">
							    <c:forEach var="couponMemberTemplateProduct" items="${couponMemberTemplateProducts }">
								    <c:if test= "${startWritingProduct.product.dbid == couponMemberTemplateProduct.productId}">
								    <c:set value="${couponMemberTemplateProduct.couponMemberTemplate }" var="couponMemberTemplate"></c:set>
							    	 <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${couponMemberTemplate.type eq 1?'6':'5'}" data-point="10" data-forginId="${couponMemberTemplate.dbid}" data-cnt="1" data-money="${couponMemberTemplate.price == 0?1000:couponMemberTemplate.price}"  data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}" >${couponMemberTemplate.type eq 1?'代金券':'免费券'} ${couponMemberTemplate.price}</option>
						    	</c:if>
							    </c:forEach>
		     				</c:if>
		     				<c:forEach var="discountType" items="${discountTypes }">
		     					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${discountType.dbid }" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="${startWritingProduct.num }" data-money= "10000"  data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}" >${discountType.name }</option>
			        		</c:forEach>
			        		
			        		<c:if test="${!empty(memberCard)&&memberCard.discount<10 }" var="memDiscStatus">
							    <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="2" data-point="${memberCard.discount }" data-forginId="<%=gindex++ %>" data-cnt="0" data-money= "10000" data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}">${memberCard.name }${memberCard.discount }折</option>
		     				</c:if>
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
          <p class="weui-tabbar__label" style="line-height: 40px;font-size: 14px;text-align: right;padding-right: 12px;">合计：￥${startWriting.money }</p>
      </a>
      <a href="javascript:;" class="weui-tabbar__item  weui-btn_primary" style="height: 40px;line-height: 40px;font-size: 20px;color: white;">
         结算
      </a>
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
    });
    function ji(){
		var option=$(data.elem).find("option:selected");
		
		var discountType=$(option).attr("data-discountType");
		var forginId= $(option).attr("data-forginId");
		var oindex=$(option).attr("data-index");
		var oldDiscountType=$(data.elem).attr("data-olddiscounttype");
		var oldForginId=$(data.elem).attr("data-oldforginid");
		
		if(discountType!=oldDiscountType||forginId!=oldForginId){
			var tmp= $("option[data-discountType='"+discountType+"'][data-forginId='"+forginId+"'][data-index!='"+oindex+"']").attr("disabled","disabled");
			var tmp= $("option[data-discountType='"+oldDiscountType+"'][data-forginId='"+oldForginId+"']").removeAttr('disabled');
			form.render();
			$(data.elem).attr("data-olddiscounttype",discountType);
			$(data.elem).attr("data-oldforginid",forginId);
		}
		calcTotalPrice();
    }
    function calcTotalPrice(){
    	var items=$("select[lay-filter='discount']");
    	var totalPrice=0;
    	var itemPrice=0.0;
    	var discountPrice=0.0;
    	var discountItemJson="[";
    	$(items).each(function (i) {
    		var selected= $(this).find("option:selected");
    		var point=selected.attr("data-point"); //折扣
    		var price=selected.attr("data-price");  //商品/项目价格
    		var money=selected.attr("data-money");  //优惠券金额
    		var cnt=selected.attr("data-cnt");  //优惠券数量
    		var num=selected.attr("data-num");  //消费数量
    		var discountType=selected.attr("data-discountType");  //订单商品Id/订单项目ID
    		var forginId=selected.attr("data-forginId");  //代金券ID/次卡ID/免费券Id
    		var type=$(this).attr("data-type");  //商品项目类型
    		var dbid=$(this).attr("data-dbid");  //订单商品Id/订单项目ID
    		
    		var cost=0.0;
    		cost=(num-cnt)*price*(point/10.0);
    		cost=cost<0?0:cost;
    		
    		var subCost=0.0;
    		subCost=(price-money)*cnt;
    		subCost=subCost<0?0.0:subCost;
    		
    		itemPrice=parseInt(cost+subCost+0.5);
    		discountPrice=price*num-itemPrice;
    		
    		totalPrice=totalPrice+itemPrice;
    		var tr=$(this).parent().parent();
    		$(tr).children('td').eq(5).text(discountPrice);
    		$(tr).children('td').eq(6).text(itemPrice);
    		console.log(totalPrice);
    		discountItemJson=discountItemJson+'{"dbid":'+dbid+',"type":'+type+',"discountType":'+discountType+',"point":'+point+',"price":'+price+',"money":'+money+',"cnt":'+cnt+',"num":'+num+',"forginId":'+forginId+'},';
    	 });
    	discountItemJson=discountItemJson.substring(0,(discountItemJson.length-1))
    	discountItemJson=discountItemJson+"]";
    	$("#discountItemJson").val(discountItemJson);
    	$("#itemTotalPrice").text("￥"+totalPrice);
    	$("#money").val(totalPrice);
    	$("#acturePrice").val(totalPrice);
    	$("#submitBut").text("确定收银（￥"+totalPrice+")");
    }
</script>
</html>