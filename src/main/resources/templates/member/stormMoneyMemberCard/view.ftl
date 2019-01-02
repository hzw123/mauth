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
        <div class="layui-form-item">
            <label class="layui-form-label label-required">充值金额<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
	                <input type="number" readonly="readonly" name="stormMoneyMemberCard.money" id="money" value="${stormMoneyMemberCard.money }"  lay-verify="required"  autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">赠送金额<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
                <input type="number" readonly="readonly" name="stormMoneyMemberCard.giveMoney" id="giveMoney" value="${stormMoneyMemberCard.giveMoney }"  lay-verify="required" placeholder="会员姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">技师</label>
            <div class="layui-input-inline" style="width:450px">
                <input type="number" readonly="readonly" name="stormMoneyMemberCard.giveMoney" id="giveMoney" value="${stormMoneyMemberCard.artificerName }"  lay-verify="required" placeholder="会员姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
		    <label class="layui-form-label">支付方式</label>
		</div>
		<c:forEach var="payway" items="${payways }">
			<div class="layui-form-item">
			    <label class="layui-form-label">${payway.payWayName }</label>
			    <div class="layui-input-block">
				    <input type="number" readonly="readonly"  value="${payway.money }"  autocomplete="off" class="layui-input">
			    </div>
			</div>
		</c:forEach>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">备注信息</label>
            <div class="layui-input-inline" style="width:450px">
                <textarea name="stormMoneyMemberCard.note" placeholder="备注信息" autocomplete="off" class="layui-textarea">${stormMoneyMemberCard.note }</textarea>
            </div>
        </div>
        <div class="layui-form-item">
		    <div class="layui-input-block">
		      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
    </form>
</div>
</body>
<script src="${ctx}/widgets/jquery.min.js"></script>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
	  var form = layui.form,upload = layui.upload
	  ,layer = layui.layer;
	  parentlayer = parent.layui;
	  var $ = layui.$;
	  var laydate = layui.laydate;
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
</html>
