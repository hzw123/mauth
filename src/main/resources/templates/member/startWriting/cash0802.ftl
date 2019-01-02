<#include "../../commons/macro.ftl">
<@commonHead/>
    <title>收银</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${ctx}/widgets/auto/jquery.autocomplete.css" />
    <style type="text/css">
    	.grid-demo{
    		line-height: 30px;
    	}
    	.layui-table th{
    		text-align: center;
    	}
    	.layui-table td{
    		padding: 5px 5px;
    		text-align: center;
    	}
    </style>
</head>
<body>
<div class="layui-container" style="width:95%;line-height:40px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
   	<button class="layui-btn layui-btn-xs" data-method="changeMember">更换会员</button>
	<button class="layui-btn layui-btn-xs" onclick="detail()">充值</button>
</div>
<div class="layui-container" style="line-height:46px;border:1px solid #ddd;font-size:16px;border-radius:5px;margin: 0px auto;margin-top:20px;;">
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员编号:</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="no">${member.no }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="memberName">${member.name }</span>  <span id="sex">${member.sex }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center"><span id="mobilePhone">${member.mobilePhone }</span></div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	生日
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">
                	<span id="birthday">${member.birthday}</span>
                </div>
            </div>
        </div>
        <div class="layui-row" style="">
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">充值总额:
                	<span id="totalStormMoney">${member.totalStormMoney }</span>
                </div>
            </div>
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">余额:
                	<span id="balance">${member.balance }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费次数：
                	<span id="totalBuy">${member.totalBuy }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费总额：
                	<span id="totalMoney">${member.totalMoney }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">剩余积分：
                	<span id="overagePiont">${member.overagePiont }</span>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">消费积分：
                	<span id="consumpiontPoint">${member.consumpiontPoint }</span>
               </div>
            </div>
        </div>
    </div>
<div class="layui-container">
	<form class="layui-form" id="frmId" name="frmId" method="post" target="_parent">
		<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
		<input type="hidden" id="startWritingId" name="startWritingId" value="${startWriting.dbid }">
		<input type="hidden" id="discountItemJson" name="discountItemJson" value="">
		<div class="layui-row" style="margin-top: 12px;">
		    <div class="layui-col-xs12" >
