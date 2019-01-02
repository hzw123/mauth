<#include "../../commons/macro.ftl">
<@commonHead/>
<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId">
    <input type="hidden" name="artificer.dbid" id="dbid" value="${artificer.dbid }">
        <div class="layui-form-item">
            <label class="layui-form-label label-required">工号</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="artificer.no" value="${artificer.no }" lay-verify="required" placeholder="技师工号"  autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">姓名</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="artificer.name" value="${artificer.name}" lay-verify="required" placeholder="姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        
        <div class="layui-form-item">
            <label class="layui-form-label label-required">性别</label>
             <div class="layui-input-inline" style="width:450px">
			    <select name="artificer.sex" >
			       <option value="男" selected="">男</option>
			       <option value="女">女</option>
			     </select>
			  </div>
        </div>
        
        <div class="layui-form-item">
            <label class="layui-form-label label-required">手机号:</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="artificer.phone" value="${artificer.phone}" lay-verify="required" placeholder="手机号码" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-block" style="width:450px">
		      <textarea name="artificer.note" placeholder="技师的备注信息" class="layui-textarea">${artificer.note }</textarea>
		    </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="layui-form-item" style="text-align:center;margin:0 auto">
            <button class="layui-btn" id="btnSubmit" lay-filter="submitButton">保存数据</button>
            <button class="layui-btn layui-btn-danger" type="button" data-confirm="确定要取消编辑吗？" data-close="">取消编辑</button>
        </div>
    </form>
</div>


<script type="text/javascript">
    layui.use(['form', 'upload', 'jquery', 'laydate'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var upload=layui.upload;
        form.render();
    });
    
    $('#btnSubmit').on('click', function (e) {
    	submitFrm('${ctx}/artificer/save');
    });
</script>
