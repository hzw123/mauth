<#include "../../commons/macro.ftl">
<@commonHead/>

<title>产品管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		<c:set value="${entProduct.product }" var="product"></c:set>
		 <input type="hidden" name="productId" id="productId" value="${product.dbid }">
		 <input type="hidden" name="entProduct.dbid" id="dbid" value="${entProduct.dbid }">
		  <table class="layui-table">
			     <colgroup>
			      <col width="160">
			      <col width="240">
			      <col width="160">
			      <col width="240">
			    </colgroup>
			    <tbody>
			      <tr>
				      	<th>编号</th>
				        <td>
				        	 ${product.sn } 
				        </td>
				       <td>名称</td>
				       <td> 
				       		${product.name }
				       	</td>
			     </tr>
			      <tr>
				      	<th>类型</th>
				        <td>
				        	${product.productcategory.name}
				        </td>
				       <td>价额</td>
				       <td> 
				       		 ${item.price }
				       </td>
			     </tr>
			      <tr>
				      	<th>单位</th>
				        <td>
				        	${product.unit}
				        </td>
				       <td>规格</td>
				       <td> 
				       		 ${item.specification }
				       </td>
			     </tr>
			      <tr>
				      	<th>备注</th>
				        <td colspan="3">
				        	${product.note}
				        </td>
			     </tr>
		    </tbody>
	    </table>
	    <br>
		 <div class="layui-form-item">
		    <label class="layui-form-label">价格（元）<span style="color: red">*</span></label>
		    <div class="layui-input-block">
		      <input type="text" name="entProduct.price" id="price" value="${entProduct.price }" lay-verify="title" autocomplete="off" placeholder="请输入名称" class="layui-input">
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label">提成（元）<span style="color: red">*</span></label>
		    <div class="layui-input-block">
		      <input type="text" name="entProduct.commissionNum" id="commissionNum" value="${entProduct.commissionNum }" lay-verify="title" autocomplete="off" placeholder="请输入提成金额" class="layui-input">
		    </div>
		 </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">序号</label>
		    <div class="layui-input-block">
		      <input type="text" name="entProduct.orderNum" id="orderNum"  value="${entProduct.orderNum }" placeholder="请输入序号" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <input type="text" name="entProduct.note" id="note"  value="${entProduct.note }" placeholder="请输入序号" class="layui-input">
		    </div>
		  </div>
		  
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">立即提交</button>
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
	        return '名称至少得2个字符啊';
	      }
	    }
	  });
	  
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/entProduct/save',
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