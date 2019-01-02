<#include "../commons/macro.ftl">
<@commonHead/>


	<script src="${ctx}/widgets/jquery.min.js"></script>
<title>文件上传组件</title>
</head>
<style>
 .imagea{
 	display: block;margin: 6px;border: 3px solid #FFF;cursor: pointer;
 }
 .imagea:HOVER {
	border:3px solid #1094fa;
}

#online {
    padding: 10px 0 0;
    width: 100%;
}
#online #imageList {
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    position: relative;
    width: 100%;
}

#online ul {
    display: block;
    list-style: outside none none;
    margin: 0;
    padding: 0;
}

#online li {
    background-color: #eee;
    cursor: pointer;
    display: block;
    float: left;
    height: 120px;
    list-style: outside none none;
    margin: 0 0 9px 9px;
    overflow: hidden;
    padding: 0;
    position: relative;
    width: 120px;
}
#online li{
	border: 3px solid #FFF;cursor: pointer;
}
#online li:HOVER{
	border:3px solid #1094fa;
}
#online li.selected .icon {
    background-image: url("${ctx}/pages/compoent/success.png");
    background-position: 80px 80px;
}
#online li .icon {
    background-repeat: no-repeat;
    border: 0 none;
    cursor: pointer;
    height: 120px;
    left: 0;
    position: absolute;
    top: 0;
    width: 120px;
    z-index: 2;
}
</style>
<script type="text/javascript">
	var addMoreState=false;
	var numStatue="${param.numState}";
	if(numStatue!=''&&numStatue!=undefined){
		if(numStatue=="true"){
			addMoreState=true;
		}
	}
	var tergetUrl='';
	$(document).ready(function(){
		$(".list li").each(function(i,v){
			$(this).bind("click",function(){
				if(addMoreState){
					var classes=$(this).attr('class');
					if(null==classes||undefined==classes||classes==''){
						$(this).addClass("selected");
					}else{
						if(classes.contains('selected')){
							$(this).removeClass("selected");
						}
					}
				}else{
					$(".list li").each(function(i,v){
						$(this).removeClass("selected");
					})
					var classes=$(this).attr('class');
					if(null==classes||undefined==classes||classes==''){
						$("#fileUpload").val("");
						$(this).addClass("selected");
						tergetUrl=$(this).find("img").attr("srctarget");
						$("#fileUpload").val(tergetUrl);
					}else{
						if(classes.contains('selected')){
							$(this).removeClass("selected");
						}
					}
				}
			});
		})
	})
</script>
<body>

