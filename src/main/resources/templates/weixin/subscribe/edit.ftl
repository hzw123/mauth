<#include "../../commons/macro.ftl">
<@commonHead/>
<title>文本消息添加</title>
<link href="${ctx}/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"	class="skin-color" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx}/main/index" title="微商城中心" class="tip-bottom"><i
				class="icon-home"></i>微商城中心</a> 
				<a href="javascript:void(-1)" class="current">
					<c:if test="${weixinSubscribe.dbid>0 }" var="status">编辑文本消息</c:if>
					<c:if test="${status==false }">添加文本消息</c:if>
				</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
								<input type="hidden" value="${weixinSubscribe.dbid }" id="dbid" name="weixinSubscribe.dbid">
								<input type="hidden" value="${weixinSubscribe.addTime }" id="addTime" name="weixinSubscribe.addTime">
								<div class="control-group">
									<label class="control-label">消息类型：</label>
									<div class="controls">
										<select id="msgtype" class="input-xlarge"  name="weixinSubscribe.msgType" checkType="string,1,20"  tip="请选择公众号类型！" onchange="ajaxTemplate(this.value)">
											<option value="text" ${weixinSubscribe.msgType=='text'?'selected="selected"':'' }>文本消息</option>
											<option value="news" ${weixinSubscribe.msgType=='news'?'selected="selected"':'' }>图文消息</option>
											<option value="news" ${weixinSubscribe.msgType=='news'?'selected="selected"':'' }>图文消息</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">消息模板：</label>
									<div class="controls">
										<select id="templateId" name="templateId" checkType="string,1,20"  tip="请选择消息模板">
											<c:forEach var="weixinTexttemplate" items="${weixinTexttemplates }">
												<option value="${weixinTexttemplate.dbid }" ${weixinTexttemplate.dbid==weixinSubscribe.templateId?'selected="selected"':'' } >${weixinTexttemplate.templatename }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">启用状态：</label>
									<div class="controls">
										<select id="accounttype" name="weixinSubscribe.status" checkType="string,1,20"  tip="请选择启用状态！">
											<option value="">请选择...</option>
											<option value="1" ${weixinSubscribe.status==1?'selected="selected"':'' }>启用</option>
											<option value="2" ${weixinSubscribe.status==2?'selected="selected"':'' }>停用</option>
										</select>
									</div>
								</div>
								<div class="form-actions">
									<a href="javascript:void(-1)" class="btn btn-primary"	onclick="$.utile.submitForm('frmId','${ctx}/weixinSubscribe/save')">
										<i class="icon-ok-sign icon-white"></i>保存
									</a> <a class="btn btn-inverse" onclick="window.history.go(-1)">
									<i	class="icon-hand-left icon-white"></i>返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
	</div>

<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.uniform.js"></script>
<script src="${ctx}/widgets/bootstrap/select2.min.js"></script>
<script src="${ctx}/widgets/bootstrap/unicorn.js"></script>

<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack2.js"></script>


<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/swfupload.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.speed.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/js/fileupload.handlers.js"></script>

<script type="text/javascript">
$().ready(function() {
	var message="${message}";
	if(null!=message&&message!=""){
		alert(message);
		window.history.go(-1);
		return false;
	}
});
function ajaxTemplate(val){
	if(null==val||val==''){
		alert("请选择品牌");
		return false;
	}
	$("#templateId").empty();
	$.post("${ctx}/weixinSubscribe/ajaxTemplate?msgType="+val+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			$("#templateId").append(data);
		}
	});
}
</script>
</body>
</html>
