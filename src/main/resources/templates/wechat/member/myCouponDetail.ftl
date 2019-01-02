<#include "../../commons/macro.ftl">
<@commonHead/>
 <link rel="stylesheet" href="${ctx}/weui/style/weui2.0.css?${now}" type="text/css" />
<title>${couponMember.name} 明细</title>
</head>
<body>
<div class="container" id="container">
	<article class="weui-article">
		<div class="page__hd">
	        <h1 class="page__title">${couponMember.name}</h1>
	        <p class="page__desc" style="color: #1AAD19">
	        	操作人：<span >${couponMember.creatorName }&#12288;</span>
	        	<span style="color: #1AAD19"><fmt:formatDate value="${couponMember.createTime}" pattern="yyyy-MM-dd"/></span>
	        </p>
	    </div>
    	<section>
            <section>
                <p>
                     <img src="${couponMember.image }" alt="">
                </p>
            </section>
        </section>
        <div class="page__hd">
	        <p class="page__desc" >
	        	<c:if test="${couponMember.type==1 }">
               		代金券 &nbsp;&nbsp;&nbsp;  抵扣金额：${couponMember.money }
               	</c:if>
               	<c:if test="${couponMember.type==2 }">
               		免费券
               	</c:if>
	        </p>
	        <p class="page__desc" style="color: #1AAD19">
	        	<span>有效期:<fmt:formatDate pattern="yyyy年MM月dd日" value="${couponMember.start_time }"/>~<fmt:formatDate pattern="yyyy年MM月dd日" value="${couponMember.stopTime }"/></span>
	        </p>
	        <p class="page__desc" >
			    <span>剩余张数：${couponMember.remainder }</span>
		    </p>
	    </div>
		<p>
			${couponMember.reason}
		</p>
	</article>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</html>