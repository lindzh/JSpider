package net.javacoding.jspider.mod.plugin.parser;

import java.net.URL;

import org.apache.log4j.Logger;

import net.javacoding.jspider.api.event.JSpiderEvent;
import net.javacoding.jspider.api.event.resource.ResourceFetchedEvent;
import net.javacoding.jspider.api.model.FetchedResource;
import net.javacoding.jspider.core.util.config.PropertySet;
import net.javacoding.jspider.spi.Plugin;

public class SimpleHtmlParserPlugin implements Plugin{
	
    public static final String MODULE_NAME = "Html parse plugin";
    public static final String MODULE_VERSION = "v1.0";
    public static final String MODULE_DESCRIPTION = "a simple html parser parse to json object";
    public static final String MODULE_VENDOR = "lindezhi html to json parse";

    private Logger logger = Logger.getLogger(SimpleHtmlParserPlugin.class);
    
    private String hello = null;
    
    public SimpleHtmlParserPlugin(PropertySet config) {
    	hello = config.getString("hello", null);
    }

	@Override
	public void initialize() {
		logger.info("started :SimpleHtmlParserPlugin hello:"+hello);
	}

	@Override
	public void shutdown() {
		
	}

	@Override
	public void notify(JSpiderEvent event) {
		if(event instanceof ResourceFetchedEvent){
			ResourceFetchedEvent ev = (ResourceFetchedEvent)event;
			URL url = ev.getURL();
			FetchedResource fetchedResource = ev.getResource();
			logger.info("json parse html:"+url+" mime:"+fetchedResource.getMime());
		}
	}

	@Override
	public String getName() {
		return MODULE_NAME;
	}

	@Override
	public String getVersion() {
		return MODULE_VERSION;
	}

	@Override
	public String getDescription() {
		return MODULE_DESCRIPTION;
	}

	@Override
	public String getVendor() {
		return MODULE_VENDOR;
	}
	
}
