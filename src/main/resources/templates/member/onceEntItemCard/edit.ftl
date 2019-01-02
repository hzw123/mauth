<#include "../../commons/macro.ftl">
<@commonHead/>

<title>次卡套餐</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		 <input type="hidden" name="onceEntItemCard.dbid" id="dbid" value="${onceEntItemCard.dbid }">
		  <div class="layui-form-item">
		    <label class="layui-form-label">名称<span style="color: red">*</span></label>
		    <div class="layui-input-block">
		      <input type="text" name="onceEntItemCard.name" id="onceEntItemCard.name" value="${onceEntItemCard.name }" lay-verify="required" autocomplete="off" placeholder="请输入次卡名称" class="layui-input" tip="次卡名称不能为空">
		    </div>
		  </div>
		  <div class="layui-form-item">
			    <label class="layui-form-label">办卡价格（元）<span style="color: red">*</span></label>
			    <div class="layui-input-block">
			      <input type="number" name="onceEntItemCard.price" id="price"  value="${onceEntItemCard.price }" placeholder="请输入办卡价格" class="layui-input" lay-verify='floatV' reg='0,100000' tip="办卡价格必须在0-100000之间">
			    </div>
		  </div>
		  <div class="layui-form-item">
			    <label class="layui-form-label">包含次数<span style="color: red">*</span></label>
			    <div class="layui-input-block">
			      <input type="number" name="onceEntItemCard.num" id="num"  value="${onceEntItemCard.num }" placeholder="请输入包含次数" class="layui-input" lay-verify='floatV' reg='1,10000' tip="包含次数必须在0-10000之间">
			    </div>
		  </div>
		  <div class="layui-form-item">
			    <label class="layui-form-label">提成金额</label>
			    <div class="layui-input-block">
			      <input type="number" name="onceEntItemCard.commissionNum" id="commissionNum"  value="${onceEntItemCard.commissionNum }${empty(onceEntItemCard)==true?'0.0':''}" placeholder="请输入积分比例" class="layui-input" lay-verify='floatV' canEmpty="Y" reg='0,10000' tip="提成金额必须在0-10000之间">
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

<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  //自定义验证规则
	   form.verify({
		    required: function(value,arg){
		   		var tip=$(arg).attr("tip");
		    	if(tip==undefined){
		    	  tip= "必填项不能为空";
		      }
	    	 	var stat=/[\S]+/.test(value);
			    if(stat==false){
		    		return tip;
		    	}
		    },
		    floatV:function(value,arg){
		    	 var canEmpty=$(arg).attr("canEmpty");
			      var tip=$(arg).attr("tip");
			      if(tip==undefined){
			    	  tip= "请输入正确的数字格式";
			      }
			      if(canEmpty=='Y'&&value.length>0){
			   		  var stat=/^([-]){0,1}([0-9]){1,}([.]){0,1}([0-9]){0,}$/.test(value);
				      if(stat==false){
			    			return tip;
			    	  }
			      } 
			      if(canEmpty==undefined){
			    	  var stat=/^([-]){0,1}([0-9]){1,}([.]){0,1}([0-9]){0,}$/.test(value);
				      if(stat==false){
			    			return tip;
			    	  }
			      }
			    	var reg=$(arg).attr("reg");
						if(null!=reg&&reg!=undefined){
							var arr = reg.split(",");
							var smallFloat = parseFloat(arr[0]);
							var bigFloat = parseFloat(arr[1]);
							if(value<smallFloat)
							{
								return "输入值必须在【"+smallFloat+"】-【"+bigFloat+"】之间";
							}
							if(value > bigFloat)
							{
								return "输入值必须在【"+smallFloat+"】-【"+bigFloat+"】之间";
							}
						}
		    },
		    rechargeMax:function(value,arg){
		    	var rechargeMax=$("#rechargeMax").val();
		    	var rechargeMin=$("#rechargeMin").val();
		    	if((rechargeMax!=undefined&&rechargeMax!='')&&(rechargeMin!=undefined&&rechargeMin!='')){
		    		rechargeMax=parseFloat(rechargeMax);
		    		rechargeMin=parseFloat(rechargeMin);
		    		if(rechargeMin<0){
		    			return "储值金额起始不能小于0";
		    		}
		    		if(rechargeMin>rechargeMax){
		    			return "储值金额起始不能大于储值金额最大值";
		    		}
		    	}
		    }
		});
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/onceEntItemCard/save',
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