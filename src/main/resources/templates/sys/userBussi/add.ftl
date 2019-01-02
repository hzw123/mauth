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
.formTableTdLeft{
	text-align: right;
	padding-right: 12px;
}
tr{
	height: 50px;
}
.depDiv{
	width: 325px;overflow: hidden;
}
.fillBox{
	margin-left: -8px;
}
.fillAddr{
    background: none repeat scroll 0 0 #E5E5E5;
    border-radius: 2px;
    cursor: default;
    float: left;
    margin: 5px 7px 2px 8px;
    padding: 0 5px;
    white-space: nowrap;
}
.fillAddr b{
	font-weight: normal;
}
.addrDel {
    background: url("../images/bizmail/ico_biz0c07e5.png") no-repeat scroll -60px 3px rgba(0, 0, 0, 0);
    border-radius: 2px;
    display: inline-block;
    height: 13px;
    margin: -3px 0 0 -4px;
    overflow: hidden;
    vertical-align: middle;
    width: 13px;
}
</style>
<title>用户添加</title>
</head>
<body class="bodycolor">
<div class="location">
   	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
   	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
    <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/userBussi/queryBussiList'">商户账号管理</a>-
    <a href="javascript:void(-1);" >创建用户</a>
</div>
<hr class="layui-bg-red">
	<form action="" name="frmId" id="frmId"  target="_self" class="layui-form" >

		<input type="hidden" name="user.dbid" id="dbid" value="">
		<input type="hidden" name="staff.dbid" id="dbid" value="">
		<input type="hidden" name="type" id="type" value="1">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">用户ID:&nbsp;</td>
				<td ><input type="text" name="user.userId" id="userId" 	value="" class="layui-input" title="用户ID" lay-verify="userId"></td>
				<td rowspan="4" colspan="2" style="text-align: center;">
						<input type="hidden" name="staff.photo" id="fileUpload" readonly="readonly"	value="">
						<div class="layui-upload-list">
						    <img class="layui-upload-img" id="demo1" width="300" height="180">
						    <p id="demoText"></p>
						  </div>
						<button type="button" class="layui-btn" id="test1">上传头像</button>
				 </td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" style="color: red;">所属公司:&nbsp;</td>
				<td>
					<select id="enterpriseId" name="enterpriseId" class="layui-input" checkType="integer,1" tip="请选择所属公司">
						<option>请选择...</option>
						<c:forEach var="enterprise" items="${enterprises }">
							<option value="${enterprise.dbid }">${enterprise.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" >所在部门:&nbsp;</td>
				<td colspan="3">
					<div class="depDiv">
						<div id="selectedDeptFill" class="fillBox" ids=" 5179943"> 
						</div>
						<a ck="addDeptDlg" style="line-height:28px;font-size:12px;text-decoration:underline;cursor: pointer;" onclick="getSelectedDepartment('selectedDeptFill');">添加</a>
				 	</div>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" >岗位:&nbsp;</td>
				<td colspan="3">
					<div class="depDiv">
						<div id="selectedPositionFill" class="fillBox" ids=" 5179943"> 
						</div>
						<a ck="addDeptDlg" style="line-height:28px;font-size:12px;text-decoration:underline;cursor: pointer;" onclick="getSelectedPosition('selectedPositionFill');">添加</a>
				 	</div>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft"  style="color: red;">联系人姓名:&nbsp;</td>
				<td ><input type="text" name="user.realName" id="realName"
					value="" class="layui-input" title="姓名"	lay-verify="realName"></td>
				<td class="formTableTdLeft" >微信号:&nbsp;</td>
				<td >
					<input type="text" name="user.wechatId" id="wechatId"	value="" class="layui-input" >
					
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" style="color: red;">联系人手机:&nbsp;</td>
				<td><input type="text" name="user.mobilePhone" id="mobilePhone"
					value="" class="layui-input"  lay-verify='required'></td>
				<td class="formTableTdLeft" style="width: 120px;">联系人座机:&nbsp;</td>
				<td><input type="text" name="user.phone" id="phone"
					value="" class="layui-input"  ></td>
			</tr>
			<tr height="42">
			    <td class="formTableTdLeft" style="width: 120px;">联系人邮箱:&nbsp;</td>
				<td ><input type="text" name="user.email" id="email" value="" class="layui-input" title="邮箱"	></td>
			    <td class="formTableTdLeft" style="width: 120px;">联系人QQ:&nbsp;</td>
				<td ><input type="text" name="user.qq" id="qq"	value="" class="layui-input" title="QQ号"	></td>
			</tr>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton2">保存并分配权</button>
		      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
		    </div>
		  </div>
	</form>
</body>
<script type="text/javascript">
	function showCompanyDiv(value){
		if(value>1){
			$("#showCompanyDiv").show();
		}else{
			$("#showCompanyDiv").hide();
		}
	}
</script>

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
		      if(value.length < 2){
		        return '用户ID不能为空,并且2-20个字符';
		      }
		      if(value.length >20){
		        return '用户ID不能为空,并且2-20个字符';
		      }
	    },
	    realName: function(value){
		      if(value.length < 2){
		        return '真实姓名不能为空,并且2-20个字符';
		      }
		      if(value.length >20){
		        return '真实姓名不能为空,并且2-20个字符';
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
		  $('#type').val(1);
		  submitFrm('${ctx}/userBussi/saveAddBussi');
		  return false
	  });
	//监听提交
	  form.on('submit(submitButton2)', function(data){
		  $('#type').val(2);
		  submitFrm('${ctx}/userBussi/saveAddBussi')
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>