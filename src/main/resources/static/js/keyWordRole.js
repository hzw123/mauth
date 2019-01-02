//清除样式
function clearClass(divId){
	$("#"+divId).find(".error").each(function(i){
		$(this).removeClass("error");
	})
	$("#"+divId).find(".success").each(function(i){
		$(this).removeClass("success");
	})
}
//操作提示信息
function showMessage(message){
	var d = dialog({
	    content: message
	});
	d.show();
	setTimeout(function () {
	    d.close().remove();
	}, 2000);
}
//radio取值
function  getRadioBoxValue(ifram) 
{ 
    var obj = ifram.document.getElementsByName("weixinAutoresponse.matchingType");  //这个是以标签的name来取控件
         for(i=0; i<obj.length;i++)    {
          if(obj[i].checked)    { 
              return   obj[i].value; 
          } 
      }         
     return undefined;       
}
function bindClick(){
	//关注状态选择
	$(".js-filter-quickday li").each(function(index){
		  $(this).click(function(){
			  var cla=$(this).attr("class");
			  //点击选择
			  if(cla==undefined||cla==''){
				  $(".js-filter-quickday .active").removeClass("active");
				  var data_tag=$(this).attr("data-tag");
				  $("#quickday").val(data_tag);
				  $(this).addClass("active");
			  }else{
				//移除样式
				$(this).removeClass("active");
				$("#quickday").val("-1m");
			  }
		  })
	})
}
//新建规则
function editKeyWordRole(dbid){
	var url='/weixinKeyWordRole/edit';
	var title="创建规则";
	if(null!=dbid&&undefined!=dbid){
		url='/weixinKeyWordRole/edit?dbid='+dbid;
		title="编辑规则";
	}
	var d = dialog({
	    title: title,
	    fixed: false,
	    url: url,
	    width:'460px',
	    okValue: '确定',
	    ok: function () {
	    	var iframeWindow = this.iframeNode.contentWindow; 
	    	var frmObj=$(iframeWindow.document.getElementById('frmId'));
	    	var params=$(frmObj).serialize();
			var validata = validateFormObj(frmObj);
			if (validata == true) {
		    	$.post("${ctx}/weixinKeyWordRole/save",params,function callBack(data) {
		    		if(null!=dbid&&undefined!=dbid){
		    			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
		    				var obj=data[0].url; 
		    				obj=$.parseJSON(obj);
		    				$("#rule-name"+dbid).text(obj.name);
		    				showMessage(data[0].message);
		    			}
		    			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
		    				showMessage("更新数据失败");
		    			}
		    		}else{
		    			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
		    				var html=data[0].url; 
		    				$("#js-rule-container").prepend(html);
		    				showMessage(data[0].message);
		    			}
		    			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
		    				showMessage("添加数据失败");
		    			}
		    		}
				});
			}else{
				return false;
			}
	    	 return true;
	    },
	    cancelValue: '取消',
	    cancel: function () {}
	});
	d.show();
}
/**
 * 启用或停用
 * @param dbid
 * @param status
 */
function deleteWeixinKeyWordRole(dbid){
	var content="";
	content="确定删除该规则吗？";
	var d = dialog({
	    title: '提示',
	    content: content,
	    okValue: '确定',
	    width: 240,
	    ok: function () {
	    	$.post("/weixinKeyWordRole/delete?dbids="+dbid+"&datetime=" + new Date(),{},function callBack(data) {
    			if (data[0].mark == 1) {// 删除数据失败时提示信息
    				showMessage("操作失败");
    			}
    			if (data[0].mark == 0) {// 删除数据成功提示信息
    				showMessage("操作成功")
    				$("#rule-group"+dbid).remove();
    			}
	    	})
	    	return true;
	    },
	    cancelValue: '取消',
	    cancel: function () {}
	});
	d.show(document.getElementById('js-disable-rule'+dbid));
}

