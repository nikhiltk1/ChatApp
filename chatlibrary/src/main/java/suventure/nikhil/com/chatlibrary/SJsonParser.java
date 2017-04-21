package suventure.nikhil.com.chatlibrary;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import suventure.nikhil.com.chatlibrary.model.Chat;

/**
 * Created by nikhil on 27/4/16.
 */
public class SJsonParser {

    public SJsonParser()
    {

    }

    public ArrayList<Chat> getItemInfos(JSONArray jsonArray) {

        ArrayList<Chat> chats;
        Type listType = new TypeToken<List<Chat>>() {}.getType();
        chats = new Gson().fromJson(jsonArray.toString(), listType);

        return chats;

    }



}