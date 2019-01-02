<#include "../../commons/macro.ftl">
<@commonHead/>

<style type="text/css">
.location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
tr{
	height: 50px;
}
</style>
<title>系统设置</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">系统设置</a>
</div>
<hr class="layui-bg-red">
	<div class="alert alert-error" id="message" style="display: none;">
		<strong>提示：</strong>系统未设置企业号信息，点击<a href="${ctx}/qywxAccount/queryList">设置企业号信息</a>
	</div>
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent">
		<input type="hidden" name="systemInfo.dbid" value="${systemInfo.dbid }" id="dbid">

		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style='width:180px;color: red;'>系统名称:&nbsp;</td>
				<td ><input type="text" name="systemInfo.name" id="name"
					value="${systemInfo.name }" class="layui-input" title="用户名"	checkType="string,1" tip="系统名称"></td>
			</tr>
			<tr height="60">
				<td class="formTableTdLeft" style='width:180px'>
					后台logo:&nbsp;
					<span style="font-size: 12px;">大小:100*58</span>
				</td>
				<td>
					<input class="input-medium"  type="hidden" readonly="readonly" name="systemInfo.infoLogo" id="infoLogo" value="${systemInfo.infoLogo}" />
					<img alt="" title="" id="imgInfoLogo" src="${systemInfo.infoLogo }" width="100" height="58"></img>
					<a type="button" class="layui-btn" id="test1" onclick="uploadFile('infoLogo','imgInfoLogo')">
			          <i class="layui-icon"></i>上传图片
			        </a>
					<a  href="#modal-add-event" style="color: red;" onclick="deleteImage('infoLogo','imgInfoLogo')">清空</a>
				</td>
			</tr>
			<%-- <tr height="60">
				<td class="formTableTdLeft" style='width:180px'>
					系统用户创建是否同步微信资料
				</td>
				<td>
					<c:if test="${empty(systemInfo.wxUserStatus) }">
						<label>
							<input type="radio" id="wxUserStatus1" onclick="justWeixinAccount(this.value)" name="systemInfo.wxUserStatus" value="1" checked="checked">否
						</label>
						<label>
							<input type="radio" id="wxUserStatus2" onclick="justWeixinAccount(this.value)" name="systemInfo.wxUserStatus" value="2">创建
						</label>
					</c:if>
					<c:if test="${!empty(systemInfo.wxUserStatus) }">
						<label>
							<input type="radio" id="wxUserStatus1" onclick="justWeixinAccount(this.value)" name="systemInfo.wxUserStatus" value="1" ${systemInfo.wxUserStatus==1?'checked="checked"':'' } >否
						</label>
						<label>
							<input type="radio" id="wxUserStatus2" onclick="justWeixinAccount(this.value)" name="systemInfo.wxUserStatus" value="2" ${systemInfo.wxUserStatus==2?'checked="checked"':'' }>创建
						</label>
					</c:if>
				</td>
			</tr> --%>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
		    </div>
		  </div>
	</form>
</body>
<script type="text/javascript">
	var accountState="${accountState}";
	function deleteImage(input,imgeUrl){
		$("#"+input).val("");
		$("#"+imgeUrl).attr("src","");
	}
	function justWeixinAccount(vl){
		if(vl==2){
			if(accountState=="false"){
				$("#message").show();
				alert("设置同步微信资料失败！系统未设置微信企业号资料！");
				$("#wxUserStatus1").attr("checked",'checked');
			}
		}
	}
</script>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?="></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate;
	  if(topParent==undefined){
			 layer = layui.layer;
		  }else{
			 layer = topParent.layer;
		  }
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/systemInfo/save');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>