<#include "../../commons/macro.ftl">
<@commonHead/>


<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId" target="_parent">
    	<input type="hidden" value="${model.dbid }" id="dbid" name="onlineBooking.dbid">
        <div class="layui-form-item">
            <label class="layui-form-label label-required">预约电话<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" readonly="readonly" name="onlineBooking.mobilePhone" id="mobilePhone" value="${model.mobilePhone}" lay-verify="required" placeholder="电话" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
			<label class="layui-form-label label-required">客户姓名</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" readonly="readonly" name="onlineBooking.memName" id="bookingDate" value="${model.memName}" placeholder="客户姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
			<label class="layui-form-label label-required">预约时间</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" readonly="readonly" name="tmpTime" id="bookingDate" value="${model.bookingTime}"  placeholder="预约时间" autocomplete="off" class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
			<label class="layui-form-label label-required">处理结果</label>
	            <div class="layui-input-inline" style="width:450px">
		           <input type="radio" name="onlineBooking.startWritingStatus" value="1" title="预约中" checked="">
		           <input type="radio" name="onlineBooking.startWritingStatus" value="2" title="已开单">
		           <input type="radio" name="onlineBooking.startWritingStatus" value="3" title="拒绝">
		           <input type="radio" name="onlineBooking.startWritingStatus" value="4" title="取消">
	            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-inline" style="width:450px">
                <textarea name="onlineBooking.dealNote"  placeholder="备注信息" autocomplete="off" class="layui-textarea">${onlineBooking.dealNote}</textarea>
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
	layui.use(['form'], function(){
	  var form = layui.form;
	  var layer = layui.layer;
	  var $ = layui.$;

	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/onlineBooking/saveHandle',
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
