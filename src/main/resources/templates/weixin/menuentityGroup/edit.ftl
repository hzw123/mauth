<#include "../../commons/macro.ftl">
<@commonHead/>
<title>添加|编辑菜单</title>

</head>
<body>
<form action="" name="frmId" id="frmId"  target="_parent" class="layui-form" style="width: 92%;">
	<input type="hidden" value="${weixinMenuentityGroup.dbid }" id="dbid" name="weixinMenuentityGroup.dbid">
	<input type="hidden" value="${weixinMenuentityGroup.createDate }" id="createDate" name="weixinMenuentityGroup.createDate">
	<input type="hidden" value="${weixinMenuentityGroupMatchRule.dbid }" id="weixinMenuentityGroupMatchRuleId" name="weixinMenuentityGroupMatchRule.dbid">
	<c:if test="${empty(weixinMenuentityGroup)}">
		<input type="hidden" value="2" id="type" name="weixinMenuentityGroup.type">
	</c:if>
	<c:if test="${!empty(weixinMenuentityGroup)}">
		<input type="hidden" value="${weixinMenuentityGroup.type }" id="type" name="weixinMenuentityGroup.type">
	</c:if>
	<br>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red;">菜单名称：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinMenuentityGroup.name"	id="name" value="${weixinMenuentityGroup.name }" lay-verify="required"  tip="请输入菜单名称！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red;">匹配性别：</label>
		<div class="layui-input-block">
				<select id="type" name="weixinMenuentityGroupMatchRule.sex" checkType="string,1,20"  tip="匹配性别">
					<option value="0" >请选择...</option>
					<option value="1" ${weixinMenuentityGroupMatchRule.sex=='1'?'selected="selected"':'' }>男</option>
					<option value="2" ${weixinMenuentityGroupMatchRule.sex=='2'?'selected="selected"':'' }>女</option>
				</select>
		</div>
	</div> 
	<div class="layui-form-item">
		<label class="layui-form-label" style="color: red;">客户端版本：</label>
		<div class="layui-input-block">
				<select id="type" name="weixinMenuentityGroupMatchRule.client_platform_type" checkType="string,1,20"  tip="匹配客户端版本">
					<option value="0" >请选择...</option>
					<option value="1" ${weixinMenuentityGroupMatchRule.client_platform_type=='1'?'selected="selected"':'' }>IOS</option>
					<option value="2" ${weixinMenuentityGroupMatchRule.client_platform_type=='2'?'selected="selected"':'' }>Android</option>
				</select>
		</div>
	</div> 
	<div class="layui-form-item">
		<label class="layui-form-label">匹配语言：</label>
		<div class="layui-input-block">
				<select id="language" name="weixinMenuentityGroupMatchRule.language" checkType="string,1,20"  tip="匹配客户端版本">
					<option value="0" >请选择...</option>
					<option value="zh_CN" ${weixinMenuentityGroupMatchRule.language=='zh_CN'?'selected="selected"':'' }>简体中文</option>
					<option value="zh_TW" ${weixinMenuentityGroupMatchRule.language=='zh_TW'?'selected="selected"':'' }>繁体中文TW </option>
					<option value="zh_HK" ${weixinMenuentityGroupMatchRule.language=='zh_HK'?'selected="selected"':'' }>繁体中文HK </option>
					<option value="en" ${weixinMenuentityGroupMatchRule.language=='en'?'selected="selected"':'' }>英文</option>
					<option value="id" ${weixinMenuentityGroupMatchRule.language=='id'?'selected="selected"':'' }>印尼 </option>
					<option value="ms" ${weixinMenuentityGroupMatchRule.language=='ms'?'selected="selected"':'' }>马来</option>
					<option value="es" ${weixinMenuentityGroupMatchRule.language=='es'?'selected="selected"':'' }>西班牙 </option>
					<option value="ko" ${weixinMenuentityGroupMatchRule.language=='ko'?'selected="selected"':'' }>韩国</option>
					<option value="it" ${weixinMenuentityGroupMatchRule.language=='it'?'selected="selected"':'' }>意大利</option>
					<option value="ja" ${weixinMenuentityGroupMatchRule.language=='ja'?'selected="selected"':'' }>日本</option>
					<option value="pl" ${weixinMenuentityGroupMatchRule.language=='pl'?'selected="selected"':'' }>波兰</option>
					<option value="pt" ${weixinMenuentityGroupMatchRule.language=='pt'?'selected="selected"':'' }>葡萄牙</option>
					<option value="ru" ${weixinMenuentityGroupMatchRule.language=='ru'?'selected="selected"':'' }>俄国</option>
					<option value="th" ${weixinMenuentityGroupMatchRule.language=='th'?'selected="selected"':'' }>泰文</option>
					<option value="vi" ${weixinMenuentityGroupMatchRule.language=='vi'?'selected="selected"':'' }>越南</option>
				</select>
		</div>
	</div> 
	<div class="layui-form-item" >
		<label class="layui-form-label">备注：</label>
		<div class="layui-input-block">
			<textarea  class="layui-input" name="weixinMenuentityGroup.note" id="note" style="height: 120px;width: 540px;"  checkType="string,1,500" tip="请输入URL或触发关键词！">${weixinMenuentityGroup.note }</textarea>
		</div>
	</div>
	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</button>
	      <a id="closeBut"  class="layui-btn layui-btn-primary" >取消</a>
	    </div>
	  </div>
</form>

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
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitFrm('${ctx}/weixinMenuentityGroup/save');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</body>
</html>
