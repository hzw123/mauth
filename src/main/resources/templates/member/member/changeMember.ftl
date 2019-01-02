<#include "../../commons/macro.ftl">
<@commonHead/>



 <style type="text/css">
.layui-table td, .layui-table th {
	padding: 8px 10px;
}
#radiotd{
	padding: 0px;
}
 </style>
<title>选择会员</title>
</head>
<body>
<div class="layui-row" style="margin: 12px 0px;">
     	<input type="hidden" value="" id="memberData" name="memberData">
     <form name="searchPageForm" id="searchPageForm" action="${ctx}/member/changeMember" method="post">
     	<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<div class="layui-form-item" style="margin:0 auto;text-align: left;width: 1200px;">
	  		<label class="layui-form-label" style="width: 30px;text-align: left;">名称</label>
		    <div class="layui-input-inline" style="width: 1000px;">
			   <input type="text" d="name" name="name" value="${param.name}" placeholder="请输入会员名字/联系电话" autocomplete="off" class="layui-input"/>
		    </div>
		     <a id="btnSearch" class="layui-btn" lay-submit="" lay-filter="Search" onclick="document.getElementById('searchPageForm').submit()"><i class="layui-icon layui-btn-icon"></i>查询</a>
	  	</div>
	  </form>
</div>
<table lay-even class="layui-table" id="layerDemo" lay-filter="demo" style="width: 1200px;margin: 0 auto;">
    <colgroup>
      <col>
    </colgroup>
    <thead>
		<tr>
		<td class="sn" style="text-align: center;"></td>
		<td class="span2" style="text-align: center;">会员编码</td>
		<td class="span2" style="text-align: center;">会员姓名</td>
		<td class="span2" style="text-align: center;">手机号码</td>
		<td class="span2" style="text-align: center;">生日</td>
		<td class="span2" style="text-align: center;">会员卡号</td>
		<td class="span2" style="text-align: center;">会员等级</td>
		<td class="span2" style="text-align: center;">余额</td>
		<td class="span2" style="text-align: center;">最近消费时间</td>
		<td class="span2" style="text-align: center;">创建时间</td>
	</tr>
</thead>
<c:if test="${empty(page.result)||page.result==null }" var="status">
	<tr height="32" align="center">
		<td colspan="10">
			无会员信息
		</td>
	</tr>
</c:if>
<c:forEach var="member" items="${page.result }">
	<tr height="32" align="center" style="padding: 0px;">
		<td onclick="" id="radiotd">
			<label style="padding:8px 12px;" onclick="selectMember()">
				<input type="radio" name="id" id="id1" value="${member.dbid }"/>
			</label>
		</td>
		<td align="left" style="text-align: left;">${member.no }&nbsp;&nbsp; 
		</td>
		<td align="left" style="text-align: left;">${member.name }</td>
		<td align="left">${member.mobilePhone }</td>
		<td align="left" style="text-align: left;">${member.birthday }</td>
		<td>
			${member.memberCardNo }
		</td>
		<td>
			${member.memberCard.name }
		</td>
		<td>
			${member.balance }
		</td>
		<td>
			${member.lastBuyDate }
		</td>
		<td>
			${member.createTime }
		</td>
	</tr>
</c:forEach>
</table>
<div id="page" style="width: 1200px;margin: 0 auto;"></div>


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
		  deleteByIds('${ctx}/member/delete');
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
function selectMember(){
	var memberId = $("input[type='radio'][name='id']:checked").val();
	$.post("/member/ajaxMember?memberId="+memberId+"&date="+new Date(),{},function(data){
      	$("#memberData").val("");
      	$("#memberData").val(data);
	 })
}
</script>
</body>
</html>