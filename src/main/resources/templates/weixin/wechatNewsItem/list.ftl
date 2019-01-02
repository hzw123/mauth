<#include "../../commons/macro.ftl">
<@commonHead/>
<title>关注时回复</title>
<link rel="stylesheet" href="${ctx}/wxcss/css/block.css"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/global.css?da=${now}"/>
<link rel="stylesheet" href="${ctx}/wxcss/css/font-awesome.min.css"/>
</head>
<style type="text/css">
 .location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
.layui-table th{
	text-align: center;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >微信图文</a>
</div>
<hr class="layui-bg-red">
<div class="alert alert-error" style="color: red;">
	提示：微信图文将图文信息同步至微信公众号后台，请先配置好微信公众账号。
</div>
<div class="block-wrap">
  <div class="tj msg-list">
    <!-- list开始 -->
      <div id="addAppmsg" class="tc add-access"> 
	      <a href="${ctx}/WeixinNewsItem/add" class="dib vm add-btn">+单图文消息</a>
	      <a href="${ctx}/WeixinNewsItem/addMore" class="dib vm add-btn multi-access">+多图文消息</a>
      </div>
	 <c:forEach var="WeixinNewsTemplate" items="${wechatNewsTemplates }">
	 	<c:if test="${WeixinNewsTemplate.type==1 }">
	 	  <div class="msg-item-wrapper">
                <div class="msg-item">
                  <h4 class="msg-t"><a href="" class="i-title" >${WeixinNewsTemplate.title }</a></h4>
                  <p class="msg-meta"><span class="msg-date">${fn:substring(WeixinNewsTemplate.addtime,0,10) }</span></p>
                  <c:forEach items="${WeixinNewsTemplate.wechatNewsitems }" var="WeixinNewsItem">
                 	 <div class="cover">
	                    <p class="default-tip" style="display:none">封面图片</p>
	                   	 	<img src="${WeixinNewsItem.thumbUrl }" class="i-img" style=""> </div>
	                  	<p class="msg-text">${WeixinNewsItem.digest }</p>
                  </c:forEach>
                </div>
                <div class="msg-opr">
                  <ul class="f0 msg-opr-list">
                    <li class="b-dib opr-item"><a class="block tc opr-btn edit-btn" href="${ctx}/WeixinNewsItem/edit?dbid=${WeixinNewsTemplate.dbid}" data-rid="7200"><span class="th vm dib opr-icon edit-icon">编辑</span></a></li>
                    <li class="b-dib opr-item"><a class="block tc opr-btn del-btn" href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/WeixinNewsItem/delete?dbids=${WeixinNewsTemplate.dbid}','searchPageForm')" data-rid="7200"><span class="th vm dib opr-icon del-icon">删除</span></a></li>
                  </ul>
                </div>
              </div>
         </c:if>
         <c:if test="${WeixinNewsTemplate.type==2 }">
         	<div class="msg-item-wrapper">
                <div class="msg-item multi-msg">
                	<c:forEach var="WeixinNewsItem" items="${WeixinNewsTemplate.wechatNewsitems }" varStatus="i">
                		<c:if test="${i.index==0 }" var="status">
		                  <div class="appmsgItem">
		                    <h4 class="msg-t"><a href="${ctx}/WeixinNewsItem/editMore?dbid=${WeixinNewsTemplate.dbid}" class="i-title" >${WeixinNewsItem.title }</a></h4>
		                    <p class="msg-meta"><span class="msg-date">${fn:substring(WeixinNewsTemplate.addtime,0,10) }</span></p>
		                    <div class="cover">
		                      <p class="default-tip" style="display:none">封面图片</p>
		                      <img src="${WeixinNewsItem.thumbUrl }" class="i-img" style=""> </div>
		                    <p class="msg-text"></p>
		                  </div>
	                  </c:if>
	                  <c:if test="${status==false }">
		                  <div class="rel sub-msg-item appmsgItem"> 
		                  	<span class="thumb"> <img src="${WeixinNewsItem.thumbUrl }" class="i-img" style=""> </span>
		                    <h4 class="msg-t"> <a href="${ctx}/WeixinNewsItem/editMore?dbid=${WeixinNewsTemplate.dbid}"  class="i-title">${WeixinNewsItem.title }</a> </h4>
		                  </div>
                      </c:if>                                  	   
	               </c:forEach>
               </div>
                <div class="msg-opr">
                  <ul class="f0 msg-opr-list">
                    <li class="b-dib opr-item"><a class="block tc opr-btn edit-btn" href="${ctx}/WeixinNewsItem/editMore?dbid=${WeixinNewsTemplate.dbid}"><span class="th vm dib opr-icon edit-icon">编辑</span></a></li>
                      <li class="b-dib opr-item"><a class="block tc opr-btn del-btn" href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/WeixinNewsItem/delete?dbids=${WeixinNewsTemplate.dbid}','searchPageForm')" data-rid="7200"><span class="th vm dib opr-icon del-icon">删除</span></a></li>
                  </ul>
                </div>
              </div>
         </c:if>
	 </c:forEach>	   
  	  <!-- list结束 -->
  </div>
</div>


<script src="${ctx}/widgets/bootstrap/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script src="${ctx}/wxcss/js/global.js"></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
layui.use(['laypage', 'table','layer','form'], function(){
  var table = layui.table,laypage=layui.laypage;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  var $ = layui.$, active = {
	  setTop:function(){
		  deleteByIds('${ctx}/weixinTexttemplate/delete');
	  }
  };
  $('.layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
  
 //触发事件
  $('.layui-btn').on('click', function(){
	    var othis = $(this), method = othis.data('method');
	    active[method] ? active[method].call(this, othis) : '';
	});
});
</script>
</body>
</html>
