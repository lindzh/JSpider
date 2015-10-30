package net.javacoding.jspider.core.util.http;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.javacoding.jspider.api.model.HTTPHeader;

import org.apache.http.Header;

/**
 * $Id: HTTPHeaderUtil.java,v 1.2 2003/03/08 19:52:02 vanrogu Exp $
 */
public class HTTPHeaderUtil {
	
	public static HTTPHeader[] getHeaders ( List<Header> headers ){
        ArrayList arrayList = new ArrayList( );

        String headerKey = null;
        String headerValue = null;
        for(Header header:headers){
        	arrayList.add(new HTTPHeader(header.getName(), header.getValue()));
        }
        return (HTTPHeader[]) arrayList.toArray(new HTTPHeader[arrayList.size()]);
	}

    public static HTTPHeader[] getHeaders ( URLConnection connection ) {
        ArrayList arrayList = new ArrayList( );

        String headerKey = null;
        String headerValue = null;

        int i = 0;

        headerKey = connection.getHeaderFieldKey(i);
        headerValue = connection.getHeaderField(i);
        while ( headerKey != null || headerValue != null  ) {
            HTTPHeader header= new HTTPHeader(headerKey,  headerValue );
            arrayList.add(header);
            i++;
            headerKey = connection.getHeaderFieldKey(i);
            headerValue = connection.getHeaderField(i);
        }

        return (HTTPHeader[]) arrayList.toArray(new HTTPHeader[arrayList.size()]);
    }

}
