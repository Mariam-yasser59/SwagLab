package utilies;

import generateReportTest.GenerateReportTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigHandler {
    private static final Logger logger =
            LogManager.getLogger(ConfigHandler.class);
    Properties properties;
    public ConfigHandler(String filePath){
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            logger.debug("Properties file loaded");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.debug("Properties file loading failed");
        }
    }

    public String getValue(String key){
        logger.debug("Getting value for key: " + key);
        return properties.getProperty(key);
    }
}
