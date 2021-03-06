package cn.mauth.ccrm.weixin.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 请求校验工具类
 * 
 * @author 捷微团队
 * @date 2013-05-18
 */
public class WeixinSignUtil {
	private static final Logger log= LoggerFactory.getLogger(WeixinSignUtil.class);
	// 与接口配置信息中的Token要一致
	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String token,String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = getRandomStr();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        log.info(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
	/**
	 * 获取微信前段配置
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static WeixinCommon getWeixin(String jsapi_ticket, String url,String appId){
		WeixinCommon weixin=new WeixinCommon();
		Map<String, String> sign = WeixinSignUtil.sign(jsapi_ticket, url);
		log.info("jsapi_ticket:");
		weixin.setAppId(appId); 
		weixin.setNonceStr(sign.get("nonceStr"));
		weixin.setSignature(sign.get("signature"));
		weixin.setTimestamp(sign.get("timestamp"));
		log.info("jsapi_ticket:"+jsapi_ticket+"URL:"+url+"nonceStr:"+sign.get("nonceStr")+":signature:"+sign.get("signature")+"timestamp:"+sign.get("timestamp"));
		return weixin;
	}
	/**
	 * 获取微信前段配置
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static WeixinCommon getWeixinQywx(String appId,String jsapi_ticket, String url){
		WeixinCommon weixin=new WeixinCommon();
		Map<String, String> sign = WeixinSignUtil.sign(jsapi_ticket, url);
		log.info("jsapi_ticket:");
		weixin.setAppId(appId); 
		weixin.setNonceStr(sign.get("nonceStr"));
		weixin.setSignature(sign.get("signature"));
		weixin.setTimestamp(sign.get("timestamp"));
		log.info("jsapi_ticket:"+jsapi_ticket+"URL:"+url+"nonceStr:"+sign.get("nonceStr")+":signature:"+sign.get("signature")+"timestamp:"+sign.get("timestamp"));
		return weixin;
	}
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
 // 随机生成16位字符串
  	public static String getRandomStr() {
  			String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  			Random random = new Random();
  			StringBuffer sb = new StringBuffer();
  			for (int i = 0; i < 16; i++) {
  				int number = random.nextInt(base.length());
  				sb.append(base.charAt(number));
  			}
  			return sb.toString();
  	}

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
