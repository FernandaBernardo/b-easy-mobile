package br.com.b_easy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.R;
import br.com.b_easy.Volley.VolleySingleton;
import br.com.b_easy.pojo.ListJson;
import br.com.b_easy.pojo.TesteJson;

/**
 * Created by Tiago on 9/12/2015.
 */
public class HomeFragment extends Fragment {

    private FloatingActionButton fabButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        fabButton = (FloatingActionButton) v.findViewById(R.id.fabHomeFragment);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddOptions();
            }
        });

        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        String url = "http://jsonplaceholder.typicode.com/posts/1";

//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //Toast.makeText(getContext(), "" + response , Toast.LENGTH_LONG ).show();
//                        Log.d("Json", response.toString());
//                        parseJSONResponse(response);
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "ERRO VOLLEY" , Toast.LENGTH_LONG ).show();
//
//                    }
//                });

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Json", response);
                parseJSONString(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Json", "ERRO");
            }
        });

    queue.add(jsObjRequest);

        return v;
    }

    private void parseJSONString(String response){
        if(response == null || response.length() == 0)
            return;

            Gson googleJson = new Gson();
            TesteJson tj = googleJson.fromJson(response, TesteJson.class);

            Log.d("Json", tj.toString());


    }


    public void createDialogAddOptions(){

        new MaterialDialog.Builder(getContext())
                .title("Selecione a Ação")
                .items( R.array.optionsFABNoSubject)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Log.d("Click", "OnSelection " + which);
                        if (which == 0) {
                            createDialogAddSubject();
                        }
                    }
                })
                .show();
    }

    public void createDialogAddSubject(){

        MaterialDialog dialog =  new MaterialDialog.Builder(getContext())
                .title("Adicionar Matéria")
                .customView(R.layout.fragment_subject_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        boolean status = false;

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).getText().toString();

                        status = ((MainActivity)getActivity()).saveSubject(new SubjectBD(sName));

                        if (status) {
                            Log.d("DataBase", "SUCESS: Saved Subject");
                            getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                        else
                            Log.e("DataBase", "ERROR: Save Subject");

                        super.onPositive(dialog);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }



}