、		      <table class="layui-table" id="itemTable">
			    <colgroup>
			      <col width="150">
			      <col width="150">
			      <col width="100">
			      <col width="100">
			      <col width="50">
			      <col width="50">
			      <col width="50">
			      <col width="50">
			    </colgroup>
			    <thead>
			      <tr>
			        <th>项目/产品</th>
			        <th>技师</th>
			        <th>优惠</th>
			        <th>单价</th>
			        <th>数量</th>
			        <th>折扣</th>
			        <th>金额</th>
			      </tr> 
			    </thead>
			    <tbody>
			    <% int gindex=1; %>
			    <% int oindex=1; %>
			    <c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
			      <tr id="entItemTr${i.index+1  }">
			        <td>
			        	<input type="hidden" id="startWritingItemId" name="startWritingItemId" value="${startWritingItem.dbid }" >
			        	${startWritingItem.itemName }  <!-- 项目名称 -->
			        </td>
			        <td>
			        	${startWritingItem.artificerName }  <!-- 技师名称 -->
			        </td>
			        <td>
			        	<select lay-filter="discount" data-oldDiscountType="1" data-oldForginId="<%=gindex++ %>" data-key="1_${startWritingItem.item.dbid}" data-price="${startWritingItem.money}" data-cnt="${startWritingItem.num}" data-type='1' data-dbid="${startWritingItem.dbid }">
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
							    	 <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${couponMemberTemplateItem.type eq 1?'6':'5'}" data-point="10" data-forginId="${couponMemberTemplateItem.dbid}" data-cnt="1" data-money= "${couponMemberTemplateItem.type == 2?1000:couponMemberTemplateItem.price}" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${couponMemberTemplateItem.type == 1?'代金券':'免费券'}${couponMemberTemplateItem.price }</option>
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
		     				<c:if test="${!empty(memberCard)&&memberCard.discount<10 }" var="memDiscStatus">
							    <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="2" data-point="${memberCard.discount }" data-forginId="<%=gindex++ %>" data-cnt="0" data-money= "10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${memberCard.name }${memberCard.discount }折</option>
		     				</c:if>
		     				<c:forEach var="discountType" items="${discountTypes }">
		     					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${discountType.dbid }" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="${startWritingItem.num}" data-money= "10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${discountType.name }</option>
			        		</c:forEach>
		     				<option data-index=<%=oindex++ %> data-discountType="1" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="0" data-money="0" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">无优惠</option>
			        	</select>
			        </td>
			        <td>
			        	${startWritingItem.money }
			        </td>
			        <td>
			        	${startWritingItem.num }
			        </td>
			        <td>
			        	${startWritingItem.discountPrice }
			        </td>
			        <td>
			        	${startWritingItem.num*startWritingItem.money }
			        </td>
			      </tr>
			    </c:forEach>
			    <c:forEach var="startWritingProduct" items="${startWritingProducts }" varStatus="i">
			      <tr id="entItemTr${i.index+1  }">
			        <td>
			        	<input type="hidden" id="startWritingProductId" name="startWritingProductId" value="${startWritingProduct.dbid }" >
			        	${startWritingProduct.prodcutName }   <!-- 名称 -->
			        </td>
			        <td>
			        	${startWritingProduct.artificerName }  <!-- 技师名称 -->
			        </td>
			        <td>
			        	<select lay-filter="discount"  data-oldDiscountType="1" data-oldForginId="<%=gindex++ %>" data-key="2_${startWritingProduct.product.dbid}" data-price="${startWritingProduct.money}" data-cnt="${startWritingProduct.num}" data-type='2' data-dbid="${startWritingProduct.dbid }">
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
								    <c:if test= "${startWritingProduct.product.dbid == couponMemberTemplateProduct.itemId}">
							    	 <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${couponMemberTemplateProduct.type eq 1?'6':'5'}" data-point="10" data-forginId="${couponMemberTemplateProduct.dbid}" data-cnt="1" data-money="${couponMemberTemplateProduct.type eq 1?couponMemberTemplateProduct.price:1000}"  data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}" >${couponMemberTemplateProduct.type eq 1?'代金券':'免费券'} ${couponMemberTemplateProduct.price}</option>
						    	</c:if>
							    </c:forEach>
		     				</c:if>
		     				<c:forEach var="discountType" items="${discountTypes }">
		     					<option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="${discountType.dbid }" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="${startWritingItem.num}" data-money= "10000" data-price="${startWritingItem.money}" data-num="${startWritingItem.num}">${discountType.name }</option>
			        		</c:forEach>
			        		
			        		<c:if test="${!empty(memberCard)&&memberCard.discount<10 }" var="memDiscStatus">
							    <option data-index=<%=oindex++ %> lay-filter="discount" data-discountType="2" data-point="${memberCard.discount }" data-forginId="<%=gindex++ %>" data-cnt="0" data-money= "10000" data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}">${memberCard.name }${memberCard.discount }折</option>
		     				</c:if>
		     				
		     				<option data-index=<%=oindex++ %>  data-discountType="1" data-point="10" data-forginId="<%=gindex++ %>" data-cnt="0" data-money="<%=gindex++ %>" data-price="${startWritingProduct.money}" data-num="${startWritingProduct.num}">无优惠</option>
			        	</select>
			        </td>
			        <td>
			        	${startWritingProduct.money }  <!-- 单价 -->
			        </td>
			        <td>
			        	${startWritingProduct.num }<!-- 数量 -->
			        </td>
			        <td>
			        	${startWritingProduct.discountPrice }  <!-- 折扣金额 -->
			        </td>
			        <td>
			        	${startWritingProduct.num*startWritingProduct.money }
			        </td>
			      </tr>
			    </c:forEach>
			    <tr height="32" style="font-size: 16px;font-weight: bold;">
					<td  colspan="7" style="text-align: right;padding-right: 12px;border-left: 0;font-size: 16px;font-weight: normal;" >
						消费总金额：<span style="color: #FF5722" id="customPrice">￥${startWriting.money }</span> &nbsp;&nbsp;
						折扣后金额：<span style="color: #FF5722" id="itemTotalPrice">￥${startWriting.money }</span>
					</td>
				</tr>
			    </tbody>
			  </table>
		    </div>
		 </div>
		<div class="layui-row" style="margin-top: 12px;">
			<div class="layui-col-xs6 layui-form" style="border-right: 1px solid #eee;height: 420px;">
			<div class="" style="text-align: center;font-size: 18px;">优惠项目</div>
			  <c:set var="memdisStatus" value="false"></c:set>
			  <c:if test="${!empty(memberCard)}" var="memberCardStatus">
			  		<c:if test="${(!empty(memberCardDisItems)&&fn:length(memberCardDisItems)>0)||(!empty(memberCardDisProducts)&&fn:length(memberCardDisProducts)>0) }" var="memdisStatus">
					    <div class="layui-form-item" pane="">
					    	 <label class="layui-form-label">会员卡免费</label>
					    	 <c:forEach var="memberCardDisItem" items="${memberCardDisItems }">
						    	 <div class="layui-input-inline" style="width: 450px;">
									<input  id="memberCardItemId" lay-skin="primary" value="${memberCardDisItem.itemId }" lay-filter="memberCardItemId" name="memberCardItemId" title="免费项目：${memberCardDisItem.itemName }" >
						    	</div>
					    	 </c:forEach>
					    	 <c:forEach var="memberCardDisProduct" items="${memberCardDisProducts }">
						    	 <div class="layui-input-inline" style="width: 450px;">
									<input type="checkbox" checked="checked" disabled="disabled" id="memberCardProductId" lay-skin="primary" value="${memberCardDisProduct.productId }" lay-filter="memberCardProductId" name="memberCardProductId" title="免费产品：${memberCardDisProduct.prodcutName }">
						    	</div>
					    	 </c:forEach>
					    </div>
				    </c:if>
		     </c:if>
		     <!-- 会员优惠券  模块 -->
			  <c:if test="${(!empty(couponMemberTemplateItems)&&fn:length(couponMemberTemplateItems)>0)||(!empty(couponMemberTemplateProducts)&&fn:length(couponMemberTemplateProducts)>0) }" var="couponStatus">
				    <div class="layui-form-item" pane="">
				    	 <label class="layui-form-label">优惠券</label>
				    	 <c:forEach var="couponMemberTemplateItem" items="${couponMemberTemplateItems }">
					    	 <div class="layui-input-inline" style="width: 450px;">
								<input type="checkbox" checked="checked" disabled="disabled" id="couponMemberTemplateItemId" value="${couponMemberTemplateItem.itemId}" coupontype="${couponMemberTemplateItem.type }" couponMoney="${couponMemberTemplateItem.price}" lay-skin="primary" lay-filter="couponMemberTemplate" name="couponMemberTemplateItemId" title="${couponMemberTemplateItem.name },类型：${couponMemberTemplateItem.type==1?'代金券':'免费券'},项目：${couponMemberTemplateItem.itemName},抵扣金额:${couponMemberTemplateItem.price}">
					    	</div>
				    	 </c:forEach>
				    	 <c:forEach var="couponMemberTemplateProduct" items="${couponMemberTemplateProducts }">
					    	 <div class="layui-input-inline" style="width: 450px;">
								<input type="checkbox" checked="checked" disabled="disabled" id="couponMemberTemplateProductId" value="${couponMemberTemplateProduct.itemId }" coupontype="${couponMemberTemplateProduct.type }" couponMoney="${couponMemberTemplateProduct.price}" lay-skin="primary" lay-filter="couponMemberTemplate" name="couponMemberTemplateProductId" title="${couponMemberTemplateProduct.itemName }【${couponMemberTemplateProduct.type==1?'代金券':'免费券'}】${couponMemberTemplateProduct.price}">
					    	</div>
				    	 </c:forEach>
				    </div>
		     </c:if>
			  <c:if test="${!empty(stormMoneyOnceEntItemCards)&&fn:length(stormMoneyOnceEntItemCards)>0 }" var="onceStatus">
				    <div class="layui-form-item" pane="">
				    	 <label class="layui-form-label">次卡</label>
				    	 <c:forEach var="stormMoneyOnceEntItemCard" items="${stormMoneyOnceEntItemCards }">
					    	 <div class="layui-input-inline" style="width: 450px;">
								<input type="checkbox" checked="checked" disabled="disabled"  id="stormMoneyOnceEntItemCardId" value="${stormMoneyOnceEntItemCard.dbid }" itemId="${stormMoneyOnceEntItemCard.item.dbid }" num="${stormMoneyOnceEntItemCard.num-stormMoneyOnceEntItemCard.consumpiontNum}" lay-skin="primary" lay-filter="stormMoneyOnceEntItemCardId" name="stormMoneyOnceEntItemCardId" title="${stormMoneyOnceEntItemCard.onceEntItemCardName }【${stormMoneyOnceEntItemCard.itemName }】剩余: ${stormMoneyOnceEntItemCard.num-stormMoneyOnceEntItemCard.consumpiontNum} 次">
					    	</div>
				    	 </c:forEach>
				    </div>
		     </c:if>
			  <c:if test="${!empty(memberCard)&&memberCard.discount<10 }" var="memDiscStatus">
				    <div class="layui-form-item" pane="" >
				    	 <label class="layui-form-label">会员卡折扣</label>
				    	 <div class="layui-input-inline" style="width: 450px;">
							<input type="checkbox" checked="checked" disabled="disabled"  id="memberCardId" lay-skin="primary" lay-filter="memberCardId" name="memberCardId" discount="${memberCard.discount }" title="${memberCard.name }${memberCard.discount }折">
				    	</div>
				    </div>
		     </c:if>
		      <c:if test="${memdisStatus==false&&couponStatus==false&&onceStatus==false&&memDiscStatus==false }">
		      	<div style="height: 30px;line-height: 30px;background-color:#FF572;">	
		      		无优惠想信息
		      	</div>
		      </c:if>
		    </div>
		    <div class="layui-col-xs6" style="">
				<div class="" style="text-align: center;font-size: 18px;">收银信息</div>
				<div class="layui-form-item">
			            <label class="layui-form-label label-required">小费<span style="color: red;">*</span></label>
			            <div class="layui-input-inline" style="width:250px">
			                <input type="number"  name="startWriting.tipMoney" id="tipMoney" value="${startWriting.tipMoney }${empty(startWriting.tipMoney)==true?'0':''}" onkeyup="totalOrderMoney()"  lay-verify="required" placeholder="小费" autocomplete="off" class="layui-input">
			            </div>
			        </div>
			        <div class="layui-form-item">
			            <label class="layui-form-label label-required">整单优惠<span style="color: red;">*</span></label>
			            <div class="layui-input-inline" style="width:250px">
			                <input type="number" name="startWriting.discountPrice" id="discountPrice" value="${startWriting.discountPrice }" onkeyup="totalOrderMoney()" lay-verify="required" placeholder="整单优惠" autocomplete="off" class="layui-input">
			            </div>
			        </div>
		       		<div class="layui-form-item" style="margin-top: 20px;">
			            <label class="layui-form-label label-required">订单总额<span style="color: red;">*</span></label>
			            <div class="layui-input-inline" style="width:250px">
				                <input type="number" name="startWriting.money" id="money" readonly="readonly" value="${startWriting.money }"  lay-verify="required"  autocomplete="off" class="layui-input">
			            </div>
			        </div>
			        <div class="layui-form-item">
			            <label class="layui-form-label label-required">实收合计<span style="color: red;">*</span></label>
			            <div class="layui-input-inline" style="width:250px">
			                <input type="number" readonly="readonly" name="startWriting.acturePrice" id="acturePrice" value="${startWriting.acturePrice }"  lay-verify="required" placeholder="整单优惠" autocomplete="off" class="layui-input">
			            </div>
			        </div>
			        
			       <div class="layui-form-item" pane="">
					    <label class="layui-form-label">支付方式</label>
					    <div class="layui-input-block">
					      <c:forEach var="payWay" items="${payWays }">
						      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="${payWay.dbid }" title="${payWay.name }" onclick="payWay()">
					      </c:forEach>
					      <c:if test="${member.balance>0 }">
						      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="5" title="会员卡,余额【${member.balance }】" onclick="payWay()">
					      </c:if>
					      <c:if test="${(member.startTotalStormMoney-member.startTotalMoney)>=startWriting.money}">
						      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="6" title="创始会员卡,余额【${member.startTotalStormMoney-member.startTotalMoney }】" onclick="window.location.href='${ctx}/startWriting/onceCash?startWritingId=${startWriting.dbid }'">
					      </c:if>
					    </div>
					</div>
					<div id="payWayDiv">
					</div>
			        <div class="layui-form-item">
			            <label class="layui-form-label label-required">备注信息</label>
			            <div class="layui-input-inline" style="width:250px">
			                <textarea name="startWriting.note" placeholder="备注信息" autocomplete="off" class="layui-textarea"></textarea>
			            </div>
			        </div>
		    </div>
		 </div>
		 <br>
		 <br>
		 <br>
		 <br>
		<div class="layui-layout layui-layout-admin">
			<div class="layui-footer footer" style=" left: 0px;text-align: right;">
				<a class="layui-btn layui-btn-disabled" id="submitBut" lay-submit="" lay-filter="submitButton" >确定收银（￥${startWriting.acturePrice }）</a>
		   		<a id="closeBut" class="layui-btn layui-btn-primary" >取消</a>
			</div>
		</div>
    </form>
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript">