<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <ul class="layui-tab-title">
    <li class="layui-this">上传本地文件</li>
    <li>远程图片</li>
    <li>选择文件</li>
  </ul>
 <div class="layui-tab-content">
      <div class="layui-tab-item layui-show">
        	<input type="hidden" name="fileUpload" id="fileUpload" readonly="readonly"	>
			<button type="button" class="layui-btn" id="test1">上传图片</button>
			<div class="layui-upload-list">
			    <img class="layui-upload-img" id="demo1" width="300" height="180" >
			    <p id="demoText"></p>
			  </div>
      </div>
      <div class="layui-tab-item" id="net" style="margin-top: 12px;">
        	<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
				<tr height="42">
					<td style="width: 120px;">图片地址:&nbsp;</td>
					<td style="width: 600px;text-align: left;">
						<input type="text" name="netUrl" id="netUrl" onkeyup="$('#fileUpload').val(this.value)"	class="layui-input" style="width: 300px;text-align: left;" title="网络连接地址">
					</td>
				</tr>
			</table>
      </div>
      
   <div class="layui-tab-item ${param.isActive==1? 'layui-show':''} ">
     <%
   	 /***
   	 ** 梁玉龙  javayulong@qq.com
   	 ** 2011-12-21 09:21:32
   	 ** content: 文件夹下图片实现浏览并分页效果
   	 */
   	String imgeUrl="";
  	String strRealPath = getServletContext().getRealPath("/");//得到项目的绝对路径
  	List data=null;
  	String picPath="archives";
  	//out.println("strRealPath="+strRealPath);
 	data=recursion(strRealPath+picPath,true,0);//第一个参数图片文件夹路径,第二个参数只需设置true,或false,true表示也循环子目录下的图片，第三个参数默认为0即可,千万不要改动
 	//data=recursion("D:\\resin\\webapps\\PicList\\images",false);
 	if(data==null){out.println("该文件夹不存在!");return ;}
 	
 
	int pageSize=20;//每页显示多少条
 	int showPage=1;//第几页
 	int totle=data.size();//共有多少条数据(多少张照片)
 	String state="";
 	String upPage=request.getParameter("upPage");
 	String nextPage=request.getParameter("nextPage");
 	if(upPage!=null){
 		showPage=Integer.parseInt(upPage);
 	}
 	if(nextPage!=null){
 		showPage=Integer.parseInt(nextPage);
 	}
 	
  	if(data.size()>0){
			 if(totle%pageSize==0){
				  totlePage=totle/pageSize; //共有多少页
			  }
			  else{
				  totlePage=totle/pageSize+1;
			  }
			  //当前页小于等于第一页 则按第一页算 如果 当前页大于等于总页数则为最后页
			    if(showPage <=1){
			    	state="当前已是首页！";
			        showPage = 1;
			    }
			    else if(showPage >= totlePage){
			        showPage =  totlePage;
			        if(showPage==totlePage){ state="当前已是最后一页！";}
			        
			    }
%>
 <!-- <table border="0" align="center" width="100%" style="margin-left: 8px;margin-top: 8px;"> -->
 <div class="panel focus" id="online">
 <div id="imageList">
 <ul class="list">
<%
//log.info("共 "+totle+" 张图片, "+totlePage+" 页。当前"+showPage+"页，每页显示"+pageSize+"条");
//游标的位置 (当前页 - 1) * 页面大小 + 1
int posion = (showPage-1) * pageSize + 1;
int endData=pageSize*showPage;
for (int i = posion; i <= data.size(); i++) {
	  if(i>endData){
	  //out.println("break;---"+i);
		  break;
	  }
	  else{
	    		%>
			  	<%-- <td align="center">
				  	<a class="imagea" style="" >
				  		<img src="<%=basePath+data.get(i-1) %>" width="120" height="120" border="0"/>
				  	</a>
				  	
			  	</td> --%>
			  	<li><img width="150" height="120" src="<%=basePath+data.get(i-1)%>" srctarget='/<%=data.get(i-1)%>'><span class="icon"></span></li>
			  <%
		  }
		  
	}
	}
	else{
	out.println("该目录下没有图片");
	}
				  
   %>
   </ul>
  </div>
  <table border="0" align="center" width="100%" style="margin-left: 8px;margin-top: 8px;">
   <tr>
	   	<td align="center" colspan="8" nowrap="nowrap" style="font-size: 12px;">
	   	图片数量: <font color="red"><%=data.size() %></font> 张,共 <font color="red"><%=totlePage %></font>  页,当前第 <font color="red"><%=showPage %></font> 页&nbsp;
	   	<a href="/compoent/uploadConpent?upPage=<%=1 %>&isActive=1&numState=${param.numState}" style="text-decoration: none">首页</a>&nbsp;
	   	<a style="text-decoration: none" href="/compoent/uploadConpent?upPage=<%=showPage-1 %>&isActive=1&numState=${param.numState}">上一页</a>&nbsp;
	   	<a href="/compoent/uploadConpent?nextPage=<%=showPage+1 %>&isActive=1&numState=${param.numState}" style="text-decoration: none">下一页</a>&nbsp;
	   	<a href="/compoent/uploadConpent?nextPage=<%=totlePage %>&isActive=1&numState=${param.numState}" style="text-decoration: none">尾页</a>&nbsp;
	   	跳转第&nbsp;<input id="goPage" type="text" style="width:30px;text-align: center;" value="<%=showPage %>" />&nbsp;页&nbsp;<input type="button" value="GO" onclick="ck()"/>
	   	&nbsp;<span><%=state %></span>
	   	</td>
   	</tr>
   </table>
  </div>
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
			    	list.add(picPath+"/"+fileName);
			    	picPath=picPath.replace("\\", "/");
			    }
			   }
			   if (tempList[i].isDirectory()) {
				 folderCount++;
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
  </div><!-- /example -->
 </div>

<script type="text/javascript">
var topParent=window.parent;
if(parent==undefined){
	topParent=parent;
}
var layer;
	layui.use(['form', 'layedit', 'element','upload'], function(){
	  var form = layui.form,upload = layui.upload,element = layui.element;;
	  if(topParent==undefined){
			 layer = layui.layer;
		  }else{
			 layer = topParent.layer;
		  }
	  var $ = layui.$;
	  var uploadInst = upload.render({
		    elem: '#test1'
		    ,url: '/swfUpload/uploadFileLayui'
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		        $('#demo1').attr('src', result); //图片链接（base64）
		      });
		    }
		    ,done: function(res){
		      //如果上传失败
		      if(res.code > 0){
		        return layer.msg('上传失败');
		      }
		      //上传成功
		      $("#fileUpload").val(res.url);
		    }
		    ,error: function(){
		      //演示失败状态，并实现重传
		      var demoText = $('#demoText');
		      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
		      demoText.find('.demo-reload').on('click', function(){
		        uploadInst.upload();
		      });
		    }
		  });
	});
</script>	
</body>
</html>