/** 获取checkBox的value* */
function getCheckBox() {
	var obj={};
  	var array = new Array();
  	var checkeds = $("input[type='checkbox'][name='id']");
  	$.each(checkeds, function(i, checkbox) {
  		if (checkbox.checked) {
  			array.push(checkbox.value);
  		}
  	});
  	obj.length=array.length;
  	obj.ids=array.toString();
  	return obj;
}
function checkBefDel() {
	var checkeds = $("input[type='checkbox'][name='id']");
	var length = 0;
	$.each(checkeds, function(i, checkbox) {
		if (checkbox.checked) {
			length++;
		}
	})
	
}

function selectAll(checkbox, domname) {
	var doms = document.getElementsByName(domname);
	for ( var i = 0; i < doms.length; i++) {
		if (doms[i].type == "checkbox") {
			doms[i].checked = checkbox.checked;
		}
	}
}
function deleteByIds(url){
	  var obj=getCheckBox();
	  length=obj.length;
	  if (length <= 0) {
		layer.msg('请选择数据后在操作');
		return ;
	  }
	  layer.confirm('确定删除'+length+'条数据吗？',{icon: 3}, function(index){
		  var params=$("#searchPageForm").serialize();
		  $.post(url+'?dbids='+obj.ids,params,function(data){
			  if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
					layer.alert(data[0].message, {icon: 5});
				}
				if (data[0].mark == 1) {// 删除数据失败时提示信息
					layer.msg(data[0].message, {icon: 5});
				}
				if (data[0].mark == 0) {// 删除数据成功提示信息
					layer.msg(data[0].message,{icon: 1});
					setTimeout(function() {
						window.location.href = data[0].url
					}, 1000);
					layer.closeAll();
				}
		  })
	  })
}
function deleteById(url){
	layer.confirm('确定删除选中条数据吗？',{icon: 3}, function(index){
	 var params=$("#searchPageForm").serialize();
	  $.post(url + "&datetime=" + new Date(),params,callBack);
		function callBack(data) {
			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				layer.alert(data[0].message, {icon: 5});
			}
			if (data[0].mark == 1) {// 删除数据失败时提示信息
				layer.msg(data[0].message, {icon: 5});
			}
			if (data[0].mark == 0) {// 删除数据成功提示信息
				layer.msg(data[0].message,{icon: 1});
				setTimeout(function() {
					window.location.href = data[0].url
				}, 1000);
				layer.closeAll();
			}
		}
	})
}
function submitFrm(url){
	var params = $("#frmId").serialize();
	  var target = $("#frmId").attr("target") || "_self";
	  $.ajax({	
			url : url, 
			data : params, 
			async : false, 
			timeout : 20000, 
			dataType : "json",
			type:"post",
			success : function(data, textStatus, jqXHR){
				//alert(data.message);
				var obj;
				if(data.message!=undefined){
					obj=$.parseJSON(data.message);
				}else{
					obj=data;
				}
				if(obj[0].mark==1){
					//错误
					layer.msg(obj[0].message,{icon: 5});
					$("#submitBut").bind("onclick");
					return ;
				}else if(obj[0].mark==0){
					layer.msg(data[0].message,{icon: 1});
					if (target == "_self") {
						setTimeout(
								function() {
									window.location.href = obj[0].url
								}, 1000);
					}
					if (target == "_parent") {
					 setTimeout(
							function() {
								window.parent.frames["contentUrl"].location=obj[0].url;
								 parent.layer.closeAll();
							},1000) 
					}
					// 保存数据成功时页面需跳转到列表页面
				}
			},
			complete : function(jqXHR, textStatus){
				var jqXHR=jqXHR;
				var textStatus=textStatus;
			}, 
			beforeSend : function(jqXHR, configs){
				$("#submitBut").unbind("onclick");
				var jqXHR=jqXHR;
				var configs=configs;
			}, 
			error : function(jqXHR, textStatus, errorThrown){
					layer.msg("系统请求超时");
					$("#submitBut").bind("onclick");
			}
		});
}