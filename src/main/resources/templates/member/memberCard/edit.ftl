<#include "../../commons/macro.ftl">
<@commonHead/>

<title>会员卡管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId"   target="_parent" style="padding: 12px;">

		 <input type="hidden" name="memberCard.dbid" id="dbid" value="${memberCard.dbid }">
		 <c:if test="${empty(memberCard) }">
			 <input type="hidden" name="memberCard.entStatus" id="entStatus" value="1">
		 </c:if>
		 <c:if test="${!empty(memberCard) }">
			 <input type="hidden" name="memberCard.entStatus" id="entStatus" value="${memberCard.entStatus }">
		 </c:if>
		  <div class="layui-form-item">
			<div class="layui-inline">		  
			    <label class="layui-form-label">名称<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberCard.name" id="memberCard.name" value="${memberCard.name }" lay-verify="required" autocomplete="off" placeholder="请输入名称" class="layui-input" tip="会员卡名称不能为空">
			    </div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">会员卡类型<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			    	<select id="memberCard.bussiType" name="memberCard.bussiType" class="layui-input" lay-verify='required' tip="请选择会员卡类型">
			    		<option value="">请选择...</option>
			    		<option value="1" ${memberCard.bussiType==1?'selected="selected"':'' } >储值卡</option>
			    		<option value="2" ${memberCard.bussiType==2?'selected="selected"':'' }>创始会员卡</option>
			    	</select>
			    </div>
			 </div>
			

		  </div>
		  <div class="layui-form-item">
		  <div class="layui-inline">
			    <label class="layui-form-label">级别</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberCard.orderNum" id="orderNum"  value="${memberCard.orderNum }" placeholder="请输入序号" class="layui-input">
			    </div>
		    </div>
		  	<div class="layui-inline">
			    <label class="layui-form-label">卡首编号<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberCard.beginNo" id="beginNo" value="${memberCard.beginNo }" lay-verify="required" autocomplete="off" placeholder="请输卡首编号" class="layui-input" tip="卡首编号不能为空">
			    </div>
			 </div>
			 
		  </div>
		  <div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">金额范围<span style="color: red">*</span></label>
			    <div class="layui-input-inline" style="width: 80px;">
			      <input type="text" name="memberCard.rechargeMin" id="rechargeMin" value="${memberCard.rechargeMin }" lay-verify="floatV|rechargeMax" autocomplete="off" placeholder="金额>=" class="layui-input">
			    </div>
			     <label class="layui-form-label" style="width:15px;padding: 5px 5px;text-align: center;">--</label>
			    <div class="layui-input-inline" style="width: 80px;">
			      <input type="text" name="memberCard.rechargeMax" id="rechargeMax" value="${memberCard.rechargeMax }" lay-verify="floatV|rechargeMax" autocomplete="off" placeholder="金额<=" class="layui-input">
			    </div>
			</div>
			<div class="layui-inline">
			 <label class="layui-form-label">积分范围<span style="color: red">*</span></label>
			    <div class="layui-input-inline" style="width: 80px;">
			      <input type="text" name="memberCard.pointMin" id="pointMin" value="${memberCard.pointMin }"  autocomplete="off"  placeholder="积分>=" class="layui-input">
			    </div>
			     <label class="layui-form-label" style="width:15px;padding: 5px 5px;text-align: center;">--</label>
			    <div class="layui-input-inline" style="width: 80px;">
			      <input type="text" name="memberCard.pointMax" id="pointMax" value="${memberCard.pointMax }"  autocomplete="off" placeholder="积分<=" class="layui-input">
			    </div>
		 	</div>		  

		  </div>
		  <div class="layui-form-item">
		  	<div class="layui-inline">
			    <label class="layui-form-label">积分(元/分)<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="number" name="memberCard.consumptionPoint" id="consumptionPoint"  value="${memberCard.consumptionPoint }" placeholder="请输入积分比例" class="layui-input" lay-verify='floatV' reg='0,10000' tip="积分比例必须在0-10000之间">
			    </div>
			 </div>
			 <div class="layui-inline">
			    <label class="layui-form-label">折扣<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberCard.discount" id="discount"  value="${memberCard.discount }${empty(memberCard)==true?'0.00':''}" placeholder="请输入折扣比例" class="layui-input" lay-verify='floatV' reg='0,10' tip="折扣比例必须在0-10之间">
			    </div>
		    </div>
		  
		  	
			 
		  </div>
		  <div class="layui-form-item">
		  <div class="layui-inline">
			    <label class="layui-form-label">启用会员价<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <c:if test="${empty(memberCard)}">
		                <input type="radio" name="memberCard.enableVipprice" value="0" title="否" checked="">
		                <input type="radio" name="memberCard.enableVipprice" value="1" title="是">
	            	</c:if>
	            	<c:if test="${!empty(memberCard)}">
		                <input type="radio" name="memberCard.enableVipprice" value="0" title="否" ${memberCard.enableVipprice==0?'checked="checked"':'' } >
		                <input type="radio" name="memberCard.enableVipprice" value="1" title="是" ${memberCard.enableVipprice==1?'checked="checked"':'' }>
	            	</c:if>
			    </div>
			 </div>
		    <div class="layui-inline">
		    	<label class="layui-form-label">固定折扣<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <c:if test="${empty(memberCard)}">
		                <input type="radio" name="memberCard.enableFixedDiscount" value="0" title="否" checked="">
		                <input type="radio" name="memberCard.enableFixedDiscount" value="1" title="是">
	            	</c:if>
	            	<c:if test="${!empty(memberCard)}">
		                <input type="radio" name="memberCard.enableFixedDiscount" value="0" title="否" ${memberCard.enableFixedDiscount==0?'checked="checked"':'' } >
		                <input type="radio" name="memberCard.enableFixedDiscount" value="1" title="是" ${memberCard.enableFixedDiscount=='1'?'checked="checked"':'' }>
	            	</c:if>
			    </div>
		    </div>
		  </div>
		  <div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">免费项目数<span style="color: red">*</span></label>
			    <div class="layui-input-inline">
			      <input type="number" name="memberCard.disitemNum" id="disitemNum"  value="${memberCard.disitemNum }" placeholder="请输入项目免费数" class="layui-input" lay-verify='floatV' reg='0,10000' tip="项目免费数必须0-10000之间">
			    </div>
		    </div>
		 	<div class="layui-inline">
			 <label class="layui-form-label">图片</label>
				<div class="layui-input-inline">
				  	<input type="hidden" name="memberCard.pictture" id="fileUpload" readonly="readonly"	value="${memberCard.pictture }">
						<div class="layui-upload-list" style="float: left;">
							 <img class="layui-upload-img" id="demo1" width="100" height="60" src="${memberCard.pictture }">
							 <p id="demoText"></p>
						</div>
						<button type="button" class="layui-btn layui-btn-mini" id="test1" style="float: left;margin-top: 30px;margin-left: 5px">上传图片</button>
				</div>
		   	</div>
		   	</div>
		   	<div class="layui-form-item">
			 <div class="layui-inline">
				 <label class="layui-form-label">备注</label>
				 <div class="layui-inline">
					 <input type="text" name="memberCard.note" id="content" value="${memberCard.note }" class="layui-input" />
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

