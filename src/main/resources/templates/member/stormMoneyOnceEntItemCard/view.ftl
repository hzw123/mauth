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
            <label class="layui-form-label label-required">次卡套餐</label>
            <div class="layui-input-inline" style="width:450px">
            	<select id="onceEntItemCardId" name="onceEntItemCardId" class="layui-input" lay-filter="onceEntItemCardId" lay-verify="required" tip="请选择次卡套餐">
            		<option value="-1">请选择...</option>
            		<c:forEach var="onceEntItemCard" items="${onceEntItemCards }">
	            		<option value="${onceEntItemCard.dbid }"  ${stormMoneyOnceEntItemCard.onceEntItemCard.dbid==onceEntItemCard.dbid?'selected="selected"':'' } >${onceEntItemCard.name}--项目：${onceEntItemCard.itemName }--次数：${onceEntItemCard.num }--金额：${onceEntItemCard.price}</option>
            		</c:forEach>
            	</select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">购买金额<span style="color: red;">*</span></label>
            <div class="layui-input-inline" style="width:450px">
	                <input type="number" readonly="readonly" name="stormMoneyOnceEntItemCard.money" id="money" value="${stormMoneyOnceEntItemCard.money }"  lay-verify="required"  autocomplete="off" class="layui-input"  tip="购买金额不能为空">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label label-required">技师</label>
            <div class="layui-input-inline" style="width:450px">
            	<select id="artificerId" name="artificerId" class="layui-input">
            		<option value="-1">请选择...</option>
            		<c:forEach var="artificer" items="${artificers }">
	            		<option value="${artificer.dbid }"  ${stormMoneyOnceEntItemCard.artificer.dbid==artificer.dbid?'selected="selected"':'' } >${artificer.name}---- 售卡次数：0</option>
            		</c:forEach>
            	</select>
            </div>
        </div>
       <div class="layui-form-item" pane="">
		    <label class="layui-form-label">支付方式</label>
		    <div class="layui-input-block">
		      <c:forEach var="payWay" items="${payWays }">
		      	<c:set value="false" var="status"></c:set>
		      	<c:forEach var="memOrderPayWay" items="${memOrderPayWays }">
		      		<c:if test="${memOrderPayWay.payWayId==payWay.dbid }">
		      			<s:set value="true" var="status"></s:set>
		      		</c:if>
		      	</c:forEach>
			      <input type="checkbox" name="payWayId" lay-skin="primary" lay-filter="payWayId" value="${payWay.dbid }" title="${payWay.name }" ${status==true?'checked="checked"':'' } >
		      </c:forEach>
		    </div>
		</div>
		<div id="payWayDiv">
			<c:forEach var="memOrderPayWay" items="${memOrderPayWays }">
				<div class="layui-form-item">
		            <label class="layui-form-label label-required">${memOrderPayWay.payWayName }<span style="color: red;">*</span></label>
		            <div class="layui-input-inline" style="width:450px">
		                <input type="number" name="stormMoneyMemberCard.giveMoney" id="giveMoney" value="${memOrderPayWay.money }"   class="layui-input">
		            </div>
		        </div>
		    </c:forEach>
		</div>
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
