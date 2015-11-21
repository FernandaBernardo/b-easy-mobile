package br.com.b_easy.Volley;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import br.com.b_easy.Client.Subject;
import br.com.b_easy.Client.User;


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


    public static User JsontoUser(JSONObject json){
        User user = null;
        try {
            Log.d("json", json.get("user").toString());
            user = gson.fromJson(json.get("user").toString(),User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static Subject JsontoSubject(JSONObject json){
        Subject s = null;
        try {
            Log.d("json", json.get("subject").toString());
            s = gson.fromJson(json.get("subject").toString(),Subject.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return s;
    }



}
