<#include "../../commons/macro.ftl">
<@commonHead/>
	    <meta name="renderer" content="webkit">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

	    <link href="${ctx}/css/global.css" rel="stylesheet" />
	    <link href="${ctx}/css/main.css" rel="stylesheet" />
	</head>
	<body>
	<div class="layui-container" style="margin-top:20px;">
		<div class="layui-row">
		    <div class="layui-col-xs12 ">
		      <div class="grid-demo grid-demo-bg1" style="text-align: left;">
				批量导入会员成功!
		    </div>
		  </div>
		 </div>
		 <br>
		 <br>
		 <div class="layui-form-item">
		    <div class="layui-input-block">
		      <a id="closeBut" onclick="cloase()"  class="layui-btn layui-btn-primary">关闭</a>
		    </div>
		  </div>
	</div>
	</body>

<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','upload'], function(){
		 var $ = layui.$;
		$('#closeBut').on('click', function(){
			  parent.layer.closeAll(); //再执行关闭
		});
	});
	function cloase(){
		window.parent.frames["contentUrl"].location='${ctx}/member/queryList';
	}
</script>
</html>