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
<title>个人设置-修改密码</title>
<script type="text/javascript">
var isExtendsValidate = true;	//如果要试用扩展表单验证的话，该属性一定要申明
function extendsValidate(){	//函数名称，固定写法
	//密码匹配验证
	if( $('#password').val() == $('#repassword').val() ){	//匹配成功
		$('#repassword').validate_callback(null,"sucess");	//此次是官方提供的，用来消除以前错误的提示
		return true;
	}else{//匹配失败
		$('#repassword').validate_callback("密码不匹配","failed");	//此处是官方提供的API，效果则是当匹配不成功，pwd2表单 显示红色标注，并且TIP显示为“密码不匹配”
		return false;
	}
}

</script>
</head>
<body class="bodycolor">
<div class="location">
    <img src="${ctx}/images/homeIcon.png"/> &nbsp;
    <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >修改密码</a>
</div>
<hr class="layui-bg-red">
<div class="frmContent">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_self"  class="layui-form" >
		<input type="hidden" name="dbid" id="dbid" value="${user.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="width: 60px;color: red;">原密码:&nbsp;</td>
				<td ><input type="password" name="oldPassword" id="oldPassword"	value="" class="layui-input"  title="原密码" lay-verify='password' tip="原密码不能为空,字符长度为3,20"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 60px;color: red;">新密码:&nbsp;</td>
				<td ><input type="password" name="password" id="password"	value="" class="layui-input" title="新密码" lay-verify='password'	checkType="string,3,20" tip="新密码不能为空，字符长度为3,20"></td>
			</tr>
			
			<tr height="42">
				<td class="formTableTdLeft" style="width: 60px;color: red;">确认密码:&nbsp;</td>
				<td ><input type="password" name="repassword" id="repassword" class="layui-input" lay-verify='password|repassword' tip="确认密码不能为空，字符长度为3,20"></td>
			</tr>
		</table>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		    	<a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
		  	</div>
		</div>
	</form>
	</div>
</body>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload,laydate=layui.laydate
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  form.verify({
		  password: function(value){
			      if(value.length < 3){
			        return '密码不能为空,并且3-20个字符';
			      }
			      if(value.length >20){
			        return '密码不能为空,并且3-20个字符';
			      }
		    },
		    repassword:function(value){
		    	var password=$("#password").val();
		    	if(password!=value){
		    		return '两次输入密码不一致，请确认';
		    	}
		    }
	  })
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/user/updateModifyPassword');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>