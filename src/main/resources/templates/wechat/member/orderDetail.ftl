<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>订单明细</title>
</head>
<body>
<br>
	<div class="weui-form-preview">
	      <div class="weui-form-preview__hd">
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">付款金额</label>
	              <em class="weui-form-preview__value">¥${startWriting.actualMoney }</em>
	          </div>
	      </div>
	      <div class="weui-form-preview__bd">
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">订单号</label>
	              <span class="weui-form-preview__value">
	              	${startWriting.orderNo }
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">开单时间</label>
	              <span class="weui-form-preview__value">
	              	<fmt:formatDate value="${startWriting.createTime }"/> 
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">房间名称</label>
	              <span class="weui-form-preview__value">
	              	${startWriting.roomName }
	              </span>
	          </div>
<%-- 	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">付款方式</label>
	              <span class="weui-form-preview__value">
	              	${startWriting.payWay }
	              </span>
	          </div> --%>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">打赏金额</label>
	              <span class="weui-form-preview__value">
	              	${startWriting.tipMoney }
	              </span>
	          </div>
	      </div>
	  </div>
	  <br>
	<c:if test="${!empty(startWritingItems) }">
		 <div class="weui-form-preview__bd">
	       <div class="weui-form-preview__item">
	            <label class="weui-form-preview__label" style="font-size: 15px;color: #000;">项目</label>
	            <span class="weui-form-preview__value"  style="font-size: 15px;color: #000;">
	           数量&nbsp;&nbsp;&nbsp;&nbsp;原价/折扣/实收
	            </span>
	       </div>
		 <c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
	       <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">${startWritingItem.itemName }</label>
	              <span class="weui-form-preview__value">
	              	${startWritingItem.num }&nbsp;&nbsp;&nbsp;&nbsp;
	              	${startWritingItem.money*startWritingItem.num }/<span style="color: red;">${startWritingItem.discountMoney }</span>/
	              	${startWritingItem.actualMoney }
	              </span>
	       </div>
	      </c:forEach>
	     </div>
	</c:if>
</body>
</html>