var customPrice=0.0;//产品+项目 金额
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
calcTotalPrice();
var layer;
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form;
	  var layer = layui.layer;
	  if(topParent==undefined){
		 layer = layui.layer;
	  }else{
		 layer = topParent.layer;
	  }
	  var $ = layui.$, active = {
			 changeMember:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        layer.open({
			          type: 2 //此处以iframe举例
			          ,title: '更改会员'
			          ,area: ['1280px', '640px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/member/changeMember'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			          }
			        ,btn: ['确定', '关闭']
			        ,yes: function(index, layero){
			        	var body = layer.getChildFrame('body', index);
			        	var memberid= body.find("input[name=id]:checked").val();
			        	var memberData= body.find("#memberData").val();
			        	if(memberid==''||memberid==undefined){
			        		alert("请选择会员后在操作");
			        		return ;
			        	}
			        	var data=$.parseJSON(memberData);
			        	$("#memberId").val(data.dbid);
						window.location.href='${ctx}/startWriting/cash?startWritingId=${startWriting.dbid}&memberId='+data.dbid;
						layer.close(index);
			        }
			        ,btn2: function(index, layero){
			        	layer.close(index);
			          //return false 开启该代码可禁止点击该按钮关闭
			        }
			        });
		}
	 }
	  $('.layui-container .layui-btn').on('click', function(){
		  var othis = $(this), method = othis.data('method');
		  active[method] ? active[method].call(this, othis) : '';
	  });
	  
	  form.on('select(discount)', function(data){	
	  calcTotalPrice();
	  form.render();
	});
	  form.on('checkbox(payWayId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='payWayId']");
		  	var html='';
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			if(checkbox.value!=6){
			  		 	html=html+createHtml(checkbox.value,checkbox.title);
		  			}
		  		}
		  	});
		  	$("#payWayDiv").text("");
		  	$("#payWayDiv").append(html);
		  	payWayFunct();
		});
	  form.on('submit(submitButton)', function(data){
		  var attr=$("#submitBut").attr("class");
		  if(attr.indexOf("layui-btn-disabled")>0){
			 return false;
		  }
		  calcTotalPrice();
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/startWriting/saveCash',
				data : params, 
				async : false, 
				timeout : 20000, 
				dataType : "json",
				type:"post",
				success : function(data, textStatus, jqXHR){
					//alert(data.message);
					var obj;
					if(data.message!=undefined){
						obj=$.parseJSON(data.message);
					}else{
						obj=data;
					}
					if(obj[0].mark==1){
						//错误
						layer.msg(obj[0].message,{icon: 5});
						$("#submitBut").bind("onclick");
						return ;
					}else if(obj[0].mark==0){
						layer.msg(data[0].message,{icon: 1});
						if (target == "_self") {
							setTimeout(
									function() {
										window.location.href = obj[0].url
									}, 1000);
						}
						if (target == "_parent") {
						 setTimeout(
								function() {
									window.parent.frames["contentUrl"].location=obj[0].url;
									 parent.layer.closeAll();
								},1000) 
						}
						// 保存数据成功时页面需跳转到列表页面
					}
				},
				complete : function(jqXHR, textStatus){
					var jqXHR=jqXHR;
					var textStatus=textStatus;
				}, 
				beforeSend : function(jqXHR, configs){
					$("#submitBut").unbind("onclick");
					var jqXHR=jqXHR;
					var configs=configs;
				}, 
				error : function(jqXHR, textStatus, errorThrown){
						layer.msg("系统请求超时");
						$("#submitBut").bind("onclick");
				}
			});
	    return false;
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
});

