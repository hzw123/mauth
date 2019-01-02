<#include "../../commons/macro.ftl">
<@commonHead/>

    <link href="${ctx}/css/main.css" rel="stylesheet" />
</head>
<body>
    <div class="location">
        <img src="../../images/homeIcon.png" /> &nbsp;
        <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/startWriting/queryList'">开单记录</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='#'">开单明细</a>
        <span>
        		
            	<c:if test="${startWriting.state==2 }">
				 	 <span style="color:red;" >[${startWriting.roomName },待结账￥${startWriting.money }]</span>
            	</c:if>
            	<c:if test="${startWriting.state==0 }">
				 	 <span style="color:#009688;" >[${startWriting.roomName },已结账]</span>
            	</c:if>
            	<c:if test="${startWriting.state==9999 }">
				 	 <span  style="color:#F581B1;">[${startWriting.roomName },已取消]</span>
            	</c:if>
        </span>
    </div>
    <div class="layui-container" style="width:95%;line-height:46px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
    	<div class="layui-row" style="border-bottom: 1px solid #ddd">
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">开单时间</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员姓名</div>
            </div>
			<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员电话</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员卡级别</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员卡余额</div>
            </div>

            
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
					服务开始时间
                </div>
            </div>
            
        </div>
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
        	<div class="layui-col-md2">
	            <div style="border-right:1px solid #ddd;text-align:center">
	                <fmt:formatDate value="${startWriting.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/>
	        	</div>
	        </div>
	        <div class="layui-col-md2">
	                <div style="border-right:1px solid #ddd;text-align:center">
	                	 <a href="${ctx}/member/detail?dbid=${member.dbid}" class="layui-table-link">	${member.name }</a>(${member.sex })
	                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
	           	 ${member.mobilePhone }
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
	           	 ${member.memberCard.name }
                </div>
            </div>

            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">${member.balance }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	<fmt:formatDate value="${startWriting.start_time }" pattern="hh:mm:ss -"/>
                		
                	<fmt:formatDate value="${startWriting.endTime }" pattern="hh:mm:ss"/>
                </div>
            </div>
        </div>
       
    </div>
	    <div class="layui-container" style="width:95%;line-height:60px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
	    	<c:if test="${startWriting.state!=0 }">
	    		<button class="layui-btn" data-method="addItemProduct">添加产品/项目</button>
	    	</c:if>
	    	<c:if test="${startWriting.state==0 }">
	    		<button class="layui-btn layui-btn-disabled">添加产品/项目</button>
	    	</c:if>
	    	
	    	<c:if test="${startWriting.startStatus!=0 }">
	    		<button class="layui-btn" data-method="changeRoom">换包间</button>
	    	</c:if>
	    	<c:if test="${startWriting.startStatus==0 }">
	    		<button class="layui-btn layui-btn-disabled">换包间</button>
	    	</c:if>
	    	
	    	<c:if test="${startWriting.startStatus==1 }">
	    	  	<button class="layui-btn " data-method="startHour">全部上钟</button>
	    	</c:if>
	    	<c:if test="${startWriting.startStatus!=1 }">
	    	  	<button class="layui-btn layui-btn-disabled" >全部上钟</button>
	    	</c:if>
	    	
	    	<c:if test="${startWriting.startStatus==2 }">
    	  		<button class="layui-btn " data-method="endHour">全部下钟</button>
    	  	</c:if>
	    	<c:if test="${startWriting.startStatus!=2 }">
    	  		<button class="layui-btn layui-btn-disabled">全部下钟</button>
    	  	</c:if>
    	  	<c:if test="${startWriting.state==0 }">
    	  		<button class="layui-btn" data-method="cancelCash">撤销收银</button>
    	  	</c:if>
    	  	
    	  	<c:if test="${startWriting.state!=0 }">
    	  		<button class="layui-btn layui-btn-disabled">撤销收银</button>
    	  	</c:if>
    	  	
    	  	<c:if test="${startWriting.startStatus==0 }">
    	  		<button class="layui-btn " data-method="cancelEndHour">撤销下钟</button>
    	  	</c:if>
    	  	
			<c:if test="${member.dbid!=1 }">
			   	<button class="layui-btn" onclick="window.open('${ctx}/member/detail?dbid=${member.dbid }')">充值</button>
		   	</c:if>
	    	<button class="layui-btn" data-method="cancel">取消订单</button>
	    </div>
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="margin-bottom: 120px;">
        <ul class="layui-tab-title">
            <li class="layui-this">服务明细</li>
            <li lay-id="2">支付明细</li>
            <li lay-id="3">操作日志</li>
            <li lay-id="4" data-templateType="4">诊前咨询表</li>
            <li lay-id="5" data-templateType="5">诊后反馈表</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td style="text-align: center;width: 160px;">项目/产品</td>
                            <td style="text-align: center;width: 80px;">单价</td>
                            <td style="text-align: center;width: 80px;">数量</td>
                            <td style="text-align: center;width: 80px;">金额</td>
                            <td style="text-align: center;width: 80px;">优惠金额</td>
                            <td style="text-align: center;width: 80px;">实收</td>
                            <td style="text-align: center;width: 90px;">优惠方式</td>
                            <td style="text-align: center;width: 80px;">技师</td>
                            <td style="text-align: center;width: 80px;">上钟方式</td>
                            <td style="text-align: center;width: 120px;">操作</td>
                        </tr>
                    </thead>
                    <c:set value="0" var="itemTotal"></c:set>
                    <tbody>
                    	<c:if test="${empty(startWritingItems)&&empty(startWritingItems) }">
                    		<tr height="32" align="center">
                    			<td colspan="8">无项目信息</td>
                    		</tr>
                    	</c:if>
                    	<c:forEach var="startWritingItem" items="${startWritingItems }" varStatus="i">
	                        <tr height="32" align="center">
	                            <td>${startWritingItem.itemName }</td>                           		                            
	                            <td>￥${startWritingItem.money }</td>
	                            <td>${startWritingItem.num }</td>
	                            <td>￥${startWritingItem.money*startWritingItem.num }</td>
	                            <td>￥${startWritingItem.discountMoney }</td>
	                            <td>￥${startWritingItem.actualMoney }</td>
	                            <td>${startWritingItem.discountTypeName }</td>
	                            <td>${startWritingItem.artificerName}</td>
	                            <td>${startWritingItem.serviceTypeName}</td>
	                            <td>
	                            	<c:if test="${startWriting.state==2 }" var="status">
			                         	<a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-mini" onclick="cancelStartWritingItem('${startWritingItem.dbid}','${startWritingItem.itemName }')" >删除</a>
			                         </c:if>
	                            </td>
	                        </tr>
                    	</c:forEach>
                    	<c:if test="${!empty(startWritingItems) }">
                    		<tr height="32" >
                    			<td colspan="3" style="text-align: right;">
                    				项目合计：
                    			</td>
                    			<td colspan="1" style="text-align: center;">
                    				<span style="color: red;font-size: 18px;">
                    					<fmt:formatNumber value="${startWriting.money}" pattern="###.00"></fmt:formatNumber> 
                    				</span>
                    			</td>
                    			<td colspan="1" style="text-align: center;">
                    				<span style="color: red;font-size: 18px;">
                    					<fmt:formatNumber value="${startWriting.discountMoney}" pattern="###.00"></fmt:formatNumber> 
                    				</span></td>
                    			<td colspan="1" style="text-align: center;">
                    				<span style="color: red;font-size: 18px;">
                    					<fmt:formatNumber value="${startWriting.actualMoney}" pattern="###.00"></fmt:formatNumber> 
                    				</span>	</td>
                    		</tr>
                    	</c:if>
                    </tbody>
                </table>
                <br>
                
                <br>		
				<div class="site-content">
					<ul class="site-doc-bgcolor" style="font-size: 16px;">
	      				<li class="layui-bg-gray" style="height:30px">  服务评价:
	      				<c:if test="${startWriting.evaluate==3 }">
				 	 		<span style="color:red;" >好评</span>
            			</c:if>
		            	<c:if test="${startWriting.evaluate==2 }">
						 	 <span style="color:#009688;" >中评</span>
		            	</c:if>
		            	<c:if test="${startWriting.evaluate==1 }">
						 	 <span  style="color:#F581B1;">差评</span>
		            	</c:if>
	      				</li>
	      				<li class="layui-bg-gray" style="height:30px">  评价内容:${startWriting.note}</li>
	      				<li class="layui-bg-gray" style="height:30px">  打赏金额:${startWriting.tipMoney}</li>
	    			</ul>
    			</div>
            </div>

            <div class="layui-tab-item">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2" style="text-align: center;">支付方式</td>
                            <td class="span2" style="text-align: center;">支付金额</td>
                            <td class="span2" style="text-align: center;">支付时间</td>
                            <td class="span2" style="text-align: center;">状态</td>
                        </tr>
                    </thead>
                    <tbody>
	                  <c:forEach var="payway" items="${payways }" varStatus="i">
		                  <tr height="32" align="center">
		                    <td>${payway.payWayName }</td>                           		                            
		                    <td>${payway.money }</td>
		                    <td>${payway.createDate }</td>
		                    <td>${payway.stateName }</td>
		                  </tr>
	                  </c:forEach>
                    	<c:if test="${startWriting.state==0 }">
	                    	<tr>
	                    		<td colspan="7" style="text-align: right;">
	                    			订单金额：<span style="color: red;">${startWriting.money}</span> <br>
	                    			优惠金额：<span style="color: red;">${startWriting.discountMoney }</span><br>
	                    			实收金额：<span style="color: red;">${startWriting.actualMoney }</span>
	                    			<br>
	                    			收银人：${startWriting.cashPerson }<br>
	                    			收银时间：<fmt:formatDate value="${startWriting.cashDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
	                    		</td>
	                    	</tr>
                    	</c:if>
                    </tbody>
                </table>
                   <br>
                <br>
                <br>
                <br>
            </div>
            <div class="layui-tab-item">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2" style="text-align: center;width: 200px;">操作时间</td>
                            <td class="span2" style="text-align: center;width: 200px;">操作类型</td>
                            <td class="span2" style="text-align: center;width: 400px;">备注</td>
                            <td class="span2" style="text-align: center;width: 120px;">操作人</td>
                        </tr>
                    </thead>
                    <tbody>
                     	<c:if test="${empty(logs)&&empty(logs) }">
                    		<tr height="32" align="center">
                    			<td colspan="8">无操作日志记录</td>
                    		</tr>
                    	</c:if>
                    	<c:forEach var="memLog" items="${logs }">
                    		 <tr height="32" align="center">
	                            <td>
	                            	<fmt:formatDate value="${memLog.operateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            </td>
	                            <td>${memLog.typeName }</td>
	                            <td>${memLog.note }</td>
	                            <td>${memLog.operator }</td>
	                        </tr>
                    	</c:forEach>
                    </tbody>
                </table>
                   <br>
                <br>
                <br>
                <br>
            </div>
            <div class="layui-tab-item">
				<div style="text-align:center" id="divEmptyFaceConsult">
					没有给该会员添加诊前咨询表表,请先添加诊断信息 
 					<button class="layui-btn layui-btn-sm layui-btn-normal"  id="btnEditFaceConsult"  style="float:center">添加诊前咨询表</button>
				</div>
				<div name="content" id="divEditFaceConsultContent" style="display:none">
					<form class="layui-form" id="frmId" name="frmId" style="padding: 12px;">
						<input hidden="hidden" name="memConsult.userId" id="faceUserId"></input>
						 <div class="layui-form-item">
						   	<label class="layui-form-label"></label>
						  	<textarea  class="input-xlarge" name="memConsult.content" id="faceConsultContent" ></textarea>
						  </div>
						  <div class="layui-form-item">
						    <div class="layui-input-block">
						      <a class="layui-btn" id="btnSaveFaceConsult">修改</a>
						    </div>
						  </div>
					</form>
				</div>
				
				   <br>
                <br>
                <br>
                <br>
            </div>

            <div class="layui-tab-item">
                <div style="text-align:center" id="divEmptyBodyConsult">
					没有给该会员添加诊后咨询表,请先添加诊后咨询表 
 					<button class="layui-btn layui-btn-sm layui-btn-normal"  id="btnEditBodyConsult"  style="float:center">添加诊后咨询表</button>
				</div>
				<div name="content" id="divEditBodyConsultContent" style="display:none">
					<form class="layui-form" id="frmId" name="frmId" style="padding: 12px;">
						 <div class="layui-form-item">
						   	<label class="layui-form-label"></label>
						  	<textarea  class="input-xlarge" name="memConsult.content" id="bodyConsultContent" ></textarea>
						  </div>
						  <div class="layui-form-item">
						    <div class="layui-input-block">
						      <a class="layui-btn" id="btnSaveBodyConsult">修改</a>
						    </div>
						  </div>
					</form>
				</div>
				
				   <br>
                <br>
                <br>
                <br>
            </div>
            
        </div>
        <div hidden="hidden">
	        <form id="formConsult">
		        <input  id="txtUserId" value=${member.dbid } name="memConsult.userId"></input>
	        	<input  id="txtTemplateType" name="memConsult.type"></input>
	        	<input  id="txtDbid" name="memConsult.dbid"></input>
	        	<input  id="txtOrderId" name="memConsult.orderId" value="${startWriting.dbid}"></input>
	        	<textarea  id="txtConsultContent" name="memConsult.content" ></textarea>
	        </form>
	    </div>
	    
    </div>

