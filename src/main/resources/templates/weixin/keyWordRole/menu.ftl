<#include "../../commons/macro.ftl">
<@commonHead/>
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
<style type="text/css">
	.clearfix{
		width: auto;
	}
	.app-inner{
		margin: 0 0 0 1px;
	}
</style>
</head>
<body>
<div class="popover-inner popover-reply" style="overflow: auto;display: none;" id="popover-inner">
    <div class="popover-content">
        <div class="form-horizontal">
            <div class="wb-sender">
                <div class="wb-sender__inner">
                    <div class="wb-sender__input">
						<div class="misc top clearfix">
						    <div class="content-actions clearfix">
						    	<div class="editor-module insert-emotion">
						            <a class="js-open-wx_emotion" data-action-type="emotion" href="javascript:;" onclick="selectText()">文本</a>
						            <div class="emotion-wrapper">
						                <ul class="emotion-container clearfix"></ul>
						            </div>
						        </div>
						        <div class="editor-module insert-articles">
						            <a class="js-open-articles" data-action-type="articles" href="javascript:;">选择图文</a>
						            <div class="articles-wrapper"></div>
						        </div>
						    </div>
						</div>
						<div class="content-wrapper">
						    <textarea class="js-txta" cols="50" rows="4"></textarea>
						    <div class="js-picture-container picture-container"></div>
						    <div class="complex-backdrop" style="display: none;">
						    	<div class="js-complex-content complex-content"></div>
						    </div>
						</div>
						<div class="misc clearfix">
						    <div class="content-actions clearfix">
						        <div class="word-counter pull-right">还能输入 <i>300</i> 个字</div>
						    </div>
						</div>
				</div>
                <div class="wb-sender__actions in-editor">
                    <button class="js-btn-confirm btn btn-primary">确定</button>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
