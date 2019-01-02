<#include "../../commons/macro.ftl">
<@commonHead/>

<div class="layui-container" style="margin-top:20px;width:400px">
    <form class="layui-form" id="frmId" target="_parent">
    	<input type="hidden" value="${member.dbid }" id="dbid" name="memberId">
    	<input type="hidden" value="${param.type }" id="type" name="type">
        <div class="layui-form-item">
            <label class="layui-form-label label-required">会员姓名</label>
            <div class="layui-input-block" style="width:250px">
                <input type="text" disabled="disabled" name="member.mobilePhone" value="${member.name }"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">联系电话</label>
            <div class="layui-input-block" style="width:250px">
                <input type="text" disabled="disabled" name="room.count" value="${member.mobilePhone }"  class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
            <label class="layui-form-label label-required">当前级别</label>
            <div class="layui-input-block" style="width:250px">
                <input type="text" disabled="disabled" name="room.count" value="${member.memberCard.name }"  class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">
            	会员级别<span style="color: red">*</span>
            </label>
            <div class="layui-input-inline" style="width:250px">
            	<select id="memberCardId" name="memberCardId" class="layui-input" lay-verify="required" >
            		<c:forEach var="memberCard" items="${memberCards }">
            			<c:if test="${memberCard.dbid<=3&&(memberCard.dbid!=member.memberCard.dbid)}">
            				<option value="${memberCard.dbid }">${memberCard.name }</option>
            			</c:if>
            		</c:forEach>
            	</select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注</label>
            <div class="layui-input-inline" style="width:250px">
                <textarea name="member.note"  placeholder="备注信息" autocomplete="off" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保存</button>
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
    </form>
</div>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
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
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/member/saveChangeMember',
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
