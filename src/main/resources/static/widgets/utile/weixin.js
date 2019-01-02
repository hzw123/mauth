function validateFrm(){
		var name=$("#name").val();
		var phone=$("#mobilePhone").val();
		var bookingDate=$("#bookingDate").val();
		if(name==''||name.length<=0){
			$("#name").focus();
			return false;
		}
		if(phone==''||phone.length<=0){
			$("#mobilePhone").focus();
			return false;
		}else{
			if(checkMobilePhone(phone)==true||checkPhone(phone)==true){
			}else{
				$("#mobilePhone").focus();
				return false;
			}
		}
		if(bookingDate==''||bookingDate.length<0){
			$("#bookingDate").focus();
			return false;
		}
		return true;	
	}
function validateFrmFiwa(){
	var name=$("#name").val();
	var dg=$("#dg").val();
	var agt=$("#agt").val();
	var jp=$("#jp").val();
	var phone=$("#mobilePhone").val();
	alert(phone);
	if(dg=='0'){
		alert("请选择德国得分！");
		$("#dg").focus();
		return false;
	}
	if(agt=='0'){
		alert("请选择阿根廷得分！");
		$("#agt").focus();
		return false;
	}
	if(name==''||name.length<=0){
		alert("请填写您的姓名！");
		$("#name").focus();
		return false;
	}
	if(phone==''||phone.length<=0){
		alert("请填写您的电话号码！");
		$("#mobilePhone").focus();
		return false;
	}else{
		if(checkMobilePhone(phone)==true||checkPhone(phone)==true){
		}else{
			alert("请填写您的正确电话号码！");
			$("#mobilePhone").focus();
			return false;
		}
	}
	if(jp=='0'){
		alert("请选择奖品！");
		$("#jp").focus();
		return false;
	}
	return true;	
}
	/**
	 * 功能：判断手机号码
	 */
	function checkMobilePhone(value) {
		var valid = false;
		valid = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/.test(value);
		return valid;
	}
	/**
	 * 功能：判断电话号码是否合法，电话号码的格式为：电话号码；或者 区号-电话号码
	 */
	function checkPhone(value) {
		var valid = false;
		valid = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(value);
		return valid;
	}
	
	function ajaxCarModel(sel){
		var options=$("#"+sel+" option:selected");
		var value=options[0].value;
		$("#carModel").remove();
		if(value==''||value<=0){
			return;
		}
		$.post("${ctx}/carModel/ajaxCarModel?carSeriyId="+value+"&dateTime="+new Date(),{},function (data){
			if(data!="error"){
				$("#areaLabel").append(data);
			}
		});
	}
	function ajaxSubmit(url){
		var state=validateFrm();
		if(state){
			var params = $("#frmId").serialize();
			$.post(url,params,function callBack(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					// 保存数据成功时页面需跳转到列表页面
					$("#message").addClass("alert alert-success");
					$("#message").text(data[0].message);
					$("#message").css("display","");
					$("#message").show();
					$("#subButton").attr("onclick","")
					$("#subButton").find("a").css("color","#E3E0D5");
					
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					// 保存失败时页面停留在数据编辑页面
					$("#message").addClass("alert alert-error");
					$("#message").text(data[0].message);
					$("#message").css("display","");
					$("#message").show();
				}
				return;
			});
		}
	}
	function ajaxSubmitOnline(url){
		var state=validateFrm();
		if(state){
			var params = $("#frmId").serialize();
			$.post(url,params,function callBack(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					// 保存数据成功时页面需跳转到列表页面
					$("#message").addClass("alert alert-success");
					$("#message").css("display","");
					$("#message").show();
					$("#subButton").attr("onclick","")
					$("#subButton").find("a").css("color","#E3E0D5");
					
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					// 保存失败时页面停留在数据编辑页面
					$("#message").addClass("alert alert-error");
					$("#message").text(data[0].message);
					$("#message").css("display","");
					$("#message").show();
				}
				return;
			});
		}
	}
	function ajaxSubmitFiwa12(url){
		var state=validateFrmFiwa();
		var ms='<a class="shareButton" style="padding: 12px 0;cursor: pointer;color:blue;" onclick="window.location.href=\'http://mp.weixin.qq.com/s?__biz=MzA4OTM1MTQxOA==&mid=200182638&idx=1&sn=fac12073d80f1f3d5cafd03246bf063a#rd\'">关注西物奇瑞微信</a>';
		if(state){
			var params = $("#frmId").serialize();
			$.post(url,params,function callBack(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					// 保存数据成功时页面需跳转到列表页面
					$("#message").addClass("alert alert-success");
					$("#message").text(data[0].message).append(ms);
					$("#message").css("display","");
					$("#message").show();
					$("#subButton").attr("onclick","")
					$("#subButton").find("a").css("color","#E3E0D5");
					
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					// 保存失败时页面停留在数据编辑页面
					$("#message").addClass("alert alert-error");
					$("#message").text(data[0].message).append(ms);
					$("#message").css("display","");
					$("#message").show();
				}
				return;
			});
		}
	}
	
	
	
	

