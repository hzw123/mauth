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
<div class="layui-container" style="line-height:46px;border:1px solid #ddd;font-size:16px;border-radius:5px;margin: 0px auto;margin-top:20px;;">
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员编号:${member.no }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">${member.name }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">${member.sex }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">${member.mobilePhone }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	生日
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">
                	${member.birthday}
                </div>
            </div>
        </div>
        <div class="layui-row" style="">
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">充值总额:${member.totalStormMoney }</div>
            </div>
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">余额:${member.balance }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费次数：${member.totalBuy }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费总额：${member.totalMoney }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">剩余积分：${member.overagePiont }</div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">消费积分：${member.consumpiontPoint }</div>
            </div>
        </div>
    </div>
<div class="layui-container">
	<form class="layui-form" id="frmId" name="frmId" method="post" target="_parent">
		<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
		<input type="hidden" id="startWritingId" name="startWritingId" value="${startWriting.dbid }">
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
			    <c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
			      <tr id="entItemTr${i.index+1  }">
			        <td>
			        	<input type="hidden" id="startWritingItemId" name="startWritingItemId" value="${startWritingItem.dbid }" >
			        	<input type="hidden" id="itemId" name="itemId" value="${startWritingItem.item.dbid }" >
			        	<input type="hidden" id="itemNum${startWritingItem.item.dbid }" name="num" value="${startWritingItem.num }" >
			        	<input type="hidden" id="hisItemNum${startWritingItem.item.dbid }" name="hisNum" value="${startWritingItem.num }" >
			        	<input type="hidden" id="itemMoney${startWritingItem.item.dbid }" name="itemMoney" value="${startWritingItem.money }" onchange="getOrderMoney()">
			        	<input type="hidden" id="hisItemMoney${startWritingItem.item.dbid }" name="hisItemMoney" value="${startWritingItem.money }" >
			        	${startWritingItem.itemName }
			        </td>
			        <td>
			        	${startWritingItem.artificerName }
			        </td>
			        <td>
			        	<select id="discountTypeId${startWritingItem.item.dbid }" name="discountTypeId" lay-filter='discountTypeId' itemId="${startWritingItem.item.dbid }">
				        	<option value="${startWritingItem.discountTypeId }" } >${startWritingItem.discountTypeName }</option>
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
			        	<input type="hidden" id="productId" name="productId" value="${startWritingProduct.product.dbid }" >
			        	<input type="hidden" id="productNum${startWritingProduct.product.dbid }" name="productNum" value="${startWritingProduct.num }" >
			        	<input type="hidden" id="productMoney${startWritingProduct.product.dbid }" name="productMoney" value="${startWritingProduct.money }" onchange="getOrderMoney()">
			        	<input type="hidden" id="hisProductMoney${startWritingProduct.product.dbid }" name="hisProductMoney" value="${startWritingProduct.money }" >
			        	${startWritingProduct.prodcutName }
			        </td>
			        <td>
			        	-
			        </td>
			        <td>
			        	<select id="pdiscountTypeId${startWritingProduct.product.dbid }" name="pdiscountTypeId" lay-filter='pdiscountTypeId' productId="${startWritingProduct.product.dbid }">
			        		<option value="${startWritingProduct.discountTypeId }"  >${startWritingProduct.discountTypeName }</option>
			        	</select>
			        </td>
			        <td>
			        	${startWritingProduct.money }
			        </td>
			        <td>
			        	${startWritingProduct.num }
			        </td>
			        <td>
			        	${startWritingProduct.discountPrice }
			        </td>
			        <td>
			        	${startWritingProduct.num*startWritingProduct.money }
			        </td>
			      </tr>
			    </c:forEach>
			    <tr height="32" style="font-size: 16px;font-weight: bold;">
					<td  colspan="7" style="text-align: right;padding-right: 12px;border-left: 0;font-size: 16px;font-weight: normal;" >
						消费总额：<span style="color: #FF5722" id="itemTotalPrice">￥${startWriting.money }</span>
					</td>
				</tr>
			    </tbody>
			  </table>
		    </div>
		 </div>
		<div class="layui-row" style="margin-top: 12px;">
			<div class="layui-col-xs6 layui-form" style="border-right: 1px solid #eee;height: 420px;">
			<div class="" style="text-align: center;font-size: 18px;">优惠项目</div>
		      	<div style="height: 30px;line-height: 30px;background-color:#FF572;">	
		      		无优惠想信息
		      	</div>
		    </div>
		    <div class="layui-col-xs6" style="">
		    	<div class="" style="text-align: center;font-size: 18px;">收银信息</div>
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
			       <div class="layui-form-item" pane="">
					    <label class="layui-form-label">支付方式</label>
					    <div class="layui-input-block">
						      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="6" checked="checked" title="创始会员卡,余额【${member.startTotalStormMoney-member.startTotalMoney }】" >
						      <a class="layui-btn layui-btn-xs" href="${ctx}/startWriting/cash?startWritingId=${startWriting.dbid}">重选支付方式</a>
					    </div>
					</div>
					<div id="payWayDiv">
						<div class="layui-form-item">
							<label class="layui-form-label label-required">创始会员卡<span style="color: red;">*</span></label> 
							<div class="layui-input-inline" style="width:250px"> 
								<input type="number" name="payWayValue6" id="payWayValue6" value="0.0" onkeyup="payWayFunct()" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
			        <div class="layui-form-item">
			            <label class="layui-form-label label-required">备注信息</label>
			            <div class="layui-input-inline" style="width:250px">
			                <textarea name="startWriting.note" placeholder="备注信息" autocomplete="off" class="layui-textarea"></textarea>
			            </div>
			        </div>
		    </div>
		 </div>
		<div class="layui-layout layui-layout-admin">
			<div class="layui-footer footer" style=" left: 0px;text-align: right;">
				<a class="layui-btn layui-btn-disabled" id="submitBut" lay-submit="" lay-filter="submitButton" >确定收银（￥${startWriting.money }）</a>
		   		<a id="closeBut" class="layui-btn layui-btn-primary" >取消</a>
			</div>
		</div>
    </form>
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript">
var layer;
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer;
	  form.on('submit(submitButton)', function(data){
		  var attr=$("#submitBut").attr("class");
		  if(attr.indexOf("layui-btn-disabled")>0){
			 return false;
		  }
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/startWriting/saveOnceCash',
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
	  form.on('checkbox(payWayId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='payWayId']");
		  	var html='';
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  		 	html=html+createHtml(checkbox.value,checkbox.title);
		  		}
		  	});
		  	$("#payWayDiv").text("");
		  	$("#payWayDiv").append(html);
		  	payWayFunct();
		});
	  //会员卡免费项目（全免）
	  form.on('checkbox(memberCardItemId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='memberCardItemId']");
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var memberCardItemId=checkbox.value;
		  			var itemNum=$("#itemNum"+memberCardItemId).val();
		  			if(null!=itemNum&&itemNum!=''&&itemNum!=undefined){
		  				itemNum=parseInt(itemNum);
		  			}else{
		  				itemNum=0;
		  			}
		  			var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
		  			if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
		  				itemMoney=parseFloat(itemMoney);
		  			}else{
		  				itemMoney=0;
		  			}
		  			itemMoney=itemMoney*itemNum;
		  			
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			$(tr).children('td').eq(5).text(formatFloat(itemMoney));
		  			$(tr).children('td').eq(6).text("0.0");
		  			$("#itemMoney"+memberCardItemId).val("0.0");
		  			$("#discountTypeId"+memberCardItemId).append("<option value='3' selected='selected'>会员卡赠送</option>");
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			var memberCardItemId=checkbox.value;
		  			var itemNum=$("#itemNum"+memberCardItemId).val();
		  			if(null!=itemNum&&itemNum!=''&&itemNum!=undefined){
		  				itemNum=parseInt(itemNum);
		  			}else{
		  				itemNum=0;
		  			}
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
		  			$("#itemMoney"+memberCardItemId).val(itemMoney);
		  			if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
		  				itemMoney=parseFloat(itemMoney);
		  			}else{
		  				itemMoney=0;
		  			}
		  			itemMoney=itemMoney*itemNum;
		  			$(tr).children('td').eq(5).text("0.0");
		  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
		  			$("#discountTypeId"+memberCardItemId+" option[value='3']").remove();
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		});
	  //会员卡免费产品（全免）
	  form.on('checkbox(memberCardProductId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='memberCardProductId']");
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var memberCardProductId=checkbox.value;
		  			var productNum=$("#productNum"+memberCardProductId).val();
		  			if(null!=productNum&&productNum!=''&&productNum!=undefined){
		  				productNum=parseInt(productNum);
		  			}else{
		  				productNum=0;
		  			}
		  			var productMoney=$("#hisProductMoney"+memberCardProductId).val();
		  			if(null!=productMoney&&productMoney!=''&&productMoney!=undefined){
		  				productMoney=parseFloat(productMoney);
		  			}else{
		  				productMoney=0;
		  			}
		  			productMoney=productMoney*productNum;
		  			var item=$("input[type='hidden'][name='productId'][value='"+memberCardProductId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			$(tr).children('td').eq(5).text(formatFloat(productMoney));
		  			$(tr).children('td').eq(6).text("0.0");
		  			$("#productMoney"+memberCardProductId).val("0.0");
		  			$("#pdiscountTypeId"+memberCardProductId).append("<option value='3' selected='selected'>会员卡赠送</option>");
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			var memberCardProductId=checkbox.value;
		  			var productNum=$("#productNum"+memberCardProductId).val();
		  			if(null!=productNum&&productNum!=''&&productNum!=undefined){
		  				productNum=parseInt(productNum);
		  			}else{
		  				productNum=0;
		  			}
		  			var productMoney=$("#hisProductMoney"+memberCardProductId).val();
		  			if(null!=productMoney&&productMoney!=''&&productMoney!=undefined){
		  				productMoney=parseFloat(productMoney);
		  			}else{
		  				productMoney=0;
		  			}
		  			productMoney=productMoney*productNum;
		  			var item=$("input[type='hidden'][name='productId'][value='"+memberCardProductId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			var itemMoney=$("#hisProductMoney"+memberCardProductId).val();
		  			$("#productMoney"+memberCardProductId).val(itemMoney);
		  			$(tr).children('td').eq(5).text("0.0")
		  			$(tr).children('td').eq(6).text(productMoney)
		  			$("#pdiscountTypeId"+memberCardProductId+" option[value='3']").remove();
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		});
	  //优惠券
	  form.on('checkbox(couponMemberTemplate)', function(data){
		   var checkeds = $("input[type='checkbox'][name='couponMemberTemplateItemId']");
		   var couponMemberTemplateProductCheckeds = $("input[type='checkbox'][name='couponMemberTemplateProductId']");
		   var lenth=0;
		   $.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {lenth=lenth+1}
		   });
		   $.each(couponMemberTemplateProductCheckeds, function(i, checkbox) {
		  		if (checkbox.checked) {lenth=lenth+1}
		   });
		   if(lenth>1){
			   alert("只能选择一个项目或产品使用优惠券，请确认");
			   return ;
		   }
		   //项目代金券
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var memberCardItemId=$(checkbox).val();
		  			var coupontype=$(checkbox).attr("coupontype");
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
	  				var couponMoney=$(checkbox).attr("couponMoney");
		  			if(null!=couponMoney&&couponMoney!=''&&couponMoney!=undefined){
		  				couponMoney=parseFloat(couponMoney);
	  				}else{
	  					couponMoney=0;
	  				}
	  				var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
	  				if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
	  					itemMoney=parseFloat(itemMoney);
	  				}
		  			var num=$("#itemNum"+memberCardItemId).val();
	  				if(null!=num&&num!=''&&num!=undefined){
	  					num=parseInt(num);
	  				}
		  			//代金券
		  			if(coupontype==1){
		  				if(num>1){
		  					//代金券是否大于当前金额
			  				if(couponMoney>=itemMoney){
					  			$(tr).children('td').eq(5).text(formatFloat(itemMoney));
					  			itemMoney=itemMoney*(num-1)
					  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
			  				}else{
					  			$(tr).children('td').eq(5).text(formatFloat(couponMoney));
					  			var re=itemMoney-couponMoney;
					  			itemMoney=itemMoney*(num-1)+re;
					  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
			  				}
		  				}else{
		  					//代金券是否大于当前金额
			  				if(couponMoney>=itemMoney){
					  			$(tr).children('td').eq(5).text(formatFloat(itemMoney));
					  			$(tr).children('td').eq(6).text("0.0");
			  				}else{
			  					itemMoney=itemMoney-couponMoney;
					  			$(tr).children('td').eq(5).text(formatFloat(couponMoney));
					  			$(tr).children('td').eq(6).text(formatFloat(itemMoney-couponMoney));
			  				}
		  				}
		  				
			  			$("#discountTypeId"+memberCardItemId).append("<option value='6' selected='selected'>代金券</option>");
		  			}
		  			//免费券
		  			if(coupontype==2){
		  				if(num>1){
		  					$(tr).children('td').eq(5).text(formatFloat(itemMoney));
		  					itemMoney=itemMoney*(num-1);
				  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
		  				}else{
				  			$(tr).children('td').eq(5).text(formatFloat(itemMoney));
				  			$(tr).children('td').eq(6).text("0.0");
				  			$("#discountTypeId"+memberCardItemId).append("<option value='5' selected='selected'>免费券</option>");
		  				}
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			var memberCardItemId=$(checkbox).val();
		  			var num=$("#itemNum"+memberCardItemId).val();
	  				if(null!=num&&num!=''&&num!=undefined){
	  					num=parseInt(num);
	  				}
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
		  			itemMoney=validateValue(itemMoney);
		  			$(tr).children('td').eq(5).text("0.0");
		  			$(tr).children('td').eq(6).text(formatFloat(itemMoney*num));
		  			var coupontype=$(checkbox).attr("coupontype");
		  			if(coupontype==1){
		  				$("#discountTypeId"+memberCardItemId+" option[value='6']").remove();
		  			}
		  			if(coupontype==2){
		  				$("#discountTypeId"+memberCardItemId+" option[value='5']").remove();
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		   //产品优惠券
		  	$.each(couponMemberTemplateProductCheckeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var memberCardItemId=$(checkbox).val();
		  			var coupontype=$(checkbox).attr("coupontype");
		  			var item=$("input[type='hidden'][name='productId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			var itemMoney=$("#hisProductMoney"+memberCardItemId).val();
	  				if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
	  					itemMoney=parseFloat(itemMoney);
	  				}
		  			var num=$("#productNum"+memberCardItemId).val();
	  				if(null!=num&&num!=''&&num!=undefined){
	  					num=parseInt(num);
	  				}
		  			//代金券  
		  			if(coupontype==1){
		  				var couponMoney=$(checkbox).attr("couponMoney");
			  			if(null!=couponMoney&&couponMoney!=''&&couponMoney!=undefined){
			  				couponMoney=parseFloat(couponMoney);
		  				}else{
		  					couponMoney=0;
		  				}
			  			//判断商品数量信息
						if(num>1){
							if(couponMoney>=itemMoney){
				  				$(tr).children('td').eq(5).text(formatFloat(itemMoney));
								itemMoney=itemMoney*(num-1);
							}else{
				  				$(tr).children('td').eq(5).text(formatFloat(couponMoney));
								var itemMoneyTemp=itemMoney-couponMoney;
								itemMoney=itemMoney*(num-1)+itemMoneyTemp;
							}
				  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
						}else{
							//如果代金券大于商品金额，设置为零
							if(couponMoney>=itemMoney){
								$(tr).children('td').eq(5).text(formatFloat(itemMoney));
								$(tr).children('td').eq(6).text("0.0");
							}else{
								//小于商品金额，需要实际付款itemMoney-couponMoney;
								$(tr).children('td').eq(5).text(formatFloat(couponMoney));
								var itemMoneyTemp=itemMoney-couponMoney;
								$(tr).children('td').eq(6).text(formatFloat(itemMoneyTemp));
							}
						}	  				
			  			$("#pdiscountTypeId"+memberCardItemId).append("<option value='6' selected='selected'>代金券</option>");
		  			}
		  			//免费券
		  			if(coupontype==2){
		  				if(num>1){
							$(tr).children('td').eq(5).text(formatFloat(itemMoney));
							itemMoney=itemMoney*(num-1);
				  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
						}else{
							//如果代金券大于商品金额，设置为零
							$(tr).children('td').eq(5).text(formatFloat(itemMoney));
							$(tr).children('td').eq(6).text("0.0");
						}
			  			$("#pdiscountTypeId"+memberCardItemId).append("<option value='5' selected='selected'>免费券</option>");
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			var memberCardItemId=$(checkbox).val();
		  			var item=$("input[type='hidden'][name='productId'][value='"+memberCardItemId+"']")[0];
		  			var itemMoney=$("#hisProductMoney"+memberCardItemId).val();
	  				if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
	  					itemMoney=parseFloat(itemMoney);
	  				}
		  			var num=$("#productNum"+memberCardItemId).val();
	  				if(null!=num&&num!=''&&num!=undefined){
	  					num=parseInt(num);
	  				}
		  			var tr=$(item).parent().parent();
		  			$(tr).children('td').eq(5).text("0.0");
		  			$(tr).children('td').eq(6).text(formatFloat(itemMoney*num))
		  			var coupontype=$(checkbox).attr("coupontype");
		  			if(coupontype==1){
		  				$("#pdiscountTypeId"+memberCardItemId+" option[value='6']").remove();
		  			}
		  			if(coupontype==2){
		  				$("#pdiscountTypeId"+memberCardItemId+" option[value='5']").remove();
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		});
	  //会员卡次卡项目
	  form.on('checkbox(stormMoneyOnceEntItemCardId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='stormMoneyOnceEntItemCardId']");
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var memberCardItemId=$(checkbox).attr("itemId");
		  			var num=$(checkbox).attr("num");
		  			if(null!=num&&num!=''&&num!=undefined){
		  				num=parseInt(num);
		  			}else{
		  				num=0;
		  			}
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var itemNum=$("#itemNum"+memberCardItemId).val();
		  			if(null!=itemNum&&itemNum!=''&&itemNum!=undefined){
		  				itemNum=parseInt(itemNum);
		  			}else{
		  				itemNum=0;
		  			}
		  			if((num>=itemNum)&&num>0){
			  			var tr=$(item).parent().parent();
			  			$(tr).children('td').eq(6).text("0.0");
			  			$(tr).children('td').eq(5).text(itemNum);
			  			$("#itemMoney"+memberCardItemId).val("0.0");
			  			$("#discountTypeId"+memberCardItemId).append("<option value='4' selected='selected'>项目次卡</option>");
		  			}else{
		  				var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
		  				if(null!=itemMoney&&itemMoney!=''&&itemMoney!=undefined){
		  					itemMoney=parseFloat(itemMoney);
		  				}
			  			var tr=$(item).parent().parent();
			  			itemNum=itemNum-num;
			  			$("#itemNum").val(itemNum-num);
			  			itemMoney=itemMoney*itemNum;
			  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
			  			$(tr).children('td').eq(5).text((itemNum+num)+"/"+num);
			  			$("#discountTypeId"+memberCardItemId).append("<option value='4' selected='selected'>项目次卡</option>");
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			var memberCardItemId=$(checkbox).attr("itemId");
		  			var num=$(checkbox).attr("num");
		  			if(null!=num&&num!=''&&num!=undefined){
		  				num=parseInt(num);
		  			}else{
		  				num=0;
		  			}
		  			var itemMoney=$("#hisItemMoney"+memberCardItemId).val();
		  			
		  			var hisItemNum=$("#hisItemNum"+memberCardItemId).val();
		  			if(null!=hisItemNum&&hisItemNum!=''&&hisItemNum!=undefined){
		  				hisItemNum=parseInt(hisItemNum);
		  			}else{
		  				hisItemNum=0;
		  			}
		  			if((num>=hisItemNum)&&hisItemNum>0){
			  			$("#itemNum").val(hisItemNum);
		  			}else{
		  				$("#itemNum").val(num);
		  			}
		  			
		  			var item=$("input[type='hidden'][name='itemId'][value='"+memberCardItemId+"']")[0];
		  			var tr=$(item).parent().parent();
		  			$("#itemMoney"+memberCardItemId).val(itemMoney);
		  			$(tr).children('td').eq(5).text("0.0");
		  			$(tr).children('td').eq(6).text(formatFloat(itemMoney*hisItemNum));
		  			$("#discountTypeId"+memberCardItemId+" option[value='4']").remove();
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		});
	  //会员卡 折扣 
	  form.on('checkbox(memberCardId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='memberCardId']");
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  			var discount=$(checkbox).attr("discount");
		  			if(null!=discount&&discount!=''&&discount!=undefined){
		  				discount=parseFloat(discount);
		  			}else{
		  				discount=0;
		  			}
		  			//////////////////////////////项目 会员折扣 ////////////////////////////
		  			var itemMoneys=$("input[type='hidden'][name='hisItemMoney']");
		  			var itemIds=$("input[type='hidden'][name='itemId']");
		  			var nums=$("input[type='hidden'][name='num']");
		  			for(var i=0;i<itemIds.length;i++){
		  				var itemId=$(itemIds[i]).val();
		  				var num=$(nums[i]).val();
		  				if(null!=num&&num!=''&&num!=undefined){
		  					num=parseInt(num);
		  				}
		  				var discountType=$("#discountTypeId"+itemId).val();
		  				if(discountType==1){
		  					var itemMoney=$(itemMoneys[i]).val();
		  					var total=parseFloat(itemMoney)*num;
		  					itemMoney=total*discount/10;
		  					var disitemMoney=total-itemMoney;
		  					var item=$("input[type='hidden'][name='itemId'][value='"+itemId+"']")[0];
				  			var tr=$(item).parent().parent();
				  			$(tr).children('td').eq(5).text(formatFloat(disitemMoney));
				  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
				  			$("#itemMoney"+itemId).val(formatFloat(itemMoney));
				  			$("#discountTypeId"+itemId).append("<option value='2' selected='selected'>会员卡折扣</option>");
		  				}
		  			}
		  			///////////////////////////商品会员卡折扣///////////////////////////////
		  			var productIds=$("input[type='hidden'][name='productId']");
		  			var productMoneys=$("input[type='hidden'][name='hisProductMoney']");
		  			var productNums=$("input[type='hidden'][name='productNum']");
		  			for(var i=0;i<productIds.length;i++){
		  				var productId=$(productIds[i]).val();
		  				var num=$(productNums[i]).val();
		  				if(null!=num&&num!=''&&num!=undefined){
		  					num=parseInt(num);
		  				}
		  				var discountType=$("#pdiscountTypeId"+productId).val();
		  				if(discountType==1){
		  					var productMoney=$(productMoneys[i]).val();
		  					var total=(parseFloat(productMoney)*num);
		  					productMoney=total*discount/10;
		  					var disproductMoney=total-productMoney;
		  					var product=$("input[type='hidden'][name='productId'][value='"+productId+"']")[0];
				  			var tr=$(product).parent().parent();
				  			$(tr).children('td').eq(5).text(formatFloat(disproductMoney));
				  			$(tr).children('td').eq(6).text(formatFloat(productMoney));
				  			$("#productMoney"+productId).val(formatFloat(productMoney));
				  			$("#pdiscountTypeId"+productId).append("<option value='2' selected='selected'>会员卡折扣</option>");
		  				}
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}else{
		  			//////////////////////////////项目 取消会员卡折扣 ////////////////////////////
		  			var itemIds=$("input[type='hidden'][name='itemId']");
		  			var nums=$("input[type='hidden'][name='num']");
		  			var hisItemMoneys=$("input[type='hidden'][name='hisItemMoney']");
		  			for(var i=0;i<itemIds.length;i++){
		  				var itemId=$(itemIds[i]).val();
		  				var num=$(nums[i]).val();
		  				if(null!=num&&num!=''&&num!=undefined){
		  					num=parseInt(num);
		  				}
		  				var hisItemMoney=$(hisItemMoneys[i]).val();
		  				if(null!=hisItemMoney&&hisItemMoney!=''&&hisItemMoney!=undefined){
		  					hisItemMoney=parseFloat(hisItemMoney);
		  				}
		  				var discountType=$("#discountTypeId"+itemId).val();
		  				if(discountType==2){
		  					var itemMoney=hisItemMoney*num;
		  					var item=$("input[type='hidden'][name='itemId'][value='"+itemId+"']")[0];
				  			var tr=$(item).parent().parent();
				  			$(tr).children('td').eq(5).text("0.0");
				  			$(tr).children('td').eq(6).text(formatFloat(itemMoney));
				  			$("#itemMoney"+itemId).val(formatFloat(hisItemMoney));
				  			$("#discountTypeId"+itemId+" option[value='2']").remove();
		  				}
		  			}
					//////////////////////////////商品 取消会员卡折扣 ////////////////////////////
		  			var productIds=$("input[type='hidden'][name='productId']");
		  			var productMoneys=$("input[type='hidden'][name='productMoney']");
		  			var productNums=$("input[type='hidden'][name='productNum']");
		  			var hisProductMoneys=$("input[type='hidden'][name='hisProductMoney']");
		  			for(var i=0;i<productIds.length;i++){
		  				var productId=$(productIds[i]).val();
		  				var num=$(productNums[i]).val();
		  				if(null!=num&&num!=''&&num!=undefined){
		  					num=parseInt(num);
		  				}
		  				var hisProductMoney=$(hisProductMoneys[i]).val();
		  				if(null!=hisProductMoney&&hisProductMoney!=''&&hisProductMoney!=undefined){
		  					hisProductMoney=parseFloat(hisProductMoney);
		  				}
		  				var discountType=$("#pdiscountTypeId"+productId).val();
		  				if(discountType==2){
		  					var productMoney=hisProductMoney*num;
		  					var product=$("input[type='hidden'][name='productId'][value='"+productId+"']")[0];
				  			var tr=$(product).parent().parent();
				  			$(tr).children('td').eq(5).text(formatFloat("0.0"));
				  			$(tr).children('td').eq(6).text(formatFloat(productMoney));
				  			$("#productMoney"+productId).val(formatFloat(hisProductMoney));
				  			$("#pdiscountTypeId"+productId+" option[value='2']").remove();
		  				}
		  			}
		  			form.render('select');
		  			getOrderMoney();
		  		}
		  	})
		});
	  //经理选择 项目免单
	  form.on('select(discountTypeId)', function(data){
		    var value=data.value;
		    var itemId=$(data.elem).attr("itemId");
		    var itemMoney=$("#hisItemMoney"+itemId).val();
		    var itemMoney=validateValue(itemMoney);
		    var itemNum=$("#itemNum"+itemId).val();
		    var itemNum=validateValue(itemNum);
		    if(value==1){
		    	var item=$("input[type='hidden'][name='itemId'][value='"+itemId+"']")[0];
	  			var tr=$(item).parent().parent();
	  			$("#itemMoney"+itemId).val(itemMoney);
	  			$(tr).children('td').eq(5).text("0.0")
	  			$(tr).children('td').eq(6).text(formatFloat(itemMoney*itemNum));
	  			getOrderMoney();
		    }else{
	  			var item=$("input[type='hidden'][name='itemId'][value='"+itemId+"']")[0];
	  			var tr=$(item).parent().parent();
	  			$(tr).children('td').eq(5).text(formatFloat(itemMoney*itemNum));
	  			$(tr).children('td').eq(6).text("0.0");
	  			$("#itemMoney"+itemId).val("0.0");
	  			getOrderMoney();
		    }
		});
	  //经理选择 产品免单
	  form.on('select(pdiscountTypeId)', function(data){
		    var value=data.value;
		    var productId=$(data.elem).attr("productId");
		    var hisProductMoney=$("#hisProductMoney"+productId).val();
		    var hisProductMoney=validateValue(hisProductMoney);
		    var productNum=$("#productNum"+productId).val();
		    var productNum=validateValue(productNum);
		    if(value==1){
		    	var item=$("input[type='hidden'][name='productId'][value='"+productId+"']")[0];
	  			var tr=$(item).parent().parent();
	  			$("#productMoney"+productId).val(hisProductMoney);
	  			$(tr).children('td').eq(5).text("0.0");
	  			$(tr).children('td').eq(6).text(formatFloat(hisProductMoney*productNum));
	  			getOrderMoney();
		    }else{
	  			var item=$("input[type='hidden'][name='productId'][value='"+productId+"']")[0];
	  			var tr=$(item).parent().parent();
	  			$(tr).children('td').eq(5).text(formatFloat(hisProductMoney*productNum));
	  			$(tr).children('td').eq(6).text("0.0");
	  			$("#productMoney"+productId).val("0.0");
	  			getOrderMoney();
		    }
		});
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
});

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
	var money=$("#money").val();
	if(null!=money&&money!=''){
		money=parseFloat(money);
  		if(isNaN(money)){
  			money=0; 
  		}
  	}else{
  		 money=0;
  	}
	var discountPrice=$("#discountPrice").val();
	if(null!=discountPrice&&discountPrice!=''){
		discountPrice=parseFloat(discountPrice);
  		if(isNaN(discountPrice)){
  			discountPrice=0; 
  		}
  	}else{
  		discountPrice=0;
  	}
	var tipMoney=$("#tipMoney").val();
	if(null!=tipMoney&&tipMoney!=''){
		tipMoney=parseFloat(tipMoney);
  		if(isNaN(tipMoney)){
  			tipMoney=0; 
  		}
  	}else{
  		tipMoney=0;
  	}
	var acturePrice=(money+tipMoney)-discountPrice;
	acturePrice=formatFloat(acturePrice);
	$('#acturePrice').val(acturePrice);
	payWayFunct();
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
//计算订单总额
function getOrderMoney(){
	var params = $("#frmId").serialize();
	$.post("/startWriting/ajaxCash",params,function(data){
		data=$.parseJSON(data);
		if(data.state=="success"){
			$("#itemTotalPrice").text("￥"+formatFloat(data.totalMoney));
			$("#money").val(formatFloat(data.totalMoney));
			$("#acturePrice").val(formatFloat(data.totalMoney));
			$("#submitBut").text("确定收银（￥"+formatFloat(data.totalMoney)+")");
		}else{
			$("#itemTotalPrice").text("￥"+formatFloat("0"));
			$("#money").val(formatFloat("0"));
			$("#acturePrice").val(formatFloat("0"));
			$("#submitBut").text("确定收银（￥"+formatFloat("0")+")");
		}
		
	})
}
function validateValue(itemMoney){
 	if(itemMoney!=null&&itemMoney!=''&&itemMoney!=undefined){
    	itemMoney=parseFloat(itemMoney);
    }else{
    	itemMoney=0;
    }
 	return itemMoney;
}
</script>
</body>
</html>
