<#include "../../commons/macro.ftl">
<@commonHead/>
<title>公众账号设置添加</title>
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
.layui-form-label{
	width: 140px;
}
.layui-input-block{
	margin-left: 170px;
}
</style>
</head>
<body>
<div class="location">
     <img src="${ctx}/images/homeIcon.png"/> &nbsp;
     <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1)" class="current">
		设置公众账号
	</a>
</div>
<hr class="layui-bg-red">
<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
	<input type="hidden" value="${weixinAccount.dbid }" id="dbid" name="weixinAccount.dbid">
	<input type="hidden" value="${weixinAccount.addtoekntime }" id="addtoekntime" name="weixinAccount.addtoekntime">
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">名称：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accountname"	id="accountname" value="${weixinAccount.accountname }" lay-verify="required"  tip="请输入公众号名称！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">帐号TOKEN：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accounttoken"	id="accounttoken" value="${weixinAccount.accounttoken }" lay-verify="required"  tip="请输入公众号TOKEN！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">公众微信号：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accountnumber"	id="accountnumber" value="${weixinAccount.accountnumber }" lay-verify="required"  tip="请输入公众微信号！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">原始ID：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.weixinAccountid"	id="weixinAccountid" value="${weixinAccount.weixinAccountid }" lay-verify="required"  tip="请输入公众原始ID！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red">公众号类型：</label>
		<div class="layui-input-block">
			<select id="accounttype" class="layui-input" name="weixinAccount.accounttype" lay-verify="required"  tip="请选择公众号类型！">
				<option value="1" ${weixinAccount.accounttype==1?'selected="selected"':'' }>服务号</option>
				<option value="2" ${weixinAccount.accounttype==2?'selected="selected"':'' }>订阅号</option>
			</select>
		</div>
	</div>
	  <div class="layui-form-item">
		<label class="layui-form-label" style="color: red">电子邮箱：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accountemail"	id="accountemail" value="${weixinAccount.accountemail }" lay-verify="required|email"  tip="请输入电子邮箱！"  />
		</div>
	</div>
	  <div class="layui-form-item">
		<label class="layui-form-label" style="color: red">公众帐号APPID：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accountappid"	id="accountappid" value="${weixinAccount.accountappid }" lay-verify="required"  tip="请输入公众帐号APPID！"  />
			<span style="color: red">AppID(应用ID)</span>
		</div>
	</div>
	  <div class="layui-form-item">
		<label class="layui-form-label" style="color: red">公众帐号APPSECRET：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinAccount.accountappsecret"	id="accountappsecret" value="${weixinAccount.accountappsecret }" lay-verify="required"  tip="请输入公众帐号APPSECRET！"  />
			<span style="color: red">AppSecret(应用密钥)</span>
		</div>
	</div>
	<c:if test="${empty(weixinAccount) }">
	  <div class="layui-form-item">
		<label class="layui-form-label" style="color: red">Code：</label>
		<div class="layui-input-block">
			<input readonly="readonly" class="layui-input" name="weixinAccount.code"	id="code" value="${code }" lay-verify="required" />
		</div>
	</div>
	</c:if>
	<c:if test="${!empty(weixinAccount) }">
	  <div class="layui-form-item">
		<label class="layui-form-label" style="color: red">Code：</label>
		<div class="layui-input-block">
			<input readonly="readonly" class="layui-input" name="weixinAccount.code"	id="code" value="${weixinAccount.code }" checkType="string,1,500"  />
			<span style="color: red">用于配置微信公众平台服务器连接地址：http://www.XXX.com/wechatAuth/auth?code=XXX</span>
		</div>
	</div>
	</c:if>
	 <div class="layui-form-item">
		<label class="layui-form-label">公众帐号描述：</label>
		<div class="layui-input-block">
			<textarea type="text" class="layui-input" name="weixinAccount.accountdesc"	id="accountdesc" >${weixinAccount.accountdesc }</textarea>
		</div>
	</div>
  	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
	      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
	    </div>
	  </div>
</form>


<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/widgets/charscode.js"></script>

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
	//时间选择器
	  laydate.render({
	    elem: '#birthday'
	    ,type: 'date'
	  });
	  
	  //自定义验证规则
	  form.verify({
		 userId: function(value){
		      if(value.length < 3){
		        return '用户ID不能为空,并且3-20个字符';
		      }
		      if(value.length >20){
		        return '用户ID不能为空,并且3-20个字符';
		      }
	    },
	    realName: function(value){
		      if(value.length < 2){
		        return '真实姓名不能为空,并且2-5个字符';
		      }
		      if(value.length >5){
		        return '真实姓名不能为空,并且2-5个字符';
		      }
	    },
	    integer:function(value){
	    	if(value!=""){
		    	if (!(/^([-]){0,1}([0-9]){1,}$/.test(value))) {
		    		return "序号必须为数字";
		    	}
	    	}
	    }
	  });
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/weixinAccount/saveEdit');
		  return false
	  });
	});
</script>
</body>
</html>
