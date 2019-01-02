<#include "../../commons/macro.ftl">
<@commonHead/>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<style type="text/css">
.location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
.layui-form-label{
	width: 120px;
}
</style>
<title>代金券管理</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png" style="margin-bottom: 5px;"/>
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">代金券管理</a>-
		<a href="javascript:void(-1);">
		导出代金券
	</a>
</div>
<hr class="layui-bg-red">
<br>
	<div class="layui-bg-red" style="height: 30px;line-height: 30px;padding: 5px">
			<strong>提示:</strong> 请先选择导出数据条件。
	</div>
<br>
	
	<form action="" name="frmId" id="frmId"  target="_self" class="layui-form" >

		<input type="hidden" name="couponMember.dbid" id="dbid" value="${couponMember.dbid }">
		 <div class="layui-form-item">
			 <label class="layui-form-label">券类型:&nbsp;</label>
			 <div class="layui-input-inline">
				<select id="couponMemberTemplateId" name="couponMemberTemplateId" class="layui-input" onchange="ajaxCouponMemberTemplate(this.value)" >
					<option>请选择券类型...</option>
					<c:forEach var="couponMemberTemplate" items="${couponMemberTemplates }">
						<option value="${couponMemberTemplate.dbid }" ${couponMember.couponMemberTemplate.dbid==couponMemberTemplate.dbid?'selected="selected"':'' } >${couponMemberTemplate.name }</option>
					</c:forEach>
				</select>
			</div>
			<label class="layui-form-label">是否使用：&nbsp;</label>
			  <div class="layui-input-inline">
					<select class="text large" id="isUse" name="isUse" onchange="$('#searchPageForm')[0].submit()">
						<option value="-1">请选择...</option>
						<option value="0" ${param.isUse==0?'selected="selected"':'' }>未使用</option>
						<option value="1" ${param.isUse==1?'selected="selected"':'' }>使用</option>
					</select>
			</div>
		</div>
		 <div class="layui-form-item">
		 	 <label class="layui-form-label">创建时间开始:&nbsp;</label>
			 <div class="layui-input-inline">
				<input type="text" class="layui-input" name="startTime" id="startTime" readonly="readonly"  onFocus="WdatePicker()"  value="${couponMember.start_time }" />
			 </div>	
			 <label class="layui-form-label">创建时间结束:&nbsp;</label>
			 <div class="layui-input-inline">	
				<input type="text" class="layui-input" name="endTime" id="endTime" readonly="readonly"  onFocus="WdatePicker()"  value="${couponMember.start_time }" />
			</div>
		 </div>
		 <div class="layui-form-item">
		 	 <label class="layui-form-label">使用时间开始:&nbsp;</label>
			 <div class="layui-input-inline">
				<input type="text" class="layui-input" name="useStartTime" id="useStartTime" readonly="readonly"  onFocus="WdatePicker()"   />
			 </div>	
			 <label class="layui-form-label">使用时间结束:&nbsp;</label>
			 <div class="layui-input-inline">	
				<input type="text" class="layui-input" name="useEndTime" id="useEndTime" readonly="readonly"  onFocus="WdatePicker()"  />
			</div>
		 </div>
		 <br>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
				<a href="javascript:void(-1)"	onclick="exportExcel('frmId')"	class="layui-btn">导出EXCEL</a> 
			    <a href="javascript:void(-1)"	onclick="window.history.go(-1)" class="layui-btn layui-btn-primary">取&nbsp;&nbsp;消</a>
			</div>
		</div>
	</form>
</body>


<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script type="text/javascript">
layui.use(['form', 'layedit', 'laydate','upload'], function(){
  var form = layui.form,upload = layui.upload
  ,layer = layui.layer,laydate=layui.laydate,
  parentlayer = parent.layui;
  var $ = layui.$;
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length < 2){
        return '名称至少得2个字符啊';
      }
    }
  });
  laydate.render({
    elem: '#startTime'
    ,type: 'date'
  });
  laydate.render({
    elem: '#endTime'
    ,type: 'date'
  });
  laydate.render({
    elem: '#useStartTime'
    ,type: 'date'
  });
  laydate.render({
    elem: '#useEndTime'
    ,type: 'date'
  });
})
function exportExcel(searchFrm){
 	var params;
 	if(null!=searchFrm&&searchFrm!=undefined&&searchFrm!=''){
 		params=$("#"+searchFrm).serialize();
 	}
 	window.location.href='${ctx}/couponMember/exportExcel?'+params;
 }
</script>
</html>