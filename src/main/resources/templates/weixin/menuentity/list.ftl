<#include "../../commons/macro.ftl">
<@commonHead/>
<title>编辑菜单</title>
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
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >编辑菜单</a>
</div>
<hr class="layui-bg-red">
<blockquote class="layui-elem-quote">
		<p>网页授权获取用户信息配置</p>
		<p>1、网页授权获取用户信息的公众号必须认证。</p>
		<p>2、请再开发者中心->网页服务-网页账号栏目中填写安全域名
		<p>3、请填复制该域名：<%=request.getServerName() %> 至配置框中
</blockquote>
<div class="layui-row" >
    <div class="layui-col-xs6" style="padding-left: 12px;">
			<a class="layui-btn" href="javascript:void(-1)" onclick="$.utile.openDialog('${ctx}/weixinMenuentity/add?parentMenu=1&groupId=${weixinMenuentityGroup.dbid}','添加菜单',1024,560)">
				<i	class="icon-plus-sign icon-white"></i>添加</a>
			<c:if test="${weixinMenuentityGroup.type==1 }">
				<a class="layui-btn" onclick="synDataDepart()">菜单同步到微信</a>
				<a class="layui-btn layui-btn-danger" onclick="deleteWechatMenu()">删除微信菜单</a>
			</c:if>
			<c:if test="${weixinMenuentityGroup.type==2 }">
				<a class="layui-btn" onclick="synAddconditional()">菜单同步到微信</a>
				<a class="layui-btn layui-btn-danger" onclick="deleteconditional()">删除微信菜单</a>
				<a class="layui-btn layui-btn-normal" onclick="preview()">	预览</a> 
			</c:if>
			<a class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
	</div>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
			<th style="width: 120px;">名称</th>
			<th style="width: 80px;">类型</th>
			<th style="width: 320px">关键字or连接</th>
			<th style="width: 80px;">顺序号</th>
			<th style="width: 80px;">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${empty(weixinMenuentities)||weixinMenuentities==null }" var="status">
		<tr height="32" align="center">
			<td colspan="5">
				无数据
			</td>
		</tr>
	</c:if>
	<c:forEach items="${weixinMenuentities }" var="weixinMenuentity">
			<tr>
				<td style="text-align: left;">${weixinMenuentity.name }</td>
				<td>
					<c:if test="${weixinMenuentity.type=='click' }">
						消息类触发
					</c:if>
					<c:if test="${weixinMenuentity.type=='view' }">
						网页链接类
					</c:if>
				</td>
				<td>
				<c:if test="${fn:length(weixinMenuentity.url)>50 }">
					 ${fn:substring(weixinMenuentity.url,0,50)  }
				</c:if>
				<c:if test="${fn:length(weixinMenuentity.url)<=50 }">
					 ${weixinMenuentity.url  }
				</c:if>
				</td>
				<td>${weixinMenuentity.orders }</td>
				<td style="text-align: center;">
				<a href="javascript:void(-1)"  class="layui-btn layui-btn-xs"  onclick="$.utile.openDialog('${ctx}/weixinMenuentity/edit?dbid=${weixinMenuentity.dbid}&parentMenu=1&groupId=${weixinMenuentityGroup.dbid}','编辑菜单',1024,560)" >编辑</a>
				<a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-xs" onclick="$.utile.deleteById('${ctx}/weixinMenuentity/delete?dbid=${weixinMenuentity.dbid}&groupId=${weixinMenuentityGroup.dbid}','searchPageForm')" title="删除">删除</a></td>
			</tr>
			<ystech:weixinMenuentityTag dbid="${weixinMenuentity.dbid }"/>
		</c:forEach>
	</tbody>
</table>
<br>
<div style="display: none; width: 540px;" id="templateId">
	<table id="noLine" border="0" align="center" cellpadding="0" cellspacing="0" style="width: 320px;margin-top: 5px;">
		<tr style="height: 40px;" height="20">
			<td id="messageError" colspan="3" style="text-align: left;color: red;" width="280">
				关注公众号后，才能结算个性化菜单
			</td>
		</tr>
		<tr style="height: 40px;" height="30" id="imageTr">
			<td colspan="3" style="text-align: left;" width="280">
				<input type="text" id="user_id" name="user_id" value="" placeholder="请输入微信号" style="width: 320px;">
			</td>
		</tr>
		<tr style="height: 20px;" height="20">
			<td id="messageError" colspan="3" style="text-align: left;display: none;color: red;" width="280">
				填写名称错误！
			</td>
		</tr>
	</table>
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
		  deleteByIds('${ctx}/weixinAccount/delete');
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
});
</script>
<script type="text/javascript">
	function synDataDepart(){
		$.post('${ctx}/weixinMenuentity/sameMenu?groupId=${weixinMenuentityGroup.dbid}',{},function (data){
			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
				$.utile.tips(data[0].message);
			}
			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
				$.utile.tips(data[0].message);
				// 保存失败时页面停留在数据编辑页面
			}
			return;
		})
	}
	function synAddconditional(){
		$.post('${ctx}/weixinMenuentity/addconditional?groupId=${weixinMenuentityGroup.dbid}',{},function (data){
			if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
				$.utile.tips(data[0].message);
			}
			if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
				$.utile.tips(data[0].message);
				// 保存失败时页面停留在数据编辑页面
			}
			return;
		})
	}
	function deleteWechatMenu(){
		  layer.confirm('删除微信端菜删除微信端菜单，本地数据不被删除，您确定删除微信端菜单吗？',{icon: 3}, function(index){
			  $.post("${ctx}/weixinMenuentity/deleteWechatMenu?groupId=${weixinMenuentityGroup.dbid}&datetime=" + new Date(),{},callBack);
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
						}, 3000);
					}
				}
		  })
	}
	//删除个性化菜单
	function deleteconditional(){
		layer.open({
			content : '删除微信端菜删除微信端菜单，本地数据不被删除，您确定删除微信端菜单吗？',
			icon : 'question',
			width:"250px",
			height:"80px",
			lock : true,
			ok : function() {// 点击去定按钮后执行方法
				var param = getCheckBox();
				$.post("${ctx}/weixinMenuentity/deleteconditional?groupId=${weixinMenuentityGroup.dbid}&datetime=" + new Date(),{},callBack);
				function callBack(data) {
					if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						window.top.art.dialog({
							content : data[0].message,
							icon : 'warning',
							window : 'top',
							lock : true,
							width:"200px",
							height:"80px",
							ok : function() {// 点击去定按钮后执行方法
								$.utile.close();
								return;
							}
						});

					}

					if (data[0].mark == 1) {// 删除数据失败时提示信息
						$.utile.tips(data[0].message);
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						$.utile.tips(data[0].message);
					}
				}
			},
			cancel : true
		});
	}
	//预览
	function preview(){
		layer.prompt({title: '请输入微信号',btn: ['提交','关闭'],yes: 
			function(index, layero){
				//备注
				var value=$(layero).find("input").val(); 
				if(null==value||value==''){
					$.utile.tips("请输入微信号");
					return ;
				}
				var url='${ctx}/weixinMenuentity/tryconditional';
		    	var params={"user_id":value};
		    	$.post(url,params,function callBack(data) {
					if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
						$.utile.tips(data[0].message);
					  	layer.close(index);
						return true;
					}
					if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
						$.utile.tips(data[0].message);
						// 保存失败时页面停留在数据编辑页面
					}
				});
			}});
	}
</script>
</body>
</html>
