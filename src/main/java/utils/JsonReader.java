package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReader {

    /**
     * Reads a JSON array of objects and converts it into a 2D Object array
     * compatible with TestNG's @DataProvider.
     *
     * @param filePath Path to the JSON file relative to the project root.
     * @return 2D Object array containing Map<String, String> of test data.
     */
    public static Object[][] getJsonDataAsDataProvider(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> dataList;
        
        try {
            dataList = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, String>>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON test data from: " + filePath, e);
        }

        Object[][] dataProvider = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            dataProvider[i][0] = dataList.get(i);
        }
        
        return dataProvider;
    }
}
