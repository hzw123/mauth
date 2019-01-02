<c:if test="${not empty page && not empty page.result}">
	<div class="pagination">
	 <ul style="float: right;">
    	 <li>
	   		 <a>
   		 		每页显示<select name="pageSize" align="absmiddle" onchange="cwSearchPagingOrder('${templates.currentPageNo}',$(this).find('option:selected').text());" id="pageSize"
                            style="width:40px;margin-top:-5px;padding: 0;height: 16px;">
			      <c:forEach var="i" begin="5" end="50" step="5">
			          <option value="${i}" <c:if test="${i== page.pageSize}">selected</c:if>>${i}</option>
			      </c:forEach>
		     </select>
			 </a> 
           </li>
		<li>
			<a>
				跳转至<input type="text" onkeyup="this.value=this.value.replace(/\D/g,'');document.all.currentPage.value=this.value;" onafterpaste="this.value=this.value.replace(/\D/g,'');"  maxlength="5" name="curPage" id="curPage"  value="" size="3" 
				style="width:30px;margin-bottom: 0;padding: 0;height: 12px;line-height: 12px;"/>页
			</a>
		</li>
		<li>
	   	 	<a  href="javascript:void(-1)" onclick="cwSearchPagingOrder(getValue(),'${templates.pageSize}');" > 确定</a>
	     </li>
	      
     </ul>
    <ul style="float: right;">
	    <li>
	    	<a href="javascript:void(-1)">
	     	总数${page.totalCount}  
		   </a>
	    </li>
    <c:choose>
	    <c:when test="${page.currentPageNo==1}">
	       <li class="disabled"><a href="javascript:void(-1)">上一页</a></li>
	    </c:when>
	    <c:otherwise>
	        <li><a href="javascript:void(-1)"  onClick="javascript:cwSearchPagingOrder(${templates.currentPageNo-1},'${templates.pageSize}');return false;">上一页</a></li>
	    </c:otherwise>
    </c:choose>
	<c:if test="${page.currentPageNo>5}">
		 <li><a href="#" onClick="javascript:cwSearchPagingOrder(1,'${templates.pageSize}');return false;">1</a></li>
		 <li><a href="#">...</a></li>
	</c:if>
    <c:set var="minNum" value="${page.currentPageNo-2}"/>
    <c:set var="maxNum" value="${page.currentPageNo+2}"/>
    <c:if test="${page.currentPageNo<=5}">
    	<c:set var="minNum" value="1"/>
    </c:if>
    <c:if test="${page.currentPageNo>=page.totalPageCount-5}">
    	<c:set var="maxNum" value="${page.totalPageCount}"/>
    </c:if>
    <c:if test="${page.totalPageCount>=7 && maxNum<7}">
    	<c:set var="maxNum" value="7"/>
    </c:if>
    <c:forEach var="i" begin="${minNum}" end="${maxNum}" step="1">
      <c:choose>
      	<c:when test="${i==page.currentPageNo}">
      		 <li class="active"><a href="javascript:void(-1)">${i}</a></li>
      	</c:when>
      	<c:otherwise>
      		 <li><a href="javascript:void(-1)" onClick="javascript:cwSearchPagingOrder(${i},'${templates.pageSize}');return false;">  ${i}</a></li>
      	</c:otherwise>
      </c:choose>
    </c:forEach>
    <c:if test="${page.totalPageCount>maxNum}">
    	<li><a href="#">...</a></li>
    	<li><a href="#" onClick="javascript:cwSearchPagingOrder(${templates.totalPageCount},'${templates.pageSize}');return false;">${page.totalPageCount}</a></li>
    </c:if>
     <c:choose>
     	<c:when test="${page.currentPageNo==page.totalPageCount}">
        	 <li class="disabled"><a  href="javascript:void(-1)" >下一页</a></li>
     	</c:when>
	    <c:otherwise>
	       <li> <a href="javascript:void(-1)"  onClick="javascript:cwSearchPagingOrder(${templates.currentPageNo+1},'${templates.pageSize}');return false;">下一页</a></li>
	    </c:otherwise>
    </c:choose>
    	
    </ul>
     <ul style="clear: both;"></ul>
    </div>
</c:if>
<script type="text/javascript">
function getValue(){
	var curPage=$("#curPage").val();
	return curPage;
}
function cwSearchPagingOrder(curPage,pageSize){
	var qForm=$("#searchPageForm");
    if(typeof(qForm)=="undefined") return;
    if(typeof(curPage)!="undefined" && curPage!=null && curPage!=""){
      $("#currentPage").val(curPage);
    }
    if(typeof(pageSize)!="undefined" && pageSize!=null && pageSize!=""){
      $("#paramPageSize").val(pageSize);
    }
    qForm.submit();
}
</script>