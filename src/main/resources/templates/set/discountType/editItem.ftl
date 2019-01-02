<#include "../../commons/macro.ftl">
<@commonHead/>
	<title>添加产品或项目</title>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${ctx}/widgets/auto/jquery.autocomplete.css" />
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">
		<input type="hidden" name="discountTypeItem.itemId" id="itemId" value="${discountTypeItem.itemId}"/>
		<input type="hidden" name="discountTypeItem.typeId" value="${discountTypeItem.typeId}"/>
		<input type="hidden" name="discountTypeItem.dbid" value="${discountTypeItem.dbid}"/>
		  <div class="layui-form-item">
		    <label class="layui-form-label">名称名称:</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountTypeItem.itemName" id="itemName" value="${discountTypeItem.itemName}"  readonly="readonly"  placeholder="请输入名称" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">项目原价:</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountTypeItem.price" id="itemPrice"  value="${discountTypeItem.price}"  readonly="readonly" class="layui-input">
		    </div>
		  </div>
		    <div class="layui-form-item">
		    <label class="layui-form-label">方案价格:</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountTypeItem.discountPrice" id="discountPrice" value="${discountTypeItem.discountPrice}" placeholder="请输入方案价格" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">备注信息:</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountTypeItem.note" id="note"  value="${discountTypeItem.note}" placeholder="请输入备注信息" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="btnSave" onclick="save()">立即提交</a>
		      <a id="btnClose"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
	</form>
	
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript"	src="${ctx}/javascript/startWriting.js"></script>
<script type="text/javascript">
	var form ;
	var layer ;
	var parentlayer ;
	layui.use(['form', 'layedit', 'laydate'], function(){
		  form = layui.form;
		  layer = layui.layer;
		  parentlayer = parent.layui;
	});	

	function save(){
		var params = $("#frmId").serialize();
		var target = $("#frmId").attr("target")|| "_self";
		$.ajax({url : '${ctx}/discountType/EditItemSave',data : params,async : false,timeout : 20000,dataType : "json",type : "post",
			success : function(data,textStatus,jqXHR) {
				var obj;
				if (data.message != undefined) {
					obj = $.parseJSON(data.message);
				} else {
					obj = data;
				}
				if (obj[0].mark == 1) {
					//错误
					layer.msg(obj[0].message,{icon : 5});
					$("#btnSave").bind("onclick");
					return;
				} else if (obj[0].mark == 0) {
					layer.msg(data[0].message,{icon : 1});
					if (target == "_self") {
						setTimeout(
								function() {
									window.location.href = obj[0].url
								},1000);
					}
					if (target == "_parent") {
						setTimeout(
								function() {
									var index = parent.layer.getFrameIndex(window.name);
									parent.layer.close(index);
								},1000);
					}
				}
			},
			complete : function(jqXHR,textStatus) {
				var jqXHR = jqXHR;
				var textStatus = textStatus;
			},
			beforeSend : function(jqXHR, configs) {
				$("#btnSave").unbind("onclick");
				var jqXHR = jqXHR;
				var configs = configs;
			},
			error : function(jqXHR,textStatus,errorThrown) {
				layer.msg("系统请求超时");
				$("#btnSave").bind("onclick");
			}
		});
		return false;
	}
	
	$('#btnClose').on('click', function() {
		parent.layer.closeAll(); //再执行关闭
	});

	</script>
</body>
</html>