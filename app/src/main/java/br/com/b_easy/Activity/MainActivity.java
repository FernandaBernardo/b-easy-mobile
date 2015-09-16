package br.com.b_easy.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.b_easy.Fragment.HomeFragment;
import br.com.b_easy.Fragment.TaskFragment;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.R;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener{

    private HelpLiveo mHelpLiveo;
    private FloatingActionButton faButton;
    private List<Subject> subjects;
    private int INITIAL_INDEX_TASKS;
    private int FINAL_INDEX_TASKS;

    private final String TAG_HOME = "HOME";
    private final String TAG_TASK = "TASK";
    private final String SUBJECT_KEY = "NEW_SUBJECT";

    private Subject selectedSubject;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d("FIRST", "onCreateFirst");
        super.setChildLayoutRes(R.layout.activity_main);
        super.setChildToolbarRes(R.id.app_bar);
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            trocaFragment(TAG_HOME);
        }

         /***************************************
         *       Seta Float Action Button       *
         ***************************************/

        faButton = (FloatingActionButton) findViewById(R.id.fabMain);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjects.add(new Subject("Teste"));
                Toast.makeText(MainActivity.this, "Number of Subjects: " + subjects.size(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                i.putExtra(SUBJECT_KEY,new Subject("Teste"));
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }
        });

        INITIAL_INDEX_TASKS = 2;
        FINAL_INDEX_TASKS = INITIAL_INDEX_TASKS + subjects.size() -1 ;

    }

    /****************************************
     *        Inicializa Menu Lateral       *
     ***************************************/


    public void onInt(Bundle savedInstanceState) {

        Log.d("FIRST", "onIntFirst");
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

        subjects = new ArrayList<Subject>(){
            {
                add(new Subject(getString(R.string.drawer_pag_IA)));
                add(new Subject(getString(R.string.drawer_pag_BD)));
                add(new Subject(getString(R.string.drawer_pag_MD)));
                add(new Subject(getString(R.string.drawer_pag_SO)));
                add(new Subject(getString(R.string.drawer_pag_Calc)));
            }
        };

        Subject s = (Subject) getIntent().getSerializableExtra(SUBJECT_KEY);
        if(s != null){
            Log.d("PARSE", s.getName());
            subjects.add(s);
        }

        for(Subject aux : subjects){
            mHelpLiveo.addColor(aux.getName(), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        }

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

        if(position == 0)
            trocaFragment(TAG_HOME);
        else if(position == 1)
            return;
        else if( !subjects.isEmpty() && position >= INITIAL_INDEX_TASKS && position <= FINAL_INDEX_TASKS){
            setSelectedSubject(subjects.get(getSubjectIndexOnList(position)));
            trocaFragment(TAG_TASK);
        }
        else{
            // Fragment About
        }

        super.setCurrentPosition(position);
        super.setCheckedItemNavigation(position,true);

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

        if(tag.equals(TAG_HOME)) {
            fragment = new HomeFragment();
        }
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

    public Subject getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(Subject selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public int getSubjectIndexOnList(int position){
        return position - INITIAL_INDEX_TASKS;
    }
}
