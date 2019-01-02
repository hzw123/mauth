<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>次卡充值记录</title>
</head>
<body>
<div class="page navbar js_show">
    <div class="page__bd" style="height: 100%;">
        <div class="weui-tab">
            <div class="weui-navbar">
                <div class="weui-navbar__item " onclick="window.location.href='${ctx}/memberWechat/stormMoney'">
                     会员卡充值/消费记录
                </div>
                <div class="weui-navbar__item weui-bar__item_on">
                    次卡购买记录
                </div>
            </div>
            
            <div class="weui-tab__panel">         
             <c:if test="${empty(stormMoneyOnceEntItemCards) }">
             	<div class="page msg_warn js_show">
				    <div class="weui-msg">
				        <div class="weui-msg__text-area">
				            <p class="weui-msg__desc">无充值记录<a href="javascript:void(0);" onclick="window.history.go(-1)">返回</a></p>
				        </div>
				    </div>
				</div>
             </c:if>
             <br>
			<c:forEach var="stormMoneyOnceEntItemCard" items="${stormMoneyOnceEntItemCards }">
					<div class="weui-form-preview">
					      <div class="weui-form-preview__hd">
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">充值次数</label>
					              <em class="weui-form-preview__value">${stormMoneyOnceEntItemCard.num }</em>
					          </div>
					      </div>
					      <div class="weui-form-preview__bd">
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">充值金额</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyOnceEntItemCard.money }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">充值项目</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyOnceEntItemCard.itemName }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">剩余次数</label>
					              <span class="weui-form-preview__value">
						              ${stormMoneyOnceEntItemCard.remainder }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">操作人</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyOnceEntItemCard.cashierName }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">充值时间</label>
					              <span class="weui-form-preview__value">
					              	<fmt:formatDate value="${stormMoneyOnceEntItemCard.createDate }"/> 
					              </span>
					          </div>
					      </div>
					  </div>
					  <br>
				</c:forEach>
            </div>
        </div>
    </div>
</div>
<br>

  
</body>
</html>
