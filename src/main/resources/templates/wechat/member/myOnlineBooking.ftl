<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>预约记录</title>
</head>
<body>
<br>
<c:forEach var="onlineBooking" items="${onlineBookings }">
	<div class="weui-form-preview">
	      <div class="weui-form-preview__hd">
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">预约日期</label>
	              <em class="weui-form-preview__value">${onlineBooking.bookingDate }</em>
	          </div>
	      </div>
	      <div class="weui-form-preview__bd">
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">预约时间</label>
	              <span class="weui-form-preview__value">
	              	${onlineBooking.bookingTime }
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">人数</label>
	              <span class="weui-form-preview__value">
	              	${onlineBooking.maleNum }
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">技师</label>
	              <span class="weui-form-preview__value">
	              	${onlineBooking.artificerName }
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">处理结果</label>
	              <span class="weui-form-preview__value">
	              	<c:if test="${onlineBooking.startWritingStatus==1 }">
	              		正常
	              	</c:if>
	              	<c:if test="${onlineBooking.startWritingStatus==2 }">
	              		已开单
	              	</c:if>
	              	<c:if test="${onlineBooking.startWritingStatus==3 }">
	              		拒绝
	              	</c:if>
	              	<c:if test="${onlineBooking.startWritingStatus==4 }">
	              		取消
	              	</c:if>
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">处理人</label>
	              <span class="weui-form-preview__value">
	              	${onlineBooking.dealPerson }
	              </span>
	          </div>
	          <div class="weui-form-preview__item">
	              <label class="weui-form-preview__label">处理时间</label>
	              <span class="weui-form-preview__value">
	              	${onlineBooking.dealTime }
	              </span>
	          </div>
	      </div>
	  </div>
	  <br>
</c:forEach>
 
</body>
</html>
