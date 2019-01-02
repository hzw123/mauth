<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>我的优惠券</title>
</head>
<body>
<div class="page navbar js_show">
    <div class="page__bd" style="height: 100%;">
          <div class="weui-tab__panel">
           <c:if test="${empty(couponMembers) }">
	           <br>
	           <br>
	           	<div class="page msg_warn js_show">
				    <div class="weui-msg">
				        <div class="weui-msg__text-area">
				            <p class="weui-msg__desc">无优惠券<a href="javascript:void(0);" onclick="window.history.go(-1)">返回</a></p>
				        </div>
				    </div>
				</div>
           </c:if>
           <br>
			<c:forEach var="couponMember" items="${couponMembers }">
				<div class="weui-panel weui-panel_access" onclick="window.location.href='${ctx}/memberWechat/myCouponDetail?couponMemberId=${couponMember.dbid }'">
		            <div class="weui-panel__bd">
		                <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
		                    <div class="weui-media-box__hd">
			                        <img class="weui-media-box__thumb" src="${couponMember.image }" alt="" height="100px;" width="100px">
		                    </div>
		                    <div class="weui-media-box__bd">
		                        <h4 class="weui-media-box__title">${couponMember.name }</h4>
		                        <p class="weui-media-box__desc">
		                        	<c:if test="${couponMember.type==1 }">
		                        		代金券 &nbsp;&nbsp;&nbsp;  抵扣金额：${couponMember.money } &nbsp;&nbsp;&nbsp;剩余张数:${couponMember.remainder }
		                        	</c:if>
		                        	<c:if test="${couponMember.type==2 }">
		                        		免费券&nbsp;&nbsp;&nbsp;剩余张数:${couponMember.remainder }
		                        	</c:if>
		                        </p>
		                        <p class="weui-media-box__desc">
		                        	<span>有效期:<fmt:formatDate pattern="yyyy年MM月dd日" value="${couponMember.start_time }"/>~<fmt:formatDate pattern="yyyy年MM月dd日" value="${couponMember.stopTime }"/></span>
		                        </p>
		                    </div>
		                </a>
		            </div>
		            <%-- <div class="weui-panel__ft">
		                <span  class="weui-cell weui-cell_access weui-cell_link">
                        	 <c:if test="${now>couponMember.stopTime }">
						    	<span style="margin-right: 2px;color: #E2E0E0">已过期</span>
						    </c:if>
						     <c:if test="${now>=couponMember.start_time&& now<=couponMember.stopTime}">
						     <span style="color: #E2E0E0 ">已使用</span>
						    	<c:if test="${couponMember.isUsed==true }" var="statuss">
						    			<span style="color: #E2E0E0 ">已使用</span>
						    	</c:if>
						    	<c:if test="${statuss==false }">
						    		<span style=" margin-right: 2px;">未使用</span>
						    	</c:if>
						    </c:if>
		                </span>    
		            </div> --%>
		        </div>
			</c:forEach>
          </div>
      </div>
</div>
<br>

  
</body>
</html>
