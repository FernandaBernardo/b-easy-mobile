package br.com.b_easy.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.b_easy.Fragment.HomeFragment;
import br.com.b_easy.R;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener{

    private Toolbar toolbar;
    private HelpLiveo mHelpLiveo;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(savedInstanceState == null){
            trocaFragment("Home");
        }

    }
    */

    public void onInt(Bundle savedInstanceState) {

        // User Information
        this.userName.setText("Tiago Missão");
        this.userEmail.setText("t.missao@gmail.com");
        this.userPhoto.setImageResource(R.mipmap.ic_tiago);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.drawer_pag_inicial), R.drawable.ic_home_white_24dp);
        mHelpLiveo.addSubHeader(getString(R.string.drawer_pag_tarefas));

        /****************************************
         *        Pegar do Banco de Dados       *
         ***************************************/

        mHelpLiveo.add(getString(R.string.drawer_pag_IA),  R.drawable.ic_navigate_next_white_24dp);
        mHelpLiveo.add(getString(R.string.drawer_pag_BD),  R.drawable.ic_navigate_next_white_24dp);
        mHelpLiveo.add(getString(R.string.drawer_pag_SO),  R.drawable.ic_navigate_next_white_24dp);
        mHelpLiveo.add(getString(R.string.drawer_pag_MD),  R.drawable.ic_navigate_next_white_24dp);
        mHelpLiveo.add(getString(R.string.drawer_pag_Calc), R.drawable.ic_navigate_next_white_24dp);

        mHelpLiveo.addSeparator();

        mHelpLiveo.add(getString(R.string.drawer_pag_about), R.drawable.ic_info_white_24dp);

        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorItemSelected(R.color.accentColor)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .removeFooter()
                .colorItemDefault(R.color.primaryTextColor)
                .colorLineSeparator(R.color.dividerColor)
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);
    }

    @Override
    public void onItemClick(int position) {
        Log.d("Item Clicked", "Position " + position);
        trocaFragment("Home");
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            closeDrawer();
        }
    };

    public void trocaFragment(String tag){
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment,tag).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
