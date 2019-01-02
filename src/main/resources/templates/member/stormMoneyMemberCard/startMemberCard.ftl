<#include "../../commons/macro.ftl">
<@commonHead/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

</head>
<body>
<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId" target="_parent">
    	<input type="hidden" value="${member.dbid }" id="memberId" name="memberId">
    	<input type="hidden" id="paywayJson" name="paywayJson" value="">
    	<div class="layui-form-item">
            <label class="layui-form-label label-required">累计储值<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
	                <input readonly="readonly" type="number" name="stormMoneyMemberCard.totalStartMoney" id="startTotalMoney" value="${member.totalStartMoney }" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">充值金额<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
	                <input type="number" name="stormMoneyMemberCard.money" id="money" value="0" onkeyup="totalStormMoney();balance()" lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">赠送金额<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
                <input type="number" name="stormMoneyMemberCard.giveMoney" id="giveMoney" value="0" onkeyup="balance()" lay-verify="required" placeholder="会员姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">技师</label>
            <div class="layui-input-inline" style="width:450px">
            	<select id="artificerId" name="artificerId" class="layui-input">
            		<option value="-1">请选择...</option>
            		<c:forEach var="artificer" items="${artificers }">
	            		<option value="${artificer.dbid }" >${artificer.name}---- 售卡次数：0</option>
            		</c:forEach>
            	</select>
            </div>
        </div>
       <div class="layui-form-item" pane="">
		    <label class="layui-form-label">支付方式</label>
		    <div class="layui-input-block">
		      <c:forEach var="payWay" items="${payWays }">
			      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="${payWay.dbid }" title="${payWay.name }" onclick="payWay()">
		      </c:forEach>
		    </div>
		</div>
		<div id="payWayDiv">
		</div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-inline" style="width:450px">
                <textarea name="stormMoneyMemberCard.note" placeholder="备注信息" autocomplete="off" class="layui-textarea">${stormMoneyMemberCard.note }</textarea>
            </div>
        </div>
        <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn layui-btn-disabled" id="submitBut" lay-submit="" lay-filter="submitButton" >立即提交</button>
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
    </form>
