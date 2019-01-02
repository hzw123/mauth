package cn.mauth.ccrm.core.util;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugUtil {

    private static final Logger log=LoggerFactory.getLogger(DebugUtil.class);

    public static void showAllPorperty(List<?> items) {
		 log.info("============================集合size为:" + items.size() + "============================");
		for (Object object : items) {
			log.info(ReflectionToStringBuilder.toString(object));
		}

    }
    public static void showAllPorperty(Set<?> items) {
		// log.info("============================集合size为:" +
		// items.size() + "============================");
		// for (Object object : items) {
		// log.info(ReflectionToStringBuilder.toString(object));
		// }

    }

    public static void showAllPorperty(Object object) {
		 log.info(ReflectionToStringBuilder.toString(object));
    }

    public static void showParameter(HttpServletRequest re) {
    	
    }
    @SuppressWarnings("unchecked")
    public static void showRequestParameterNames(HttpServletRequest request) {
		// Enumeration v = request.getParameterNames();
		// if (null != request.getParameter("method")) {
		// // log.info("=================method==============" +
		// // "     " + request.getParameter("method"));
		// }
		// while (v.hasMoreElements()) {
		// String temp = (String) v.nextElement();
		// String[] parameters = request.getParameterValues(temp);
		// if (parameters.length == 0) {
		// log.info(temp + "<===>" + request.getParameter(temp));
		// } else {
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < parameters.length; i++) {
		// sb.append(parameters[i] + "       ");
		// }
		// log.info(temp + "<===>" + sb.toString());
		// }
		// }
    }
}
