<#include "../../commons/macro.ftl">
<@commonHead/>
<title>微信图片库</title>
<link rel="stylesheet" href="${ctx}/css/weixin/media.css"	/>
 <style type="text/css">
 .location {
	font-size:14px;
	height:40px;
	line-height: 40px;
	margin-left: 10px;
}
.layui-table th{
	text-align: center;
}
/***数据列表 样式开始***/
.line {
	background:#e5e5e5;
	height:1px;
	width:100%;
}
  .layui-btn-xs {
    height: 22px;
    line-height: 22px;
    padding: 0 5px;
    font-size: 12px;
}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >微信图片库</a>
</div>
<hr class="layui-bg-red">

		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<a class="layui-btn" href="javascript:void();" onclick="uploadLocalImage()">上传本地素材</a>
					<!-- <a class="layui-btn layui-btn-danger" href="javascript:void(-1);"   data-method="setTop">删除</a> -->
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/wechatMedia/queryList" method="post">
				     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
				     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
					</form>
				</div>
				<div  style="clear: both;"></div>
			</div>
			<div class="widget-content">
				<c:if test="${empty(page.result)||page.result==null }" var="status">
					<div class="alert">
						<strong>提示!</strong> 当前未添加数据.
					</div>
				</c:if>
				<c:if test="${status==false }">
					<div class="group img_pick" id="js_imglist">
						<ul class="group">
							<c:forEach var="wechatMedia" items="${page.result }">
								<li class="img_item js_imgitem" data-id="210819047">
									<div class="img_item_bd">
						                <img class="pic" width="169" height="169" src="${wechatMedia.thumbUrl }" data-previewsrc="" data-id="${wechatMedia.dbid }">
										<span class="check_content">
											<label class="frm_checkbox_label selected" >
											<input type="checkbox" class="frm_checkbox" name="id" id="id1" value="${wechatMedia.dbid }">
												<span class="lbl_content">${fn:length(wechatMedia.name)>10==true?fn:substring(wechatMedia.name,0,18):wechatMedia.name}</span>
											</label>
										</span>
									</div>
									<div class="msg_card_ft">
						                <ul class="grid_line msg_card_opr_list">
											<li class="grid_item size1of3 msg_card_opr_item">
						                        <a class="js_edit js_tooltip js_popover" onclick="editImage('${wechatMedia.name }','${wechatMedia.dbid }')"  href="javascript:;" >
													<span class="msg_card_opr_item_inner">
														<i class="icon18_common edit_gray">编辑</i>
													</span>
													<span class="vm_box"></span>
												</a>
											</li>
											<li class="grid_item size1of3 no_extra msg_card_opr_item">
												<a class="js_del js_tooltip js_popover" onclick="$.utile.operatorDataByDbid('${ctx}/wechatMedia/delete?dbids=${wechatMedia.dbid }','searchPageForm','您确定删除【${wechatMedia.name }】吗？')"  title="删除">
													<span class="msg_card_opr_item_inner">
														<i class="icon18_common del_gray">删除</i>
													</span>
													<span class="vm_box"></span>
												</a>
											</li>
										</ul>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
		</div>
		<div id="page"></div>
	</div>
</body>
<div style="display: none; width: 340px;" id="templateId">
	
</div>


<script type="text/javascript" src="${ctx}/widgets/utile/utile.js?=1"></script>
<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
layui.use(['laypage', 'table','layer','form'], function(){
  var table = layui.table,laypage=layui.laypage;
  form = layui.form;
  if(topParent==undefined){
	 layer = layui.layer;
  }else{
	 layer = topParent.layer;
  }
  var $ = layui.$, active = {
	  setTop:function(){
		  deleteByIds('${ctx}/wechatMedia/delete');
	  }
  };
  $('.layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
  
 //触发事件
  $('.layui-btn').on('click', function(){
	    var othis = $(this), method = othis.data('method');
	    active[method] ? active[method].call(this, othis) : '';
	});
  //完整功能
  laypage.render({
    elem: 'page',
    limit:${page.pageSize}
    ,count: ${page.totalCount}
    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    ,curr:${page.currentPageNo}
    ,jump: function(obj,first){
    	if(!first){
	      $("#currentPage").val(obj.curr);
	      $("#paramPageSize").val(obj.limit);
	      var qForm=$("#searchPageForm");
	      qForm.submit();
   	    }
    }
  });
});
</script>
	<script type="text/javascript">
		function editImage(v,wechatMediaId){
			var s='<br>'+
				'<div class="layui-form-item">'+
				'<label class="layui-form-label" style="color: red">名称：</label>'+
				'<div class="layui-input-block">'+
				'	<input type="hidden" class="layui-input"   id="wechatMediaId" name="wechatMediaId" value="'+wechatMediaId+'">'+
				'	<input type="text" class="layui-input" style="width:320px;" id="imageName" name="imageName" value="'+v+'" >'+
				'</div>'+
				'</div>'+
			'<div class="layui-form-item" i>'+
				'<label class="layui-form-label" style="color: red"></label>'+
				'<div class="layui-input-block" d="messageError" style="display:none">'+
			'		填写名称错误！'+
			'</div>'+
			'</div>';
			
		layer.open({
				type: 1,
			    title: '设置图片名称',
			    content:s
			    ,area: ['460px', '260px']
		        ,shade: 0.8
			    ,btn: ['确定', '关闭']
				 ,yes: function(index, layero){
					 var body = layer.getChildFrame('body', index);
					var imageName= layero.find('#imageName').val();
					var wechatMediaId= layero.find('#wechatMediaId').val();
			    	if(null==imageName||imageName==''){
			    		layero.find("#messageError").show();
			    		return false;
			    	}
			    	var url='${ctx}/wechatMedia/saveEdit';
			    	var params={"name":imageName,"wechatMediaId":wechatMediaId};
			    	$.post(url,params,function callBack(data) {
						if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
							$.utile.tips(data[0].message);
								setTimeout(
								function() {
									window.location.href = data[0].url;
									layer.close(index);
								}, 1000);
						}
						if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
							$.utile.tips(data[0].message);
							// 保存失败时页面停留在数据编辑页面
							layer.close(index);
						}
						return;
					});
		    		
					return true;
			    }
			    ,btn2: function(index, layero){
			    	layer.close(index);
			    }
			}); 
		}
		function uploadLocalImage(){
			var path="";
			layer.open({
		        type: 2 //此处以iframe举例
		        ,title: '选择文件'
		        ,area: ['760px', '460px']
		        ,shade: 0.8
		        ,maxmin: true
		        ,content: path+'/wechatMedia/uploadLocalImage'
		        ,zIndex: layer.zIndex //重点1
		        ,success: function(layero){
		          //layer.setTop(layero); //重点2
		        }
		        ,btn: ['确定', '关闭']
		        ,yes: function(index, layero){
		        	var body = layer.getChildFrame('body', index);
					var json= body.find('#fileUpload').val();
					if(null!=json){
		    			window.location.reload();
				       	layer.close(index);
			       }else{
			    	   alert("请选择图片后点击【确定】按钮");
			    	   layer.close(index);
			       }
		        }
		        ,btn2: function(index, layero){
		        	layer.close(index);
		          //return false 开启该代码可禁止点击该按钮关闭
		        }
		      });
		}
	</script>
</html>
