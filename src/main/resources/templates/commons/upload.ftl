<#include "macro.ftl">
<@commonHead/>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"/>
<title>Insert title here</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
	<body>
		
		<div id="content">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
							<div class="widget-title">
								<div class="buttons">
									<a id="add-event" data-toggle="modal" href="#modal-add-event" class="btn btn-success btn-mini" ><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event" data-toggle="modal" href="#modal-add-event" class="btn btn-success btn-mini"><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event" data-toggle="modal" href="#modal-add-event" class="btn btn-success btn-mini"><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event"  href="#modal-add-event" class="btn btn-success btn-mini" onclick="op()"><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event"  href="#modal-add-event" class="btn btn-success btn-mini" onclick="getUrl()"><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event"  href="#modal-add-event" class="btn btn-success btn-mini" onclick="getUrl('yy')"><i class="icon-plus icon-white"></i>上传图片</a>
									<a id="add-event"  href="#modal-add-event" class="btn btn-success btn-mini" onclick="getUrl('dd')"><i class="icon-plus icon-white"></i>上传图片</a>
									
									<div class="modal hide" id="modal-add-event">
										 <div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">×</button>
											<h3>上传图片</h3>
										</div>
										<div class="modal-body" id="uploadId">
											<input type="hidden" name="brand.logo" id="fileUpload" readonly="readonly"	value="${brand.logo }">
											<img alt="" id="fileUploadImage" src="${brand.logo }" width="50" height="30">
											<div id="div1">
											<div style="padding-left: 5px;">
												<span id="spanButtonPlaceholder1"></span> <br />
											</div>
												<div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
											</div>
											
													<input type="text" id="dd" name="dd" value="">
													<input type="text" id="yy" name="yy" value="">
										</div>
											
										<div class="modal-footer">
											<a href="#" class="btn" data-dismiss="modal">取消</a>
											<a href="#" id="add-event-submit" class="btn btn-primary" onclick="getUrl(this)">确定</a>
										</div>
									</div>
								</div>
							</div>
					</div>
				</div>
				<div class="row-fluid">
					<div id="footer" class="span12">
						2012 &copy; Unicorn Admin. Brought to you by <a href="https://wrapbootstrap.com/user/diablo9983">diablo9983</a>
					</div>
				</div>
			</div>
		</div>
		
</body>
	<script src="${ctx}/widgets/jquery.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
	<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.uniform.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.dataTables.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.bootstrap.teninedialog.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/unicorn.js"></script>
	<script src="${ctx}/widgets/bootstrap/unicorn.tables.js"></script>


	<script type="text/javascript">
		function getUrl(dd){
			art.dialog.open('uploa.jsp', {
			    title: '上传图片',
			    width:"720px",height:"400px",
			    init: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var top = art.dialog.top;// 引用顶层页面window对象
			        top.document.title = '上传图片';
			    },
			    ok: function () {
			    	var iframe = this.iframe.contentWindow;
			    	if (!iframe.document.body) {
			        	alert('iframe还没加载完毕呢')
			        	return false;
			        };
			       var fileUpload= iframe.document.getElementById('fileUpload').value;
			       if(null!=fileUpload&&fileUpload.length>0){
				        $("#"+dd).val(fileUpload);
				       	return true;
			       }else{
			    	   return false;
			       }
			    },
			    cancel: true
			});
		}
		function op(){
			$.teninedialog({
                title:'系统提示',
                content:upload,
                showCloseButton:true,
                otherButtons:["确定","取消"],
                otherButtonStyles:['btn-primary','btn-primary'],
                bootstrapModalOption:{keyboard: true},
                dialogShow:function(){
                },
                dialogShown:function(){
                    alert('显示对话框');
                },
                dialogHide:function(){
                    alert('即将关闭对话框');
                },
                dialogHidden:function(){
                    alert('关闭对话框');
                },                    
                clickButton:function(sender,modal,index){
                    alert('选中第'+index+'个按钮：'+sender.html());
                    $(this).closeDialog(modal);
                }
            });
		}
	</script>
</html>