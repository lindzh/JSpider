package net.javacoding.jspider;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.javacoding.jspider.core.logging.LogFactory;
import net.javacoding.jspider.core.task.work.SpiderHttpJdkURLTask;

public class UrlConnectionTest {

	public static void test(String uri) throws IOException{
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
        connection.setDefaultUseCaches(false);
        connection.setUseCaches(false);
        connection.setRequestProperty("Cache-Control","max-age=0,no-cache");
        connection.setRequestProperty("Pragma","no-cache");
        connection.setDoInput(true);
        long start = System.currentTimeMillis();
        connection.connect();
        BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = new BufferedInputStream(inputStream);
        int size = 0;
        try {
                int i = is.read();
                while (i != -1) {
                    size++;
                    os.write(i);
                    i = is.read();
                }
        } catch (IOException e) {
            LogFactory.getLog(SpiderHttpJdkURLTask.class).error("i/o exception during fetch",e);
        }
        is.close();
        long end = System.currentTimeMillis();
        System.out.println("jdk urlconnection "+url+" cost:"+(end-start));
	}
//	²âÊÔ½á¹û
//	jdk urlconnection http://baike.baidu.com/ cost:254
//	blocked
	
	
	public static void main(String[] args) throws IOException {
		UrlConnectionTest.test("http://baike.baidu.com/");
	}
	
}
