package net.javacoding.jspider.core.task.work;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.javacoding.jspider.api.model.HTTPHeader;
import net.javacoding.jspider.api.model.Site;
import net.javacoding.jspider.core.SpiderContext;
import net.javacoding.jspider.core.event.CoreEvent;
import net.javacoding.jspider.core.event.impl.URLFoundEvent;
import net.javacoding.jspider.core.event.impl.URLSpideredErrorEvent;
import net.javacoding.jspider.core.event.impl.URLSpideredOkEvent;
import net.javacoding.jspider.core.logging.LogFactory;
import net.javacoding.jspider.core.task.WorkerTask;
import net.javacoding.jspider.core.util.URLUtil;
import net.javacoding.jspider.core.util.http.HTTPHeaderUtil;
import net.javacoding.jspider.core.util.http.HttpComponent;
import net.javacoding.jspider.core.util.http.HttpResponseMeta;

public class SpiderHttpClientUrlTask  extends BaseWorkerTaskImpl{
	
    protected URL url;
    protected Site site;
    
    private Logger logger = Logger.getLogger(SpiderHttpClientUrlTask.class);

    public SpiderHttpClientUrlTask(SpiderContext context, URL url, Site site) {
        super(context, WorkerTask.WORKERTASK_SPIDERTASK);
        this.url = url;
        this.site = site;
    }

    public void prepare() {
        context.throttle(site);
    }

    public void execute() {
        CoreEvent event = null;
        int httpStatus = 0;
        HTTPHeader[] headers = null;

        try {
        	
        	HashMap<String, String> requestHeaders = new HashMap<String,String>();
//        	requestHeaders.put("User-Agent", site.getUserAgent());
        	requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
//        	context.preHandle(requestHeaders, site);
        	
            long start = System.currentTimeMillis();
            logger.info("start fetching:"+url.toString());
            HttpResponseMeta response = HttpComponent.httpLongGet(url.toString(), requestHeaders, null);
            logger.info("fetched :"+url.toString());
			if (response != null) {
				httpStatus = response.getStatusCode();
				switch (httpStatus) {
				case HttpURLConnection.HTTP_MOVED_PERM:
				case HttpURLConnection.HTTP_MOVED_TEMP:
					String redirectURL = response.getHeader("location");
					notifyEvent(url, new URLFoundEvent(context, url, URLUtil.normalize(new URL(redirectURL))));
					break;
				case HttpURLConnection.HTTP_NOT_FOUND:
		            headers = HTTPHeaderUtil.getHeaders(response.getHeaders());
		            FileNotFoundException exception = new FileNotFoundException(url.toString());
		            event = new URLSpideredErrorEvent(context, url, 404, null, headers, exception);
		            notifyEvent(url, event);
		            break;
				default:
					break;
				}
			}
			if(httpStatus!=HttpURLConnection.HTTP_NOT_FOUND){
	            String contentType = response.getContentType();
	            int timeMs = (int) (System.currentTimeMillis() - start);
	            headers = HTTPHeaderUtil.getHeaders(response.getHeaders());
	            if (httpStatus >= 200 && httpStatus < 303) {
	            	byte[] responseAsBytes = response.getResponseAsBytes();
	                event = new URLSpideredOkEvent(context, url, httpStatus, null, contentType, timeMs, responseAsBytes.length, responseAsBytes, headers);
	            } else {
	                event = new URLSpideredErrorEvent(context, url, httpStatus, null, headers, null);
	            }
	            context.postHandle(response, site);
			}
        }catch (Exception e) {
            LogFactory.getLog(this.getClass()).error("exception during spidering", e);
            event = new URLSpideredErrorEvent(context, url, httpStatus, null, headers, e);
        } finally {
            notifyEvent(url, event);
        }
    }
}
