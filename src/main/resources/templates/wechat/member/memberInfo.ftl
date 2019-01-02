<#include "../../commons/macro.ftl">
<@commonHead/>
    <link rel="stylesheet" href="${ctx}/css/wechat/comm.css?${now}" type="text/css" />
    <link rel="stylesheet" href="${ctx}/weui/style/weui.css?" type="text/css" />
    <script src="${ctx}/widgets/bootstrap3/jquery.min.js"></script>
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <script src="${ctx}/widgets/easyvalidator/js/easy_validator.pack5.js"></script>
    <style type="text/css">
    </style>
<title>我的订单</title>
</head>
<body>
<div class="title">
    <a class="btn-back" onclick="window.history.go(-1)" href="javascript:void(0)"><i class="icon icon-2-1"></i></a>
    <h3>我的资料</h3>
</div>
<br>
<br>
	<form action="" id="frmId" name="frmId" method="post" target="_self">
	<input type="hidden" id="dbid" name="member.dbid" value="${member.dbid }">
	<div class="weui_cells weui_cells_form">
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input type="text" id="name" name="member.name"  placeholder="请输姓名" value="${member.name }"  class="weui_input" checkType="string,1">
            </div>
        </div>
        <div class="weui_cell weui_cell_select weui_select_after">
            <div class="weui_cell_hd">
               	称呼
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <select class="weui_select" name="member.sex" id="sex">
                    <option value="男" ${member.sex==男?'selected="selected"':'' } >先生</option>
                    <option value="女" ${member.sex==女?'selected="selected"':'' } >女士</option>
                </select>
            </div>
        </div>
    </div>
    <div class="weui_btn_area">
        <a class="weui_btn weui_btn_warn" href="javascript:" id="showTooltips" onclick="ajaxSubmit('${ctx}/agentWechat/saveInfo','frmId')">提交</a>
    </div>
    </form>
     <div id="toast" style="display: none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <i class="weui_icon_toast"></i>
	        <p class="weui_toast_content">保存数据成功</p>
	    </div>
	</div>
</body>
<script type="text/javascript">
var comm=1,sendStatus=1,delay=0,oldValue;
function ajaxCity(sel){
	var value=$(sel).val();
	$.post("${ctx}/agentWechat/ajaxCity?parentId="+value+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			$("#cityDiv").text("");
			$("#cityDiv").append(data.city);
			$("#areaDiv").text("");
			$("#areaDiv").append(data.area);
		}
	});
}
function ajaxArea(sel){
	var value=$(sel).val();
	$.post("${ctx}/agentWechat/ajaxArea?parentId="+value+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			$("#areaDiv").text("");
			$("#areaDiv").append(data.area);
		}
	});
}
function sendMsg() {
	if(sendStatus==1){
		var text="重新获取（<span id='second'>10</span>）";
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
					alert("保存数据失败!");
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
				url2=$(".butSave").attr("onclick");
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