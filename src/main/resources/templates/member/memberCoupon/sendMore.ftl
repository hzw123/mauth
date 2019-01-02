<#include "../../commons/macro.ftl">
<@commonHead/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="${ctx}/css/global.css" rel="stylesheet" />
    <link href="${ctx}/css/main.css" rel="stylesheet" />
<title>发送代金券</title>
</head>
<body>
<div class="location">
      <img src="../../images/homeIcon.png" /> &nbsp;
      <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
      <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/member/queryList'">会员管理</a>-
      <a href="javascript:void(-1);" onclick="">发送代金券</a>
</div>
<br>
<br>
	<form action="" name="frmId" id="frmId"  target="_parent" class="layui-form" >

		 <input type="hidden" name="memberIds" id="memberIds" value="${memberIds }">
		  <div class="layui-form-item">
		    <label class="layui-form-label">接受会员<span style="color: red">*</span></label>
		     <div class="layui-input-block">
		     	<c:forEach var="memberName" items="${memberNames }">
		     		<span class="layui-badge-rim" style="padding: 2px 8px;border: 1px solid #01AAED;color: #01AAED;border-radius: 14px;margin-right: 5px;margin-bottom: 2px; ">${memberName }</span>
		     	</c:forEach>
		     	<a href="${ctx}/member/queryList" class="layui-table-link" >去筛选</a>
				<p class="help-desc">
					<div class="layui-form-mid layui-word-aux">
		                您正准备向 <span>${fn:length(memberNames)}</span> 人发送代金券
					</div>
	            </p>
			</div>
		  </div>
		  <div class="layui-form-item">
			    <label class="layui-form-label">优惠券类型<span style="color: red">*</span></label>
			    <div class="layui-input-block">
			    <select id="templateId" name="templateId" class="layui-input" lay-filter="templateId" lay-verify='required' tip="优惠券类型不能为空">
					<option>请选择优惠券...</option>
					<c:forEach var="couponMemberTemplate" items="${couponMemberTemplates }">
						<option value="${couponMemberTemplate.dbid}" data-price="${couponMemberTemplate.price}" data-name="${couponMemberTemplate.name}" data-note="${couponMemberTemplate.description}">${couponMemberTemplate.name }</option>
					</c:forEach>
				</select>
			</div>
		  </div>
		  <div class="layui-form-item">
			    <label class="layui-form-label">名称<span style="color: red">*</span></label>
			    <div class="layui-input-block">
			      <input type="text" name="couponMember.name" id="name" readonly="readonly"
					value="${couponMember.name }" class="layui-input" title="名称" placeholder="请输入优惠券名称"	lay-filter=""  lay-verify='required' tip="名称不能为空">
			    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">金额<span style="color: red;">*</span></label>
		    <div class="layui-input-block">
		      <input type="text" readonly="readonly" class="layui-input" name="couponMember.money" id="money"  value="${couponMember.money }" checkType="float" lay-verify='required'  tip="必须输入数字!"  />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">数量<span style="color: red;">*</span></label>
		    <div class="layui-input-block">
		      <input type="text" class="layui-input" name="couponMember.count" id="count"  value="1" checkType="float" lay-verify='required'  tip="必须输入数字!"  />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">生效时间<span style="color: red;">*</span></label>
		    <div class="layui-input-inline">
		      <input type="text"  class="layui-input" name="couponMember.start_time" id="startTime"  value="${couponMember.start_time}" lay-verify='required'    tip="必须选择生效开始时间!"  />
		    </div>
		   <label class="layui-form-label">失效时间<span style="color: red;">*</span></label>
		    <div class="layui-input-inline">
		  		<input type="text" class="layui-input" value="${couponMember.stopTime }" id="stopTime" name="couponMember.stopTime" lay-verify='required'    tip="必须选择生效结束时间!"  />
			</div>
		  </div>
		  
		  <div class="layui-form-item">
		   	<label class="layui-form-label">发券理由<span style="color: red;">*</span></label>
		   	<div class="layui-input-block">
		   		<input type="text" class="layui-input" name="couponMember.reason" id="reason"  value="${couponMember.reason }"   />
		  	</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">发送代金券</button>
		      <a id="closeBut"  class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
		    </div>
		  </div>
	</form>
</body>

<script type="text/javascript"	src="${ctx}/widgets/jquery.min.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript">
layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,laydate=layui.laydate;
		 layer = layui.layer;
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
		    }
	  });
	  
		//时间选择器
	  laydate.render({
	    elem: '#startTime'
	    ,type: 'date'
	  });
	  laydate.render({
	    elem: '#stopTime'
	    ,type: 'date'
	  });
	  form.on('select(templateId)', function(data){
		 var selected= $("#templateId option:selected");
		 var name=selected.attr("data-name");
		 var price=selected.attr("data-price");
		 var note=selected.attr("data-note");
		 console.info("name:"+name+",price:"+price+",note:"+note);
		  $("#name").val(name);
		  $("#money").val(price);
		  $("#reason").val(note);
		});
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/couponMember/saveMore',
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
	function automember(id){
		var id1 = "#"+id;
			$(id1).autocomplete("${ctx}/member/ajaxCoup",{
				max: 20,      
		        width: 130,    
		        matchSubset:false,   
		        matchContains: true,  
				dataType: "json",
				parse: function(data) {   
			    	var rows = [];      
			        for(var i=0; i<data.length; i++){      
			           rows[rows.length] = {       
			               data:data[i]       
			           };       
			        }       
			   		return rows;   
			    }, 
				formatItem: function(row, i, total) {   
			       return "<span>会员名称："+row.name+"&nbsp;&nbsp;&nbsp;联系电话："+row.mobilePhone+"&nbsp;&nbsp;门店："+row.enterpriseName+"</span>";   
			    },   
			    formatMatch: function(row, i, total) {   
			       return row.name;   
			    },   
			    formatResult: function(row) {   
			       return row.name;   
			    }		
			});
		$(id1).result(function(event, data, formatted){
			$("#memberName").val(data.name);
			$("#memberId").val(data.dbid);
		});
		//计算总金额
	}
</script>
</html>