package cn.mauth.ccrm.core.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class ParamUtil {

	public static Date getDateParam(HttpServletRequest request, String name) {
		name = request.getParameter(name);
		if (name == null || "".equals(name.trim()))
			return null;
		java.text.SimpleDateFormat sf = null;
		if (name.length() > 10) {
			sf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			Date date = sf.parse(name);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}

	public static Double getDoubleParam(HttpServletRequest request,
			String name, double def) {
		Double integer = null;
		String temp = request.getParameter(name);

		try {
			integer = new Double(temp);
		} catch (NumberFormatException e) {
			return def;
		}
		if (integer == null) {
			return def;

		} else {
			return integer;
		}
	}

	public static Integer[] getIntArray(HttpServletRequest request, String s) {

		String[] s1 = getStrArray(request, s);
		Integer[] j = null;

		if (s1 != null) {
			for (int i = 0; i < s1.length; i++) {  
				if (i == 0)
					j = new Integer[s1.length];
				j[i] = new Integer(s1[i]);
			}
		}
		return j;
	}

	/**
	 * @param request
	 */
	public static Integer[] getIntArraryByDbids(HttpServletRequest request,String name) {
		String dbids = request.getParameter(name);
		Integer[] ids=null;
		if(null!=dbids&&dbids.length()>0){
			String[] split = dbids.split(",");
			ids=new Integer[split.length];
			for (int i=0;i<split.length;i++) {
				ids[i]=Integer.parseInt(split[i]);
			}
		}
		return ids;
	}
	public static Integer getIntParam(HttpServletRequest request, String name,
			int def) {
		Integer integer = null;
		String temp = request.getParameter(name);
		try {
			integer = new Integer(temp);
		} catch (NumberFormatException e) {
			return def;
		}

		if (integer == null) {
			return def;
		} else {
			return integer;
		}
	}

	public static Boolean getBoolParam(HttpServletRequest request, String name,
			Boolean def) {
		Boolean result = null;
		String strVal = request.getParameter(name);
		try {
			result = Boolean.parseBoolean(strVal);
		} catch (Exception e) {
			result = def;
		}
		return result;
	}

	public static Long getLongParam(HttpServletRequest request, String name,
			Long def) {
		Long result = null;
		String temp = request.getParameter(name);
		try {
			result = new Long(temp);
		} catch (NumberFormatException e) {
			return def;
		}
		if (result == null) {
			return def;
		} else
			return result;
	}

	public static String getParam(HttpServletRequest request, String name,
			String def) {
		String string = request.getParameter(name);
		if (string == null || "".equals(string))
			string = def;
		return string;
	}

	public static String[] getStrArray(HttpServletRequest request, String s) {
		return request.getParameterValues(s);
	}

	public static Integer[] getIntArrayByIds(HttpServletRequest request,
			String s) {
		String ids = request.getParameter(s);
		Integer[] idInts = null;
		if (null != ids && ids.trim().length() > 0) {
			String[] idStr = ids.split(",");
			idInts = new Integer[idStr.length];
			for (int i = 0; i < idStr.length; i++) {
				idInts[i] = Integer.parseInt(idStr[i]);
			}
		}
		return idInts;
	}

	/**
	 * 消除js字符串常量中的三个特殊字符',",\
	 * 
	 * @param s
	 *            String 要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String toJSString(String s) {
		String temp = new String(s);
		if (temp != null && !temp.equals(""))
			temp = temp.replace("\\", "\\\\").replace("'", "\\'")
					.replace("\"", "\\\"");// 把字符串的\-->\\,'-->\',"-->\"
		return temp;

	}
	public static String getQueryUrl(HttpServletRequest request){
		Enumeration<String> parameterNames = request.getParameterNames();
		String param="?";
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String value = request.getParameter(name);
			param=param+name+"="+value+"&";
		}
		return param;
	}
	public static String getUTF8(HttpServletRequest request,String s) {
		String value = request.getParameter(s);
		if(null!=value&&value.trim().length()>0){
			boolean messyCode = isMessyCode(value);
			if(messyCode){
				String name="";
				try {
					name = new String(value.getBytes("iso-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					return null;
				}
				return name;
			}else{
				return value;
			}
		}
		return null;
	}
	/* 判断字符是否是中文 
    * 
    * @param c 字符 
    * @return 是否是中文 
    */  
   public static boolean isChinese(char c) {  
       Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
       if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
               || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
               || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
               || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
               || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
               || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
           return true;  
       }  
       return false;  
   }  
  
   /** 
    * 判断字符串是否是乱码 
    * 
    * @param strName 字符串 
    * @return 是否是乱码 
    */  
   public static boolean isMessyCode(String strName) {  
	   Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");  
       Matcher m = p.matcher(strName);  
       String after = m.replaceAll("");  
       String temp = after.replaceAll("\\p{P}", ""); 
       char[] ch = temp.trim().toCharArray();  
       float chLength = ch.length;  
       float count = 0;  
       for (int i = 0; i < ch.length; i++) {  
           char c = ch[i];  
           if (!Character.isLetterOrDigit(c)) {  
               if (!isChinese(c)) {  
                   count = count + 1;  
               }  
           }  
       }  
       float result = count / chLength;  
       if (result > 0.4) {  
           return true;  
       } else {  
           return false;  
       }  
  
   } 
}
