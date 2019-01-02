<#include "../../commons/macro.ftl">
<@commonHead/>
<title>添加|编辑菜单</title>
</head>
<body>
<blockquote class="layui-elem-quote">
		<p>如果连接需要获取关注用户信息，请再连接后面添加：?code=${weixinAccount.code }</p>
</blockquote>
<form action="" name="frmId" id="frmId"  target="_parent" class="layui-form" style="width: 92%;">
	<input type="hidden" value="${weixinMenuentity.dbid }" id="dbid" name="weixinMenuentity.dbid">
	<input type="hidden" value="${weixinMenuentityGroup.dbid }" id="weixinMenuentityGroupId" name="weixinMenuentityGroupId">
	<div class="layui-form-item">
		<label class="layui-form-label">菜单名称：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinMenuentity.name"	id="name" value="${weixinMenuentity.name }" checkType="string,1,20"  tip="请输入菜单名称！"  />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">上级菜单：</label>
		<div class="layui-input-block">
			<select id="parentId" name="parentId" >
				<option value="0" >顶级分类</option>
				${productCateGorySelect }
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">动作设置：</label>
		<div class="layui-input-block">
				<select id="type" name="weixinMenuentity.type" checkType="string,1,20"  tip="请选择动作">
					<option value="click" ${weixinMenuentity.type=='click'?'selected="selected"':'' }>消息类触发</option>
					<option value="view" ${weixinMenuentity.type=='view'?'selected="selected"':'' }>网页链接类</option>
				</select>
		</div>
	</div> 
	<div class="layui-form-item" style="display: none;">
		<label class="layui-form-label">消息类型：</label>
		<div class="layui-input-block">
			<input type="radio" value="text" name="msgType" id="msgType" />文本
	        <input type="radio" value="news" name="msgType" id="msgType" />图文
	        <input type="radio" value="expand" name="msgType"  id="msgType"/>扩展
		</div>
	</div>
	<div class="layui-form-item" >
		<label class="layui-form-label">URL或触发关键词：</label>
		<div class="layui-input-block">
			<textarea  class="layui-input" name="weixinMenuentity.url" id="url" style="height: 120px;width: 540px;"  checkType="string,1,500" tip="请输入URL或触发关键词！">${weixinMenuentity.url }</textarea>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">排序：</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input" name="weixinMenuentity.orders" id="orders"  value="${weixinMenuentity.orders }" checkType="integer" canEmpty="Y" tip="请输入排序号！"/>
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
		  submitFrm('${ctx}/weixinMenuentity/save');
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</body>
</html>
