<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>订单记录</title>
</head>
<body>
<br>
 <c:if test="${empty(startWritings) }">
 <div class="page msg_warn js_show">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">无订单信息</h2>
            <p class="weui-msg__desc">无开单记录</p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
            	
                <a href="javascript:history.back();" class="weui-btn weui-btn_default">返回</a>
            </p>
        </div>
    </div>
</div>
</c:if>
<c:forEach var="startWriting" items="${startWritings }">
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
	      <div class="weui-form-preview__ft">
	          <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="${ctx}/memberWechat/orderDetail?startWritingId=${startWriting.dbid}">明细</a>
	      </div>
	  </div>
	  <br>
</c:forEach>
 
</body>
</html>
