<#include "../../commons/macro.ftl">
<@commonHead/>
<link href="${ctx}/css/weixin/yz.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/weixin/qrCode.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/weixin/yzimage.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/widgets/artDialogMaster/lib/jquery-1.10.2.js"></script>

<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack4.js"></script>
<link rel="stylesheet" href="${ctx}/widgets/artDialogMaster/css/ui-dialog.css">
<script src="${ctx}/widgets/artDialogMaster/dist/dialog-min.js"></script>
<script src="${ctx}/widgets/artDialogMaster/dist/dialog-plus-min.js"></script>
<script src="${ctx}/js/keyWordRole.js"></script>

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
<title>自动回复</title>
</head>
<body class="bodycolor">

<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">自动回复</a>
      <button class="layui-btn layui-btn-small" id="submitButCredit" onclick="editKeyWordRole()" style="float: right;margin-top: 5px;margin-right: 12px;">新建规则</button>
</div>
<hr class="layui-bg-red">
<div class="app">
		<div class="app-init-container">
			<div class="page-with-sidebar">
					<div class="page-with-sidebar-content">
						<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/weixinKeyWordRole/queryList" method="post">
							    <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
							    <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
						</form>
        				<div class="app-init">
							<div class="app__content" >
							    <div class="weixin-scan" >
							       <div id="js-rule-container" class="rule-group-container" >
							         <c:forEach var="weixinKeyWordRole" items="${page.result }" varStatus="i"> 
									      <div class="rule-group"  id="rule-group${weixinKeyWordRole.dbid }">
									      		<div class="rule-meta">
												    <h3 style=" width: 460px;">
												        <em class="rule-id">${page.totalCount-((page.currentPageNo-1)*page.pageSize+i.index)}）</em>
												        <span class="rule-name" id="rule-name${weixinKeyWordRole.dbid }">${weixinKeyWordRole.name}</span>
												        <div class="rule-opts">
												            <a href="javascript:;" class="js-edit-rule" onclick="editKeyWordRole(${weixinKeyWordRole.dbid})">编辑</a>
												            <span>-</span>
												            <a href="javascript:;" class="js-delete-rule" id="js-disable-rule${weixinKeyWordRole.dbid}" onclick="deleteWeixinKeyWordRole(${weixinKeyWordRole.dbid})" >删除</a>
												        </div>
												    </h3>
												</div>
												<div class="rule-body">
												    <div class="long-dashed"></div>
												    <div class="rule-keywords" >
												        <div class="rule-inner">
												            <h4>关键词：</h4>
												            <div class="keyword-container">
												                <c:if test="${fn:length(weixinKeyWordRole.weixinKeyWords)<=0 }" >
															     	<div class="info">
															     		还没有任何关键字!
															     	</div>
												             	</c:if>
												                <div class="keyword-list" id="keyword-list${weixinKeyWordRole.dbid }">
												                	<c:forEach items="${weixinKeyWordRole.weixinKeyWords}" var="weixinKeyWord">
														                <div class="keyword input-append" id="keyword${weixinKeyWordRole.dbid }${weixinKeyWord.dbid }" >
																		    <a href="javascript:;" class="close--circle" onclick="deleteKey(${weixinKeyWord.dbid },${weixinKeyWordRole.dbid })">×</a>
																		    <span class="value" onclick="editKey(${weixinKeyWord.dbid },${weixinKeyWordRole.dbid })">${weixinKeyWord.keyword }</span>
																		    <span class="add-on">
																		    	<c:if test="${weixinKeyWord.matchingType==1 }">
																		    		全匹配
																		    	</c:if>
																		    	<c:if test="${weixinKeyWord.matchingType==2 }">
																		    		模糊匹配
																		    	</c:if>
																			</span>
																		</div>
												                	</c:forEach>
												                </div>
												            </div>
												            <hr class="dashed">
												            <div class="opt">
												                <a href="javascript:;" class="js-add-keyword" onclick="editKey('',${weixinKeyWordRole.dbid })" id="editKey${weixinKeyWordRole.dbid }">+ 添加关键词</a>
												            </div>
												        </div>
												    </div>
												    <div class="rule-replies" >
												        <div class="rule-inner">
												            <h4>自动回复：
												                <span class="send-method">
												                    随机发送
												                </span>
												                    <!-- <div class="reply-head-opts">
												                        <a href="javascript:;" class="js-edit-send-method">编辑</a>
												                    </div> -->
												            </h4>
												            <div class="reply-container" id="reply-container${weixinKeyWordRole.dbid }" style="display: block;">
													            <div class="info" id=""  style="${fn:length(weixinKeyWordRole.weixinKeyAutoresponses)<=0?'':'display: none;'};">还没有任何回复！</div>
												                <ol class="reply-list" id="reply-list${weixinKeyWordRole.dbid }">
												                	<c:forEach var="weixinKeyAutoresponse" items="${weixinKeyWordRole.weixinKeyAutoresponses }">
												                		<li id="reply-list${weixinKeyWordRole.dbid }${weixinKeyAutoresponse.dbid}">
																			<div class="reply-cont">
																				<c:if test="${weixinKeyAutoresponse.msgtype=='image' }" var="status">
																				    <div class="picture-wrapper">
																				    	<img src="${weixinKeyAutoresponse.content }" class="js-img-preview" data-src="${weixinKeyAutoresponse.content }">
																				    </div>
																				</c:if>
																				<c:if test="${status==false }">
																				    <div class="reply-summary">
																				    	<c:if test="${weixinKeyAutoresponse.msgtype=='text' }">
																					        <span class="label label-success">文本</span>
																					        ${weixinKeyAutoresponse.content }
																				    	</c:if>
																				    	<c:if test="${weixinKeyAutoresponse.msgtype=='news' }">
																					        <span class="label label-success">图文</span>
																					        <a href=""  class="new-window">${weixinKeyAutoresponse.templatename }</a>
																				    	</c:if>
																				    </div>
																				</c:if>
																			</div>
																			<div class="reply-opts">
																				<a class="js-edit-it" href="javascript:;" id="js-add-reply${weixinKeyWordRole.dbid }${weixinKeyAutoresponse.dbid}" onclick="editMessage(${weixinKeyWordRole.dbid },${weixinKeyAutoresponse.dbid})">编辑</a> - 
																				<a class="js-delete-it" href="javascript:;" onclick="deleteAutoResponse(${weixinKeyWordRole.dbid },${weixinKeyAutoresponse.dbid})">删除</a>
																			</div>
																		</li>
												                	</c:forEach>
												                </ol>
												            </div>
												            <hr class="dashed">
												            <div class="opt">
												                <a class="js-add-reply add-reply-menu" id="js-add-reply${weixinKeyWordRole.dbid }" href="javascript:;" onclick="message(${weixinKeyWordRole.dbid })">+ 添加一条回复</a>
												                <span class="disable-opt hide">最多三条回复</span>
												            </div>
												        </div>
												   </div>
											</div>
									</div>
							</c:forEach> 
						</div>
					<div id="page"></div>
		 		</div>
		</div>
		</div>
    </div>
   </div>
   </div>
