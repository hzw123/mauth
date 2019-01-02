<#include "../../commons/macro.ftl">
<@commonHead/>
<div class="layui-container" style="margin-top:20px;width:600px">
    <form class="layui-form" id="frmId">
    <input type="hidden" name="room.dbid" id="dbid" value="${room.dbid }">
        <div class="layui-form-item">
            <label class="layui-form-label label-required">房间名称</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="text" name="room.name" value="${room.name }" lay-verify="required" placeholder="房间名称"  autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">容纳人数</label>
            <div class="layui-input-block" style="width:450px">
                <input type="text" name="room.count" value="${room.count}" lay-verify="required" placeholder="容纳人数" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-block" style="width:450px">
		      <textarea name="room.note" placeholder="请输入备注信息" class="layui-textarea">${room.note }</textarea>
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
    layui.use(['form', 'table', 'jquery', 'laydate'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var layedit = layui.layedit;
        var laydate = layui.laydate;
        form.render();
    });
    
    $('#btnSubmit').on('click', function (e) {
    	submitFrm('${ctx}/room/save');
    });
</script>
