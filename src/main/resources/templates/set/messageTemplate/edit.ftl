<#include "../../commons/macro.ftl">
<@commonHead/>

<div class="layui-container" style="margin-top:20px;width:500px">
    <form class="layui-form" id="frmId">
    	<input type="hidden" name="messageTemplateModel.dbid" id="dbid" value="${messageTemplateModel.dbid }" />
    	
    	<div class="layui-form-item">
			<label class="layui-form-label label-required">类型</label>
			<div class="layui-input-inline"  style="width:350px">
			    <select id="templateType" name="messageTemplateModel.templateType" class="layui-input" lay-verify='required'>
			    	<option value="1">成为会员通知</option>
			    	<option value="2">充值成功通知</option>
			    	<option value="3">会员计次充值通知</option>
			    	<option value="4">消费成功通知</option>
			    	<!-- <option value="5">打赏</option> -->
			    	<option value="6">预约成功通知</option>
			    	<option value="7">计次项目消费提醒</option>
			    	<option value="8">积分提醒</option>
			    	<option value="9">订单撤销通知</option>
			    	<option value="10">会员卡余额修正提醒</option>
			    </select>
			    </div>
		</div>
    	
    	<div class="layui-form-item">
            <label class="layui-form-label label-required">模板Id</label>
            <div class="layui-input-inline" style="width:350px">
                <input type="text" name="messageTemplateModel.templateId" value="${messageTemplateModel.templateId}" placeholder="消息模板Id" class="layui-input" />
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="layui-form-item" style="text-align:center;margin:0 auto">
            <button class="layui-btn" id="btnSubmit" lay-filter="submitButton">保存数据</button>
            <button class="layui-btn layui-btn-danger" type="button" data-close="">取消编辑</button>
        </div>
    </form>
</div>


<script type="text/javascript">
    layui.use(['form', 'upload', 'jquery', 'laydate'], function () {
        var form = layui.form;
        var layer = layui.layer;
        form.render();
    });
    
    $('#btnSubmit').on('click', function (e) {
    	 var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/messageTemplate/save',
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
	 
</script>