</div>
 	<form action="" id="submitFm" name="submitFm">
 		<input type="hidden" value="" name="weixinKeyAutoresponseDbid" id="weixinKeyAutoresponseDbid">
 		<input type="hidden" value="" name="weixinKeyWordRoleId" id="weixinKeyWordRoleId">
 		<input type="hidden" value="" name="msgdbid" id="msgdbid">
 		<input type="hidden" value="1" name="msgtype" id="msgtype">
 		<input type="hidden" value="" name="msgtext" id="msgtext">
 	</form>
<div class="popover-inner popover-reply" style="overflow: auto;display: none;" id="popover-inner">
    <div class="popover-content">
        <div class="form-horizontal">
            <div class="wb-sender">
                <div class="wb-sender__inner">
                    <div class="wb-sender__input">
						<div class="misc top clearfix">
						    <div class="content-actions clearfix">
						    	<div class="editor-module insert-emotion">
						            <a class="js-open-wx_emotion" data-action-type="emotion" href="javascript:;" onclick="choiceText()">输入文本</a>
						            <div class="emotion-wrapper">
						                <ul class="emotion-container clearfix"></ul>
						            </div>
						        </div>
						    	<div class="editor-module insert-emotion">
						            <a class="js-open-wx_emotion" data-action-type="emotion" href="javascript:;" onclick="selectText()">选择文本</a>
						            <div class="emotion-wrapper">
						                <ul class="emotion-container clearfix"></ul>
						            </div>
						        </div>
						        <div class="editor-module insert-image">
						            <a class="js-open-image" data-action-type="image" href="javascript:;" onclick="selectWechatMedia()">图片</a>
						            <div class="image-wrapper"></div>
						        </div>
						        <div class="editor-module insert-articles">
						            <a class="js-open-articles" data-action-type="articles" href="javascript:;" onclick="selectNewsItem()">选择图文</a>
						            <div class="articles-wrapper"></div>
						        </div>
						    </div>
						</div>
						<div class="content-wrapper" style="display: block;min-height: 120px;">
						    <textarea class="js-txta" cols="50" rows="4" id="js-txta" onkeyup="countWordNum()"></textarea>
						    <div class="js-picture-container picture-container"></div>
						    <div class="complex-backdrop" >
						    	<div class="js-complex-content complex-content" id="js-complex-content" >
						    	</div>
						    </div>
						</div>
						<div class="misc clearfix">
						    <div class="content-actions clearfix">
						        <div class="word-counter pull-right">还能输入 <i>300</i> 个字</div>
						    </div>
						</div>
				</div>
                <div class="wb-sender__actions in-editor">
                    <button class="js-btn-confirm btn btn-primary" onclick="submitForm()">确定</button>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>

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
		  deleteByIds('${ctx}/weixinAccount/delete');
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
  //完整功能
  laypage.render({
    elem: 'page',
    limit:${page.pageSize}
    ,count: ${page.totalCount}
    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    ,curr:${page.currentPageNo}
    ,jump: function(obj,first){
    	if(!first){
	      $("#currentPage").val(obj.curr);
	      $("#paramPageSize").val(obj.limit);
	      var qForm=$("#searchPageForm");
	      qForm.submit();
   	    }
    }
  });
});
</script>
</body>
</html>