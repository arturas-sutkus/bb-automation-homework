package api.helpers;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;

public class JsonHelper {
    public static void removeFieldValues(JSONObject jsonObject) {
        try {
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                jsonObject.put(key, JSONObject.NULL);
            }
        } catch (JSONException e) {
            throw new RuntimeException("Error while removing field values from JSON", e);
        }
    }
}
