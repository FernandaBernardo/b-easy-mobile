package br.com.b_easy.Volley;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.b_easy.Application.ApplicationClass;
import br.com.b_easy.Preferences;


public class Request {
	
	public static void getDataString(String url, Listener<String> success, ErrorListener error) {
		makeRequest(null, Method.GET, url, success, error);
	}
	
	public static void postDataJson(String url, JSONObject json, Listener<JSONObject> success, ErrorListener error) {
		makePost(url, Method.POST, json, success, error);
	}
	
	public static void postDataString(String url, Map<String, String> params, Listener<String> success, ErrorListener error) {
		makeRequest(params, Method.POST, url, success, error);
	}
	
	public static void updateDataJson(String url, JSONObject json, Listener<JSONObject> success, ErrorListener error) {
		makePost(url, Method.PUT, json, success, error);
	}
	
	public static void deleteDataJson(String url, Listener<String> success, ErrorListener error) {
		makeRequest(null, Method.DELETE, url, success, error);
	}

	private static void makeRequest(final Map<String, String> params, int method, String url, Listener<String> success, ErrorListener error) {
		url = setupUrl(url);

        Log.d("JSON_OBJECT", "connecting to: " + url);
		
		StringRequest request = new StringRequest(method, url, success, error) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> params = new HashMap<String, String>();
				String creds = String.format("%s:%s", Preferences.USER_TOMCAT_AUTH, Preferences.PASSWORD_TOMCAT_AUTH);
				String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
				params.put("Authorization", auth);
				return params;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(30000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		ApplicationClass.getInstance().addToRequestQueue(request);
	}
	
	private static void makePost(String url, int method, JSONObject json, Listener<JSONObject> listener, ErrorListener errorListener) {
		url = setupUrl(url);

        Log.d("JSON_OBJECT", "connecting to: " + url);
		
		JsonObjectRequest request = new JsonObjectRequest(method, url, json, listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> params = new HashMap<>(super.getHeaders());
				String creds = String.format("%s:%s", Preferences.USER_TOMCAT_AUTH, Preferences.PASSWORD_TOMCAT_AUTH);
				String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
				params.put("Authorization", auth);
				return params;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		ApplicationClass.getInstance().addToRequestQueue(request);
	}
	
	private static String setupUrl(String url) {
		return Preferences.SERVER_URL + url;
	}

	public static Response.ErrorListener getListenerCadastroError(){
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Log.d("JSON_OBJECT","REQUISITION Error " + volleyError.getMessage());
			}
		};
	}
}
