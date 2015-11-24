package br.com.b_easy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Arrays;

import br.com.b_easy.Activity.LoginActivity;
import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.DAO.UserDao;
import br.com.b_easy.DataBase.DatabaseManager;
import br.com.b_easy.DataBaseModel.UserBD;
import br.com.b_easy.Preferences;
import br.com.b_easy.R;
import br.com.b_easy.Client.User;
import br.com.b_easy.Util;
import br.com.b_easy.Volley.JsonParser;
import br.com.b_easy.Volley.Request;


/**
 * Created by Tiago on 10/27/2015.
 */
public class FragmentLogin extends Fragment {

    private EditText etEmail, etSenha;
    private TextView tvEsqueceuSenha, tvCadastre, tvEntrar, tvLoginFacebook, tvErroEmail, tvErroSenha;
    private CallbackManager callbackManagerFacebook;
    private LoginButton facebookButton;
    private final String logClass = "FragmentLogin";
    private MaterialDialog dialog;

    public static final String BUNDLE_USER = "USER";



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        callbackManagerFacebook = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_login, null);

        etEmail = (EditText) v.findViewById(R.id.etEmailFragmentLogin);
        etSenha = (EditText) v.findViewById(R.id.etSenhaFragmentLogin);
        tvEsqueceuSenha = (TextView) v.findViewById(R.id.tvEsqueceuSenhaFragmentLogin);
        tvCadastre = (TextView) v.findViewById(R.id.tvCadastroFragmentLogin);
        tvEntrar = (TextView) v.findViewById(R.id.tvEntrarFragmentLogin);
        tvLoginFacebook = (TextView) v.findViewById(R.id.tvFacebookFragmentLogin);
        tvErroEmail = (TextView) v.findViewById(R.id.tvEmailErroFragmentLogin);
        tvErroSenha = (TextView) v.findViewById(R.id.tvSenhaErroFragmentLogin);
        facebookButton = new LoginButton(getContext());

        facebookButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));

        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(logClass, "Click on Esqueceu a Senha");
                // TODO RECUPERAR SENHA
            }
        });

        tvCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(logClass, "Click on Cadastre-se");
                ((LoginActivity) getActivity()).trocaFragment(LoginActivity.TAG_CADASTRO, null);
            }
        });

        tvEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(logClass, "Click on Entrar");

                boolean erro = false;
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                if(email.trim().length() == 0){
                    erro = true;
                    tvErroEmail.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroEmail.setVisibility(View.INVISIBLE);
                }

                if(senha.trim().length() == 0){
                    erro = true;
                    tvErroSenha.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroSenha.setVisibility(View.INVISIBLE);
                }

                if(!erro) {
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(senha);
                    showProgressDialog(null, getString(R.string.conectando));
                    Request.postDataJson(getString(R.string.url_login), JsonParser.objectToJson(user), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("volley", "responseXXX " + response);
                            User user = JsonParser.JsontoUser(response);
                            Log.d("json", user.getName() + "\n" + user.getEmail());
                            hideProgressDialog();
                            updateUserReferences(user);
                            startMainActivity();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgressDialog();
                        }
                    });

                }
            }
        });

        tvLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(logClass, "Click on Facebook");
                facebookButton.performClick();
            }
        });

        facebookButton.registerCallback(callbackManagerFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d(logClass,"Facebook Login " + loginResult.getAccessToken().getUserId());

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                User user = new User();
                                user.setFacebookId(loginResult.getAccessToken().getUserId());
//                                Request.postDataJson(getString(R.string.url_login), JsonParser.objectToJson(user), new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        hideProgressDialog();
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        hideProgressDialog();
//                                    }
//                                });
//                                showProgressDialog(null, getString(R.string.conectando));


                                user = decodeJsonFacebook(response);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(BUNDLE_USER, user);
                                ((LoginActivity)getActivity()).trocaFragment(LoginActivity.TAG_CADASTRO, bundle);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d(logClass,"Facebook Login Canceled");

            }

            @Override
            public void onError(FacebookException e) {
                Log.d(logClass,"Facebook Login Error: " + e.toString());
                //TODO Dialog ERROR
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(logClass, "OnActivityResult fragment");
        callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);

    }

    public User decodeJsonFacebook(GraphResponse response){
        User user = new User();

        Log.v("Facebook","response: " + response.toString());
        JSONObject info = response.getJSONObject();

        String email = null;
        String name =  null;
        String idFace = null;

        try {
            if(info.has("email")){
                email = info.getString("email");
            }

            if(info.has("name")) {
                name = info.getString("name");
            }

            idFace = info.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.setEmail(email);
        user.setName(name);
        user.setPictureUrl("https://graph.facebook.com/" + idFace + "/picture?type=normal");
        user.setFacebookId(idFace);

        return user;

    }

    private void updateUserReferences(User user){
        try {
            UserDao userDao = new UserDao(Util.openBD().getConnectionSource());
            UserBD userBD = Util.fromModelUser(user);
            Util.updateReferences(user);
//            if(userDao.idExists(userBD.getId())) userDao.update(userBD);
//            else userDao.create(userBD);

            //Preferences.getInstance().setUser(userBD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void showProgressDialog(String title, String content){
        dialog = Util.buildProgressDialog(getContext(),title,content);
        dialog.show();
    }

    private void hideProgressDialog(){
        if(dialog != null)
            dialog.dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
