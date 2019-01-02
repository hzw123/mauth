<#include "../../commons/macro.ftl">
<@commonHead/>
<link rel="stylesheet" type="text/css"  href="${ctx}/css/weixin.css"/>
<title>积分记录</title>
</head>
<body>

<div class="score_show">
    <div class="score_show_available">可用积分：<span>
	    <c:if test="${empty(member) }" var="status">
	    	无
	    </c:if>
	    <c:if test="${status==false }">
	    ${member.remainderPoint }
	    </c:if>
    </span></div>
</div>
<div class="scoreSelect" id="tab-container">
	 <a class="score_select_p" href="javascript:void(-1)"  onclick="window.location.href='${ctx}/memberWechat/myPoint?wechat_id=${param.wechat_id }&selectType=0'" id="tab1">积分明细</a>
    <div class="score_select_line">
      <div class="score_select_linel"></div>
    </div>
    <a class="score_select_p" href="javascript:void(-1)"  onclick="window.location.href='${ctx}/memberWechat/myPoint?wechat_id=${param.wechat_id }&selectType=1'" id="tab2">积分收入</a>
    <div class="score_select_line">
      <div class="score_select_linel"></div>
    </div>
    <a class="score_select_p"href="javascript:void(-1)"  onclick="window.location.href='${ctx}/memberWechat/myPoint?wechat_id=${param.wechat_id }&selectType=2'" id="tab3">积分支出</a>
</div>

<c:if test="${empty(pointRecords) }">
	<div id="message" class="alert alert-success" style="margin:14px;">
		<strong>提示：</strong>您当前无积分记录！
  	</div>
</c:if>
<c:if test="${!empty(pointRecords) }">
<div class="score_list">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tbody><tr class="score_list_head">
        <th  scope="col">名称</th>
        <th  scope="col">时间</th>
        <th  scope="col">积分数</th>
      </tr>
      <c:forEach var="pointRecord" items="${pointRecords }" varStatus="i">
      <c:if test="${i.index<(fn:length(pointRecords)-1) }" var="status">
	     <tr>
	       <td style="border-bottom: 1px solid  #BFBFBF;padding: 5px 0"  class="left">${pointRecord.note }</td>
	       <td style="border-bottom: 1px solid  #BFBFBF;">${pointRecord.num }</td>
	       <td style="border-bottom: 1px solid  #BFBFBF;"><fmt:formatDate value="${pointRecord.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
	     </tr>
      </c:if>
      <c:if test="${status==false }">
	     <tr >
	       <td style="padding: 5px 0" class="left">${pointRecord.note }</td>
	       <td>${pointRecord.num }</td>
	       <td><fmt:formatDate value="${pointRecord.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
	     </tr>
      </c:if>
      </c:forEach>
    </tbody></table>
  </div>
</c:if>
  
</body>
</html>
