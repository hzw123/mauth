<#include "../../commons/macro.ftl">
<@commonHead/>

<link rel="stylesheet" href="${ctx}/widgets/bootstrap3/css/bootstrap.min.css"/>
<title>文件上传组件</title>
</head>

<body>

<div>
<div class="bs-docs-section">
  <div class="bs-example bs-example-tabs">
    <ul id="myTab" class="nav nav-tabs" role="tablist">
      <c:if test="${param.isActive==1 }" var="status">
      	<li ><a href="#home" role="tab" data-toggle="tab">上传本地文件</a></li>
      	<li class="active"><a href="#profile" role="tab" data-toggle="tab">选择文件</a></li>
      </c:if>
      <c:if test="${status==false }">
      	<li class="active"><a href="#home" role="tab" data-toggle="tab">上传本地文件</a></li>
      	<li ><a href="#profile" role="tab" data-toggle="tab">选择文件</a></li>
      </c:if>
    </ul>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane fade in active" id="home">
        <div style="" class="upload">
				<div style="padding-left: 5px;">
					<span id="spanButtonPlaceholder1"></span> <br />
				</div>
				<div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
				
			</div>
			<div style="">
				<input type="hidden" name="brand.logo" id="fileUpload" readonly="readonly"	value="${brand.logo }">
				<img alt="" id="fileUploadImage" src="${brand.logo }" width="80" height="60">
			</div>
      </div>
    <div class="tab-pane fade" id="profile" style="width: 80%;">
     <%
   	 /***
   	 ** 梁玉龙  javayulong@qq.com
   	 ** 2011-12-21 09:21:32
   	 ** content: 文件夹下图片实现浏览并分页效果
   	 */
   	String imgeUrl="";
  	String strRealPath = getServletContext().getRealPath("/");//得到项目的绝对路径
  	List data=null;
  	String picPath="archives/coupon";
  	//out.println("strRealPath="+strRealPath);
 	data=recursion(strRealPath+picPath,true,0);//第一个参数图片文件夹路径,第二个参数只需设置true,或false,true表示也循环子目录下的图片，第三个参数默认为0即可,千万不要改动
 	//data=recursion("D:\\resin\\webapps\\PicList\\images",false);
 	if(data==null){out.println("该文件夹不存在!");return ;}
 	
 
%>
 <table border="0" align="center" width="100%">
<%
			  //log.info("共 "+totle+" 张图片, "+totlePage+" 页。当前"+showPage+"页，每页显示"+pageSize+"条");
			   //游标的位置 (当前页 - 1) * 页面大小 + 1
			
			  for (int i = posion; i <= data.size(); i++) {
				    	if((i-posion)%7==0){
				    		%>
				    		<tr>
				    		<%
				    		imgeUrl=basePath+data.get(i-1);
				    		imgeUrl=imgeUrl.replace("\\", "/");
						  }
						  %>
						  	<td align="center">
							  	<div class=thumbnail style="margin-left:0;width: 80px;height: 80px;">
								  	<img src="<%=imgeUrl %>" width="160" height="160" border="0"/>
							  	</div>
						  	</td>
						  <%
						if((i-posion)%7==6){
						%>
							</tr>
						<%
						}

					  //out.println("图片：>>>"+data.get(i-1)+"\r\n");//有问题，要判断，小于4，
			}
				  
   %>
   </table>
   <%!
   //遍历某个目录下所有文件
  	  int folderCount;//文件夹个数
	  int fileCount;//文件个数
	  int picFilCount=0;//图片个数
	// 总共的数据量   (图片个数)
      int totle;   

    // 共有多少页   
      int totlePage;    
    // 数据   
      List list;
   /**
	 * 
	 * @param root 文件夹所在位置
	 * @param isBlAll 是否连子文件夹下的某个文件(图片)也遍历
	 * @param isrRef 可设为0或1,为0时，表示是翻页或刷新时请求，为1时表示循环内部所有文件夹时请求，我们调用时,只需要设置为0就ok，其他的不用操心
	 */
	public  List recursion(String root,boolean isBlAll,int isRef){
		if(isRef==0){
			list=new ArrayList();
		}
		//Java中读取某个目录下的所有文件和文件夹
		  String filePath=getServletContext().getRealPath("/");
		  String picPath=root.replace(filePath,"");
		  log.info("梁玉龙弄国际奥法"+picPath);
		 // String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		  // log.info("梁玉龙弄国"+);
		  File file=new File(root);
		  if(file.isDirectory()){
		  File[] tempList = file.listFiles();
		  //log.info("该目录下对象个数："+tempList.length);
		  for (int i = 0; i < tempList.length; i++) {
			   if (tempList[i].isFile()) {
				fileCount++;
			    //log.info("文     件："+tempList[i]);
			    String fileName=tempList[i].getName();//文件名称
			    String hzm=fileName.substring(fileName.indexOf(".")+1,fileName.length()); //文件后缀名
			    hzm=hzm.toLowerCase();
			    if(hzm.equals("jpg")||hzm.equals("bmp")||hzm.equals("gif")||hzm.equals("png")){
			    	picFilCount++;
			    	//list.add(tempList[i]);//这里要注意，data.add(fileName);
			    	list.add(picPath+"\\"+fileName);
			    }
			   // log.info("名称"+hzm);
			   }
			   if (tempList[i].isDirectory()) {
				 folderCount++;
				 //log.info("文件夹："+tempList[i]);
				 if(isBlAll){
					 recursion(tempList[i].getAbsolutePath(),isBlAll,1);
				 }
			  
			   }
		  }
		  totle=picFilCount;
		 // log.info("文件夹个数："+folderCount+"\r\n文件个数："+fileCount+"\r\n图片个数："+picFilCount);
			return list;
		}
		else{
			return null;
		}
	}
   
    %>
      </div>
    </div>
  </div><!-- /example -->
  
<script src="${ctx}/widgets/bootstrap3/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/swfupload.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/plugins/swfupload.speed.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/SWFUpload/js/fileupload.handlers.js"></script>
<script type="text/javascript">
var upload1;
window.onload = function() {
	upload1 = new SWFUpload(
			{
				// Backend Settings
				upload_url : "${ctx}/swfUpload/uploadFileCoupon",
				post_params : {
					"PHPSESSID" : "6a95034fff6ba3a6aa8a990ca3af42ee","userId":"${sessionScope.user.dbid}"
				},
				//上传文件的名称
				file_post_name : "file",

				// File Upload Settings
				file_size_limit : "5242880", // 200MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "100",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueErrorHandler,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button Settings
				button_image_url : "${ctx}/widgets/SWFUpload/images/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width : 61,
				button_height : 22,

				// Flash Settings
				flash_url : "${ctx}/widgets/SWFUpload/Flash/swfupload.swf",

				custom_settings : {
					progressTarget : "uploadFileContent",
					cancelButtonId : "btnCancel1",
					titlePicture : "fileUpload",
					fileUploadImage : "fileUploadImage"
				},
				// Debug Settings
				debug : false
			});
}
$('#myTab a').click(function (e) {
  e.preventDefault()
  $(this).tab('show')
})
</script>	
</body>
</html>