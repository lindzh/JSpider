package net.javacoding.jspider;

import java.util.HashMap;

import net.javacoding.jspider.core.util.http.HttpComponent;
import net.javacoding.jspider.core.util.http.HttpResponseMeta;

public class HttpClientTest {
	
	
	public static void test(String url){
		long start = System.currentTimeMillis();
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
		HttpResponseMeta resp = HttpComponent.httpGet(url, headers, null);
		long end = System.currentTimeMillis();
		System.out.println("httpclient "+url+" cost:"+(end-start)+" code:"+resp.getStatusCode());
	}
//		²âÊÔ½á¹û
//	    httpclient http://baike.baidu.com/ cost:972 code:200
//		httpclient http://baike.baidu.com/ cost:997 code:200
//		httpclient http://baike.baidu.com/ cost:1200 code:200
//		httpclient http://baike.baidu.com/ cost:1036 code:200
//		httpclient http://baike.baidu.com/ cost:1036 code:200
	
	public static void main(String[] args) {
		HttpClientTest.test("http://www.oschina.net/");
	}

}
