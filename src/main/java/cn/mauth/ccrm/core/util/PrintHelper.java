package cn.mauth.ccrm.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.mauth.ccrm.SpringContextUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.mauth.ccrm.core.domain.set.SetPrinterModel;
import cn.mauth.ccrm.server.set.PrinterServer;

public class PrintHelper {
	
/*	public static final String URL = "http://api.feieyun.cn/Api/Open/";//不需要修改
	
	public static final String USER = "yehilong@126.com";//*必填*：账号名
	public static final String UKEY = "NqsV94rmpaFHrFn5";//*必填*: 注册账号后生成的UKEY
	public static final String SN = "917503768";//*必填*：打印机编号，必须要在管理后台里添加打印机或调用API接口添加之后，才能调用API
*/	
	public static String Print(String content,Integer enterpriseId){	
		PrinterServer printerManage= SpringContextUtil.getBean(PrinterServer.class);
		List<SetPrinterModel> printers= printerManage.queryList(enterpriseId);
		if(printers.size()>0){
			for(SetPrinterModel printer :printers){
				if(printer.getState()!=0){
					continue;
				}
				RequestConfig requestConfig = RequestConfig.custom()  
			            .setSocketTimeout(30000)//读取超时  
			            .setConnectTimeout(30000)//连接超时
			            .build();
				
				CloseableHttpClient httpClient = HttpClients.custom()
						 .setDefaultRequestConfig(requestConfig)
						 .build();	
				String URL = printer.getUrl();
				
				String USER = printer.getUser();
				String UKEY = printer.getUkey();
				String SN = printer.getSn();
				
				HttpPost post = new HttpPost(URL);
		        List<NameValuePair> nvps = new ArrayList<>();
				nvps.add(new BasicNameValuePair("user",USER));
				String STIME = String.valueOf(System.currentTimeMillis()/1000);
				nvps.add(new BasicNameValuePair("stime",STIME));
				nvps.add(new BasicNameValuePair("sig",signature(USER,UKEY,STIME)));
				nvps.add(new BasicNameValuePair("apiname","Open_printMsg"));//固定值,不需要修改
				nvps.add(new BasicNameValuePair("sn",SN));
				nvps.add(new BasicNameValuePair("content",content));
				nvps.add(new BasicNameValuePair("times","1"));//打印联数
				
				CloseableHttpResponse response = null;
				String result = null;
		        try
		        {
		    	   post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		    	   response = httpClient.execute(post);
		       	   int statecode = response.getStatusLine().getStatusCode();
		       	   if(statecode == 200){
			        	HttpEntity httpentity = response.getEntity(); 
			            if (httpentity != null){
			            	//服务器返回的JSON字符串，建议要当做日志记录起来
			            	result = EntityUtils.toString(httpentity);
			            }
		           }
		       }
		       catch (Exception e)
		       {
		    	   e.printStackTrace();
		       }
		       finally{
		    	   try {
		    		   if(response!=null){
		    			   response.close();
		    		   }
		    	   } catch (IOException e) {
		    		   e.printStackTrace();
		    	   }
		    	   try {
		    		   post.abort();
		    	   } catch (Exception e) {
		    		   e.printStackTrace();
		    	   }
		    	   try {
		    		   httpClient.close();
		    	   } catch (IOException e) {
		    		   e.printStackTrace();
		    	   }
		       }
			}
		}    
       return "true";
	}
	
	private static String signature(String USER,String UKEY,String STIME){
		String s = DigestUtils.shaHex(USER+UKEY+STIME);
		return s;
	}
}
