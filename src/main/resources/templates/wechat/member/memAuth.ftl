<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui.css?${now}" type="text/css" />
	<script src="${ctx}/widgets/bootstrap3/jquery.min.js"></script>
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <script src="${ctx}/widgets/easyvalidator/js/easy_validator.pack5.js"></script>
    <style type="text/css">
    </style>
<title>会员认证</title>
</head>
<body>
<div class="title">
    <a class="btn-back" onclick="window.history.go(-1)" href="javascript:void(0)"><i class="icon icon-2-1"></i></a>
    <h3>会员认证</h3>
</div>
<br>
<br>
<form action="" id="frmId" name="frmId" method="post" target="_self">
<input type="hidden" id="url" name="url" value="${url }">
<input type="hidden" id="dbid" name="dbid" value="${member.dbid }">
<div class="weui_cells weui_cells_form">
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input type="text" id="name" name="name"  placeholder="请输姓名"  class="weui_input" checkType="string,1">
            </div>
        </div>
        <div class="weui_cell weui_cell_select weui_select_after">
            <div class="weui_cell_hd">
               	称呼
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <select class="weui_select" name="sex" id="sex" checkType="string,1">
                	<option value="">请选择</option>
                    <option value="男">先生</option>
                    <option value="女">女士</option>
                </select>
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">电话</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input class="weui_input" id="mobilePhone" name="mobilePhone" type="number" pattern="[0-9]*" placeholder="请输入号码" checkType="mobilePhone" onchange="validateAgent()" >
            </div>
        </div>
        <div class="weui_cell">
            <div class="weui_cell__hd"><label for="" class="weui_label">生日</label></div>
            <div class="weui_cell__bd">
                <input class="weui_input" type="date" name="birthday" id="birthday" value="" placeholder="" style="width: 160px;">
            </div>
        </div>
        <div class="weui_cell weui_vcode" style="height: 50px;">
            <div class="weui_cell_hd"><label class="weui_label">验证码</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input type="number" id="codeNum" name="codeNum" placeholder="请输入验证码" pattern="[0-9]*" class="weui_input" checkType="string,4,4">
            </div>
            <div class="weui_cell_ft1" style="padding-right: 14px;">
                <a href="javascript:;" id="btnSendMsg" class="weui_btn weui_btn_mini weui_btn_default" onclick="sendMsg()">获取验证码</a>
            </div>
        </div>
    </div>
   <div class="weui_btn_area">
        <a class="weui_btn weui_btn_warn" href="javascript:" id="showTooltips" onclick="ajaxSubmit('${ctx}/memberWechat/saveMemAuth','frmId')">提交</a>
    </div>
    </form>
     <div id="toast" style="display: none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <i class="weui_icon_toast"></i>
	        <p class="weui_toast_content" id="weui_toast_content">认证成功</p>
	    </div>
	</div>
</body>
<script type="text/javascript">
var comm=1,sendStatus=1,delay=0,oldValue;
function validateAgent(){
	var mobile=$("#mobilePhone").val();
	if(null==mobile||mobile==''){
		change_error_style($("#mobilePhone"),"add")
		return ;
	}else{
		if (!checkMobilePhone($("#mobilePhone"))) {
			return ;
		}
	}
}
/**
 * 发送验证码
 */
function sendMsg() {
	var mobile=$("#mobilePhone").val();
	if(null==mobile||mobile==''){
		change_error_style($("#mobilePhone"),"add")
		return ;
	}
	else{
		if (!checkMobilePhone($("#mobilePhone"))) {
			return ;
		}
	}
	if(sendStatus==1){
		$.post("${ctx}/verificationCodeWechat/sendMobileMessage",{'mobile':mobile},function callBack(data) {
			if (data=='2') {
				alert("手机号已经注册，请确认");
				return ;
			}
			if (data[0].mark == 0) {
			}
		})
		var text="重新获取（<span id='second'>60</span>）";
		oldValue=$("#btnSendMsg").text();
		$("#btnSendMsg").removeClass("weui_btn_plain_primary").addClass("weui_btn_plain_default");
		$("#btnSendMsg").text("");
		$("#btnSendMsg").html(text);
		$("#btnSendMsg").attr("onclick","");
		delay=document.getElementById("second").innerHTML;
		//最后的innerHTML不能丢，否则delay为一个对象
		if(delay>0){
			delay--;
			sendStatus=2;
			document.getElementById("second").innerHTML=delay;
			setTimeout("sendMsg()", 1000);
		}
	}
	else{
		if(delay>0){
			delay--;
			document.getElementById("second").innerHTML=delay;
			setTimeout("sendMsg()", 1000);
		}else{
			sendStatus=1;
			$("#btnSendMsg").removeClass("weui_btn_plain_default").addClass("weui_btn_plain_primary");
			$("#btnSendMsg").text("");
			$("#btnSendMsg").html(oldValue);
			$("#btnSendMsg").attr("onclick","sendMsg()");
		} 
	}
}
function ajaxSubmit(url,frmId){
	var validata = validateForm(frmId);
	if(validata){
		var params = $("#"+frmId).serialize();
		var url2="";
		$.ajax({	
			url : url, 
			data : params, 
			async : false, 
			timeout : 20000, 
			dataType : "json",
			type:"post",
			success : function(data, textStatus, jqXHR){
				var obj;
				if(data.message!=undefined){
					obj=$.parseJSON(data.message);
				}else{
					obj=data;
				}
				if(obj[0].mark==1){
					alert(obj[0].message);
					//错误
					$("#showTooltips").attr("onclick",url2);
					$("#showTooltips").removeClass("weui_btn_disabled");
					return ;
				}else if(obj[0].mark==0){
					$("#weui_toast_content").text("");
					$("#weui_toast_content").text(obj[0].message);
					$('#toast').show();
	                setTimeout(function () {
	                    $('#toast').hide();
	                	window.location.href = data[0].url;
	                }, 5000);
				}
			},
			complete : function(jqXHR, textStatus){
				$("#showTooltips").attr("onclick",url2);
				var jqXHR=jqXHR;
				var textStatus=textStatus;
			}, 
			beforeSend : function(jqXHR, configs){
				url2=$("#showTooltips").attr("onclick");
				$("#showTooltips").attr("onclick","");
				$("#showTooltips").addClass("weui_btn_disabled");
				var jqXHR=jqXHR;
				var configs=configs;
			}, 
			error : function(jqXHR, textStatus, errorThrown){
					alert("系统请求超时");
					$("#showTooltips").attr("onclick",url2);
					$("#showTooltips").removeClass("weui_btn_disabled");
			}
		});
	}
}
</script>
</html>