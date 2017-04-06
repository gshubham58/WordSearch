package com.nephilim.WordSearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 21-Mar-17.
 */

public class JsonParserSyn {
    public static model parsefeed(String content) {
        ArrayList<String> synonym = new ArrayList<>();
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
            JSONArray ar5 = obj5.getJSONArray("synonyms");
            int x = ar5.length();
            JSONObject obj[] =new JSONObject[x];
            for (int i = 0; i < x; i++) {
                obj[i] = ar5.getJSONObject(i);
                synonym.add(i, obj[i].getString("text"));
            }
        /*JSONObject obj7 = ar5.getJSONObject(1);
            String syn2 = obj7.getString("text");
            JSONObject obj8 = ar5.getJSONObject(2);
            String syn3 = obj8.getString("text");/*
            *//*synonym.add(0, syn1);
            synonym.add(1, syn2);
            synonym.add(2, syn3);*/
            model newmodel = new model();
            newmodel.setSynonym(synonym);
            return newmodel;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

    }
}