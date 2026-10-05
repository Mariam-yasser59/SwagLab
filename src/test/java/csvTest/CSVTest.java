package csvTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilies.CSVFileManager;

import java.util.List;
import java.util.Map;

public class CSVTest {

    private static final Logger logger =
            LogManager.getLogger(CSVTest.class);

    @Test
    public void findMostFrequentWords() {

        logger.info("Starting CSV frequency test");

        String filePath = "src/main/resources/words.csv";

        Map<String, List<String>> result =
                CSVFileManager.getMostFrequentWords(filePath);

        logger.info("CSV file processed successfully");

        for (Map.Entry<String, List<String>> entry : result.entrySet()) {

            logger.info(
                    "Column: {} | Most frequent words: {}",
                    entry.getKey(),
                    entry.getValue()
            );

            System.out.println(
                    entry.getKey() + " -> " + entry.getValue()
            );
        }

        Assert.assertFalse(
                result.isEmpty(),
                "CSV result should not be empty"
        );

        logger.info("CSV frequency test completed successfully");
    }
}
