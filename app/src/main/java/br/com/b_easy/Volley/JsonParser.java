package br.com.b_easy.Volley;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.b_easy.Client.Subject;
import br.com.b_easy.Client.Task;
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

    public static JSONObject taskToJson(Task task){
        JSONObject jTask = null;
        JSONObject jSubject = null;
        JSONObject jObject = null;
        jTask = new JSONObject();
        jSubject = new JSONObject();
        jObject = new JSONObject();
        try {
            if(task.getId() != null)jTask.put("id", task.getId());
            jSubject.put("id",task.getSubject().getId());
            jTask.put("title", task.getTitle());
            jTask.put("description", task.getDescription());
            jTask.put("status", task.getStatus());
            jTask.put("subject", jSubject);
            jObject.put("task", jTask);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON_OBJECT", jObject.toString());
        return jObject;
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

    public static Task JsontoTask(JSONObject json){
        Task t = null;
        try {
            Log.d("json", json.get("task").toString());
            t = gson.fromJson(json.get("task").toString(),Task.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return t;
    }

    public static ArrayList<Task> JsontoListTask(JSONObject json){
        ArrayList<Task> tasks = new ArrayList();
        try {
            JSONArray array = json.getJSONArray("list");
            for(int i=0;i<array.length();i++){
                Task t = gson.fromJson(array.get(i).toString(), Task.class);
                tasks.add(t);
            }
            for(Task t : tasks){
                Log.d("json", "Task Id: " + t.getId() + " Task Title: " + t.getTitle() + " Task Status: " + t.getStatus());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tasks;
    }



}
