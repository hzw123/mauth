<#include "../../commons/macro.ftl">
<@commonHead/>

<title>会员卡管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		 <input type="hidden" name="couponMemberTemplateId" id="couponMemberTemplateId" value="${couponMemberTemplate.dbid }">
		<c:forEach var="map" items="${maps }">
			<c:set value="${map.key }" var="itemType"></c:set>
			<c:set value="${map.value }" var="items"></c:set>
			<fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">
			  <legend>${itemType.name }</legend>
			  <div class="layui-form-item">
			    <div class="layui-input-block">
			    <c:forEach var="item" items="${items }">
			    	<c:set value="false" var="status"></c:set>
			    	<c:forEach var="memberCardDisItem" items="${couponMemberTemplateItems }">
			    		<c:if test="${item.dbid==memberCardDisItem.itemId }">
			    			<c:set value="true" var="status"></c:set>
			    		</c:if>
			    	</c:forEach>
			    	<c:if test="${status==true }">
				       <input type="checkbox" name="itemId" value="${item.dbid }" lay-skin="primary" title="${item.name }" checked="checked">
			    	</c:if>
			    	<c:if test="${status==false }">
				       <input type="checkbox" name="itemId" value="${item.dbid }" lay-skin="primary" title="${item.name }">
			    	</c:if>
			    </c:forEach>
			    </div>
			   </div>
			</fieldset>
		</c:forEach>
		<br>
		<br>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">立即提交</a>
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
	</form>
</body>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var productIds=$("input:[type='checkbox']:checked");
		  if(productIds.length<=0){
			  layer.msg("请选择商品"); 
			  return ;
		  }
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/couponMemberTemplate/saveSelectItem',
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
										parent.layer.closeAll();
									}, 1000);
						}
						if (target == "_parent") {
						 setTimeout(
								function() {
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