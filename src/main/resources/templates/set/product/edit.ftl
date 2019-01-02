<#include "../../commons/macro.ftl">
<@commonHead/>

<title>项目管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		 <input type="hidden" name="product.dbid" id="dbid" value="${product.dbid }">
		  <div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">编号<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.sn" id="sn" value="${product.sn }" lay-verify="required" autocomplete="off" placeholder="请输入编号" class="layui-input">
			    </div>
			</div>
			<div class="layui-inline">		  
			    <label class="layui-form-label">名称<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.name" id="product.name" value="${product.name }" lay-verify="title" autocomplete="off" placeholder="请输入名称" class="layui-input">
			    </div>
			</div>
		  </div>
		  <div class="layui-form-item">
		  	<div class="layui-inline">
			    <label class="layui-form-label">价格（元）<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.price" id="price" value="${product.price }${empty(product)==true?'0.00':''}" lay-verify="title" autocomplete="off" placeholder="请输入名称" class="layui-input">
			    </div>
			 </div>
			 <div class="layui-inline">
			    <label class="layui-form-label">类型<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			    	<select id="productCategoryDbid" name="productCategoryDbid" class="layui-input" lay-verify='required'>
			    		<option value="">请选择...</option>
			    		<c:forEach items="${productCategories }" var="productType">
			    			<option value="${productType.dbid }" ${product.productcategory.dbid==productType.dbid?'selected="selected"':'' } >${productType.name }</option>
			    		</c:forEach>
			    	</select>
			    </div>
			 </div>
		  </div>
		  <div class="layui-form-item">
		  	<div class="layui-inline">
			    <label class="layui-form-label">单位</label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.unit" id="unit"  value="${product.unit }" placeholder="请输入单位" class="layui-input">
			    </div>
			 </div>
			 <div class="layui-inline">
			    <label class="layui-form-label">规格</label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.specification" id="specification"  value="${product.specification }" placeholder="请输入规格" class="layui-input">
			    </div>
		    </div>
		  </div>
		  <div class="layui-form-item">
		   <div class="layui-inline">
			    <label class="layui-form-label">提成(元)</label>
			    <div class="layui-input-inline">
			      <input type="text" name="product.commissionNum" id="commissionNum"  value="${product.commissionNum }${empty(product)==true?'0.00':''}" placeholder="请输入提成金额" class="layui-input">
			    </div>
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
				url : '${ctx}/product/save',
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