package br.com.b_easy.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.b_easy.Fragment.FragmentCadastro;
import br.com.b_easy.Fragment.FragmentLogin;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/27/2015.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG_LOGIN = "LOGIN";
    public static final String TAG_CADASTRO = "CADASTRO";
    public static final String LogClass = "LoginActivity";

    public final String STATE_FRAGMENT = "ATUAL_FRAGMENT";

    private String atualTag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if(savedInstanceState == null) {
            trocaFragment(TAG_LOGIN, null);
        }
        else{
            atualTag = savedInstanceState.getString(STATE_FRAGMENT);
        }

    }

    public void trocaFragment(String tag, Bundle bundle) {

        Fragment fragment;
        if(tag.equals(TAG_LOGIN)) {
            atualTag = TAG_LOGIN;
            fragment = new FragmentLogin();
        }
        else {
            atualTag = TAG_CADASTRO;
            fragment = new FragmentCadastro();
        }
        if(bundle != null)
            fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.flContainerLoginActivity, fragment, tag).commit();
    }

    @Override
    public void onBackPressed() {
        if(atualTag == TAG_CADASTRO) {
            trocaFragment(TAG_LOGIN, null);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LogClass, "OnActivityResult Activity");
        getSupportFragmentManager().findFragmentByTag(atualTag).onActivityResult(requestCode, resultCode, data);;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_FRAGMENT, atualTag);
        super.onSaveInstanceState(outState);
    }
}
