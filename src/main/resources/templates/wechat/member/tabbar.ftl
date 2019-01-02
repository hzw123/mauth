<#include "../../commons/macro.ftl">
<@commonHead/>
<div class="butt container" id="container">
	<div class="tabbar">
		<div class="weui_tab">
		    <div class="weui_tabbar butt">
		        <a href="${ctx}/agentWechat/index?type=1" class="weui_tabbar_item">
		            <div class="weui_tabbar_icon">
		                <img src="${ctx}/weui/example/images/icon_nav_button.png" alt="" >
		            </div>
		            <p class="weui_tabbar_label">首页</p>
		        </a>
		        <a href="${ctx}/recommendCustomerWechat/recommendCustomer" class="weui_tabbar_item">
		            <div class="weui_tabbar_icon">
		                <img src="${ctx}/weui/example/images/icon_nav_msg.png" alt="">
		            </div>
		            <p class="weui_tabbar_label">推荐客户</p>
		        </a>
		        <a href="${ctx}/agentWechat/mySpreadDetail" class="weui_tabbar_item">
		            <div class="weui_tabbar_icon">
		                <img src="${ctx}/weui/example/images/icon_nav_article.png" alt="">
		            </div>
		            <p class="weui_tabbar_label">专属二维码</p>
		        </a>
		        <a href="${ctx}/agentWechat/memberCenter" class="weui_tabbar_item">
		            <div class="weui_tabbar_icon">
		                <img src="${ctx}/weui/example/images/icon_nav_cell.png" alt="">
		            </div>
		            <p class="weui_tabbar_label">我</p>
		        </a>
		    </div>
		</div>
	</div>
</div>