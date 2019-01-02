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
            <label class="layui-form-label label-required">剩余积分</label>
            <div class="layui-input-block" style="width:250px">
                <input type="text" disabled="disabled" name="member.overagePoint" value="${member.remainderPoint }"  class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
            <label class="layui-form-label label-required">
            	${param.type==1?'加积分':'消费积分' }<span style="color: red">*</span>
            </label>
            <div class="layui-input-block" style="width:250px">
                <input type="number" name="point" id="money" placeholder="" autocomplete="off" class="layui-input"></input>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注</label>
            <div class="layui-input-inline" style="width:250px">
                <textarea name="member.note"  placeholder="操作理由" autocomplete="off" class="layui-textarea"></textarea>
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
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  var params = $("#frmId").serialize();
		  var target = $("#frmId").attr("target") || "_self";
		  $.ajax({	
				url : '${ctx}/member/saveChanagePoint',
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
