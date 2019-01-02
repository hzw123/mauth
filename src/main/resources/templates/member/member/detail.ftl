<#include "../../commons/macro.ftl">
<@commonHead/>

    <link href="${ctx}/css/main.css" rel="stylesheet" />
</head>
<body>
    <div class="location">
        <img src="../../images/homeIcon.png" /> &nbsp;
        <a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='${ctx}/member/queryList'">会员列表</a>-
        <a href="javascript:void(-1);" onclick="window.location.href='#'">会员基本信息</a>
    </div>
    <div class="layui-container" style="width:95%;line-height:46px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	编号:
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	${member.no }
                	<c:if test="${member.state==9999 }">
                		<span style="color: red;">已注销</span>
                	</c:if>
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	${member.name }
                	${member.sex }
                </div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">${member.mobilePhone }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	${member.birthday}
                </div>
            </div>
        	<div class="layui-col-md2">
                <div style="text-align:center">充值总额:${member.totalCardMoney }</div>
            </div>
        </div>
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
        	<div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">余额:${member.balance }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费次数：${member.totalBuy }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">消费总额：${member.consumeMoney }</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">总积分：${member.remainderPoint }</div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center;border-right:1px solid #ddd;">会员卡号：</div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">
                	${member.memberCardNo }
                </div>
            </div>
        </div>
        <div class="layui-row" style="border-bottom: 1px solid #ddd">
       		 <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">最近消费时间：</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	<fmt:formatDate value="${member.lastBuyDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                	&nbsp;
                </div>
            </div>
       		 <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员创建时间：</div>
            </div>
            <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">
                	<fmt:formatDate value="${member.createTime }" pattern="yyyy-MM-dd"/>
                </div>
            </div>
       		 <div class="layui-col-md2">
                <div style="border-right:1px solid #ddd;text-align:center">会员卡：</div>
            </div>
            <div class="layui-col-md2">
                <div style="text-align:center">
                	${member.memberCard.name }
                </div>
            </div>
         </div>
        <c:if test="${!empty(member.startMemberCard) }">
	        <div class="layui-row" style="border-bottom: 1px solid #ddd">
	       		<div class="layui-col-md2">
	                <div style="border-right:1px solid #ddd;text-align:center">创始会编号：</div>
	            </div>
	            <div class="layui-col-md2">
	                <div style="border-right:1px solid #ddd;text-align:center">
	                	${member.startMemberCardNo }
	                </div>
	            </div>
	       		 <div class="layui-col-md2">
	                <div style="border-right:1px solid #ddd;text-align:center">储值总额：</div>
	            </div>
	            <div class="layui-col-md2">
	                <div style="border-right:1px solid #ddd;text-align:center">
	                	${member.totalStartMoney }
	                </div>
	            </div>
	         </div>
        </c:if>
        <div class="layui-row">
           <div class="layui-col-md12">
                <div class="grid-demo grid-demo-bg1" style="text-align:left">${member.note }</div>
            </div>
       	</div>
    </div>
     <div class="layui-container" style="width:95%;line-height:60px;border:1px solid #ddd;margin-left:20px;margin-right:20px;font-size:16px;border-radius:5px;margin-top:20px;margin-bottom:20px">
              <c:if test="${member.state==0 }">
		    	   <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="preExamResult"  >会员卡储值</button>
	               <c:if test="${enterprise.startMemberCardStatus==2 }">
	                <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="startMemberCard" >创始会员卡储值</button>
	               </c:if>
	              <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="stormMoneyOnceEntItemCard" >购买次卡</button>
	              <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="changeMemberCard">修改会员级别</button>
		          <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="changeBalance1">加余额</button>
		          <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="changeBalance2">减余额</button>
		          <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="changePoint1">消费积分</button>
		          <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="changePoint2">加积分</button>
	              <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="memberCancelFrozon" >冻结</button>
              </c:if>
              <c:if test="${member.state>=1 }">
              		<button class="layui-btn layui-btn-disabled" >会员卡储值</button>
		            <button class="layui-btn layui-btn-disabled"  >购买次卡</button>
		            
              </c:if>
               <c:if test="${member.state==9998 }">
	              <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="memberThawFrozon" >解冻</button>
               </c:if>
               <c:if test="${member.state!=9999 }">
	              <button class="layui-btn layui-btn-sm layui-btn-normal"  data-method="memberCancel" >注销</button>
               </c:if>
              <a class="layui-btn layui-btn-primary" onclick="window.history.go(-1)">返回</a>
              
	    </div>
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="margin-bottom: 120px;">
        <ul class="layui-tab-title">
            <li class="layui-this">储值卡</li>
            <li lay-id="2">次卡</li>
            <li lay-id="3">消费记录</li>
            <li lay-id="4" data-templateType="1">面部诊断表</li>
            <li lay-id="5" data-templateType="2">身体诊断表</li>
            <li lay-id="6" data-templateType="3">皮肤诊断表</li>
            <li lay-id="7">优惠券</li>
            <li lay-id="8">积分记录</li>
            <li lay-id="9">操作日志</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td style="text-align: center;width: 120px;">订单号</td>
                            <td style="text-align: center;width: 140px;">充值时间</td>
                            <td style="text-align: center;width: 80px;">充值金额</td>
                            <td style="text-align: center;width: 80px;">赠送金额</td>
                            <td style="text-align: center;width: 160px;">充值备注</td>
                            <td style="text-align: center;width: 80px;">状态</td>
                            <td style="text-align: center;width: 120px;">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:if test="${empty(stormMoneyMemberCards)&&empty(cancelStormMoneyMemberCards) }">
                    		<tr height="32" align="center">
                    			<td colspan="6">无储值记录</td>
                    		</tr>
                    	</c:if>
                    	<c:forEach var="stormMoneyMemberCard" items="${stormMoneyMemberCards }" varStatus="i">
	                        <tr height="32" align="center">
	                            <td><a href="#" class="layui-table-link" style="color: rgb(1, 170, 237);" onclick="ope('${stormMoneyMemberCard.dbid}')">${stormMoneyMemberCard.orderNo }</a></td>
	                            <td>
	                            	<fmt:formatDate value="${stormMoneyMemberCard.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            </td>
	                            <td>￥${stormMoneyMemberCard.money }</td>
	                            <td>￥${stormMoneyMemberCard.giveMoney }</td>
	                            <td>￥${stormMoneyMemberCard.typeName }</td>
	                            <td>${stormMoneyMemberCard.stateName }</td>
	                            <td>
			                        <a href="#" class="layui-btn layui-btn-danger layui-btn-mini" onclick="cancelStormCard('${stormMoneyMemberCard.dbid}')" >撤销</a>
	                            </td>
	                        </tr>
                    	</c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="layui-tab-item">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2" style="text-align: center;">订单号</td>
                            <td class="span2" style="text-align: center;">购买日期</td>
                            <td class="span2" style="text-align: center;">套餐名称</td>
                            <td class="span2" style="text-align: center;">项目</td>
                            <td class="span2" style="text-align: center;">价格</td>
                            <td class="span2" style="text-align: center;">次数</td>
                            <td class="span2" style="text-align: center;">剩余次数</td>
                            <td class="span2" style="text-align: center;">状态</td>
                            <td style="text-align: center;width: 120px;">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                     	<c:if test="${empty(stormMoneyOnceEntItemCards)&&empty(stormMoneyOnceEntItemCards) }">
                    		<tr height="32" align="center">
                    			<td colspan="8">无储值记录</td>
                    		</tr>
                    	</c:if>
                    	<c:forEach var="stormMoneyOnceEntItemCard" items="${stormMoneyOnceEntItemCards }">
                    		 <tr height="32" align="center">
	                            <td><a href="#" class="layui-table-link" style="color: rgb(1, 170, 237);" onclick="viewStormMoneyOnceEntItemCard('${stormMoneyOnceEntItemCard.dbid}')" >${stormMoneyOnceEntItemCard.orderNo }</a></td>
	                            <td>
	                            	<fmt:formatDate value="${stormMoneyOnceEntItemCard.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            </td>
	                            <td>${stormMoneyOnceEntItemCard.onceEntItemCardName }</td>
	                            <td>${stormMoneyOnceEntItemCard.itemName }</td>
	                            <td>￥${stormMoneyOnceEntItemCard.money }</td>
	                            <td>${stormMoneyOnceEntItemCard.num }</td>
	                            <td>${stormMoneyOnceEntItemCard.remainder }</td>
	                            <td>${stormMoneyOnceEntItemCard.stateName }</td>
	                            <td>
			                        <a href="javascript:void(-1)" class="layui-btn layui-btn-danger layui-btn-mini" onclick="cancelCountCard('${stormMoneyOnceEntItemCard.dbid }')" >撤销</a>
	                            </td>
	                        </tr>
                    	</c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="layui-tab-item">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2" style="text-align: center;">订单号</td>
                            <td class="span2" style="text-align: center;">消费日期</td>
                            <td class="span2" style="text-align: center;">金额</td>
                            <td class="span2" style="text-align: center;">优惠金额</td>
                            <td class="span2" style="text-align: center;">实付金额</td>
                            <td class="span2" style="text-align: center;">积分</td>
                            <td class="span2" style="text-align: center;">状态</td>
                        </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty(startWritings) }">
                    	 <tr height="32" align="center">
                            <td colspan="8">无消费记录</td>
                          </tr>
                    </c:if>
                    <c:forEach var="startWriting" items="${startWritings }">
                        <tr height="32" align="center">
                            <td> <a href="${ctx}/startWriting/view?dbid=${startWriting.dbid}" class="layui-table-link">${startWriting.orderNo }</a></td>
                            <td> <fmt:formatDate value="${startWriting.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td> ${startWriting.money}</td>
                            <td> ${startWriting.discountMoney}</td>
                            <td> ${startWriting.actualMoney}</td>
                            <td> ${startWriting.pointNum}</td>
                            <td> ${startWriting.stateName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="layui-tab-item">
				<div style="text-align:center" id="divEmptyFaceConsult">
					没有给该会员添加面部咨询诊断表,请先添加诊断信息 
 					<button class="layui-btn layui-btn-sm layui-btn-normal"  id="btnEditFaceConsult"  style="float:center">添加面部咨询</button>
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
            </div>

            <div class="layui-tab-item">
                <div style="text-align:center" id="divEmptyBodyConsult">
					没有给该会员添加身体咨询诊断表,请先添加诊断信息 
 					<button class="layui-btn layui-btn-sm layui-btn-normal"  id="btnEditBodyConsult"  style="float:center">添加身体咨询</button>
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
            </div>
            <div class="layui-tab-item">
                <div style="text-align:center" id="divEmptySkinConsult">
					没有给该会员添加皮肤咨询诊断表,请先添加诊断信息 
 					<button class="layui-btn layui-btn-sm layui-btn-normal"  id="btnEditSkinConsult"  style="float:center">添加皮肤咨询</button>
				</div>
				<div name="content" id="divEditSkinConsultContent" style="display:none">
					<form class="layui-form" id="frmId" name="frmId" style="padding: 12px;">
						 <div class="layui-form-item">
						   	<label class="layui-form-label"></label>
						  	<textarea  class="input-xlarge" name="memConsult.content" id="skinConsultContent" ></textarea>
						  </div>
						  <div class="layui-form-item">
						    <div class="layui-input-block">
						      <a class="layui-btn" id="btnSaveSkinConsult">修改</a>
						    </div>
						  </div>
					</form>
				</div>
            </div>
            <div class="layui-tab-item">
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2">名称</td>
							<td class="span2">sn码</td>
							<td class="span1">类型</td>
							<td class="span1">金额</td>
							<td class="span1">姓名</td>
							<td class="span1">次数</td>
							<td class="span2">剩余次数</td>
							<td class="span1">电话</td>
							<td class="span1">发放时期</td>
							<td class="span2">有效期</td>
							<td class="span2">创建人</td>
							
                        </tr>
                    </thead>
						<tbody>
						<c:if test="${empty(couponMembers)&&empty(couponMembers) }">
                    		<tr height="32" align="center">
                    			<td colspan="10">优惠券</td>
                    		</tr>
                    	</c:if>
					<c:forEach var="couponMember" items="${couponMembers }">
							<tr>
								<td>${couponMember.name }</td>
								<td style="text-align: center;">
									${couponMember.code }
								</td>
								<td style="text-align: center;">
									<c:if test="${couponMember.type==1 }">
										代金券
									</c:if>
									<c:if test="${couponMember.type==2 }">
										免费券
									</c:if>
								</td>
								<td>
									<c:if test="${couponMember.type==1 }">
										￥<fmt:formatNumber value="${ couponMember.money}"></fmt:formatNumber>
									</c:if>
									<c:if test="${couponMember.type==2 }">
										免费券
									</c:if>
								</td>
								<td style="text-align: center;">
									${couponMember.member.name }
								</td>
								<td style="text-align: center;">
									${couponMember.count }
								</td>
							    <td style="text-align: center;">
									${couponMember.remainder }
								</td>
							    <td style="text-align: center;">
									${couponMember.member.mobilePhone }
								</td>
								<td style="text-align: center;">
									${couponMember.member.createTime }
								</td>
								<td style="text-align: center;">
									<fmt:formatDate value="${couponMember.start_time}" pattern="yyyy-MM-dd"/> ~
									<fmt:formatDate value="${couponMember.stopTime }" pattern="yyyy-MM-dd"/>
								</td>
								 <td style="text-align: center;">
									${couponMember.creatorName }
								</td>
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
                <table lay-even="" class="layui-table" id="layerDemo" lay-filter="demo" style="width: 100%;">
                    <colgroup>
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <td class="span2" style="text-align: center;width: 200px;">名称</td>
                            <td class="span2" style="text-align: center;width: 200px;">时间</td>
                            <td class="span2" style="text-align: center;width: 400px;">积分数</td>
                            <td class="span2" style="text-align: center;width: 120px;">操作人</td>
                        </tr>
                    </thead>
						<tbody>
						<c:if test="${empty(pointRecords)&&empty(pointRecords) }">
                    		<tr height="32" align="center">
                    			<td colspan="4">无积分记录</td>
                    		</tr>
                    	</c:if>
						<c:forEach var="pointRecord" items="${pointRecords }" varStatus="i">
					      <c:if test="${i.index<(fn:length(pointRecords)-1) }" var="status">
						     <tr>
						       <td>${pointRecord.note }</td>
						       <td><fmt:formatDate value="${pointRecord.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
						       <td >${pointRecord.num }</td>
						       <td>${pointRecord.creator }</td>
						     </tr>
					      </c:if>
					      <c:if test="${status==false }">
						     <tr >
						       <td>${pointRecord.note }</td>
						       <td><fmt:formatDate value="${pointRecord.createTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>
						       <td>${pointRecord.num }</td>
						       <td>${pointRecord.creator }</td>
						     </tr>
					      </c:if>
					      </c:forEach>
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
                     	<c:if test="${empty(memLogs)&&empty(memLogs) }">
                    		<tr height="32" align="center">
                    			<td colspan="8">无操作日志记录</td>
                    		</tr>
                    	</c:if>
                    	<c:forEach var="startWritingLog" items="${memLogs }">
                    		 <tr height="32" align="center">
	                            <td>
	                            	<fmt:formatDate value="${startWritingLog.operateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                            </td>
	                            <td>${startWritingLog.type }</td>
	                            <td>${startWritingLog.note }</td>
	                            <td>${startWritingLog.operator }</td>
	                        </tr>
                    	</c:forEach>
                    </tbody>
                </table>
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
	        	<input  id="txtOrderId" name="memConsult.orderId" value="0"></input>
	        	<textarea  id="txtConsultContent" name="memConsult.content" ></textarea>
	        </form>
	    </div>
    </div>
  <br>
  <br>
  <br>
  <br>
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
		  player = parent.layer;
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
			  preExamResult:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '会员卡储值'
		          ,area: ['760px', '540px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/stormMoneyMemberCard/edit?memberId=${member.dbid}'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  startMemberCard:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '创始会员卡储值'
		          ,area: ['600px', '560px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/stormMoneyMemberCard/startMemberCard?memberId=${member.dbid}'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  changeBalance1:function(){
				  var that = this;
		        player.open({
		          type: 2 
		          ,title: '加余额'
		          ,area: ['420px', '450px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/changeBalance?memberId=${member.dbid}&type=1'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  changeBalance2:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '减余额'
		          ,area: ['420px', '450px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/changeBalance?memberId=${member.dbid}&type=2'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  changePoint1:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '消费积分'
		          ,area: ['420px', '450px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/changePoint?memberId=${member.dbid}&type=-1'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  changePoint2:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '加积分'
		          ,area: ['420px', '450px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/changePoint?memberId=${member.dbid}&type=1'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  changeMemberCard:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '修改会员级别'
		          ,area: ['420px', '450px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/changeMemberCard?memberId=${member.dbid}&type=2'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  memberCancelFrozon:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '冻结会员卡'
		          ,area: ['420px', '400px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/memberCancelFrozon?memberId=${member.dbid}'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  memberThawFrozon:function(){
				  var that = this;
				  layer.confirm('您确取消【${member.name}】冻结状态吗?', function(index){
		        	 $.post("${ctx}/member/saveMemberThawFrozon?memberId=${member.dbid}&date=" + new Date(),
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
			  memberCancel:function(){
				  var that = this;
		        //多窗口模式，层叠置顶
		        player.open({
		          type: 2 //此处以iframe举例
		          ,title: '注销会员卡'
		          ,area: ['420px', '400px']
		          ,shade: 0.8
		          ,maxmin: true
		          ,content: '${ctx}/member/memberCancel?memberId=${member.dbid}'
		          ,zIndex: layer.zIndex //重点1
		          ,success: function(layero){
		            //layer.setTop(layero); //重点2
		          }
		        });
			  },
			  cancelStormMoneyMemberCard: function(){
				  player.confirm('您确定取消【${stormMoneyMemberCard2.orderNo}】会员卡储值记录吗？',{icon: 5}, function(index){
					  $.post('/stormMoneyMemberCard/cancel?id=${stormMoneyMemberCard2.dbid}',{},function(data){
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
			  },
			  stormMoneyOnceEntItemCard:function(){
				  var that = this;
			        //多窗口模式，层叠置顶
			        player.open({
			          type: 2 //此处以iframe举例
			          ,title: '会员购买次卡'
			          ,area: ['760px', '540px']
			          ,shade: 0.8
			          ,maxmin: true
			          ,content: '${ctx}/stormMoneyOnceEntItemCard/edit?memberId=${member.dbid}'
			          ,zIndex: layer.zIndex //重点1
			          ,success: function(layero){
			            //layer.setTop(layero); //重点2
			          }
			        });
			  }
			  
		  }
		  $('.layui-container .layui-btn').on('click', function(){
			  var othis = $(this), method = othis.data('method');
			    active[method] ? active[method].call(this, othis) : '';
		  });
		  $('.layui-tab .layui-btn').on('click', function(){
			  var othis = $(this), method = othis.data('method');
			    active[method] ? active[method].call(this, othis) : '';
		  });
		 element.on('tab(docDemoTabBrief)', function(data){
			  var templateType=this.getAttribute("data-templateType");
			  $("#txtTemplateType").val(templateType);
			  if(templateType)
			  {
				  $.ajax({	
						url : '${ctx}/memConsult/getByUserIdAndType?type='+templateType+"&userId="+${member.dbid },
						async : false, 
						timeout : 20000, 
						dataType : "json",
						type:"get",
						success : function(result){
							console.info(result);
							templateContent=result.content;
							$("#txtDbid").val(result.dbid);
							if(templateType==1){
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
							} else if(templateType==2){
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
							else{
								if(result.type==0){
									$("#divEmptySkinConsult").css('display','block'); 
									$("#divEditFaceConsultContent").css('display','none');
								}
								else{
									$("#txtDbid").val(result.dbid);
									$("#divEmptySkinConsult").css('display','none'); 
									$("#divEditSkinConsultContent").css('display','block');
									editor= CKEDITOR.replace("skinConsultContent");
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
	function ope(dbid){
		player.open({
          type: 2 //此处以iframe举例
          ,title: '会员卡储值明细'
          ,area: ['760px', '540px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/stormMoneyMemberCard/view?stormMoneymember_card_id='+dbid+'&memberId=${member.dbid}'
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
            //layer.setTop(layero); //重点2
          }
        });
	}
	function cancelStormCard(dbid){
		player.confirm('您确定撤销会员卡储值记录吗？',{icon: 5}, function(index){
			player.closeAll();
			$.post('/stormMoneyMemberCard/cancel?id='+dbid,{},function(data){
				  if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						layer.alert(data[0].message, {icon: 5});
					}
					if (data[0].mark == 1) {// 删除数据失败时提示信息
						layer.msg(data[0].message, {icon: 5});
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						layer.msg(data[0].message,{icon: 1});
					    setTimeout(	function() {window.location.reload() ;}, 1000);
					}
			  });
	      });
	}
	
	function  cancelCountCard(id){
		  player.confirm('您确定撤销会员的次卡购买记录吗？',{icon: 5}, function(index){
			  $.post('/stormMoneyOnceEntItemCard/cancel?id='+id,{},function(data){
				  player.closeAll();
				  if (data[0].mark == 2) {// 关系存在引用，删除时提示用户，用户点击确认后在退回删除页面
						layer.alert(data[0].message, {icon: 5});
					}
					if (data[0].mark == 1) {// 删除数据失败时提示信息
						layer.msg(data[0].message, {icon: 5});
					}
					if (data[0].mark == 0) {// 删除数据成功提示信息
						layer.msg(data[0].message,{icon: 1});
					    setTimeout(	function() {window.location.reload() ;}, 1000);
					}
			  })
	      });
	  }
	function viewStormMoneyOnceEntItemCard(dbid){
		player.open({
          type: 2 //此处以iframe举例
          ,title: '次卡购买明细'
          ,area: ['760px', '540px']
          ,shade: 0.8
          ,maxmin: true
          ,content: '${ctx}/stormMoneyOnceEntItemCard/view?stormMoneyOnceEntItemCardId='+dbid+'&memberId=${member.dbid}'
          ,zIndex: layer.zIndex //重点1
          ,success: function(layero){
            //layer.setTop(layero); //重点2
          }
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
    
    $(document).on('click', '#btnEditSkinConsult', function () {
		$("#divEmptySkinConsult").css('display','none'); 
		$("#divEditSkinConsultContent").css('display','block');
		editor= CKEDITOR.replace("skinConsultContent");
		editor.setData(templateContent);
    });

    $(document).on('click', '#btnSaveFaceConsult,#btnSaveBodyConsult,#btnSaveSkinConsult', function () {
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
