package com.etnet.coresdk.ums;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;

public class InitWebService {

    /**
     *
     */
    private static final long serialVersionUID = 8393142255140892268L;
    public static final String TAG = "InitWebService";
    private static final String DEFAULT_CONFIGURATION = "env/local/config.properties";
    private static Properties prop = new Properties();
    private static Map<String, String> configMap;

    public static void init(){
       System.out.println("init start");
        System.out.println( "Config path: " + DEFAULT_CONFIGURATION);
        if (initAppProperities()) {
            System.out.println( "init Config success");
        } else {
            System.out.println( "init Config fail");
        }
        System.out.println( "init end");
    }

    private static boolean initAppProperities(){
        String configPath = DEFAULT_CONFIGURATION;
        InputStream input = null;
        try {
            input = getResourceInputStream(configPath);
            configMap = new HashMap<String, String>();
            prop.load(input);
            Set<Object> set = prop.keySet();
            if (set != null) {
                Iterator<Object> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String name = (String) iterator.next();
                    if (StringUtils.isNotBlank(name)) {
                        String value = prop.getProperty(name);
                        configMap.put(name, value);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println( e.toString());
            return false;
        } catch (Exception e) {
            System.out.println( e.toString());
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println( e.toString());
                }
            }
        }
    }

    public static String getConfig(String key) {
        if (configMap == null) {
            return null;
        } else {
            return configMap.get(key);
        }
    }

    private static InputStream getResourceInputStream(String strRelPath) {
        return com.etnet.coresdk.ums.InitWebService.class.getClassLoader().getResourceAsStream(strRelPath);
    }
}
