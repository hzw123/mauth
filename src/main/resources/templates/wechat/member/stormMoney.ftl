<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
	<title>会员卡充值记录</title>
</head>
<body>
<div class="page navbar js_show">
    <div class="page__bd" style="height: 100%;">
        <div class="weui-tab">
            <div class="weui-navbar">
                <div class="weui-navbar__item weui-bar__item_on">
                    会员卡充值/消费记录
                </div>
                <div class="weui-navbar__item" onclick="window.location.href='${ctx}/memberWechat/stormOnceItemMoney'">
                    次卡购买记录
                </div>
            </div>
            
            <div class="weui-tab__panel">
             <br>
             <br>
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
			          <div class="weui-form-preview__item">
			              <label class="weui-form-preview__label">创始会员余额</label>
			              <em class="weui-form-preview__value">${member.startBalance }</em>
			          </div>
			      </div>
			</div>
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
					              <label class="weui-form-preview__label">已用次数</label>
					              <span class="weui-form-preview__value">
					              <c:if test="${stormMoneyOnceEntItemCard.num>=stormMoneyOnceEntItemCard.consumpiontNum}" var="status">
					              		${stormMoneyOnceEntItemCard.consumpiontNum }
					              </c:if>
					              	<c:if test="${status==false }">
					              		${stormMoneyOnceEntItemCard.num }
					              	</c:if>
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">剩余次数</label>
					              <span class="weui-form-preview__value">
					              	<c:if test="${status==true }" var="status">
						              	${stormMoneyOnceEntItemCard.num-stormMoneyOnceEntItemCard.consumpiontNum }
					              	</c:if>
					              	<c:if test="${status==false }">
					              		0
					              	</c:if>
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
             <c:if test="${empty(stormMoneyMemberCards) }">
             	<div class="page msg_warn js_show">
				    <div class="weui-msg">
				        <div class="weui-msg__text-area">
				            <p class="weui-msg__desc">无充值记录<a href="javascript:void(0);" onclick="window.history.go(-1)">返回</a></p>
				        </div>
				    </div>
				</div>
             </c:if>
             <br>
			<c:forEach var="stormMoneyMemberCard" items="${stormMoneyMemberCards }">
					<div class="weui-form-preview">
					      <div class="weui-form-preview__hd">
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">${stormMoneyMemberCard.typeName }</label>
					              <em class="weui-form-preview__value">¥${stormMoneyMemberCard.money }</em>
					          </div>
					      </div>
					      <div class="weui-form-preview__bd">
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">金额</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyMemberCard.money }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">赠送金额</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyMemberCard.giveMoney }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">操作人</label>
					              <span class="weui-form-preview__value">
					              	${stormMoneyMemberCard.cashierName }
					              </span>
					          </div>
					          <div class="weui-form-preview__item">
					              <label class="weui-form-preview__label">充值时间</label>
					              <span class="weui-form-preview__value">
					              	<fmt:formatDate value="${stormMoneyMemberCard.createDate }"/> 
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
