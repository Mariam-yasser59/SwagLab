package utilies;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.json.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class JSONFileManager {
    private static final Logger logger =
            LogManager.getLogger(JSONFileManager.class);
    public LinkedHashMap<String, Object> data;

    public JSONFileManager(String filePath) {
        try{
            logger.info("Loading JSON file: " + filePath);
            Type t = new TypeToken<LinkedHashMap<String, Object>>(){}.getType();
            data = new Gson().fromJson(new FileReader(filePath), t);
            logger.info("JSON file loaded");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("JSON file loading failed");
        }
    }

    public Object getValue(String key){
        logger.debug("Getting value for key: " + key);
        return data.get(key);
    }
}
