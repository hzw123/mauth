<#include "macro.ftl">
<@commonHead/>

<title>文件上传组件</title>
<script type="text/javascript">
var upload1;
window.onload = function() {
	upload1 = new SWFUpload(
			{
				// Backend Settings
				upload_url : "${ctx}/swfUpload/uploadFile",
				post_params : {
					"PHPSESSID" : "6a95034fff6ba3a6aa8a990ca3af42ee","userId":"${sessionScope.user.dbid}"
				},
				//上传文件的名称
				file_post_name : "file",

				// File Upload Settings
				file_size_limit : "5242880", // 200MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "100",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueErrorHandler,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button Settings
				button_image_url : "${ctx}/widgets/SWFUpload/images/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width : 61,
				button_height : 22,

				// Flash Settings
				flash_url : "${ctx}/widgets/SWFUpload/Flash/swfupload.swf",

				custom_settings : {
					progressTarget : "uploadFileContent",
					cancelButtonId : "btnCancel1",
					titlePicture : "fileUpload",
					fileUploadImage : "fileUploadImage"
				},
				// Debug Settings
				debug : false
			});

}
</script>
</head>
<body>
<div style="" class="upload">
	<div style="padding-left: 5px;">
		<span id="spanButtonPlaceholder1"></span> <br />
	</div>
	<div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
</div>
<div></div>
<div style="">
	<input type="hidden" name="brand.logo" id="fileUpload" readonly="readonly"	value="${brand.logo }">
	<img alt="" id="fileUploadImage" src="${brand.logo }" width="80" height="60">
</div>
</body>
	<script src="${ctx}/widgets/jquery.min.js"></script>
	<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
	<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/swfupload.js"></script>
	<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.queue.js"></script>
	<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.speed.js"></script>
	<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/js/fileupload.handlers.js"></script>
</html>