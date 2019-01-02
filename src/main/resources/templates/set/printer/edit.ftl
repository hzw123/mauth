<#include "../../commons/macro.ftl">
<@commonHead/>

<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId">
    	<input type="hidden" name="printerModel.dbid" id="dbid" value="${printerModel.dbid }" />
    	
    	<div class="layui-form-item">
            <label class="layui-form-label label-required">名称</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="printerModel.name" value="${printerModel.name}" placeholder="打印机名称,如  前台打印机" class="layui-input" />
            </div>
        </div>
    	
        <div class="layui-form-item">
            <label class="layui-form-label label-required">url地址</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="printerModel.url" value="${printerModel.url }" placeholder="云打印机地址" class="layui-input" />
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">用户</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="printerModel.user" value="${printerModel.user}" placeholder="云打印机用户名" class="layui-input" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">密匙</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="printerModel.ukey" value="${printerModel.ukey}" placeholder="云打印机密匙" class="layui-input" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">SN</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="printerModel.sn" value="${printerModel.sn}" placeholder="云打印机序列号" class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
            <label class="layui-form-label label-required">状态</label>
            <div class="layui-input-inline" style="width:450px">
		       <input type="radio" name="printerModel.state" value="0" title="正常" checked>
		       <input type="radio" name="printerModel.state" value="10001" title="停用">
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
				url : '${ctx}/printer/save',
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
