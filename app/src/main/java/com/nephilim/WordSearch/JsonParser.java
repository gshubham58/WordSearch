package com.nephilim.WordSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 05-Mar-17.
 */

public class JsonParser  {
    public  static model parsefeed(String content){
        try {
            JSONObject obj1=new JSONObject(content);
            JSONArray ar1 =obj1.getJSONArray("results");
            JSONObject obj2=ar1.getJSONObject(0);
            JSONArray ar2=obj2.getJSONArray("lexicalEntries");
            JSONObject obj3=ar2.getJSONObject(0);
            JSONArray ar3=obj3.getJSONArray("entries");
            JSONObject obj4=ar3.getJSONObject(0);
            JSONArray ar4=obj4.getJSONArray("senses");
            JSONObject obj5=ar4.getJSONObject(0);
            JSONArray def = obj5.getJSONArray("definitions");
            String defini=def.getString(0);
          JSONArray exa=obj5.getJSONArray("examples");
            JSONObject obj6=exa.getJSONObject(0);
            String tex=obj6.getString("text");
            model newmodel=new model();
            newmodel.setDefinitions(defini);
           newmodel.setExamples(tex);
            return newmodel;
        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
