<#include "../../commons/macro.ftl">
<@commonHead/>

<title>折扣类型</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">
		<input type="hidden" name="discountType.roleType" value="1" />
		  <input type="hidden" name="discountType.dbid" id="dbid" value="${discountType.dbid }">
		  <div class="layui-form-item">
		    <label class="layui-form-label">名称</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountType.name" id="discountType.name" value="${discountType.name }" lay-verify="title" autocomplete="off" placeholder="请输入名称" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">支付方式:</label>
		    <div class="layui-input-block">
		      <select id="paywayId" name="discountType.paywayId" class="layui-input" lay-verify='required'>
				<c:forEach items="${payways }" var="payway">
					<option value="${payway.dbid }" ${discountType.paywayId==payway.dbid?'selected="selected"':'' }>${payway.name }
					</option>
				</c:forEach>
			  </select>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">序号</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountType.orderNum" id="orderNum"  value="${discountType.orderNum }" placeholder="请输入序号" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <input type="text" name="discountType.note" id="discountType.note" value="${discountType.note }"  class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit=""	lay-filter="submitButton">立即提交</button>
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
	</form>
</body>

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
	        return '名称至少得2个字符!';
	      }
	    }
	  });
	  
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/discountType/TypeSave',
				data : params, 
				async : false, 
				timeout : 20000, 
				dataType : "json",
				type:"post",
				success : function(data, textStatus, jqXHR){
					//alert(data.message);
					var obj;
					if(data.message!=undefined){
						obj=$.parseJSON(data.message);
					}else{
						obj=data;
					}
					if(obj[0].mark==1){
						//错误
						layer.msg(obj[0].message,{icon: 5});
						$("#submitBut").bind("onclick");
						return ;
					}else if(obj[0].mark==0){
						layer.msg(data[0].message,{icon: 1});
						if (target == "_self") {
							setTimeout(
									function() {
										window.location.href = obj[0].url
									}, 1000);
						}
						if (target == "_parent") {
						 setTimeout(
								function() {
									window.parent.frames["contentUrl"].location=obj[0].url;
									parent.layer.closeAll();
								},1000) 
						}
						// 保存数据成功时页面需跳转到列表页面
					}
				},
				complete : function(jqXHR, textStatus){
					var jqXHR=jqXHR;
					var textStatus=textStatus;
				}, 
				beforeSend : function(jqXHR, configs){
					$("#submitBut").unbind("onclick");
					var jqXHR=jqXHR;
					var configs=configs;
				}, 
				error : function(jqXHR, textStatus, errorThrown){
						layer.msg("系统请求超时");
						$("#submitBut").bind("onclick");
				}
			});
	    return false;
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	 
	  
	});
</script>
</html>