</div>
</body>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload;
	  if(topParent==undefined){
		 layer = layui.layer;
	  }else{
		 layer = topParent.layer;
	  }
	  var $ = layui.$;
	  var laydate = layui.laydate;
      //日期
      laydate.render({
          elem: '#birthday'
      });
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
	   form.on('checkbox(payWayId)', function(data){
		   var checkeds = $("input[type='checkbox'][name='payWayId']");
		  	var html='';
		  	$.each(checkeds, function(i, checkbox) {
		  		if (checkbox.checked) {
		  		 	html=html+createHtml(checkbox.value,checkbox.title);
		  		}
		  	});
		  	$("#payWayDiv").text("");
		  	$("#payWayDiv").append(html);
		  	payWayFunct();
		});
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var attr=$("#submitBut").attr("class");
		  if(attr.indexOf("layui-btn-disabled")>0){
			 return false;
		  }
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/stormMoneyMemberCard/saveStartMemberCard',
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
									var ifme=window.parent.frames["contentUrl"];
									if(ifme==undefined){
										window.location.href = obj[0].url
									}else{
										window.parent.frames["contentUrl"].location=obj[0].url;
									}
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
	$("#submitBut").unbind();
	function totalStormMoney(){
		var money=$("#money").val();
		if(null!=money&&money!=''){
			money=parseFloat(money);
	  		if(isNaN(money)){
	  			money=0; 
	  		}
	  	}else{
	  		 money=0;
	  	}
		$("#giveMoney").val(money);
		
		var totalStormMoney="${member.totalStartMoney}";
		if(null!=totalStormMoney&&totalStormMoney!=''){
			totalStormMoney=parseFloat(totalStormMoney);
	  		if(isNaN(totalStormMoney)){
	  			totalStormMoney=0; 
	  		}
	  	}else{
	  		totalStormMoney=0;
	  	}
		var total=totalStormMoney+money;
		total=formatFloat(total);
		$('#totalStormMoney').text("")
		$('#totalStormMoney').text(total);
		payWayFunct();
	}
	function balance(){
		var money=$("#money").val();
		if(null!=money&&money!=''){
			money=parseFloat(money);
	  		if(isNaN(money)){
	  			money=0; 
	  		}
	  	}else{
	  		 money=0;
	  	}
		var giveMoney=$("#giveMoney").val();
		if(null!=giveMoney&&giveMoney!=''){
			giveMoney=parseFloat(giveMoney);
	  		if(isNaN(giveMoney)){
	  			giveMoney=0; 
	  		}
	  	}else{
	  		giveMoney=0;
	  	}
		var balance="${member.balance}";
		if(null!=balance&&balance!=''){
			balance=parseFloat(balance);
	  		if(isNaN(balance)){
	  			balance=0; 
	  		}
	  	}else{
	  		balance=0;
	  	}
		var total=balance+money+giveMoney;
		total=formatFloat(total);
		$('#balance').text("")
		$('#balance').text(total)
	}
	function formatFloat(x) {
	    var f_x = parseFloat(x);
	    if (isNaN(f_x)) {
	        return 0;
	    }
	    var f_x = Math.round(x * 100) / 100;
	    var s_x = f_x.toString();
	    var pos_decimal = s_x.indexOf('.');
	    if (pos_decimal < 0) {
	        pos_decimal = s_x.length;
	        s_x += '.';
	    }
	    while (s_x.length <= pos_decimal + 2) {
	        s_x += '0';
	    }
	    return s_x;
	}
	function createHtml(value,name){
		var html='<div class="layui-form-item">'+
				 '<label class="layui-form-label label-required">'+name+'<span style="color: red;">*</span></label>'+
				 ' <div class="layui-input-inline" style="width:450px">'+
		         ' <input type="number" name="payWayValue'+value+'" id="payWayValue'+value+'"  value="0.0" onkeyup="payWayFunct()" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">'+
		         '</div>'+
		         '</div>';
		return html;
	}
	function payWayFunct(){
		var checkeds = $("input[type='checkbox'][name='payWayId']");
		var actureMoney=parseFloat(0);
		var paywayJson = "[";
	  	$.each(checkeds, function(i, checkbox) {
	  		if (checkbox.checked) {
	  			var value=$("#payWayValue"+checkbox.value).val();
	  			actureMoney=parseFloat(actureMoney);
	  			value=parseFloat(value);
	  			actureMoney=actureMoney+value;
	  			paywayJson = paywayJson + '{"paywayId":' + checkbox.value + ',"money":' + value +'},';
	  		}
	  	});
	  	paywayJson = paywayJson.substring(0,(paywayJson.length - 1))
		paywayJson = paywayJson + "]";
	  	actureMoney=parseFloat(actureMoney);
	  	var money=$("#money").val();
		if(null!=money&&money!=''){
			money=parseFloat(money);
	  		if(isNaN(money)){
	  			money=0; 
	  		}
	  	}else{
	  		 money=0;
	  	}
		if(actureMoney==money&&money!=0){
			$("#paywayJson").val(paywayJson);
			$("#submitBut").text("确定收银");
			$("#submitBut").removeClass("layui-btn-disabled");
		}
		if(actureMoney>money){
			var attr=$("#submitBut").attr("class");
			if(attr.indexOf("layui-btn-disabled")){
				$("#submitBut").addClass("layui-btn-disabled");
			}
			$("#submitBut").text("确定收银（多收￥"+(actureMoney-money)+"元");
		}
		if(actureMoney<money){
			var attr=$("#submitBut").attr("class");
			if(attr.indexOf("layui-btn-disabled")){
				$("#submitBut").addClass("layui-btn-disabled");
			}
			$("#submitBut").text("确定收银（少收￥"+(money-actureMoney)+"元");
		}
	}
</script>
</html>
