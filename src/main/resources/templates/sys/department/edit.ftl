<#include "../../commons/macro.ftl">
<@commonHead/>

<style type="text/css">
	tr{
		height: 50px;
	}
	.formTableTdLeft{
		text-align: right;
		padding-right: 12px;
		width: 80px;
	}
</style>

<title>部门信息编辑页面</title>
</head>
<body class="bodycolor">
	<div class="frmContent">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent" class="layui-form" >
		<c:if test="${not empty(department) }">
			<input type="hidden" name="parentId" value="${department.parent.dbid }" id="parentId"></input>
		</c:if>
		<c:if test="${empty(department) }">
			<input type="hidden" name="parentId" value="${param.parentId }" id="parentId"></input>
		</c:if>
		<input type="hidden" name="department.dbid" id="dbid" value="${department.dbid }">
		<input type="hidden" name="enterpriseId" id="enterpriseId" value="${enterprise.dbid }">

		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style="color: red;">名称:&nbsp;</td>
				<td ><input type="text" name="department.name" id="name" value="${department.name }" class="layui-input" title="部门名称"	lay-verify='required'></td>
				<c:if test="${fn:contains(user.userId,'super')}" var="status">
						<td class="formTableTdLeft" style="color: red;">类型:&nbsp;</td>
						<td >
							<select id="type" name="department.type"  class="layui-input" lay-verify='required'>
								<option value="-1">请选择....</option>
								<option  value="1" ${department.type==1?'selected="selected"':'' } >普通部门</option>
								<option  value="2" ${department.type==2?'selected="selected"':'' }>分店部门</option>
							</select>
						</td>
				</c:if>
				<c:if test="${status==false}">
					<input type="hidden" name="department.type" id="type" value="1">
				</c:if>
			</tr>
			<tr>
				<%-- <td class="formTableTdLeft" style="color: red;">部门类型:&nbsp;</td>
				<td >
					<select id="bussiType" name="department.bussiType"  class="layui-input" lay-verify='required'>
						<option value="-1">请选择....</option>
						<option  value="1" ${department.bussiType==1?'selected="selected"':'' }>销售业务部</option>
						<option  value="2" ${department.bussiType==2?'selected="selected"':'' }>信审业务部</option>
						<option  value="3" ${department.bussiType==3?'selected="selected"':'' }>后勤部门</option>
						<option  value="4" ${department.bussiType==4?'selected="selected"':'' }>其他部门</option>
					</select>
				</td> --%>
				<td class="formTableTdLeft">电话:&nbsp;</td>
				<td ><input type="text" name="department.phone" id="phone"
					value="${department.phone }" class="layui-input" title="电话"	checkType="phone" canEmpty="Y" tip="电话格式不对"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">传真:&nbsp;</td>
				<td ><input type="text" name="department.fax" id="fax"
					value="${department.fax }" class="layui-input" title="传真"	checkType="phone" canEmpty="Y" tip="传真格式不对"></td>
				<td class="formTableTdLeft">序号:&nbsp;</td>
				<td ><input type="text" name="department.suqNo" id="suqNo"
					value="${department.suqNo }" class="layui-input" checkType="integer" canEmpty="Y" tip="必须输入数字" title="序号"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">部门主管:&nbsp;</td>
				<td colspan="3">
					<input type="text" name="managerName" id="managerName" value="${department.manager.realName }" class="layui-input" style="width: 240px;float: left;" title="部门主管" readonly="readonly">
					<input type="hidden" name="managerId" id="managerId"	value="${department.manager.dbid }" class="layui-input" >
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">部门职能:&nbsp;</td>
				<td colspan="3"><textarea  name="department.discription" id="discription"
					 class="layui-input" title="用户名">${department.discription }</textarea></td>
			</tr>
		</table>
		<br>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <a class="layui-btn" id="submitBut" lay-submit="" lay-filter="submitButton">保&nbsp;&nbsp;存</a>
		      <a id="closeBut"  class="layui-btn layui-btn-primary" >关闭</a>
		    </div>
		  </div>
	</form>
</div>

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
	  var $ = layui.$;
	  //监听提交
	  form.on('submit(submitButton)', function(data){
		  submitForm('frmId','${ctx}/department/save')
		  return false
	  });
	  $('#closeBut').on('click', function(){
		  parent.layer.closeAll(); //再执行关闭
	  });
	});
</script>
<script type="text/javascript">
function submitForm(frmId, url) {
	var name=$("#name").val();
	var dbid=$("#dbid").val();
	var iframe = window.parent.frames["contentUrl"];
	var zTree=iframe.zTree;
 	var departmentId=$("#parentId").val();
	var parent=zTree.getNodesByParam("id", departmentId, null)[0];
	try {
		if (undefined != frmId && frmId != "") {
				var params = getParam(frmId);
				var url2="";
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
							$.utile.tips(obj[0].message);
							$(".butSave").attr("onclick",url2);
							return ;
						}else if(obj[0].mark==0){
							$.utile.tips(data[0].message);
							layer.closeAll()
							if(null==dbid||dbid==undefined||''==dbid){
								var zNodes =[
								 			{ id:data[0].depId,pId:departmentId, name:name,iconOpen:"${ctx}/widgets/ztree/css/zTreeStyle/img/diy/2.png"}];
								//zTree.addNodes(parent,-1,zNodes,false);
								iframe.addHoverDom('treeDemo',parent,zNodes);
							}else{
								var node=zTree.getNodesByParam("id", dbid, null)[0];
									node.name = name;
									iframe.updateDom('treeDemo',node);
							}
							return ;
						}
					},
					complete : function(jqXHR, textStatus){
						$(".butSave").attr("onclick",url2);
						var jqXHR=jqXHR;
						var textStatus=textStatus;
					}, 
					beforeSend : function(jqXHR, configs){
						url2=$(".butSave").attr("onclick");
						$(".butSave").attr("onclick","");
						var jqXHR=jqXHR;
						var configs=configs;
					}, 
					error : function(jqXHR, textStatus, errorThrown){
							$.utile.tips("系统请求超时");
							$(".butSave").attr("onclick",url2);
					}
				});
		} else {
			return;
		}
	} catch (e) {
		$.utile.tips(e);
		return;
	}
}
</script>
</body>
</html>