<#include "../../commons/macro.ftl">
<@commonHead/>

<title>项目管理</title>
</head>
<body>
	<form class="layui-form" id="frmId" name="frmId" target="_parent"
		style="padding: 12px;">

		<input type="hidden" name="item.dbid" id="dbid" value="${item.dbid }">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">编号<span style="color: red">*</span></label>
				<div class="layui-input-inline">
					<input type="text" name="item.no" id="no" value="${item.no }"
						lay-verify="required" autocomplete="off" placeholder="请输入编号"
						class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">类型<span style="color: red">*</span></label>
				<div class="layui-input-inline">
					<select id="itemTypeId" name="itemTypeId" class="layui-input"
						lay-verify='required'>
						<option value="">请选择...</option>
						<c:forEach items="${itemTypes }" var="itemType">
							<option value="${itemType.dbid }"
								${item.itemType.dbid==itemType.dbid?'selected="selected"':'' }>${itemType.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
		</div>
		<div class="layui-form-item">
		<div class="layui-inline">
				<label class="layui-form-label">名称<span style="color: red">*</span></label>
				<div class="layui-input-inline">
					<input type="text" name="item.name" id="item.name"
						value="${item.name }" lay-verify="title" autocomplete="off"
						placeholder="请输入名称" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">价格（元）<span
					style="color: red">*</span></label>
				<div class="layui-input-inline">
					<input type="text" name="item.price" id="price"
						value="${item.price }${empty(item)==true?'0.00':''}"
						autocomplete="off" placeholder="请输入价格" class="layui-input">
				</div>
			</div>
			
		</div>
		<div class="layui-form-item">
		<div class="layui-inline">
				<label class="layui-form-label">会员价</label>
				<div class="layui-input-inline">
					<input type="text" name="item.vipprice" id="vipprice"
						value="${item.vipprice }" placeholder="会员价（没有则填-1）" class="layui-input">
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">提成(元)</label>
				<div class="layui-input-inline">
					<input type="text" name="item.commissionNum" id="commissionNum"
						value="${item.commissionNum }${empty(item)?'0.00':''}"
						placeholder="请输入提成金额" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
		<div class="layui-inline">
				<label class="layui-form-label">固定折扣</label>
				<div class="layui-input-inline">
					<input type="text" name="item.fixedDiscount" id="fixedDiscount"
						value="${item.fixedDiscount }${empty(item)?'10':''}" placeholder="固定折扣" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">支持会员卡折扣</label>
				<div class="layui-input-inline">
			      <c:if test="${empty(item)}">
		                <input type="radio" name="item.enableCardDiscount" value="1" title="是" checked="">
		                <input type="radio" name="item.enableCardDiscount" value="0" title="否">
	            	</c:if>
	            	<c:if test="${!empty(item)}">
		                <input type="radio" name="item.enableCardDiscount" value="1" title="是" ${item.enableCardDiscount==1?'checked="checked"':'' } >
		                <input type="radio" name="item.enableCardDiscount" value="0" title="否" ${item.enableCardDiscount==0?'checked="checked"':'' }>
	            	</c:if>
			    </div>
			</div>
			
		</div>
		<div class="layui-form-item">
		<div class="layui-inline">
				<label class="layui-form-label">时长(分钟)<span
					style="color: red">*</span></label>
				<div class="layui-input-inline">
					<input type="number" name="item.timeLong" id="timeLong"
						value="${item.timeLong }" placeholder="请输入时长" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">序号</label>
				<div class="layui-input-inline">
					<input type="text" name="item.orderNum" id="orderNum"
						value="${item.orderNum }" placeholder="请输入序号" class="layui-input">
				</div>
			</div>
			
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" id="submitBut" lay-submit=""
					lay-filter="submitButton">立即提交</button>
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
					if (value.length < 2) {
						return '名称至少得2个字符啊';
					}
				}
			});

			//监听提交
			form.on('submit(submitButton)',
				function(data) {
					var params = $("#frmId").serialize();
					var target = $("#frmId").attr("target")|| "_self";
					$.ajax({url : '${ctx}/item/save',data : params,async : false,timeout : 20000,dataType : "json",type : "post",
						success : function(data,textStatus,jqXHR) {
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
									setTimeout(
											function() {
												window.location.href = obj[0].url
											},
											1000);
								}
								if (target == "_parent") {
									setTimeout(
											function() {
												window.parent.frames["contentUrl"].location = obj[0].url;
												parent.layer
														.closeAll();
											},
											1000)
								}
							}
						},
						complete : function(jqXHR,textStatus) {
							var jqXHR = jqXHR;
							var textStatus = textStatus;
						},
						beforeSend : function(jqXHR, configs) {
							$("#submitBut").unbind("onclick");
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