<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	//var editor=CKEDITOR.replace("content");
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
		    date: function(value,arg){
		      var canEmpty=$(arg).attr("canEmpty");
		      var tip=$(arg).attr("tip");
		      if(tip==undefined){
		    	  tip= "请选择正确日期格式";
		      }
		      if(canEmpty=='Y'&&value.length>0){
		   		  var stat=/^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		      if(canEmpty==undefined){
		    	  var stat=/^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		    },
		    selfPhone: function(value,arg){
		      var canEmpty=$(arg).attr("canEmpty");
		      var tip=$(arg).attr("tip");
		      if(tip==undefined){
		    	  tip= "请输入正确的手机号码格式";
		      }
		      if(canEmpty=='Y'&&value.length>0){
		   		  var stat=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		      if(canEmpty==undefined){
		    	  var stat=/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		    },
		    identity: function(value,arg){
		      var canEmpty=$(arg).attr("canEmpty");
		      var tip=$(arg).attr("tip");
		      if(tip==undefined){
		    	  tip="请输入正确的身份证号"
		      }
		      if(canEmpty=='Y'&&value.length>0){
		   		  var stat=/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		      if(canEmpty==undefined){
		    	  var stat=/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		    },
		    phone: function(value,arg){
		      var canEmpty=$(arg).attr("canEmpty");
		      var tip=$(arg).attr("tip");
		      if(tip==undefined){
		    	  tip= "请输入正确的座机号码格式";
		      }
		      if(canEmpty=='Y'&&value.length>0){
		   		  var stat=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
		      }
		      if(canEmpty==undefined){
		    	  var stat=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(value);
			      if(stat==false){
		    			return tip;
		    	  }
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
	  var uploadInst = upload.render({
		    elem: '#test1'
		    ,url: '/swfUpload/uploadFileLayui'
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		        $('#demo1').attr('src', result); //图片链接（base64）
		      });
		    }
		    ,done: function(res){
		      //如果上传失败
		      if(res.code > 0){
		        return layer.msg('上传失败');
		      }
		      //上传成功
		      $("#fileUpload").val(res.url);
		    }
		    ,error: function(){
		      //演示失败状态，并实现重传
		      var demoText = $('#demoText');
		      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
		      demoText.find('.demo-reload').on('click', function(){
		        uploadInst.upload();
		      });
		    }
		  });
	  //监听提交
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  //$('#content').val(CKEDITOR.instances.content.getData());
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/memberCard/save',
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