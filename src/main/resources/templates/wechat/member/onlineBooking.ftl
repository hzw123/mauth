<#include "../../commons/macro.ftl">
<@commonHead/>
	<link rel="stylesheet" href="${ctx}/weui/style/weui.css?${now}" type="text/css" />
	<script src="${ctx}/widgets/jquery.min.js"></script>
	<script src="${ctx}/weui/example/zepto.min.js"></script>
    <script src="${ctx}/weui/example/router.min.js"></script>
    <script src="${ctx}/widgets/utile/jsdateUtile.js"></script>
    <script src="${ctx}/widgets/easyvalidator/js/easy_validator.pack5.js"></script>
    <style type="text/css">
    	.weui_label{
    		width: 80px;
    	}
    </style>
<title>在线预约</title>
</head>
<body>
<br>
<div class="container" id="container">
	<form action="" id="frmId" name="frmId" method="post" target="_self">
	<input type="hidden" id="url" name="url" value="${url }">
	<input type="hidden" id="memberId" name="memberId" value="${member.dbid }">
	<div class="weui_cells">
		<div class="cell">
			<div class="bd">
					<div class="weui_cells_title">预约店铺</div>
					<div class="weui_cells weui_cells_radio">
						<c:forEach var="enterprise" items="${enterprises }" >
				            <label class="weui_cell weui_check_label" for="enterpriseId${enterprise.dbid }">
				                <div class="weui_cell_bd weui_cell_primary">
				                    <p>${enterprise.name}</p>
				                </div>
				                <div class="weui_cell_ft">
				                    <input type="radio" class="weui_check" name="enterpriseId" id="enterpriseId${enterprise.dbid }" value="${enterprise.dbid }" ${member.enterprise.dbid==enterprise.dbid?'checked="checked"':'' } onclick="ajaxArit()">
				                    <span class="weui_icon_checked"></span>
				                </div>
				            </label>
			            </c:forEach>
			        </div>
			        <br>
			        <div class="weui_cell weui_cell_select weui_select_after">
			            <div class="weui_cell_hd">
			                技师
			            </div>
			            <div class="weui_cell_bd weui_cell_primary">
			                <select class="weui_select" id="artificerId" name="artificerId" >
			                    <option value="-1">请选择...</option>
				            	<c:forEach items="${artificers }" var="artificer">
				            		<option value="${artificer.dbid }">${artificer.name }</option>
				            	</c:forEach>
			                </select>
			            </div>
			        </div>
			        <div class="weui_cell">
			            <div class="weui_cell__hd"><label for="" class="weui_label">日期</label></div>
			            <div class="weui_cell__bd">
			                <input class="weui_input" type="date" name="onlineBooking.bookingDate" id="bookingDate" value="" placeholder="" style="width: 160px;">
			            </div>
			        </div>
			        <div class="weui_cell">
			            <div class="weui_cell__hd"><label for="" class="weui_label">时间</label></div>
			            <div class="weui_cell__bd">
			                <input class="weui_input" type="time" name="onlineBooking.bookingTime" id="bookingTime" value="" placeholder="" style="width: 160px;">
			            </div>
			        </div>
			        <div class="weui_cell">
			           <div class="weui_cell__hd"><label class="weui_label">人数</label></div>
			           <div class="weui_cell__bd">
			               <input class="weui_input" type="number" name="onlineBooking.maleNum" pattern="[0-9]*" placeholder="请输入人数">
			           </div>
			       </div>
			    </div>
		    </div>
		 </div>
	    <br>
	   <div class="weui_btn_area">
	        <a class="weui_btn weui_btn_primary" href="javascript:" id="showTooltips" onclick="ajaxSubmit('${ctx}/memberWechat/saveOnlineBooking','frmId')">立即预约</a>
	    </div>
    </form>
     <div id="toast" style="display: none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <i class="weui_icon_toast"></i>
	        <p class="weui_toast_content" id="weui_toast_content">预约成功，页面跳转到会员中心</p>
	    </div>
	</div>
</div>
</body>
<script type="text/javascript">
var start = new Date();
var bookingDate= start.format("yyyy-MM-dd");
$("#bookingDate").val(bookingDate);

start.add("n", 180);
var bookingTime=start.format("hh:mm");
$("#bookingTime").val(bookingTime);
function ajaxArit(){
	var enterpriseId=$("input[type='radio']:checked").val();
	$.post('${ctx}/memberWechat/ajaxArtificer?enterpriseId='+enterpriseId+"&date="+new Date,{},function (data){
		$("#artificerId").empty();
		$("#artificerId").append(data);
	})
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