//创建编辑关键词
function editKey(dbid,weixinKeyWordRoleId){
	var url='/weixinKeyWordRole/editKey?weixinKeyWordRoleId='+weixinKeyWordRoleId;
	if(null!=dbid&&undefined!=dbid&&dbid!=''){
		url='/weixinKeyWordRole/editKey?dbid='+dbid+"&weixinKeyWordRoleId="+weixinKeyWordRoleId;
	}
	var d = dialog({
		fixed: false,
	    url: url,
	    width:'340px',
	    okValue: '确定',
	    ok: function () {
	    	var iframeWindow = this.iframeNode.contentWindow; 
	    	var frmObj=$(iframeWindow.document.getElementById('frmId'));
	    	var params=$(frmObj).serialize();
			var validata = validateFormObj(frmObj);
			var keyword=iframeWindow.document.getElementById('keyword').value;
			var matchingType=getRadioBoxValue(iframeWindow);
			if (validata == true) {
		    	$.post("${ctx}/weixinKeyWordRole/saveEditKey",params,function callBack(data) {
		    		if(null!=dbid&&undefined!=dbid&&dbid!=''){
		    			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
		    				var obj=data[0].url; 
		    				var span=$("#keyword"+weixinKeyWordRoleId+""+dbid).find("span");
		    				$(span[0]).text(keyword);
		    				if(matchingType=="1"){
		    					$(span[1]).text("全匹配");
		    				}
		    				if(matchingType=="2"){
		    					$(span[1]).text("模糊匹配");
		    				}
		    				showMessage(data[0].message);
		    			}
		    			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
		    				showMessage(data[0].message);
		    			}
		    		}else{
		    			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
		    				var html=data[0].url; 
		    				$("#keyword-list"+weixinKeyWordRoleId).append(html);
		    				showMessage(data[0].message);
		    			}
		    			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
		    				showMessage(data[0].message);
		    			}
		    		}
				});
			}else{
				return false;
			}
	    	 return true;
	    },
	    cancelValue: '取消',
	    cancel: function () {}
	});
	if(null!=dbid&&undefined!=dbid&&dbid!=''){
		d.show(document.getElementById('keyword'+weixinKeyWordRoleId+dbid));
	}else{
		d.show(document.getElementById('editKey'+weixinKeyWordRoleId+dbid));
	}
}
//删除
function deleteKey(dbid,spreadDetailId){
	var d = dialog({
	    title: '提示',
	    content: '确定删除关键词吗？',
	    okValue: '确定',
	    width: 240,
	    ok: function () {
	    	$.post("/weixinKeyWordRole/deleteKeyWord?dbids="+dbid+"&datetime=" + new Date(),{},function callBack(data) {
	    		if (data[0].mark == 1) {// 删除数据失败时提示信息
	    			showMessage("删除数据失败");
	    			return false;
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					showMessage("删除数据成功");
					$("#keyword"+spreadDetailId+""+dbid).remove();
					return false;
				}
	    	})
	       
	    },
	    cancelValue: '取消',
	    cancel: function () {}
	});
	d.show(document.getElementById('keyword'+spreadDetailId+dbid));
}
var d=null;
//设置回复
function message(dbid){
	$("#weixinKeyWordRoleId").val(dbid);
	$("#js-txta").show();
	$("#js-txta").val("");
	$("#msgdbid").val("");
	$(".complex-backdrop").hide();
	$("#js-complex-content").hide();
	$("#submitFm input").each(function(i){
		if($(this).attr("id")!='weixinKeyWordRoleId'){
			$(this).val("");
		}
	})
	$("#msgtype").val(1);
	var content=$("#popover-inner");
	d = dialog({
		content: content,
		quickClose: true,
		align: 'right'
	});
	d.show(document.getElementById('js-add-reply'+dbid));
}
//编辑
function editMessage(weixinKeyWordRoleId,dbid){
	$("#weixinKeyWordRoleId").val(weixinKeyWordRoleId);
	$("#weixinKeyAutoresponseDbid").val(dbid);
	$.post("/weixinKeyWordRole/ajaxEditweixinKeyAutoresponse?weixinKeyAutoresponseDbid="+dbid+"&date="+new Date(),{},function(data){
		if(data.type=="1"){
			$("#msgtext").val("");
			$("#js-txta").val("");
			$("#js-txta").show();
			$(".complex-backdrop").hide();
			$("#js-complex-content").hide();
			$("#js-txta").val(data.value);
			$("#msgtype").val(1);
			$("#msgtext").val(data.value);
		}
		if(data.type=="2"||data.type=="3"){
			$("#js-txta").hide();
			$(".complex-backdrop").show();
			$("#js-complex-content").show();
			$("#msgtype").val(data.type);
			$("#msgdbid").val(data.dbid);
			$("#js-complex-content").text("");
			$("#js-complex-content").append(data.value);
			var height=$("#js-complex-content").height();
			if(height>120){
				$(".content-wrapper").css("min-height",height+20);
			}else{
				$(".content-wrapper").css("min-height",120);
			}
		}
		var content=$("#popover-inner");
		d = dialog({
			content: content,
			quickClose: true,
			align: 'right'
		});
		d.show(document.getElementById('js-add-reply'+weixinKeyWordRoleId+""+dbid));
	} )
}
/**
 * 功能描述：选择文本
 */
