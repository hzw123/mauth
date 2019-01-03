<#include "commons/macro.ftl">
<@commonHead/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>${systemInfo.name}</title>

	<style type="text/css">
		#contentUrl{
			 height: 100%;
		    width: 100%;
		    z-index: 5;
		}
		.layui-layout-admin .layui-body{
			bottom: 1px;
		}
	</style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">
    	<img alt="" src="${systemInfo.infoLogo}" width="100" height="60">
    </div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
		<#list resources as resource>
			<#if resource_index==0>
				<li class="layui-nav-item"><a href="javascript:void(-1)" onclick="subMenu(${resource.dbid},this)" class="active" id="menu${resource.dbid }">${resource.title }</a></li>
			</#if>

		</#list>

		 <li class="layui-nav-item">
			 <a href="javascript:;">更多...</a>
			 <dl class="layui-nav-child">
				 <#list resources as bean>
					 <dd><a href="javascript:void(-1)" onclick="subMenu(${bean.dbid},this)" >${bean.title }</a></dd>
				 </#list>
			 </dl>
		 </li>
		<li class="layui-nav-item">
	   		 <a href="${ctx}/distributorContract/dayDistributorTrack" target="contentUrl">今日回访客户<span class="layui-badge">9</span></a>
	  	</li>
    </ul>
    <ul class="layui-nav layui-layout-right">
		<#if user.userId=='super'>
		    <li class="layui-nav-item">
				<#if user.userId=='super'>
				    <a href="javascript:;">
                        切换到分店
                    </a>
                    <dl class="layui-nav-child">
						<#list users as user>
						    <#if user.userId!='super'>
						        <dd><a href="${ctx}/switch?userId=${user.userId}">${user.realName }</a></dd>
						    </#if>
						</#list>
                    </dl>
				<#else>
					<dd><a href="${ctx}/exit">退出切换账号</a></dd>
				</#if>
            </li>
		</#if>
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img  src="${user.staff.photo}" class="layui-nav-img" >
          ${user.userId}
        </a>
        <dl class="layui-nav-child">
          <dd>
          	<a href="${ctx}/user/editSelf" target="contentUrl">
				个人设置
			</a>
		  </dd>
          <dd><a href="${ctx}/user/modifyPassword" target="contentUrl">修改密码</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${ctx}/logout">退出</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll" >
       ${menu}
    </div>
  </div>
  <div class="layui-body">
    <!-- 内容主体区域 -->
	  	<#if resource??>
	  	    <iframe id="contentUrl" name="contentUrl" src="${ctx}${resource.content }" scrolling="auto"  frameborder="0" class="contentUrl"></iframe>
	  	<#else>
			<iframe id="contentUrl" name="contentUrl" src="${ctx}/main/adminContent" scrolling="auto"  frameborder="0" class="contentUrl"></iframe>
		</#if>
  </div>
</div>
  <!--main-->
  <audio id="chatAudio"><source src="${ctx}/archives/tip.mp3"> </audio>

<script>
	layui.use(['element','layer'], function(){
	  var element = layui.element;
	});
	function subMenu(dbid,obj){
		$(".layui-nav-tree").each(function(i){
			$(this).hide();
		});
		$("#parentId"+dbid).show();
	}
</script>
</body>
</html>
