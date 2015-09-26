package br.com.b_easy.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Adapter.TaskAdapter;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;
import br.com.b_easy.R;
import br.com.b_easy.Util;
import br.com.b_easy.customView.SlidingTabLayout;

/**
 * Created by Tiago on 9/12/2015.
 */
public class TaskFragment extends Fragment {

    private SubjectBD subject;
    private TextView tvTabToDo, tvTabDoing, tvTabDone;
    private MainActivity context;
    private List<Task> toDoTasks, doingTasks, doneTasks;
    private Fragment[] fragments;
    private RelativeLayout rlToDo, rlDoing, rlDone;
    private RelativeLayout rlVisible;
    private RecyclerView rvToDo, rvDoing, rvDone;
    private TaskAdapter adTodo, adDoing, adDone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

        }

        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        context = ((MainActivity)getActivity());
        subject = context.getSelectedSubject();
        tvTabToDo = (TextView) v.findViewById(R.id.tvTabToDo);
        tvTabDoing = (TextView) v.findViewById(R.id.tvTabDoing);
        tvTabDone = (TextView) v.findViewById(R.id.tvTabDone);
        rlToDo = (RelativeLayout) v.findViewById(R.id.rlToDoFragment);
        rlDoing = (RelativeLayout)v.findViewById(R.id.rlDoingFragment);
        rlDone = (RelativeLayout) v.findViewById(R.id.rlDoneFragment);
        rvToDo = (RecyclerView) v.findViewById(R.id.rvToDoFragment);
        rvDoing = (RecyclerView) v.findViewById(R.id.rvDoingFragment);
        rvDone = (RecyclerView) v.findViewById(R.id.rvToDoneFragment);
        toDoTasks = Util.getListTasks();
        doingTasks = Util.getListTasks();
        doneTasks = Util.getListTasks();

        Util.setRecicleView(getContext(),rvToDo,false);
        adTodo = new TaskAdapter(getContext(), toDoTasks, Util.Task_Enum.DO_TO, this);
        rvToDo.setAdapter(adTodo);

        Util.setRecicleView(getContext(),rvDoing,false);
        adDoing = new TaskAdapter(getContext(), doingTasks, Util.Task_Enum.DOING, this);
        rvDoing.setAdapter(adDoing);

        Util.setRecicleView(getContext(),rvDone,false);
        adDone = new TaskAdapter(getContext(), doneTasks, Util.Task_Enum.DONE, this);
        rvDone.setAdapter(adDone);

        showFragment(rlToDo);

        tvTabToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "TODO");
                tvTabToDo.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlToDo);
            }
        });

        tvTabDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Doing");
                tvTabDoing.setTextColor(getResources().getColor(R.color.white));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlDoing);
            }
        });

        tvTabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Done");
                tvTabDone.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlDone);
            }
        });


        //tvCardTitle = (TextView) v.findViewById(R.id.tvTitleCardFragmentTask);
        //tvCardTitle.setText(subject.getName());


//        mViewPager = (ViewPager) v.findViewById(R.id.pager);
//        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
//
//        updateFragment();

        return v;
    }

    public void showFragment(RelativeLayout rl){
        if(rlVisible != null)
            rlVisible.setVisibility(View.GONE);
        rl.setVisibility(View.VISIBLE);
        rlVisible = rl;
    }

    public void updateFragment(){
//        Log.d("UPDATE", "Calling");
//        fragments = new Fragment[3];
//        fragments[0] = new ToDoFragment();
//        fragments[1] = new DoingFragment();
//        fragments[2] = new DoneFragment();
//
//        toDoTasks = Util.getListTasks();
//        doingTasks= Util.getListTasks();
//        doneTasks = Util.getListTasks();
//
//        mPagerAdapter = new NavigationAdapter( context.getSupportFragmentManager(), context.getResources().getStringArray(R.array.TabsTaskFragments), fragments);
//        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setCurrentItem(1);
//
//
//        tabs.setCustomTabView(R.layout.custom_tab, R.id.tvTab);
//        tabs.setDistributeEvenly(true);
//        tabs.setViewPager(mViewPager);
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.accentColor);
//            }
//        });
    }

    public void deleteTask(Util.Task_Enum cod, int index){
        switch(cod){
            case DO_TO:
                toDoTasks.remove(index);
                adTodo.notifyDataSetChanged();
                break;
            case DOING:
                doingTasks.remove(index);
                adDoing.notifyDataSetChanged();
                break;
            case DONE:
                doneTasks.remove(index);
                adDone.notifyDataSetChanged();
                break;
        }
    }

    public void createTask(Util.Task_Enum cod, Task new_Task){
        switch(cod){
            case DO_TO:
                toDoTasks.add(new_Task);
                adTodo.notifyDataSetChanged();
                break;
            case DOING:
                doingTasks.add(new_Task);
                adDoing.notifyDataSetChanged();
                break;
            case DONE:
                doneTasks.add(new_Task);
                adDone.notifyDataSetChanged();
                break;
        }
    }

    public void createDialogTask(Util.Task_Enum cod, int index){

        final Task aux;
        final TaskAdapter adapter;

        switch (cod){
            case DO_TO:
                aux = toDoTasks.get(index);
                adapter = adTodo;
                break;
            case DOING:
                aux = doingTasks.get(index);
                adapter = adDoing;
                break;
            case DONE:
                aux = doneTasks.get(index);
                adapter = adDone;
                break;
            default:
                adapter = null;
                aux = null;
        }

        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Adicionar Tarefa")
                .customView(R.layout.fragment_task_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .callback(new MaterialDialog.ButtonCallback(){
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        String sName = ((EditText)dialog.findViewById(R.id.etTitleFragmentTaskCreate)).getText().toString();
                        String sDesc = ((EditText)dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).getText().toString();
                        String sRel  = ((EditText)dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).getText().toString();

                        Log.d("CreateTask", "Name: " + sName + " Descricao: " + sDesc + " Relevancia: " + sRel);

                        if(aux != null && adapter != null){
                            aux.setTitle(sName);
                            aux.setDescription(sDesc);
                            aux.setRelevance(sRel);
                            adapter.notifyDataSetChanged();
                        }

                        // getData
                        // createObject

                        super.onPositive(dialog);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();

        dialog.show();

        if(aux != null) {
            ((EditText) dialog.findViewById(R.id.etTitleFragmentTaskCreate)).setText(aux.getTitle());
            ((EditText) dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).setText(aux.getDescription());
            ((EditText) dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).setText(aux.getRelevance());
        }
    }

    public List<Task> getToDoTasks(){
        return this.toDoTasks;
    }

    public List<Task> getDoingTasks(){
        return this.doingTasks;
    }

    public List<Task> getDoneTasks(){
        return this.doneTasks;
    }

//    static class NavigationAdapter extends FragmentPagerAdapter{
//
//        private CharSequence[] mTitles;
//        private Fragment[] fragments;
//
//        public NavigationAdapter( FragmentManager fm, CharSequence[] mTitles, Fragment[] fragments) {
//            super(fm);
//            this.mTitles = mTitles;
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if(position < fragments.length)
//                return fragments[position];
//            else
//                return null;
//        }
//
//        @Override
//        public int getCount() {
//            return mTitles.length;
//        }
//
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//
//    }



}
