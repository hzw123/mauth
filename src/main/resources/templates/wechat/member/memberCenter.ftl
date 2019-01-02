<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui.css?${now}" type="text/css" />
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <style type="text/css">
    	
   	</style>
<title>金拇指</title>
</head>
<body>
<div class="order" style="">
	<div class="head">
		<div class="headImage">
				<div class="centerImg">
					<c:if test="${empty(weixinGzuserinfo) }" >
						<img src="${ctx}/img/weixin/head_05.png" width="57" height="57"/>
					</c:if>
					<c:if test="${!empty(weixinGzuserinfo) }" >
						<img src="${weixinGzuserinfo.headimgurl }" width="57" height="57"/>
					</c:if>
				</div>
		      	<div class="centerInfo">
			      	<div class="customName">${member.name}</div>
			      	<div class="receiverManage">
			      		账号：${member.mobilePhone }
			      	</div>
		    	</div>
		</div>
	</div>
	<div class="nav">
		<ul>
              <%-- <li>
                  <a href="${ctx}/memberWechat/stormMoney">
                      <p >余额</p>
                      <p style="font-size: 14px;font-weight: bold;">
                      	${member.balance }
                      </p>
                  </a>
              </li>
              <li>
                  <a href="javascript:void(-1)">
                      <p >&nbsp;</p>
                      <p style="font-size: 14px;font-weight: bold;">
                      </p>
                  </a>
              </li>--%>
              <li>
                  <a href="${ctx}/memberWechat/myPoint">
                      <p>我的积分</p>
                      <p style="font-size: 14px;font-weight: bold;">
                      	${member.remainderPoint }
                      </p>
                  </a>
              </li> 
         </ul>
	</div>
</div>
<br>
<div class="mycenterMian" >
	  	 <article>
               <div class="mycenter">
                    <ul>
                        <li>
                            <a href="${ctx}/cashWechat/roomCash?roomId=${startWriting.roomId}">
                                 <img src="${ctx}/img/weixin/product_item17.png">
                                <p>待付款</p>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/memberWechat/myOrder">
                                <img src="${ctx}/img/weixin/myagent.png">
                                <p>我的订单</p>
                            </a>
                        </li>
                         <li>
                            <a href="${ctx}/memberWechat/stormMoney">
                                <img src="${ctx}/img/weixin/memberDis.png">
                                <p>我的充值</p>
                            </a>
                        </li>
                         <li>
                            <a href="${ctx}/memberWechat/myPoint">
                                <img src="${ctx}/img/weixin/mypoint.png">
                                <p>我的积分</p>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/memberWechat/myCoupon">
                                <img src="${ctx}/img/weixin/product_item48.png">
                                <p>我的优惠券</p>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/memberWechat/myOnlineBooking">
                                <img src="${ctx}/img/weixin/product_item27.png">
                                <p>我的预约</p>
                            </a>
                        </li>
                         
                    </ul>
                </div>
            </article>
	</div>
	<br>
	<br>
<br>
<br>
<%-- <jsp:include page="tabbar.jsp"></jsp:include> --%>
	
</body>
</html>