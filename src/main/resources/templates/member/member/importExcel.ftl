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
		<c:set value="${ctx}/archives/orderContractHanCar.xls" var="fileP"></c:set>
		<div class="layui-row">
		    <div class="layui-col-xs12 ">
		      <div class="grid-demo grid-demo-bg1" style="text-align: left;">
				<p>1、请按系统提供的excel《会员信息导入模板》填写信息！</p>
				<p>2、会员信息导入模板.xls&nbsp;&nbsp;&nbsp;&nbsp;<a class="but butSave" href='javascript:void(-1)' onclick="downLoad('${ctx}/swfUpload/downLoad?path=/archives/会员信息导入模板.xls')" title="会员信息导入模板">下载模板</a></div>
		    </div>
		  </div>
		  <br>
		  <br>
	    <form class="layui-form"  action="${ctx}/member/saveImportExcel" name="frmId" id="frmId" method="post" style="margin-bottom: 40px;" enctype="multipart/form-data" onsubmit="">
	        <div class="layui-form-item">
	            <label class="layui-form-label label-required">选择文件<span style="color: red;">*</span></label>
	            <div class="layui-input-inline" style="width:450px">
	            	<input type="file" name="file" id="file"><span style="color: red;">*</span><span>只能上传*.xls</span>
	            </div>
	        </div>
	        <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" id="submitBut" onclick="if(sbMit()){$('#frmId')[0].submit()}">立即提交</button>
			      <a id="closeBut"  class="layui-btn layui-btn-primary">关闭</a>
			    </div>
			  </div>
	    </form>
	</div>
	</body>


	<script type="text/javascript">
		layui.use(['form', 'layedit', 'laydate','upload'], function(){
			var $ = layui.$;
			$('#closeBut').on('click', function(){
				  parent.layer.closeAll(); //再执行关闭
			});
		});
	</script>
	<script type="text/javascript">
		function sbMit(){
			var fileName=$("#file").val();
			if(null==fileName||fileName==""){
				alert("请选择导入文件");
				return false;
			}
			var fileLast=fileName.substring(fileName.lastIndexOf("."),fileName.length);
			if(fileLast==".xls"||fileLast==".xlsx"){
				return true;
			}else{
				alert("导入文件格式不正确，请选正确的导入文件！");
				return false;
			}
		}
		function downLoad(url){
			window.location.href=encodeURI(encodeURI(url));
		}
	</script>
</html>