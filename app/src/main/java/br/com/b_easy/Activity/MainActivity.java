package br.com.b_easy.Activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import br.com.b_easy.DAO.SubjectDao;
import br.com.b_easy.DAO.UserDao;
import br.com.b_easy.DAO.UserSubjectDao;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.UserBD;
import br.com.b_easy.DataBaseModel.UserSubjectBD;
import br.com.b_easy.Fragment.HomeFragment;
import br.com.b_easy.Fragment.TaskFragment;
import br.com.b_easy.R;
import br.com.b_easy.Util;
import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener{

    private HelpLiveo mHelpLiveo;
    private FloatingActionButton faButton;
    private List<SubjectBD> subjects;
    private TextView tvToolbar;
    private int INITIAL_INDEX_TASKS;
    private int FINAL_INDEX_TASKS;

    public static final String TAG_HOME = "HOME";
    public static final String TAG_TASK = "TASK";
    private String ATUAL_TAG;
    private final String SUBJECT_KEY = "NEW_SUBJECT";
    private Fragment fragment;

    private SubjectBD selectedSubject;

    private UserBD user;

    private final String STATE_CURRENT_POSITION = "currentPosition";
    private final String STATE_SELECTED_SUBJECT = "selectedSubject";
    private final String STATE_TOOLBAR_TEXT = "toolbarText";
    private final String STATE_TOOLBAR_ELEVATION = "toolbarElevation";
    private final String STATE_ATUAL_TAG = "atualTag";
    private final String STATE_ = "atualTag";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.setChildLayoutRes(R.layout.activity_main);
        super.setChildToolbarRes(R.id.app_bar);
        super.onCreate(savedInstanceState);

         /***************************************
         *       Seta Float Action Button       *
         ***************************************/


        INITIAL_INDEX_TASKS = 2;
        FINAL_INDEX_TASKS = INITIAL_INDEX_TASKS + subjects.size() -1 ;

    }

    /****************************************
     *        Inicializa Menu Lateral       *
     ***************************************/


    public void onInt(Bundle savedInstanceState) {

        tvToolbar = (TextView) findViewById(R.id.toolbar_title);

        int startPosition = 0;
        String toolbarText = "";
        float toolbarElevation = getResources().getDimension(R.dimen.md_appbar_elevation);

        Bundle saved = getIntent().getExtras();

        if(savedInstanceState != null){
            startPosition = savedInstanceState.getInt(STATE_CURRENT_POSITION);
            setSelectedSubject((SubjectBD) savedInstanceState.getSerializable(STATE_SELECTED_SUBJECT));
            tvToolbar.setText(savedInstanceState.getString(STATE_TOOLBAR_TEXT));
            toolbarElevation = savedInstanceState.getFloat(STATE_TOOLBAR_ELEVATION);
            ATUAL_TAG = savedInstanceState.getString(STATE_ATUAL_TAG);
        }
        else if(saved != null){
            Log.d("SaveInstance", "Activity Bundle NOT NULL");
            startPosition = saved.getInt(STATE_CURRENT_POSITION);
            setSelectedSubject((SubjectBD) saved.getSerializable(STATE_SELECTED_SUBJECT));
            tvToolbar.setText(saved.getString(STATE_TOOLBAR_TEXT));
            toolbarElevation = saved.getFloat(STATE_TOOLBAR_ELEVATION);
            ATUAL_TAG = saved.getString(STATE_ATUAL_TAG);
            if(saved.getBoolean(TaskFragment.STATE_NEW_ACTIVITY))
                trocaFragment(ATUAL_TAG,saved);
        }
        else{
            Log.d("SaveInstance", "Activity Bundle NULL");
            trocaFragment(TAG_HOME, null);
        }

        Log.d("FIRST", "onIntFirst");
        loadDatabase();

        Log.d("SaveInstance", "Saved IS " + (savedInstanceState == null ? "NULL" : "NOT NULL"));

        this.userName.setText(user.getName());
        this.userEmail.setText(user.getEmail());
        this.userPhoto.setImageResource(R.mipmap.ic_tiago);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.addColor(getString(R.string.drawer_pag_inicial), R.drawable.ic_home_white_24dp, R.color.primaryTextColor);
        mHelpLiveo.addSubHeader(getString(R.string.drawer_pag_tarefas));


        for(SubjectBD aux : subjects){
            mHelpLiveo.addColor(aux.getName(), R.drawable.ic_navigate_next_white_24dp, R.color.primaryTextColor);
        }

        mHelpLiveo.addSeparator();

        mHelpLiveo.addColor(getString(R.string.drawer_pag_about), R.drawable.ic_info_white_24dp, R.color.primaryTextColor);

        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);


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
        super.setCheckedItemNavigation(startPosition, true);
        getSupportActionBar().setElevation(toolbarElevation);

    }

    public void loadDatabase(){

        Log.d("Database", "OnLoadDatabase");

        try {
            UserDao userDao = new UserDao(Util.openBD().getConnectionSource());
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());

            this.user = userDao.queryForId(Util.getUserId());
            this.subjects = subjectDao.getUserSubjects(Util.getUserId());

            for(SubjectBD aux : subjectDao.queryForAll()){
                Log.d("DataBase", aux.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveSubject(SubjectBD nSubject){

        if(user == null) return false;

        int statusQ1 = 0;
        int statusQ2 = 0;

        try {
            UserSubjectDao userSubjectDao = new UserSubjectDao(Util.openBD().getConnectionSource());
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());

            statusQ2 = subjectDao.create(nSubject);
            statusQ1 = userSubjectDao.create(new UserSubjectBD(this.user, nSubject));

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return (statusQ1 == 1 && statusQ2 == 1) ? true : false;
    }

    public boolean updateSubject(SubjectBD nSubject){

        if(user == null) return false;

        int statusQ1 = 0;

        try {
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());
            statusQ1 = subjectDao.update(nSubject);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return (statusQ1 == 1 ) ? true : false;
    }

    public boolean deleteSubject(SubjectBD nSubject){

        if(user == null) return false;

        int statusQ1 = 0;

        try {
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());
            statusQ1 = subjectDao.delete(nSubject);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return (statusQ1 == 1 ) ? true : false;
    }

    /****************************************
     *          Click Menu Lateral          *
     ***************************************/

    @Override
    public void onItemClick(int position) {
        Log.d("Item Clicked", "Position " + position);
        Log.d("SaveState", "onItemClick action perfomed");

        if(position == 0) {
            trocaFragment(TAG_HOME, null);
        }
        else if(position == 1) {
            return;
        }
        else if( !subjects.isEmpty() && position >= INITIAL_INDEX_TASKS && position <= FINAL_INDEX_TASKS){
            setSelectedSubject(subjects.get(getSubjectIndexOnList(position)));
            trocaFragment(TAG_TASK, null);
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

    public void trocaFragment(String tag, Bundle savedState){

        if(tag.equals(TAG_HOME)) {
            fragment = new HomeFragment();
            tvToolbar.setText("B-Easy");
            getSupportActionBar().setElevation(getResources().getDimension(R.dimen.md_appbar_elevation));

        }
        else{
            fragment = new TaskFragment();
            tvToolbar.setText(getSelectedSubject().getName());
            getSupportActionBar().setElevation(0);
        }

        ATUAL_TAG = tag;
        fragment.setArguments(savedState);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment, tag).commit();

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


        return super.onOptionsItemSelected(item);
    }

    public SubjectBD getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(SubjectBD selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public int getSubjectIndexOnList(int position){
        return position - INITIAL_INDEX_TASKS;
    }

    public String getAtualTag(){return this.ATUAL_TAG;}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_POSITION, this.getCurrentPosition());
        outState.putSerializable(STATE_SELECTED_SUBJECT, getSelectedSubject());
        outState.putFloat(STATE_TOOLBAR_ELEVATION, getSupportActionBar().getElevation());
        outState.putString(STATE_TOOLBAR_TEXT, tvToolbar.getText().toString());
        outState.putString(STATE_ATUAL_TAG, ATUAL_TAG);
        super.onSaveInstanceState(outState);
    }

    public Bundle createBundleState(){
        Bundle state = new Bundle();
        state.putInt(STATE_CURRENT_POSITION, this.getCurrentPosition());
        state.putSerializable(STATE_SELECTED_SUBJECT, getSelectedSubject());
        state.putFloat(STATE_TOOLBAR_ELEVATION, getSupportActionBar().getElevation());
        state.putString(STATE_TOOLBAR_TEXT, tvToolbar.getText().toString());
        state.putString(STATE_ATUAL_TAG, ATUAL_TAG);
       return state;
    }
}