function calcTotalPrice(){
	var items=$("select[lay-filter='discount']");
	customPrice=0.0;  //项目，商品折扣前的金额
	var totalPrice=0;  //实际支付的金额
	var itemPrice=0.0;  //项目、商品折扣后的金额
	var discountPrice=0.0;  //整单优惠金额
	var orderPrice=0.0;  //订单打折前的金额
	var discountItemJson="[";
	$(items).each(function (i) {
		var oldDiscountType=$(this).attr("data-olddiscounttype");//上一次选中的优惠方式
		var oldForginId=$(this).attr("data-oldforginId");  //上一次优惠方式的forginid
		console.info(oldDiscountType);
		var selected= $(this).find("option:selected");
		var type=$(this).attr("data-type");  //商品项目类型
		var dbid=$(this).attr("data-dbid");  //订单商品Id/订单项目ID
		var cost=0.0;

		if($(selected).prop("disabled")){
			selected= $(this).find("option:enabled").first()
			selected.attr('selected','selected'); 
		}
		
		var oindex=selected.attr("data-index");//获取当前选项的全局index
		var point=selected.attr("data-point"); //折扣
		var price=selected.attr("data-price");  //商品/项目价格
		var money=selected.attr("data-money");  //优惠券金额
		var cnt=selected.attr("data-cnt");  //优惠券数量
		var num=selected.attr("data-num");  //消费数量
		var discountType=selected.attr("data-discountType");  //订单商品Id/订单项目ID
		var forginId=selected.attr("data-forginId");  //代金券ID/次卡ID/免费券Id
		
		$("option[data-discountType='"+oldDiscountType+"'][data-forginId='"+oldForginId+"']").removeAttr('disabled');
		$("option[data-discountType='"+discountType+"'][data-forginId='"+forginId+"'][data-index!='"+oindex+"']").attr("disabled","disabled");
		cost=(num-cnt)*price*(point/10.0);
		cost=cost<0?0:cost;
		
		var subCost=0.0;
		subCost=(price-money)*cnt;
		subCost=subCost<0?0.0:subCost;
		
		customPrice+=price*num;
		itemPrice=parseInt(cost+subCost+0.5);
		discountPrice=price*num-itemPrice;
		
		totalPrice=totalPrice+itemPrice;
		var tr=$(this).parent().parent();
		$(tr).children('td').eq(5).text(discountPrice);
		$(tr).children('td').eq(6).text(itemPrice);
		discountItemJson=discountItemJson+'{"dbid":'+dbid+',"type":'+type+',"discountType":'+discountType+',"point":'+point+',"price":'+price+',"money":'+money+',"cnt":'+cnt+',"num":'+num+',"forginId":'+forginId+'},';
		$(this).attr("data-olddiscounttype",discountType);
		$(this).attr("data-oldforginid",forginId); 
	 });
	
	
	discountItemJson=discountItemJson.substring(0,(discountItemJson.length-1))
	discountItemJson=discountItemJson+"]";
	var tipMoney=$("#tipMoney").val();
	var orderDiscountMoney=$("#discountPrice").val();
	
	if(null!=tipMoney&&tipMoney!=''){
		tipMoney=parseFloat(tipMoney);
  		if(isNaN(tipMoney)){
  			tipMoney=0; 
  		}
  	}else{
  		tipMoney=0;
  	}

	if(null!=orderDiscountMoney&&orderDiscountMoney!=''){
		orderDiscountMoney=parseFloat(orderDiscountMoney);
  		if(isNaN(tipMoney)){
			orderDiscountMoney=0; 
  		}
  	}else{
		orderDiscountMoney=0;
  	}
	  
	$("#discountItemJson").val(discountItemJson);
	$("#customPrice").text("￥"+customPrice);
	$("#itemTotalPrice").text("￥"+totalPrice);
	$("#money").val(customPrice+tipMoney);
	$("#acturePrice").val(totalPrice+tipMoney-orderDiscountMoney);

	$("#submitBut").text("确定收银（￥"+totalPrice+tipMoney-orderDiscountMoney+")");
	payWayFunct();
}


