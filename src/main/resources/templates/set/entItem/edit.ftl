<#include "../../commons/macro.ftl">
<@commonHead/>

<title>项目管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId" target="_parent"
		style="padding: 12px;">

		<input type="hidden" name="itemId" id="itemId" value="${entItem.itemId }">
		<input type="hidden" name="entItem.dbid" id="dbid"	value="${entItem.dbid }">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block">
					<input type="text" disabled="disabled" name="entItem.name"
						id="price" value="${entItem.itemName}" lay-verify="title"
						autocomplete="off" placeholder="请输入名称" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">价格（元）<span style="color: red">*</span></label>
				<div class="layui-input-block">
					<input type="text" name="entItem.price" id="price"
						value="${entItem.price }" lay-verify="title" autocomplete="off"
						placeholder="价格" class="layui-input">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">会员价（元）<span style="color: red">*</span></label>
				<div class="layui-input-block">
					<input type="text" name="entItem.vipprice" id="vipprice"
						value="${entItem.vipprice }" lay-verify="title" autocomplete="off"
						placeholder="请输入会员价" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">提成（元）<span style="color: red">*</span></label>
				<div class="layui-input-block">
					<input type="text" name="entItem.commissionNum" id="commissionNum"
						value="${entItem.commissionNum }" lay-verify="title"
						autocomplete="off" placeholder="请输入名称" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
					<label class="layui-form-label">固定折扣</label>
					<div class="layui-input-inline">
						<input type="text" name="entItem.fixedDiscount" id="fixedDiscount"
							value="${entItem.fixedDiscount }${empty(entItem)?'10':''}" placeholder="固定折扣" class="layui-input">
					</div>
			</div>	
			<div class="layui-inline">
				<label class="layui-form-label">时长(分钟)<span
					style="color: red">*</span></label>
				<div class="layui-input-block">
					<input type="text" name="entItem.timeLong" id="timeLong"
						value="${entItem.timeLong }" lay-verify="title" autocomplete="off"
						placeholder="请输入名称" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">支持会员卡折扣</label>
				<div class="layui-input-inline">
			      <c:if test="${empty(entItem)}">
		                <input type="radio" name="entItem.enableCardDiscount" value="1" title="是" checked="">
		                <input type="radio" name="entItem.enableCardDiscount" value="0" title="否">
	            	</c:if>
	            	<c:if test="${!empty(entItem)}">
		                <input type="radio" name="entItem.enableCardDiscount" value="1" title="是" ${entItem.enableCardDiscount==1?'checked="checked"':'' } >
		                <input type="radio" name="entItem.enableCardDiscount" value="0" title="否" ${entItem.enableCardDiscount==0?'checked="checked"':'' }>
	            	</c:if>
			    </div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">序号</label>
				<div class="layui-input-block">
					<input type="text" name="entItem.orderNum" id="orderNum" value="${entItem.orderNum }" placeholder="请输入序号"	class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<input type="text" name="entItem.note" id="note"
						value="${entItem.note }" placeholder="备注信息" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" id="submitBut" lay-submit=""	lay-filter="submitButton">立即提交</button>
				<a id="closeBut" class="layui-btn layui-btn-primary">关闭</a>
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">
	layui.use([ 'form', 'layedit', 'laydate' ],
		function() {
			var form = layui.form, layer = layui.layer;
			parentlayer = parent.layui;
			var $ = layui.$;
			//自定义验证规则
			form.verify({
				title : function(value) {
					if (value.length < 1) {
						return '请输入合法信息';
					}
				}
			});

			//监听提交
			form.on('submit(submitButton)',
				function(data) {
					var params = $("#frmId").serialize();
					var target = $("#frmId").attr("target")|| "_self";
					$.ajax({url : '${ctx}/entItem/save',
						data : params,
						async : false,
						timeout : 20000,
						dataType : "json",
						type : "post",
						success : function(data,textStatus,jqXHR) {
							//alert(data.message);
							var obj;
							if (data.message != undefined) {
								obj = $.parseJSON(data.message);
							} else {
								obj = data;
							}
							if (obj[0].mark == 1) {
								//错误
								layer.msg(obj[0].message,{icon : 5});
								$("#submitBut").bind("onclick");
								return;
							} else if (obj[0].mark == 0) {
								layer.msg(data[0].message,{icon : 1});
								if (target == "_self") {
									setTimeout(function() {window.location.href = obj[0].url},1000);
								}
								if (target == "_parent") {
									setTimeout(function() {
										window.parent.frames["contentUrl"].location = obj[0].url;
										parent.layer.closeAll();
									},1000)
								}
								// 保存数据成功时页面需跳转到列表页面
							}
						},
						complete : function(jqXHR,textStatus) {
							var jqXHR = jqXHR;
							var textStatus = textStatus;
						},
						beforeSend : function(jqXHR, configs) {
							$("#submitBut").unbind(	"onclick");
							var jqXHR = jqXHR;
							var configs = configs;
						},
						error : function(jqXHR,textStatus,errorThrown) {
							layer.msg("系统请求超时");
							$("#submitBut").bind("onclick");
						}
					});
					return false;
				});
			$('#closeBut').on('click', function() {
				parent.layer.closeAll(); //再执行关闭
			});

		});
</script>
</html>