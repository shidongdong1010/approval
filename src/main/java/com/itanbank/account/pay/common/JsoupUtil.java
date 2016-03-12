package com.itanbank.account.pay.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Jsoup工具类
 * @author admin
 *
 */
public class JsoupUtil {
    
    /**
     * 获取返回结果
     * @param html html字符串
     * @return
     * @throws Exception 
     */
    public static Map<String, String> getResult(String html) throws Exception{
    	Document doc = null;
    	Map<String, String> m = new HashMap<String, String>();
		doc = Jsoup.parse(html);
		String content = doc.getElementsByTag("META").get(0).attr("content").toString();
    	System.out.println(content);
		String[] contextArray = content.split("&");
		for (String field : contextArray) {
			String[] signArray = field.split("sign=");
			if(signArray.length==2){
				m.put("sign", signArray[1]);
			}else{
				String[] fieldArray = field.split("=");
				m.put(fieldArray[0], field.substring(fieldArray[0].length()+1));
			}
		}
		return m;
    }
}