function selectText(){
	$("#msgtype").val(2);
	$("#js-txta").val("");
	var url='/weixinTexttemplate/selectText';
	var d = dialog({
		title: '选择文本',
		fixed: false,
	    url: url,
	    width:'760px',
    	onclose: function () {
			var value=this.returnValue;
			$.post("${ctx}/weixinTexttemplate/ajaxTempt?weixinTexttemplateId="+value+"&date="+new Date(),{},function(data){
				$("#js-txta").hide();
				$(".complex-backdrop").show();
				$("#js-complex-content").show();
				$("#js-complex-content").text("");
				$("#msgdbid").val("");
				$("#js-complex-content").append(data.value);
				$("#msgdbid").val(data.dbid);
				var height=$("#js-complex-content").height();
				if(height>120){
					$(".content-wrapper").css("min-height",height+20);
				}else{
					$(".content-wrapper").css("min-height",120);
				}
			})
		},
		oniframeload: function () {
		}
	});
	d.show();
}
/**
 * 功能描述：选择文本
 */
function selectNewsItem(){
	$("#msgtype").val(3);
	$("#js-txta").val("");
	var url='/weixinNewsItem/selectNewsItem';
	var d = dialog({
		title: '选择图文',
		fixed: false,
		url: url,
		width:'760px',
		onclose: function () {
			var value=this.returnValue;
			$.post("${ctx}/weixinNewsItem/ajaxTempt?weixinTexttemplateId="+value+"&date="+new Date(),{},function(data){
				$("#js-txta").hide();
				$(".complex-backdrop").show();
				$("#js-complex-content").show();
				$("#js-complex-content").text("");
				$("#msgdbid").val("");
				$("#js-complex-content").append(data.value);
				$("#msgdbid").val(data.dbid);
				var height=$("#js-complex-content").height();
				if(height>120){
					$(".content-wrapper").css("min-height",height+20);
				}else{
					$(".content-wrapper").css("min-height",120);
				}
			})
		},
		oniframeload: function () {
		}
	});
	d.show();
}
/**
 * 功能描述：选择图片
 */
