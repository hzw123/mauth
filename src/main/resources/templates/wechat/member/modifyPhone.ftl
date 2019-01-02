<#include "../../commons/macro.ftl">
<@commonHead/>

    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui.css?${now}" type="text/css" />
	<script src="${ctx}/widgets/bootstrap3/jquery.min.js"></script>
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <script src="${ctx}/widgets/easyvalidator/js/easy_validator.pack5.js"></script>
<title>修改电话号码</title>
</head>
<body>
<br>
<br>
<div class="msg">
<div class="weui_msg">
    <div class="weui_text_area">
         <p class="weui_msg_desc" style="font-size: 16px;">您当前的电话号码是</p>
       <h2 class="weui_msg_title">${member.mobilePhone }</h2> 
    </div>
</div>
</div>
<form action="" id="frmId" name="frmId" method="post" target="_self">
<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
<div class="weui_cells weui_cells_form">
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">电话</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input class="weui_input" id="mobilePhone" name="mobilePhone" type="number" pattern="[0-9]*" placeholder="请输入号码" checkType="mobilePhone" onchange="validatemobilePhone()" >
            </div>
        </div>
        <div class="weui_cell weui_vcode" style="height: 50px;">
            <div class="weui_cell_hd"><label class="weui_label">验证码</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input type="number" id="codeNum" name="codeNum" placeholder="请输入验证码" pattern="[0-9]*" class="weui_input" checkType="string,4,4">
            </div>
            <div class="weui_cell_ft" style="padding-right: 14px;">
                <a href="javascript:;" id="btnSendMsg" class="weui_btn weui_btn_mini weui_btn_default" onclick="sendMsg()">获取验证码</a>
            </div>
        </div>
    </div>
   <div class="weui_btn_area">
        <a class="weui_btn weui_btn_warn" href="javascript:" id="showTooltips" onclick="ajaxSubmit('${ctx}/memberWechat/saveModifyPhone','frmId')">提交</a>
    </div>
    </form>
     <div id="toast" style="display: none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <i class="weui_icon_toast"></i>
	        <p class="weui_toast_content">提交成功</p>
	    </div>
	</div>
</body>
<script type="text/javascript">
var comm=1,sendStatus=1,delay=0,oldValue;
function validatemobilePhone(){
	var mobile=$("#mobilePhone").val();
	if(null==mobile||mobile==''){
		change_error_style($("#mobilePhone"),"add")
		return ;
	}else{
		if (!checkMobilePhone($("#mobilePhone"))) {
			return ;
		}
	}
	var mobilePhone='${member.mobilePhone }';
	if(mobilePhone==mobile||mobilePhone.indexOf(mobile)!=-1){
		change_error_style($("#mobilePhone"),"add")
		alert("修改手机号和当前号码一样，请确认");
		return ;
	}
	$.post("${ctx}/memberWechat/validateMember",{'mobile':mobile},function callBack(data) {
		if (data=='2') {
		}
		if (data=='1') {
			alert("手机号已经注册，请确认");
		}
	})
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
		$.post("${ctx}/verificationCodeWechat/sendModifyMobileMessage",{'mobile':mobile},function callBack(data) {
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
					$('#toast').show();
	                setTimeout(function () {
	                    $('#toast').hide();
	                	window.location.href = data[0].url;
	                }, 2000);
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