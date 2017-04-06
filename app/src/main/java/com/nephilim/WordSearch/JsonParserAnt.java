package com.nephilim.WordSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 21-Mar-17.
 */

public class JsonParserAnt {  public static model parsefeed(String content) {
    ArrayList<String> antonym = new ArrayList<>();
    try {
        JSONObject obj1 = new JSONObject(content);
        JSONArray ar1 = obj1.getJSONArray("results");
        JSONObject obj2 = ar1.getJSONObject(0);
        JSONArray ar2 = obj2.getJSONArray("lexicalEntries");
        JSONObject obj3 = ar2.getJSONObject(0);
        JSONArray ar3 = obj3.getJSONArray("entries");
        JSONObject obj4 = ar3.getJSONObject(0);
        JSONArray ar4 = obj4.getJSONArray("senses");
        JSONObject obj5 = ar4.getJSONObject(0);
        JSONArray ar5 = obj5.getJSONArray("antonyms");
        int x = ar5.length();
        JSONObject obj[] =new JSONObject[x];
        for (int i = 0; i < x; i++) {
            obj[i] = ar5.getJSONObject(i);
            antonym.add(i, obj[i].getString("text"));
        }

        /*JSONObject obj6 = ar5.getJSONObject(0);
        String ant1 = obj6.getString("text");
        antonym.add(0, ant1);
        */model newmodel = new model();
        newmodel.setAntonym(antonym);
        return newmodel;
    } catch (JSONException e) {
        e.printStackTrace();
        return null;

    }

}
}
