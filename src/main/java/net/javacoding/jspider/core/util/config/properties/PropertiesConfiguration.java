package net.javacoding.jspider.core.util.config.properties;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import net.javacoding.jspider.api.model.Site;
import net.javacoding.jspider.core.exception.JSpiderException;
import net.javacoding.jspider.core.util.config.ConfigConstants;
import net.javacoding.jspider.core.util.config.ConfigurationFactory;
import net.javacoding.jspider.core.util.config.JSpiderConfiguration;
import net.javacoding.jspider.core.util.config.PropertySet;

/**
 * $Id: PropertiesConfiguration.java,v 1.14 2003/04/10 16:19:16 vanrogu Exp $
 * 使用classpath的配置方式，放弃jspider_home的方式
 */
public class PropertiesConfiguration implements JSpiderConfiguration {

    protected PropertySet jspiderProperties;
    protected PropertySet pluginsProperties;
    protected PropertySet websitesConfig;
    protected File defaultOutputFolder;
    
    public String getClassPath(String resource){
    	URL url = this.getClass().getClassLoader().getResource("conf/"+resource);
    	try {
			File file = new File(url.toURI());
			return file.getAbsolutePath();
		} catch (URISyntaxException e) {
			throw new JSpiderException(resource,e);
		}
    }

    public PropertiesConfiguration () {
        jspiderProperties = PropertiesFilePropertySet.getInstance (this.getClassPath("jspider.properties"));
        pluginsProperties = PropertiesFilePropertySet.getInstance (this.getClassPath("plugin.properties"));
        websitesConfig = PropertiesFilePropertySet.getInstance(this.getClassPath("sites.properties"));
        
        String outputBase = jspiderProperties.getString("spider.output", null);
        defaultOutputFolder = new File (outputBase);
        System.err.println("[Engine] default output folder=" + defaultOutputFolder );
    }

    public File getDefaultOutputFolder() {
        return defaultOutputFolder;
    }

    public PropertySet getJSpiderConfiguration() {
        return jspiderProperties;
    }

    public PropertySet getPluginsConfiguration() {
        return pluginsProperties;
    }

    public PropertySet getPluginConfiguration(String pluginName) {
        return PropertiesFilePropertySet.getInstance(this.getClassPath("plugins/" +pluginName + ".properties"));
    }

    public File getPluginConfigurationFolder(String pluginName) {
    	String pluginFileName = this.getClassPath("plugins/"+pluginName);
        return new File (pluginFileName);
    }

    public PropertySet getSiteConfiguration(Site site) {
        if ( site.isBaseSite() ) {
          return ConfigurationFactory.getConfiguration().getBaseSiteConfiguration();
        } else {
          return getSiteConfiguration(site.getHost(), site.getPort());
        }
    }

    public PropertySet getSiteConfiguration(String host, int port) {
        String matchString = host + ":" + port;
        String configName = null;
        if ( port > 0 ) {
            configName = websitesConfig.getString(matchString, null);
        }
        if ( configName == null ) {
            matchString = host;
            configName = websitesConfig.getString(matchString, websitesConfig.getString(ConfigConstants.SITES_DEFAULT_SITE, "default") );
        }
        return PropertiesFilePropertySet.getInstance(this.getClassPath("sites/"+ configName + ".properties"));
    }

    public PropertySet getDefaultSiteConfiguration() {
        return PropertiesFilePropertySet.getInstance(this.getClassPath("sites/" +  websitesConfig.getString(ConfigConstants.SITES_DEFAULT_SITE, "default" ) + ".properties"));
    }

    public PropertySet getBaseSiteConfiguration() {
        return PropertiesFilePropertySet.getInstance(this.getClassPath("sites/" + websitesConfig.getString(ConfigConstants.SITES_BASE_SITE, "default" ) + ".properties"));
    }

}
