package br.com.b_easy.Fragment;


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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.b_easy.R;


/**
 * Created by Tiago on 10/27/2015.
 */
public class FragmentCadastro extends Fragment implements DatePickerDialog.OnDateSetListener{

    private final String STATE_NASCIMENTO = "NASCIMENTO";

    private EditText etNome, etSobrenome, etEmail, etSenha, etSenhaConfirmacao;
    private TextView tvErroNome, tvErroSobrenome, tvErroEmail, tvErroSenha, tvErroSenhaConfirmacao, tvNascimento, tvErroNascimento, tvCriarConta;
    private RadioButton rbMasculino, rbFeminino;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastro, null);
        Bundle args = this.getArguments();

        etNome = (EditText) v.findViewById(R.id.etNomeFragmentCadastro);
        etSobrenome = (EditText) v.findViewById(R.id.etSobrenomeFragmentCadastro);
        etEmail = (EditText) v.findViewById(R.id.etEmailFragmentCadastro);
        etSenha = (EditText) v.findViewById(R.id.etSenhaFragmentCadastro);
        etSenhaConfirmacao = (EditText) v.findViewById(R.id.etConfirmacaoSenhaFragmentCadastro);
        tvNascimento = (TextView) v.findViewById(R.id.tvNascimentoFragmentCadastro);
        tvCriarConta = (TextView) v.findViewById(R.id.tvCriarContaFragmentCadastro);
        tvErroNome = (TextView) v.findViewById(R.id.tvNomeErroFragmentCadastro);
        tvErroSobrenome = (TextView) v.findViewById(R.id.tvSobrenomeErroFragmentCadastro);
        tvErroEmail = (TextView) v.findViewById(R.id.tvEmailErroFragmentCadastro);
        tvErroSenha = (TextView) v.findViewById(R.id.tvSenhaErroFragmentCadastro);
        tvErroSenhaConfirmacao = (TextView) v.findViewById(R.id.tvConfirmacaoSenhaErroFragmentCadastro);
        tvErroNascimento = (TextView) v.findViewById(R.id.tvNascimentoErroFragmentCadastro);
        rbMasculino = (RadioButton) v.findViewById(R.id.radioMasculinoSexoFragmentCadastro);
        rbFeminino = (RadioButton) v.findViewById(R.id.radioFemininoSexoFragmentCadastro);

        tvNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Log.d("DatePicker","Onclick");
                DatePickerDialog dpd = DatePickerDialog.newInstance(FragmentCadastro.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

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
                String sNascimento = tvNascimento.getText().toString();
                Date nascimento = null;

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


                if(sNascimento.trim().length() == 0 ){
                    erro = true;
                    tvErroNascimento.setVisibility(View.VISIBLE);
                }
                else{
                    tvErroNascimento.setVisibility(View.INVISIBLE);
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                        nascimento = formatter.parse(sNascimento);
                    } catch (ParseException e) {
                        erro = true;
                        tvErroNascimento.setVisibility(View.VISIBLE);
                    }
                }


                if(rbMasculino.isChecked()){
                    masculino = true;
                    Log.d("Sexo","Masculino");
                }
                else{
                    masculino = false;
                    Log.d("Sexo","Feminino");
                }


                if(!erro){
                    //TODO REQUEST SERVIDOR CRIAR CONTA
                    //TODO SAVE AT DATABASE
                }
            }
        });

        if(args != null && args.containsKey(FragmentLogin.BUNDLE_USER)){
            /*Usuario user = (Usuario) args.getSerializable(FragmentLogin.BUNDLE_USER);
            String[] nomes = user.getNome().split(" ");

            etNome.setText(nomes[0]);
            if(nomes.length > 1){
                etSobrenome.setText(nomes[nomes.length - 1]);
            }
            etEmail.setText(user.getEmail());
            tvNascimento.setText(user.getNascimento());

            if(user.getSexo().equals("masculino")){
                rbMasculino.setChecked(true);
                rbFeminino.setChecked(false);
            }
            else{
                rbFeminino.setChecked(true);
                rbMasculino.setChecked(false);
            }*/

        }

        if(savedInstanceState != null){
            tvNascimento.setText(savedInstanceState.getString(STATE_NASCIMENTO));
        }


        return v;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(tvNascimento != null){
            monthOfYear++;
            tvNascimento.setText( (dayOfMonth < 10 ?  "0" + dayOfMonth : dayOfMonth) + "/" + (monthOfYear < 10 ?  "0" + monthOfYear : monthOfYear) + "/" + year);
            tvErroNascimento.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_NASCIMENTO, tvNascimento.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
