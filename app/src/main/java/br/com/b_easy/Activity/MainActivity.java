package br.com.b_easy.Activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import br.com.b_easy.Fragment.HomeFragment;
import br.com.b_easy.Fragment.TaskFragment;
import br.com.b_easy.R;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener{

    private HelpLiveo mHelpLiveo;
    private FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.setChildActivity(this);
        super.setChildLayoutRes(R.layout.activity_main);
        super.setChildToolbarRes(R.id.app_bar);
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            trocaFragment("home");
        }

        /****************************************
         *       Seta Float Action Button       *
         ***************************************/

        faButton = (FloatingActionButton) findViewById(R.id.fabMain);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click Float Action Button ", Toast.LENGTH_SHORT).show();
            }
        });



    }

    /****************************************
     *        Inicializa Menu Lateral       *
     ***************************************/


    public void onInt(Bundle savedInstanceState) {

        // User Information
        this.userName.setText("Tiago Missão");
        this.userEmail.setText("t.missao@gmail.com");
        this.userPhoto.setImageResource(R.mipmap.ic_tiago);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.addColor(getString(R.string.drawer_pag_inicial), R.drawable.ic_home_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addSubHeader(getString(R.string.drawer_pag_tarefas));

        /****************************************
         *        Pegar do Banco de Dados       *
         ***************************************/

        mHelpLiveo.addColor(getString(R.string.drawer_pag_IA), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addColor(getString(R.string.drawer_pag_BD), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addColor(getString(R.string.drawer_pag_SO), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addColor(getString(R.string.drawer_pag_MD), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addColor(getString(R.string.drawer_pag_Calc), R.drawable.ic_navigate_next_white_24dp,  R.color.primaryTextColor);

        mHelpLiveo.addSeparator();

        mHelpLiveo.addColor(getString(R.string.drawer_pag_about), R.drawable.ic_info_white_24dp, R.color.primaryTextColor);

        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        int startPosition = 0;

        with(this).startingPosition(startPosition) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorItemDefault(R.color.primaryTextColor)
                .removeFooter()
                .colorItemSelected(R.color.accentColor)
                .colorLineSeparator(R.color.dividerColor)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .build();

        super.setCurrentPosition(startPosition);
        super.setCheckedItemNavigation(startPosition,true);

    }

    /****************************************
     *          Click Menu Lateral          *
     ***************************************/

    @Override
    public void onItemClick(int position) {
        Log.d("Item Clicked", "Position " + position);

        super.setCurrentPosition(position);
        super.setCheckedItemNavigation(position,true);

        if(position == 0)
            trocaFragment("home");
        else
            trocaFragment("task");
    }

    /****************************************
     *      Click Menu Lateral Foto        *
     ***************************************/

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

    /****************************************
     *           Troca Fragments            *
     ***************************************/

    public void trocaFragment(String tag){
        Fragment fragment;

        if(tag.equals("home"))
            fragment = new HomeFragment();
        else{
            fragment = new TaskFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container,fragment,tag).commit();

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
