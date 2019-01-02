<#include "../../commons/macro.ftl">
<@commonHead/>

<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId" target="_parent">
    	<input type="hidden" value="${member.dbid }" id="dbid" name="member.dbid">
    	<input type="hidden" value="${param.roomId }" id="roomId" name="roomId">
        <div class="layui-form-item">
            <label class="layui-form-label label-required">会员编号<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
            	<c:if test="${empty(member) }">
	                <input type="text" readonly="readonly" name="member.no" value="${no }" lay-verify="required"  autocomplete="off" class="layui-input">
            	</c:if>
            	<c:if test="${!empty(member) }">
	                <input type="text" readonly="readonly"  name="member.no" value="${member.no }"  lay-verify="required" autocomplete="off" class="layui-input">
            	</c:if>
            </div>
        </div>
        <div class="layui-form-item">
        	<div class="layui-inline">	
	            <label class="layui-form-label label-required">会员姓名<span style="color: red;">*</span></label>
	            <div class="layui-input-inline" style="width:250px">
	                <input type="text" name="member.name" id="name" value="${member.name }" lay-verify="required" placeholder="会员姓名" autocomplete="off" class="layui-input">
	            </div>
            </div>
            <div class="layui-inline">	
	            <div class="layui-input-inline" style="width:150px">
	            	<c:if test="${empty(member)}">
		                <input type="radio" name="member.sex" value="男" title="男" checked="">
		                <input type="radio" name="member.sex" value="女" title="女">
	            	</c:if>
	            	<c:if test="${!empty(member)}">
		                <input type="radio" name="member.sex" value="男" title="男" ${member.sex=='男'?'checked="checked"':'' } >
		                <input type="radio" name="member.sex" value="女" title="女" ${member.sex=='女'?'checked="checked"':'' }>
	            	</c:if>
	            </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">会员电话<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="member.mobilePhone" id="mobilePhone" value="${member.mobilePhone }" lay-verify="required" placeholder="电话" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">会员生日</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="member.birthday" id="birthday" value="${member.birthday }" lay-verify="date" placeholder="yyyy年MM月dd日" autocomplete="off" class="layui-input">
            </div>
        </div>
    <%--     <div class="layui-form-item">
            <label class="layui-form-label label-required">生日类型</label>
            <div class="layui-input-inline" style="width:450px">
            	<select id="member.birthdayType" name="member.birthdayType" class="layui-input">
            		<option value="-1">请选择...</option>
            		<option value="1" ${member.birthdayType==1?'selected="selected"':'' } >阳历</option>
            		<option value="2" ${member.birthdayType==2?'selected="selected"':'' }>农历</option>
            	</select>
            </div>
        </div> --%>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-inline" style="width:450px">
                <textarea name="member.note"  placeholder="备注信息" autocomplete="off" class="layui-textarea">${member.note }</textarea>
            </div>
        </div>
        <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">立即提交</button>
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
				url : '${ctx}/member/save',
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
