<#include "../../commons/macro.ftl">
<@commonHead/>

<link rel="stylesheet" href="${ctx}/widgets/auto/jquery.autocomplete.css" />
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
.formTableTdLeft{
	text-align: right;
	padding-right: 12px;
}
tr{
	height: 50px;
}
</style>
<title>个人设置-编辑个人信息</title>
</head>
<body class="bodycolor">
<div class="location">
    <img src="${ctx}/images/homeIcon.png"/> &nbsp;
    <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >修改个人信息</a>
</div>
<hr class="layui-bg-red">
<div class="frmContent">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_self" class="layui-form" >

		<input type="hidden" name="user.dbid" id="dbid" value="${user.dbid }">
		<input type="hidden" name="staffId" id="dbid" value="${staff.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" >用户ID:&nbsp;</td>
				<td ><input type="text" name="user.userId" id="userId"
					value="${user.userId }" class="layui-input" readonly="readonly" title="用户ID" ></td>
				<td rowspan="5" colspan="2" style="text-align: center;">
						<input type="hidden" name="staff.photo" id="fileUpload" readonly="readonly"	value="${staff.photo }">
						<div class="layui-upload-list">
						    <img class="layui-upload-img" id="demo1" width="300" height="180" src="${staff.photo}">
						    <p id="demoText"></p>
						  </div>
						<button type="button" class="layui-btn" id="test1">上传头像</button>
				 </td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">姓名:&nbsp;</td>
				<td ><input type="text" name="user.realName" id="realName"
					value="${user.realName }" class="layui-input" title="姓名"	lay-verify='required'>
				</td>
			</tr>
				<tr height="50">
				<td class="formTableTdLeft" >性别:&nbsp;</td>
				<td>
					<input type="radio" id="sex1"  name="staff.sex" ${staff.sex=='男'?'checked="checked"':'' }  value="男" title="男">
					<input type="radio" id="sex2"  name="staff.sex" ${staff.sex=='女'?'checked="checked"':'' } value="女" title="女">
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" >生日:&nbsp;</td>
				<td ><input type="text" name="staff.birthday" id="birthday"
					value="${staff.birthday }" readonly="readonly" class="layui-input" onFocus="WdatePicker({isShowClear:false,readOnly:true})"></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" >微信号:&nbsp;</td>
				<td ><input type="text" name="user.wechatId" id="wechatId"	value="${user.wechatId }" class="layui-input" ></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" >手机:&nbsp;</td>
				<td><input type="text" name="user.mobilePhone" id="mobilePhone"
					value="${user.mobilePhone }" class="layui-input"  checkType="mobilePhone" canEmpty="Y" tip="请输入正确的手机号"></td>
				<td class="formTableTdLeft" >座机:&nbsp;</td>
				<td><input type="text" name="user.phone" id="phone"
					value="${user.phone }" class="layui-input"  checkType="phone"  canEmpty="Y" tip="请输入正确的座机号"></td>
			</tr>
			<tr height="42">
			    <td class="formTableTdLeft" >邮箱:&nbsp;</td>
				<td ><input type="text" name="user.email" id="email"
					value="${user.email }" class="layui-input" title="邮箱"	checkType="email" canEmpty="Y" tip="请输入正确的邮箱"></td>
				<td class="formTableTdLeft" style="width: 120px;">QQ:&nbsp;</td>
				<td ><input type="text" name="user.qq" id="qq"
					value="${user.qq }" class="layui-input" title="QQ号"	checkType="string,3,20" canEmpty="Y" tip="请输入正确的QQ号"></td>
			</tr>
			<tr height="42">
			    <td class="formTableTdLeft" style="width: 120px;">现居住地地址:&nbsp;</td>
				<td ><input type="text" name="staff.nowAddress" id="nowAddress"
					value="${staff.nowAddress }" class="layui-input" title="现居住地地址"	checkType="string,1,100" canEmpty="Y" tip="请输入正确的现居住地地址"></td>
			    <td class="formTableTdLeft" style="width: 120px;">户籍地址:&nbsp;</td>
				<td ><input type="text" name="staff.familyAddress" id="familyAddress"
					value="${staff.familyAddress }" class="layui-input" title="户籍地址"	checkType="string,1,100" canEmpty="Y" tip="请输入正确的户籍地址"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" >学历:&nbsp;</td>
				<td >
					<select class="layui-input" id="educationalBackground" name="staff.educationalBackground" style="width: 120px;">
						<option value="小学"  ${staff.educationalBackground=='小学'?'selected="selected"':'' } >小学</option>
						<option value="初中生" ${staff.educationalBackground=='初中生'?'selected="selected"':'' }>初中生</option>
						<option value="高中生" ${staff.educationalBackground=='高中生'?'selected="selected"':'' }>高中生</option>
						<option value="中专生" ${staff.educationalBackground=='中专生'?'selected="selected"':'' }>中专生</option>
						<option value="大专生" ${staff.educationalBackground=='大专生'?'selected="selected"':'' }>大专生</option>
						<option value="本科生" ${staff.educationalBackground=='本科生'?'selected="selected"':'' }>本科生</option>
						<option value="硕士生" ${staff.educationalBackground=='硕士生'?'selected="selected"':'' }>硕士生</option>
						<option value="博士生" ${staff.educationalBackground=='博士生'?'selected="selected"':'' }>博士生</option>
						<option value="其它" ${staff.educationalBackground=='其它'?'selected="selected"':'' }>其它</option>
					</select>
				</td>
				<td class="formTableTdLeft" >毕业学校:&nbsp;</td>
				<td colspan="1"><input type="text" name="staff.graduationSchool" id="graduationSchool"
					value="${staff.graduationSchool }" class="layui-input" title="毕业学校"></td>
			</tr>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
		    </div>
		  </div>
	</form>
</div>
</body>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>

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
	//普通图片上传
	  var uploadInst = upload.render({
	    elem: '#test1'
	    ,url: '/swfUpload/uploadFileLayui'
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo1').attr('src', result); //图片链接（base64）
	      });
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	        return layer.msg('上传失败');
	      }
	      //上传成功
	      $("#fileUpload").val(res.url);
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
	      });
	    }
	  });
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/user/saveEditSelf');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>