function createHtml(value,name){
	var names=name.split(",");
	var html='<div class="layui-form-item">'+
			 '<label class="layui-form-label label-required">'+names[0]+'<span style="color: red;">*</span></label>'+
			 ' <div class="layui-input-inline" style="width:250px">'+
	         ' <input type="number" name="payWayValue'+value+'" id="payWayValue'+value+'"  value="0.0" onkeyup="payWayFunct()" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">'+
	         '</div>'+
	         '</div>';
	return html;
}
function payWayFunct(){
	var checkeds = $("input[type='checkbox'][name='payWayId']");
	var balance="${member.balance}";
	balance=validateValue(balance);
	var actureMoney=parseFloat(0);
  	$.each(checkeds, function(i, checkbox) {
  		if (checkbox.checked) {
  			var value=$("#payWayValue"+checkbox.value).val();
  			actureMoney=parseFloat(actureMoney);
  			value=parseFloat(value);
			if(checkbox.value==5){
  				if(value>balance){
  					value=balance;
  					$("#payWayValue"+checkbox.value).val(value);
  				}
  			}
			if(checkbox.value==6){
				layer.open({
			        type: 1
			        ,title: false //不显示标题栏
			        ,closeBtn: false
			        ,area: '300px;'
			        ,shade: 0.8
			        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
			        ,btn: ['确定', '取消']
			        ,btnAlign: 'c'
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">确定使用创始会员卡支付吗</div>'
			        ,success: function(layero){
			        	 var btn = layero.find('.layui-layer-btn');
			             btn.find('.layui-layer-btn0').attr({
			               href: '${ctx}/startWriting/onceCash?startWritingId=${startWriting.dbid}'
			               ,target: '_self'
			             });
			        }
			      });
				return ;
			}
  			actureMoney=actureMoney+value;
  		}
  	});
  	actureMoney=parseFloat(actureMoney);
  	var money=$("#acturePrice").val();
	if(null!=money&&money!=''){
		money=parseFloat(money);
  		if(isNaN(money)){
  			money=0; 
  		}
  	}else{
  		 money=0;
  	}
	if(actureMoney==money){
		$("#submitBut").text("确定收银");
		$("#submitBut").removeClass("layui-btn-disabled");
	}
	if(actureMoney>money){
		var attr=$("#submitBut").attr("class");
		if(attr.indexOf("layui-btn-disabled")){
			$("#submitBut").addClass("layui-btn-disabled");
		}
		$("#submitBut").text("确定收银（多收￥"+(actureMoney-money)+"元");
	}
	if(actureMoney<money){
		var attr=$("#submitBut").attr("class");
		if(attr.indexOf("layui-btn-disabled")){
			$("#submitBut").addClass("layui-btn-disabled");
		}
		$("#submitBut").text("确定收银（少收￥"+(money-actureMoney)+"元");
	}
}
function totalOrderMoney(){
	calcTotalPrice();
}
function formatFloat(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        return 0;
    }
    var f_x = Math.round(x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 1) {
        s_x += '0';
    }
    return s_x;
}
function validateValue(itemMoney){
 	if(itemMoney!=null&&itemMoney!=''&&itemMoney!=undefined){
    	itemMoney=parseFloat(itemMoney);
    }else{
    	itemMoney=0;
    }
 	return itemMoney;
}
function detail(){
	var memberId=$('#memberId').val();
	window.open('${ctx}/member/detail?dbid='+memberId);
}
</script>
</body>
</html>
