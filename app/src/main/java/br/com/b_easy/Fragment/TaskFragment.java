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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Adapter.TaskAdapter;
import br.com.b_easy.DAO.TaskDao;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.TaskBD;
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
    private List<TaskBD> toDoTasks, doingTasks, doneTasks;
    private Fragment[] fragments;
    private RelativeLayout rlToDo, rlDoing, rlDone;
    private RelativeLayout rlVisible;
    private Util.Task_Enum tabVisible;
    private RecyclerView rvToDo, rvDoing, rvDone;
    private TaskAdapter adTodo, adDoing, adDone;

    private final String STATE_CURRENT_LAYOUT = "currentLayout";

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

        try {

            TaskDao taskDao = new TaskDao(Util.openBD().getConnectionSource());

            toDoTasks = taskDao.getTasksToDoBySubject(subject.getId());
            doingTasks= taskDao.getTasksDoingBySubject(subject.getId());
            doneTasks = taskDao.getTasksDoneBySubject(subject.getId());

            for(TaskBD aux : toDoTasks){
                Log.d("DataBase", "TODO Task: " + aux.getTitle() + " ID: " + aux.getId());
            }

            for(TaskBD aux : doingTasks){
                Log.d("DataBase", "DOING Task: " + aux.getTitle() + " ID: " + aux.getId());
            }

            for(TaskBD aux : doneTasks){
                Log.d("DataBase", "Done Task: " + aux.getTitle() + " ID: " + aux.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Util.setRecicleView(getContext(),rvToDo,false);
        adTodo = new TaskAdapter(getContext(), toDoTasks, Util.Task_Enum.DO_TO, this);
        rvToDo.setAdapter(adTodo);

        Util.setRecicleView(getContext(),rvDoing,false);
        adDoing = new TaskAdapter(getContext(), doingTasks, Util.Task_Enum.DOING, this);
        rvDoing.setAdapter(adDoing);

        Util.setRecicleView(getContext(),rvDone,false);
        adDone = new TaskAdapter(getContext(), doneTasks, Util.Task_Enum.DONE, this);
        rvDone.setAdapter(adDone);

        tvTabToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "TODO");
                tvTabToDo.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(Util.Task_Enum.DO_TO);
            }
        });

        tvTabDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Doing");
                tvTabDoing.setTextColor(getResources().getColor(R.color.white));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(Util.Task_Enum.DOING);
            }
        });

        tvTabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Done");
                tvTabDone.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(Util.Task_Enum.DONE);
            }
        });

        showFragment(tabVisible);

        if(savedInstanceState != null){
            tabVisible = (Util.Task_Enum) savedInstanceState.getSerializable(STATE_CURRENT_LAYOUT);

            switch(tabVisible){
                case DO_TO: tvTabToDo.performClick();
                    break;
                case DOING: tvTabDoing.performClick();
                    break;
                case DONE: tvTabDone.performClick();
                    break;
            }

        }

        return v;
    }

    public void showFragment(Util.Task_Enum tab){
        if(rlVisible != null)
            rlVisible.setVisibility(View.GONE);

        if(tab != null){
            switch (tab){
                case DO_TO:
                    rlVisible = rlToDo;
                    break;
                case DOING:
                    rlVisible = rlDoing;
                    break;
                case DONE:
                    rlVisible = rlDone;
            }
            rlVisible.setVisibility(View.VISIBLE);
            tabVisible = tab;
        }
        else{
            rlVisible = rlToDo;
            rlVisible.setVisibility(View.VISIBLE);
            tabVisible = Util.Task_Enum.DO_TO;
        }

    }

    public void updateFragment(){

    }

    public boolean deleteTask(Util.Task_Enum cod, int index){

        TaskBD aux = null;

        switch(cod){
            case DO_TO:
                aux = toDoTasks.remove(index);
                adTodo.notifyDataSetChanged();
                break;
            case DOING:
                aux = doingTasks.remove(index);
                adDoing.notifyDataSetChanged();
                break;
            case DONE:
                aux = doneTasks.remove(index);
                adDone.notifyDataSetChanged();
                break;
        }

        if(aux != null){
            try {

                int status = new TaskDao(Util.openBD().getConnectionSource()).delete(aux);
                return status == 1 ? true : false;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean createTask(Util.Task_Enum from, Util.Task_Enum to, TaskBD new_Task){

        switch(to){
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
            default:
                return false;
        }

        if(from != null){
            switch (from){
                case DO_TO:
                    toDoTasks.remove(new_Task);
                    adTodo.notifyDataSetChanged();
                    break;
                case DOING:
                    doingTasks.remove(new_Task);
                    adDoing.notifyDataSetChanged();
                    break;
                case DONE:
                    doneTasks.remove(new_Task);
                    adDone.notifyDataSetChanged();
                    break;
            }
        }

        try {
            new_Task.setStatus(to.toString());
            TaskDao taskDao = new TaskDao(Util.openBD().getConnectionSource());
            int status = taskDao.idExists(new_Task.getId()) ? taskDao.update(new_Task) : taskDao.create(new_Task);
            return status == 1 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createDialogTask(Util.Task_Enum cod, int index){

        final TaskBD aux;
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

                            try {
                                int status = new TaskDao(Util.openBD().getConnectionSource()).update(aux);
                                if(status == 1)
                                    adapter.notifyDataSetChanged();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


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
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();

        if(aux != null) {
            ((EditText) dialog.findViewById(R.id.etTitleFragmentTaskCreate)).setText(aux.getTitle());
            ((EditText) dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).setText(aux.getDescription());
            ((EditText) dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).setText(aux.getRelevance());
        }
    }

    public List<TaskBD> getToDoTasks(){
        return this.toDoTasks;
    }

    public List<TaskBD> getDoingTasks(){
        return this.doingTasks;
    }

    public List<TaskBD> getDoneTasks(){
        return this.doneTasks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_CURRENT_LAYOUT, tabVisible);
        super.onSaveInstanceState(outState);
    }
}
