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
import br.com.b_easy.Client.Subject;
import br.com.b_easy.Client.User;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.Preferences;
import br.com.b_easy.R;
import br.com.b_easy.Volley.JsonParser;
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
                .title("Adicionar MatériaX")
                .customView(R.layout.fragment_subject_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        boolean status = false;

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).getText().toString();

                        if (sName == null || sName.trim().equals("")) {
                            Toast.makeText(getContext(), "Invalid Subject Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            Subject s = new Subject();
                            s.setName(sName);
                            User user = new User();
                            user.setEmail(Preferences.getInstance().getUser().getEmail());
                            s.setUser(user);
                            br.com.b_easy.Volley.Request.postDataJson(getString(R.string.url_createSubject), JsonParser.objectToJson(s), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("volleySubject", "response" + response);
                                    Subject subject = JsonParser.JsontoSubject(response);
                                    SubjectBD sbd = new SubjectBD(subject.getName());
                                    sbd.setIdGlobal(subject.getId());
                                    boolean status = ((MainActivity) getActivity()).saveSubject(sbd);

                                    if (status) {
                                        Log.d("DataBase", "SUCCESS: Saved Subject");
                                        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                        getActivity().finish();
                                    } else
                                        Log.e("DataBase", "ERROR: Save Subject");


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("volleySubject", "error" + error.toString());
                                }
                            });

                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }



}
