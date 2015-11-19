package br.com.b_easy.Fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Client.User;
import br.com.b_easy.DAO.UserDao;
import br.com.b_easy.DataBaseModel.UserBD;
import br.com.b_easy.Preferences;
import br.com.b_easy.R;
import br.com.b_easy.Util;
import br.com.b_easy.Volley.JsonParser;
import br.com.b_easy.Volley.Request;


/**
 * Created by Tiago on 10/27/2015.
 */
public class FragmentCadastro extends Fragment{

    private final String STATE_NASCIMENTO = "NASCIMENTO";

    private EditText etNome, etSobrenome, etEmail, etSenha, etSenhaConfirmacao;
    private TextView tvErroNome, tvErroSobrenome, tvErroEmail, tvErroSenha, tvErroSenhaConfirmacao, tvCriarConta;
    private MaterialDialog dialog;
    private String pictureURL;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastro, null);
        Bundle args = this.getArguments();

        etNome = (EditText) v.findViewById(R.id.etNomeFragmentCadastro);
        etSobrenome = (EditText) v.findViewById(R.id.etSobrenomeFragmentCadastro);
        etEmail = (EditText) v.findViewById(R.id.etEmailFragmentCadastro);
        etSenha = (EditText) v.findViewById(R.id.etSenhaFragmentCadastro);
        etSenhaConfirmacao = (EditText) v.findViewById(R.id.etConfirmacaoSenhaFragmentCadastro);
        tvCriarConta = (TextView) v.findViewById(R.id.tvCriarContaFragmentCadastro);
        tvErroNome = (TextView) v.findViewById(R.id.tvNomeErroFragmentCadastro);
        tvErroSobrenome = (TextView) v.findViewById(R.id.tvSobrenomeErroFragmentCadastro);
        tvErroEmail = (TextView) v.findViewById(R.id.tvEmailErroFragmentCadastro);
        tvErroSenha = (TextView) v.findViewById(R.id.tvSenhaErroFragmentCadastro);
        tvErroSenhaConfirmacao = (TextView) v.findViewById(R.id.tvConfirmacaoSenhaErroFragmentCadastro);


        tvCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean erro = false;
                boolean masculino = true;
                String nome = etNome.getText().toString();
                String sobrenome = etSobrenome.getText().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                String cSenha = etSenhaConfirmacao.getText().toString();

                if(nome.trim().length() == 0){
                    erro = true;
                    tvErroNome.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroNome.setVisibility(View.INVISIBLE);
                }


                if(sobrenome.trim().length() == 0){
                    erro = true;
                    tvErroSobrenome.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroSobrenome.setVisibility(View.INVISIBLE);
                }


                if(email.trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    erro = true;
                    tvErroEmail.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroEmail.setVisibility(View.INVISIBLE);
                }


                if(senha.trim().length() == 0){
                    erro = true;
                    tvErroSenha.setText(getResources().getString(R.string.login_erro_senha));
                    tvErroSenha.setVisibility(View.VISIBLE);
                }
                else{
                    if(!senha.equals(cSenha)){
                        erro = true;
                        tvErroSenha.setText(getResources().getString(R.string.login_erro_senhaConfirmacao));
                        tvErroSenha.setVisibility(View.VISIBLE);
                    }
                    else{
                        tvErroSenha.setVisibility(View.INVISIBLE);
                    }
                }


                if(!erro){
                    final User user = new User();
                    user.setEmail(email);
                    user.setName(nome + " " + sobrenome);
                    user.setPassword(Util.toMD5(senha));
                    user.setPictureUrl(pictureURL);
                    showProgressDialog(null, getString(R.string.conectando));
                    Request.postDataJson(getString(R.string.url_createUser), JsonParser.objectToJson(user), new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        hideProgressDialog();
                                        updateUserReferences(user);
                                        startMainActivity();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        hideProgressDialog();
                                        updateUserReferences(user);
                                        startMainActivity();
                                    }
                                });
                                showProgressDialog(null, getString(R.string.conectando));
                    }
                }
        });

        if(args != null && args.containsKey(FragmentLogin.BUNDLE_USER)){
            User user = (User) args.getSerializable(FragmentLogin.BUNDLE_USER);
            String[] nomes = user.getName().split(" ");

            etNome.setText(nomes[0]);
            if(nomes.length > 1){
                etSobrenome.setText(nomes[nomes.length - 1]);
            }
            etEmail.setText(user.getEmail());
            pictureURL = user.getPictureUrl();

        }



        return v;
    }

    private void updateUserReferences(User user){
        try {
            UserDao userDao = new UserDao(Util.openBD().getConnectionSource());
            UserBD userBD = Util.fromModelUser(user);
            if(userDao.idExists(userBD.getId())) userDao.update(userBD);
            else userDao.create(userBD);

            Preferences.getInstance().setUser(userBD);

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
        dialog = Util.buildProgressDialog(getContext(), title, content);
        dialog.show();
    }

    private void hideProgressDialog(){
        if(dialog != null)
            dialog.dismiss();
    }

}
