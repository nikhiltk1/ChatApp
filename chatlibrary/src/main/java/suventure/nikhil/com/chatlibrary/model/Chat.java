package suventure.nikhil.com.chatlibrary.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

import suventure.nikhil.com.chatlibrary.SFetcher;
import suventure.nikhil.com.chatlibrary.SJsonParser;

/**
 * Created by suventure on 3/4/17.
 */

public class Chat {

    private String number;
    private String name;

    private static final String TAG="Chat";


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  ArrayList<Chat> getAllChats(Context ctx)
    {
        SJsonParser parser=new SJsonParser();
        JSONArray jsonArray;
        jsonArray=new SFetcher().getItemForNewsList();
        Log.v(TAG,"JsnArray : "+jsonArray);
        return parser.getItemInfos(jsonArray);
    }


}