<div class="layui-layout layui-layout-admin">
	 <div class="layui-footer footer" style=" left: 0px;text-align: right;">
	 	<c:if test="${startWriting.state==2 }">
	 		<button class="layui-btn" data-method="cash">去收银</button>
	 	</c:if>
	 	<c:if test="${startWriting.state==0 }">
	 		<button class="layui-btn layui-btn-disabled" data-method="setTop">已收银</button>
	 	</c:if>
	 	<c:if test="${startWriting.state==9999 }">
	 		<button class="layui-btn layui-btn-disabled" data-method="setTop">订单已取消</button>
	 	</c:if>
	    <button class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</button>
	</div>
</div>
<script src="${ctx}/widgets/jquery.min.js"></script>


<script type="text/javascript" src="${ctx}/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var templateContent="";
	var consultId=0;
	var form2;
	var player;
	var userId;
	layui.use(['form', 'layedit', 'laydate','element','table'], function(){
		  var state="${param.state}";
		  var form = layui.form
		  ,layer = layui.layer,laydate = layui.laydate,element = layui.element,table=layui.table;
		  player = parent.layer;;
		  if(state==2){
			  element.tabChange('docDemoTabBrief', '2'); 
		  }
		  if(state==3){
			  element.tabChange('docDemoTabBrief', '3'); 
		  }
		  if(state==4){
			  element.tabChange('docDemoTabBrief', '4'); 
		  }
		  var $ = layui.$, active = {
			  changeRoom:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        player.open({
			          type: 2 //此处以iframe举例
			          ,title: '选择包间'
			          ,area: ['760px', '540px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/startWriting/changeRoom?startWritingId=${startWriting.dbid}'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			            //layer.setTop(layero); //重点2
			          }
			        ,btn: ['确定', '关闭']
			        ,yes: function(index, layero){
			        	var body = player.getChildFrame('body', index);
						var checkeds= body.find("input[type='checkbox'][name='roomId']");
						var array = new Array();
					  	$.each(checkeds, function(i, checkbox) {
					  		if (checkbox.checked) {
					  			array.push(checkbox.value);
					  		}
					  	});
						if(array.length==0){
							player.msg("请选择包间");
						}else{
							var url='${ctx}/startWriting/changeRoomSave';
					    	var params={"startWritingId":"${startWriting.dbid}","roomId":array[0].toString()};
					    	$.post(url,params,function callBack(data) {
								if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
									player.msg(data[0].message);
									setTimeout(
											function() {player.close(index);}, 1000);
								}
								if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
									player.msg(data[0].message);
									// 保存失败时页面停留在数据编辑页面
									setTimeout(
											function() {
												player.close(index);
											}, 1000);
								}
								return;
							});
						}
			        }
			        ,btn2: function(index, layero){
			        	player.close(index);
			          //return false 开启该代码可禁止点击该按钮关闭
			        }
			        });
			  },
			  changeMember:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        player.open({
			          type: 2 //此处以iframe举例
			          ,title: '更改会员'
			          ,area: ['1280px', '640px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/member/changeMember'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			          }
			        ,btn: ['确定', '关闭']
			        ,yes: function(index, layero){
			        	var body = player.getChildFrame('body', index);
			        	var memberid= body.find("input[name=id]:checked").val();
			        	if(memberid==''||memberid==undefined){
			        		alert("请选择会员后在操作");
			        		return ;
			        	}
			        	$.post('${ctx}/startWriting/saveChangeMember?startWritingId=${startWriting.dbid}&memberId='+memberid,{},function callBack(data) {
							if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
								player.msg(data[0].message);
								setTimeout(
										function() {
											player.close(index);
											window.location.href = data[0].url;
										}, 1000);
							}
							if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
								player.msg(data[0].message);
								// 保存失败时页面停留在数据编辑页面
								setTimeout(
										function() {
											player.close(index);
										}, 1000);
							}
							return;
						});
			        }
			        ,btn2: function(index, layero){
			        	player.close(index);
			          //return false 开启该代码可禁止点击该按钮关闭
			        }
			        });
			  },
			  addItemProduct:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        player.open({
			          type: 2 //此处以iframe举例
			          ,title: '添加产品项目'
			          ,area: ['1280px', '640px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/startWriting/addItemProduct?startWritingId=${startWriting.dbid}'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			            //layer.setTop(layero); //重点2
			          }
			        ,btn: ['确定', '关闭']
			        ,yes: function(index, layero){
			        	var body = player.getChildFrame('body', index);
						var entItemNames= body.find("input[name=entItemName]");
						var entItemIds= body.find("input[name=entItemId]");
						var artificerIds= body.find("input[name=artificerId]");
						var partificerIds= body.find("input[name=partificerId]");
						var artificerNames= body.find("input[name=artificerName]");
						var serviceTypes=body.find("select[name='serviceType']");
						var itemNums= body.find("input[name=num]");
						var entStatus=false,productStatus=false;
						var arrayItemIds=new Array();
						var arrayArtificerIds=new Array()
						var arrayItemNums=new Array();
						var arrayServiceTypes=new Array();
						for(var i=0;i<entItemIds.length;i++){
							var entItemId=$(entItemIds[i]).val();
							if(entItemId!=''&&entItemId!=undefined){
								arrayItemIds.push(entItemId);
								entStatus=true;
								var entItemName=$(entItemNames[i]).val();
								if(entItemName==''||entItemName==undefined){
									player.msg("项目不能为空，请选择第【"+(i+1)+"】行项目");
									return ;
								}
								var artificerId=$(artificerIds[i]).val();
								if(artificerId==''||artificerId==undefined){
									artificerId=0;
								}
								arrayArtificerIds.push(artificerId);
								var itemNum=$(itemNums[i]).val();
								if(itemNum==''||itemNum==undefined){
									itemNum=1;
								}
								var num=parseInt(itemNum);
								if(num<=0){
									player.msg("项目数量不能小于0，请选输入【"+(i+1)+"】项目数量");
									return ;
								}
								arrayItemNums.push(num);
								
								var serviceType=$(serviceTypes[i]).val();
								if(serviceType==''||serviceType==undefined){
									serviceType=1;
								}
								arrayServiceTypes.push(serviceType);
							}
						}
						
						if(entStatus==false){
							player.msg("请添加项目或者产品信息");
							return ;
						}
						var url='${ctx}/startWriting/addItemProductSave';
				    	var params={"startWritingId":"${startWriting.dbid}",
				    				"itemIds":arrayItemIds.toString(),
				    				"artificerIds":arrayArtificerIds.toString(),
				    				"itemNums":arrayItemNums.toString(),
				    				"serviceTypes":arrayServiceTypes.toString()
				    				};
				    	$.post(url,params,function callBack(data) {
							if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
								player.msg(data[0].message);
								setTimeout(
										function() {
											player.close(index);
											window.location.href = data[0].url;
										}, 1000);
							}
							if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
								player.msg(data[0].message);
								setTimeout(function() {player.close(index);}, 1000);
							}
							return;
						});
			        }
			        ,btn2: function(index, layero){
			        	player.close(index);
			          //return false 开启该代码可禁止点击该按钮关闭
			        }
			        });
			  },
			  cash:function(){
				var that = this;
				player.open({
				  type: 2 
				  ,title: '${startWriting.roomName} 收银'
				  ,area: ['1280px', '640px']
				  ,shade: 0.8
				  ,maxmin: true
				  ,content: '${ctx}/startWriting/cash?startWritingId=${startWriting.dbid}'
				  ,zIndex: layer.zIndex //重点1
				  ,success: function(layero){
				  }
				});   
			  },
			  cancel:function(){
			        layer.confirm('您确定取消【${startWriting.orderNo}】订单吗', function(index){
			        	 $.post("${ctx}/startWriting/cancel?dbid=${startWriting.dbid}&type=1&datetime=" + new Date(),
				     		function callBack(data) {
				     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				     				layer.alert(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 1) {// 删除数据失败时提示信息
				     				layer.msg(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 0) {// 删除数据成功提示信息
				     				layer.msg(data[0].message,{icon: 1});
				     				setTimeout(
										function() {
							     			window.location.href='${ctx}/startWriting/startWriting';
										}, 1000);
				     			}
				     		})
			            layer.close(index);
			          });
			  },
			  startHour:function(){
				   layer.confirm('您确定【${startWriting.orderNo}】上钟？', function(index){
			        	 $.post("${ctx}/startWriting/saveStartHour?startWritingId=${startWriting.dbid}&datetime=" + new Date(),
				     		function callBack(data) {
				     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				     				layer.alert(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 1) {// 删除数据失败时提示信息
				     				layer.msg(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 0) {// 删除数据成功提示信息
				     				layer.msg(data[0].message,{icon: 1});
				     				setTimeout(
										function() {
							     			window.location.href=data[0].url;
										}, 1000);
				     			}
				     		})
			            layer.close(index);
			          });
			  },
			  cancelEndHour:function(){
				   layer.confirm('您确定【${startWriting.orderNo}】撤销下钟吗？', function(index){
			        	 $.post("${ctx}/startWriting/cancelEndHour?startWritingId=${startWriting.dbid}&type=2&datetime=" + new Date(),
				     		function callBack(data) {
				     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				     				layer.alert(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 1) {// 删除数据失败时提示信息
				     				layer.msg(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 0) {// 删除数据成功提示信息
				     				layer.msg(data[0].message,{icon: 1});
				     				setTimeout(
										function() {
							     			window.location.href=data[0].url;
										}, 1000);
				     			}
				     		})
			            layer.close(index);
			          });
			  },
			  cancelCash:function(){
				   layer.confirm('您确定【${startWriting.orderNo}】撤销收银吗？', function(index){
			        	 $.post("${ctx}/startWriting/cancelCash?startWritingId=${startWriting.dbid}&date=" + new Date(),
				     		function callBack(data) {
				     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				     				layer.alert(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 1) {// 删除数据失败时提示信息
				     				layer.msg(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 0) {// 删除数据成功提示信息
				     				layer.msg(data[0].message,{icon: 1});
				     				setTimeout(
										function() {
							     			window.location.href=data[0].url;
										}, 1000);
				     			}
				     		})
			            layer.close(index);
			          });
			  },
			  endHour:function(){
				   layer.confirm('您确定【${startWriting.orderNo}】下钟？', function(index){
			        	 $.post("${ctx}/startWriting/saveEndHour?startWritingId=${startWriting.dbid}&datetime=" + new Date(),
				     		function callBack(data) {
				     			if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
				     				layer.alert(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 1) {// 删除数据失败时提示信息
				     				layer.msg(data[0].message, {icon: 5});
				     			}
				     			if (data[0].mark == 0) {// 删除数据成功提示信息
				     				layer.msg(data[0].message,{icon: 1});
				     				setTimeout(
										function() {
							     			window.location.href=data[0].url;
										}, 1000);
				     			}
				     		})
			            layer.close(index);
			          });
			  }
		  }
		  $('.layui-container .layui-btn').on('click', function(){
			  var othis = $(this), method = othis.data('method');
			 var cla=$(this).attr("class");
			 if(cla.indexOf("layui-btn-disabled")<0){
			  active[method] ? active[method].call(this, othis) : '';
			 }
		  });
		  $('.layui-footer .layui-btn').on('click', function(){
			  var othis = $(this), method = othis.data('method');
			  active[method] ? active[method].call(this, othis) : '';
		  });
		 element.on('tab(docDemoTabBrief)', function(data){
			  var templateType=this.getAttribute("data-templateType");
			  $("#txtTemplateType").val(templateType);
			  if(templateType)
			  {
				  $.ajax({	
						url : '${ctx}/memConsult/getByUserIdAndType?type='+templateType+"&userId=${member.dbid }&orderId=${startWriting.dbid }",
						async : false, 
						timeout : 20000, 
						dataType : "json",
						type:"get",
						success : function(result){
							console.info(result);
							templateContent=result.content;
							$("#txtDbid").val(result.dbid);
							if(templateType==4){
								if(result.type==0){
									$("#divEmptyFaceConsult").css('display','block'); 
									$("#divEditFaceConsultContent").css('display','none');
								}
								else{
									$("#txtDbid").val(result.dbid);
									$("#divEmptyFaceConsult").css('display','none'); 
									$("#divEditFaceConsultContent").css('display','block');
									editor= CKEDITOR.replace("faceConsultContent");
									editor.setData(result.content);
								}
							} else if(templateType==5){
								if(result.type==0){
									$("#divEmptyBodyConsult").css('display','block'); 
									$("#divEditBodyConsultContent").css('display','none');
								}
								else{
									$("#txtDbid").val(result.dbid);
									$("#divEmptyBodyConsult").css('display','none'); 
									$("#divEditBodyConsultContent").css('display','block');
									editor= CKEDITOR.replace("bodyConsultContent");
									editor.setData(result.content);
								}
							}		
						},
						error : function(jqXHR, textStatus, errorThrown){
								layer.msg("系统请求超时");
								$("#submitBut").bind("onclick");
						}
					});
			  }
		  });	  
	});
	function  cancelStartWritingItem(dbid,itemName){
		  player.confirm('您确定删除【'+itemName+'】项目吗？',{icon: 5}, function(index){
			  $.post('/startWriting/deleteStartWritingItem?startWritingId=${startWriting.dbid}&dbid='+dbid,{},function(data){
				  player.closeAll();
				  if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						layer.alert(data[0].message, {icon: 5});
					}
					if (data[0].mark == 1) {// 删除数据失败时提示信息
						layer.msg(data[0].message, {icon: 5});
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						player.msg(data[0].message,{icon: 1});
						setTimeout(
							function() {
								window.location.href = data[0].url
							}, 1000);
					}
			  })
	      });
	 }
	var editor=null;
    $(document).on('click', '#btnEditFaceConsult', function () {
		$("#divEmptyFaceConsult").css('display','none'); 
		$("#divEditFaceConsultContent").css('display','block');
		editor= CKEDITOR.replace("faceConsultContent");
		editor.setData(templateContent);
    });
    
    $(document).on('click', '#btnEditBodyConsult', function () {
		$("#divEmptyBodyConsult").css('display','none'); 
		$("#divEditBodyConsultContent").css('display','block');
		editor= CKEDITOR.replace("bodyConsultContent");
		editor.setData(templateContent);
    });

    $(document).on('click', '#btnSaveFaceConsult,#btnSaveBodyConsult', function () {
    	var content= editor.getData();
    	 $('#txtConsultContent').val(content);
    	$.post("/memConsult/save",
    			$("#formConsult").serialize(),
                function (result) {
                    if(result){
                    	layer.msg("修改成功");
                    	return ;
                    } 
                },
                "Json"
            );

    });
</script>
</body>
</html>
