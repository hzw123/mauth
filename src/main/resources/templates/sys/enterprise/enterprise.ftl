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
</style>
<title>企业信息</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">企业信息管理</a>
</div>
<hr class="layui-bg-red">
<form action="" name="frmId" id="frmId"  target="_self" class="layui-form">
		<input type="hidden" name="enterprise.dbid" value="${enterprise.dbid }" id="dbid">

		<div class="layui-form-item">
	    	<label class="layui-form-label" style="color: red">分店名称</label>
	     	<div class="layui-input-inline">
			<input type="text" name="enterprise.allName" id="allName"	value="${enterprise.allName }" class="layui-input" title="用户名"	lay-verify="title">
			</div>
	    	<label class="layui-form-label" style="color: red">编码</label>
	     	<div class="layui-input-inline">
			<input type="text" name="enterprise.no" id="no"	value="${enterprise.no }" class="layui-input" title="编码"	lay-verify="require">
			</div>
	    	<label class="layui-form-label" style="color: red">分店类型</label>
	     	<div class="layui-input-inline">
	     		<select id="entType" name="enterprise.entType" class="layui-input" lay-verify='required' tip="请选择会员卡类型">
		    		<option value="">请选择...</option>
		    		<option value="1" ${enterprise.entType==1?'selected="selected"':'' } >直营店</option>
		    		<option value="2" ${enterprise.entType==2?'selected="selected"':'' }>加盟店</option>
		    	</select>
			</div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label" style="color: red;">简称</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.name" id="name"	value="${enterprise.name }" class="layui-input" title="用户名" autocomplete="off"	lay-verify="title" >
		    </div>
		    <label class="layui-form-label" style="color: red;">电话</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.phone" id="phone" value="${enterprise.phone }" class="layui-input" title="电话" autocomplete="off"	lay-verify="required" >
		    </div>
		    <label class="layui-form-label" >传真</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.fax" id="fax" value="${enterprise.fax }" class="layui-input" title="传真" >
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label" >地址</label>
		    <div class="layui-input-inline" style="width: 500px;">
				<input type="text" name="enterprise.address" id="address"	value="${enterprise.address }" class="layui-input" title="地址">
		    </div>
		    <label class="layui-form-label" >邮编</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.zipCode" id="zipCode" value="${enterprise.zipCode }" class="layui-input" title="邮编"  >
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label" >网址</label>
		    <div class="layui-input-inline" style="width: 500px;">
				<input type="text" name="enterprise.webAddress" id="webAddress" value="${enterprise.webAddress }" class="layui-input" >
		    </div>
		    <label class="layui-form-label" >email</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.email" id="email" value="${enterprise.email }" class="layui-input" >
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label" >开户银行</label>
		    <div class="layui-input-inline" style="width: 500px;">
				<input type="text" name="enterprise.bank" id="bank"	value="${enterprise.bank }" class="layui-input"  title="开户银行" >
		    </div>
		    <label class="layui-form-label" >账号</label>
		    <div class="layui-input-inline">
				<input type="text" name="enterprise.account" id="account"	value="${enterprise.account }" class="layui-input" >
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label" >备注</label>
		     <div class="layui-input-block">
		      <textarea placeholder="请输入备注" id="note" name="enterprise.content" class="layui-textarea">${enterprise.content }</textarea>
		    </div>
		 </div>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">立即提交</a>
		    </div>
		  </div>
	</form>
</body>

<script type="text/javascript" src="${ctx}/widgets/utile/comm.js?date=${now}"></script>
<script type="text/javascript" src="${ctx}/layui/layui.js"></script>
<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //自定义验证规则
	  form.verify({
	    title: function(value){
	      if(value.length < 2){
	        return '名称至少得2个字符啊';
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
		  submitFrm('/enterprise/saveEnterprise')
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>