package br.com.b_easy.Volley;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;


/**
 * Created by Tiago on 11/5/2015.
 */
public class JsonParser {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static JSONObject objectToJson(Object object){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
            Log.d("JSON_OBJECT", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



}
