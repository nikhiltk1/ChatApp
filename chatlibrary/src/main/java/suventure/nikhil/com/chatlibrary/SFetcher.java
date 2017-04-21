package suventure.nikhil.com.chatlibrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhil on 27/4/16.
 */
public class SFetcher {


    public JSONArray getItemForNewsList() {
        JSONArray jsonArrayItems=new JSONArray();
        try {
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));
            jsonArrayItems.put( new JSONObject("{\"name\" : \"Nikhil\" , \"number\" : \" 9633969962 \"}"));


        } catch (JSONException e) {

            e.printStackTrace();
        }
        return jsonArrayItems;
    }

}
