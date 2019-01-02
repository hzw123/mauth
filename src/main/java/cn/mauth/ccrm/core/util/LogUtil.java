package cn.mauth.ccrm.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 
 * @author  shusanzhan
 *
 */
public class LogUtil {

  private static final String LOGCONFIG = "log4j.properties";
  private static final Logger logger=LoggerFactory.getLogger(LogUtil.class);;
  
  private static String getConfigFile(){
    String s = LogUtil.class.getClassLoader().getResource("").toString();
    String filePath = s + LOGCONFIG;
    return filePath;
  }
  
  // Start Info
  // /
  // / 记录信息
  // /
  // / 信息内容
  public static void info(String message, Exception exception) {
    try {
      log("INFO", message, exception);
    } catch (Exception ex) {

    }
  }

  // /
  // / 记录信息
  // /
  // / 信息内容
  public static void info(Object message) {
    logger.info(message.toString());
  }
  // end Info

  // Start Error 
  public static void trace(String message) {
      logger.trace(message);
  }

  public static void trace(String message, Exception exception) {
    logger.trace(message,exception);
  }

  // /
  // / 记录一个错误信息
  // /
  // / 信息内容
  // / 异常对象
  public static void error(String message, Exception exception) {
    logger.error(message,exception);
  }

  // /
  // / 记录一个错误信息
  // /
  // / 信息内容
  public static void error(String message) {
   logger.error(message);
  }

  // end Error

  // Start Warning

  // /
  // / 记录一个警告信息
  // /
  // / 信息内容
  // / 异常对象
  public static void warning(String message, Exception exception) {
    try {
      log("WARN", message, exception);
    } catch (Exception ex) {

    }
    logger.warn(message,exception);
  }

  // /
  // / 记录一个警告信息
  // /
  // / 信息内容
  public static void warning(String message) {
    logger.warn(message);
  }

  // end Warning

  // Start Fatal

  // /
  // / 记录一个程序致命性错误
  // /
  // / 信息内容
  // / 异常对象
  public static void fatal(String message, Exception exception) {
    logger.debug(message,exception);
  }

  // /
  // / 记录一个程序致命性错误
  // /
  // / 信息内容
  public static void fatal(String message) {
    logger.debug(message);
  }

  // end Fatal

  // Start Debug

  // /
  // / 记录调试信息
  // /
  // / 信息内容
  // / 异常对象
  public static void debug(String message, Exception exception) {
    logger.debug(message,exception);
  }

  // /
  // / 记录调试信息
  // /
  // / 信息内容
  public static void debug(String message) {
    logger.debug(message);
  }
  // end Debug

  public static void log(String level, Object msg)
  {
    log(level, msg, null);
  }

  public static void log(String level, Throwable e)
  {
    log(level, null, e);
  }

  public static void log(String level, Object msg, Throwable e)
  {
    try{
      StringBuilder sb = new StringBuilder();
      Throwable t = new Throwable();
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      String input = sw.getBuffer().toString();
      StringReader sr = new StringReader(input);
      BufferedReader br = new BufferedReader(sr);
      for(int i=0;i<4;i++)
        br.readLine(); 
      String line = br.readLine();
      //at com.media.web.action.DicManageAction.list(DicManageAction.java:89)
      int paren = line.indexOf("at ");
      line = line.substring(paren+3);
      paren = line.indexOf('(');
      String invokeInfo = line.substring(0, paren);
      int period = invokeInfo.lastIndexOf('.');
      sb.append('[');
      sb.append(invokeInfo.substring(0,period));
      sb.append(':');
      sb.append(invokeInfo.substring(period+1));
      sb.append("():");
      paren = line.indexOf(':');
      period = line.lastIndexOf(')');
      sb.append(line.substring(paren+1,period));
      sb.append(']');
      sb.append(" - ");
      sb.append(msg);
    }catch(Exception ex){
      LogUtil.info(ex.getLocalizedMessage());
    }
  }
}