function selectWechatMedia(){
	$("#msgtype").val(4);
	$("#js-txta").val("");
	var url='/wechatMedia/selectWechatMedia';
	var d = dialog({
		title: '选择图片',
		fixed: false,
		url: url,
		width:'760px',
		onclose: function () {
			var value=this.returnValue;
			$.post("${ctx}/wechatMedia/ajaxTempt?wechatMediaId="+value+"&date="+new Date(),{},function(data){
				$("#js-txta").hide();
				$(".complex-backdrop").show();
				$("#js-complex-content").show();
				$("#js-complex-content").text("");
				$("#msgdbid").val("");
				$("#js-complex-content").append(data.value);
				$("#msgdbid").val(data.dbid);
				var height=$("#js-complex-content").height();
				if(height>120){
					$(".content-wrapper").css("min-height",height+20);
				}else{
					$(".content-wrapper").css("min-height",120);
				}
			})
		},
		oniframeload: function () {
		}
	});
	d.show();
}
function countWordNum(){
	var jstxta=$("#js-txta").val();
	var length=jstxta.length;
	if(length>300){
		$("#js-txta").val(jstxta.substring(0,300));
	}
	$(".word-counter i").text(300-length);
}
/**
 * 功能描述：删除选择文本
 */
function removeSelectText(obj){
	$(obj).parent().remove();
	$("#msgtype").val(1);
	$("#js-txta").show();
	$("#msgdbid").val("");
	$(".complex-backdrop").hide();
	$("#js-complex-content").hide();
}
/**
 * 功能描述：选择文本输入
 */
function choiceText(){
	$("#msgtype").val(1);
	$("#js-txta").show();
	$(".complex-backdrop").hide();
	$("#js-complex-content").hide();
}
/**
 * 提交选择图文
 */
function submitForm(){
	var jstxta=$("#js-txta").val();
	var weixinKeyAutoresponseDbid=$("#weixinKeyAutoresponseDbid").val();
	$("#msgtext").val(jstxta);
	var param=$("#submitFm").serialize();
	var weixinKeyWordRoleId=$("#weixinKeyWordRoleId").val();
	$.post("${ctx}/weixinKeyWordRole/saveAjaxResult?date="+new Date(),param,function(data){
		if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
			if(null==weixinKeyAutoresponseDbid||weixinKeyAutoresponseDbid==''||weixinKeyAutoresponseDbid==undefined){
				$("#reply-list"+weixinKeyWordRoleId).append(data[0].url);
				d.remove();
				var info=$("#reply-container"+weixinKeyWordRoleId+" .info");
				info.hide();
				showMessage("设置自动回复成功");
			}else{
				$("#reply-list"+weixinKeyWordRoleId+""+weixinKeyAutoresponseDbid).text("");
				$("#reply-list"+weixinKeyWordRoleId+""+weixinKeyAutoresponseDbid).append(data[0].url);
				d.remove();
			}
		}
		if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
			showMessage(data[0].message);
			// 保存失败时页面停留在数据编辑页面
		}
		
	})
}
/**
 * 删除自动回复
 * @param weixinKeyWordRoleId
 * @param dbid
 */
function deleteAutoResponse(weixinKeyWordRoleId,dbid){
	var d = dialog({
	    title: '提示',
	    content: '确定删除回复吗？',
	    okValue: '确定',
	    width: 240,
	    ok: function () {
	    	$.post("/weixinKeyWordRole/deleteAutoResponse?dbids="+dbid+"&datetime=" + new Date(),{},function callBack(data) {
	    		if (data[0].mark == 1) {// 删除数据失败时提示信息
	    			showMessage('删除数据失败');
	    			return false;
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					showMessage("删除数据成功");
					$("#reply-list"+weixinKeyWordRoleId+dbid).remove();
					var li=$("#reply-list"+weixinKeyWordRoleId).find("li");
					if(li.length<=0){
						 var info=$("#reply-container"+weixinKeyWordRoleId+" .info");
						 info.show();
					}
					return false;
				}
	    	})
	       
	    },
	    cancelValue: '取消',
	    cancel: function () {}
	});
	d.show(document.getElementById('reply-list'+weixinKeyWordRoleId+dbid));
}