<#include "../../commons/macro.ftl">
<@commonHead/>
<title>回复-选择图文</title>

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />

<link href="${ctx}/css/weixin/yz.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/weixin/qrCode.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/widgets/artDialogMaster/lib/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/widgets/bootstrap/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/widgets/easyvalidator/js/easy_validator.pack4.js"></script>

<link rel="stylesheet" href="${ctx}/widgets/artDialogMaster/css/ui-dialog.css">
<script src="${ctx}/widgets/artDialogMaster/dist/dialog-min.js"></script>
<script src="${ctx}/widgets/artDialogMaster/dist/dialog-plus-min.js"></script>
<script src="${ctx}/pages/weixin/keyWordRole/keyWordRole.js"></script>
</head>
<body>
	<table class="table ui-table ui-table-list" style="width: 760px;">
        <colgroup>	
            <col class="modal-col-title">
            <col class="modal-col-time" span="2">
            <col class="modal-col-action">
        </colgroup>
    <!-- 表格头部 -->
	    <thead>    
	    <tr>
	        <th class="title">
	            <div class="td-cont">
	                <span>标题</span> <a class="js-update" href="javascript:void(0);" onclick="window.location.href='${ctx}/wechatMedia/selectWechatMedia'">刷新</a>
	            </div>
	        </th>
	        
	        <th class="time">
	            <div class="td-cont">
	                <span>创建时间</span>
	            </div>
	        </th>
	        <th class="opts">
	            <div class="td-cont">
	                <form class="form-search" id="searchFrm" action="${ctx}/wechatMedia/selectWechatMedia" method="post">
	                    <div class="input-append">
	                        <input class="input-small js-modal-search-input" type="text" value="${param.title }" id="title" name="title">
	                        <a href="javascript:void(0);" onclick="$('#searchFrm')[0].submit();" class="btn js-fetch-page js-modal-search" data-action-type="search">搜</a>
	                    </div>
	                </form>
	            </div>
	        </th>
	    </tr>
	    
	</thead>
	    <!-- 表格数据区 -->
	    <tbody>
	    <c:forEach var="wechatMedia" items="${wechatMedias }">
		    <tr>
		    <td class="title">
		        <div class="td-cont">
		            <div class="ui-image-text">
		                <div class="ui-image-text-item">
		                	 <img class="pic" width="169" height="169" src="${wechatMedia.thumbUrl }" data-previewsrc="" data-id="${wechatMedia.dbid }">
		                </div>
		            </div>
		        </div>
		    </td>
		    <td class="time">
		        <div class="td-cont">
		            <span>
		            	${fn:length(wechatMedia.name)>10==true?fn:substring(wechatMedia.name,0,18):wechatMedia.name}
		            </span>
		        </div>
		    </td>
		    <td class="opts">
		        <div class="td-cont">
		            <a class="btn js-choose" href="javascript:void(-1)" data-id="49014339" data-url="" data-page-type="mpNews" data-cover-attachment-id="" data-cover-attachment-url="" data-title="小主语录" data-alias="" onclick="getTemplateId(${wechatMedia.dbid})">选取</a>
		        </div>
		    </td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</body>
<script type="text/javascript">
function getTemplateId(templateId){
	var dialog=null;
	var contentUrl = top.frames["contentUrl"];
	if(null==contentUrl||contentUrl==undefined){
		dialog = top.dialog.get(window);
	}else{
		dialog=contentUrl.dialog.get(window);
	}
	dialog.close(templateId);
	dialog.remove();
}
</script>
</html>
