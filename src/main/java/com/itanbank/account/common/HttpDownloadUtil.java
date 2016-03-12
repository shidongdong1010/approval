package com.itanbank.account.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpDownloadUtil {
	public static final int cache = 10 * 1024;  
    /** 
     * 根据url下载文件，保存到filepath中 
     * @param url 
     * @param filepath 
     * @return 
     */  
    public static boolean download(String url, String filepath,String fileName) { 
    	boolean flag = false;
        try {
            HttpClient client = new DefaultHttpClient();  
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response = client.execute(httpget);  
            HttpEntity entity = response.getEntity();  
            InputStream is = entity.getContent();  
            File file = new File(filepath);
            if(!file.exists()){
            	file.mkdirs();
            }
            File downFile = new File(filepath+File.separator+fileName);
            FileOutputStream fileout = new FileOutputStream(downFile);  
            /** 
             * 根据实际运行效果 设置缓冲区大小 
             */  
            byte[] buffer=new byte[cache];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            is.close();  
            fileout.flush();  
            fileout.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return flag;  
    }  
}
