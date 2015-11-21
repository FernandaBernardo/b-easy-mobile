package br.com.b_easy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.melnykov.fab.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Adapter.TaskAdapter;
import br.com.b_easy.Client.User;
import br.com.b_easy.DAO.TaskDao;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.TaskBD;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;
import br.com.b_easy.Preferences;
import br.com.b_easy.R;
import br.com.b_easy.Util;
import br.com.b_easy.Volley.JsonParser;
import br.com.b_easy.customView.SlidingTabLayout;


/**
 * Created by Tiago on 9/12/2015.
 */
public class TaskFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

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
    private FloatingActionButton fabTodo, fabDoing, fabDone;
    private EditText datePickerHelper;

    private final String STATE_CURRENT_LAYOUT = "currentLayout";
    public static final String STATE_NEW_ACTIVITY = "newActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

        }

        Log.d("SaveState", "OnCreateView");

        //setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        Log.d("SaveState", "OnCreateView");
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
        fabTodo = (FloatingActionButton) v.findViewById(R.id.fabToDoFragment);
        fabDoing = (FloatingActionButton) v.findViewById(R.id.fabDoingFragment);
        fabDone = (FloatingActionButton) v.findViewById(R.id.fabDoneFragment);

        fabTodo.attachToRecyclerView(rvToDo);
        fabDoing.attachToRecyclerView(rvDoing);
        fabDone.attachToRecyclerView(rvDone);

        fabTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddOptions();
            }
        });

        fabDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddOptions();
            }
        });

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddOptions();
            }
        });

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
                Log.d("SaveState", "TAB TO DO Calling ShowFragment: ");
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
                Log.d("SaveState", "TAB DOING Calling ShowFragment: ");
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
                Log.d("SaveState", "TAB DONE Calling ShowFragment: ");
                showFragment(Util.Task_Enum.DONE);
            }
        });

        if(savedInstanceState != null){
            tabVisible = (Util.Task_Enum) savedInstanceState.getSerializable(STATE_CURRENT_LAYOUT);

                switch (tabVisible) {
                    case DO_TO:
                        tvTabToDo.performClick();
                        break;
                    case DOING:
                        tvTabDoing.performClick();
                        break;
                    case DONE:
                        tvTabDone.performClick();
                        break;
                }

        }
        else if(getArguments() != null){
            tabVisible = (Util.Task_Enum) getArguments().getSerializable(STATE_CURRENT_LAYOUT);

            switch (tabVisible) {
                case DO_TO:
                    tvTabToDo.performClick();
                    break;
                case DOING:
                    tvTabDoing.performClick();
                    break;
                case DONE:
                    tvTabDone.performClick();
                    break;
            }
        }
        else{
            tvTabToDo.performClick();
        }

        return v;
    }

    private void showFragment(Util.Task_Enum tab){
        Log.d("SaveState", "Called ShowFragment: ");
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
            Log.d("SaveState", "IF TabVisible " + tabVisible.toString());

        }
        else{
            rlVisible = rlToDo;
            rlVisible.setVisibility(View.VISIBLE);
            tabVisible = Util.Task_Enum.DO_TO;
            Log.d("SaveState", "ELSE TabVisible " + tabVisible.toString());
        }

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

    public void createDialogUpdateTask(Util.Task_Enum cod, int index){

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
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentTaskCreate)).getText().toString();
                        String sDesc = ((EditText) dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).getText().toString();
                        String sRel = ((EditText) dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).getText().toString();
                        String sDate = ((EditText) dialog.findViewById(R.id.etDateFragmentTaskCreate)).getText().toString();
                        Date date = null;

                        if (sName.trim().length() == 0) {
                            Toast.makeText(getContext(), "Invalid Task Name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (sName.trim().length() != 0) {
                            boolean exception = false;
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                date = formatter.parse(sDate);
                            } catch (ParseException e) {
                                exception = true;
                            }

                            if (exception) {
                                Toast.makeText(getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } else {
                            Toast.makeText(getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (aux != null && adapter != null) {
                            aux.setTitle(sName);
                            aux.setDescription(sDesc);
                            aux.setRelevance(sRel);
                            aux.setFinalDate(date);

                            try {
                                int status = new TaskDao(Util.openBD().getConnectionSource()).update(aux);
                                if (status == 1)
                                    adapter.notifyDataSetChanged();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


                        }

                        super.onPositive(dialog);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();
        datePickerHelper = (EditText) dialog.findViewById(R.id.etDateFragmentTaskCreate);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        datePickerHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                now.setTime(aux.getFinalDate());
                Log.d("DatePicker","Onclick");
                DatePickerDialog dpd = DatePickerDialog.newInstance(TaskFragment.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show(TaskFragment.this.getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();

        if(aux != null) {
            ((EditText) dialog.findViewById(R.id.etTitleFragmentTaskCreate)).setText(aux.getTitle());
            ((EditText) dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).setText(aux.getDescription());
            ((EditText) dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).setText(aux.getRelevance());
        }
    }


    public void createDialogAddOptions(){

        new MaterialDialog.Builder(getContext())
                .title("Selecione a Ação")
                .items( R.array.optionsFAB)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Log.d("Click", "OnSelection " + which);
                        if (which == 0) {
                            createDialogAddSubject();
                        } else {
                            createDialogAddTask();
                        }
                    }
                })
                .show();
    }

    public void createDialogAddSubject(){

        MaterialDialog dialog =  new MaterialDialog.Builder(getContext())
                .title("Adicionar Matéria")
                .customView(R.layout.fragment_subject_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        boolean status = false;

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).getText().toString();

                        if (sName == null || sName.trim().equals("")) {
                            Toast.makeText(getContext(), "Invalid Subject Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            status = ((MainActivity) getActivity()).saveSubject(new SubjectBD(sName));

                            if (status) {
                                Log.d("DataBase", "SUCCESS: Saved Subject");
                                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            } else
                                Log.e("DataBase", "ERROR: Save Subject");

                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }

    public void createDialogEditSubject(){

        MaterialDialog dialog =  new MaterialDialog.Builder(getContext())
                .title("Adicionar Matéria")
                .customView(R.layout.fragment_subject_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        boolean status = false;

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).getText().toString();

                        if (sName == null || sName.trim().equals("")) {
                            Toast.makeText(getContext(), "Invalid Subject Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            br.com.b_easy.Client.Subject s = new br.com.b_easy.Client.Subject();
                            s.setName(sName);
                            User user = new User();
                            user.setEmail(Preferences.getInstance().getUser().getEmail());
                            s.setUser(user);
                            s.setId(subject.getIdGlobal());
                            br.com.b_easy.Volley.Request.postDataJson(getString(R.string.url_updateSubject), JsonParser.objectToJson(s), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("volleySubject", "response" + response);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("volleySubject", "error" + error.toString());
                                }
                            });

                            subject.setName(sName);
                            status = ((MainActivity) getActivity()).updateSubject(subject);

                            if (status) {
                                Log.d("DataBase", "SUCCESS: Saved Subject");
                                Intent i = new Intent(getContext(),MainActivity.class);
                                i.putExtras(createBundleState());
                                getActivity().startActivity(i);
                                getActivity().finish();
                            } else
                                Log.e("DataBase", "ERROR: Save Subject");

                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build();

        ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).setText(subject.getName());
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }

    public void createDialogDeleteSubject(){

        MaterialDialog dialog =  new MaterialDialog.Builder(getContext())
                .content("Deseja deletar a matéria " + subject.getName() + " juntamente com suas tarefas ? ")
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        br.com.b_easy.Client.Subject s = new br.com.b_easy.Client.Subject();
                        s.setId(subject.getIdGlobal());
                        JSONObject json = JsonParser.objectToJson(s);
                        JSONObject json2 = new JSONObject();
                        try {
                            json2.put("subject",json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        br.com.b_easy.Volley.Request.postDataJson(getString(R.string.url_deleteSubject), json2, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("volleySubject", "response" + response);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("volleySubject", "error" + error.toString());
                            }
                        });

                        boolean status = false;

                            status = ((MainActivity) getActivity()).deleteSubject(subject);

                            if (status) {
                                Log.d("DataBase", "SUCCESS: Saved Subject");
                                Intent i = new Intent(getContext(),MainActivity.class);
                                getActivity().startActivity(i);
                                getActivity().finish();
                            } else
                                Log.e("DataBase", "ERROR: Save Subject");

                            dialog.dismiss();

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }

    public void createDialogAddTask(){
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Adicionar Tarefa")
                .customView(R.layout.fragment_task_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .autoDismiss(false)
                .negativeColorRes(R.color.secondaryTextColor)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentTaskCreate)).getText().toString();
                        String sDesc = ((EditText) dialog.findViewById(R.id.etDescritionFragmentTaskCreate)).getText().toString();
                        String sRel = ((EditText) dialog.findViewById(R.id.etRelevanciaFragmentTaskCreate)).getText().toString();
                        String sDate = ((EditText) dialog.findViewById(R.id.etDateFragmentTaskCreate)).getText().toString();
                        Date date = null;

                        if (sName == null || sName.toString().trim().length() == 0) {
                            Toast.makeText(getContext(), "Invalid Task Name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(sDate != null && sName.toString().trim().length() != 0){
                            boolean exception = false;
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                date = formatter.parse(sDate);
                            } catch (ParseException e) {
                                exception = true;
                            }

                            if(exception){
                                Toast.makeText(getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        else{
                            Toast.makeText(getContext(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("CreateTask", "Name: " + sName + " Descricao: " + sDesc + " Relevancia: " + sRel);

                        br.com.b_easy.Volley.Request.postDataJson(getString(R.string.url_createSubject), JsonParser.objectToJson(s), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("volleySubject", "response" + response);
                                br.com.b_easy.Client.Subject subject = JsonParser.JsontoSubject(response);
                                SubjectBD sbd = new SubjectBD(subject.getName());
                                sbd.setIdGlobal(subject.getId());
                                boolean status = ((MainActivity) getActivity()).saveSubject(sbd);

                                if (status) {
                                    Log.d("DataBase", "SUCCESS: Saved Subject");
                                    getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                    getActivity().finish();
                                } else
                                    Log.e("DataBase", "ERROR: Save Subject");


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("volleySubject", "error" + error.toString());
                            }
                        });

                        boolean success = createTask(null, tabVisible, new TaskBD(sName, sDesc, sRel, tabVisible.toString(), date, subject));

                        Log.d("Database", "Task Created: " + (success ? "SUCCESS" : "FAILED"));

                        super.onPositive(dialog);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build();
                    datePickerHelper = (EditText) dialog.findViewById(R.id.etDateFragmentTaskCreate);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    datePickerHelper.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar now = Calendar.getInstance();
                            Log.d("DatePicker","Onclick");
                            DatePickerDialog dpd = DatePickerDialog.newInstance(TaskFragment.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            dpd.show(TaskFragment.this.getActivity().getFragmentManager(), "Datepickerdialog");
                        }
                    });
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(datePickerHelper != null)
            datePickerHelper.setText(dayOfMonth + "/" + (++monthOfYear) + "/" + year);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.editItemMenuFragment :
                Toast.makeText(getContext(), "Edit Clicked",Toast.LENGTH_SHORT).show();
                createDialogEditSubject();
                return true;
            case R.id.deleteItemMenuFragment:
                Toast.makeText(getContext(), "Delete Clicked",Toast.LENGTH_SHORT).show();
                createDialogDeleteSubject();
                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_CURRENT_LAYOUT, tabVisible);
        super.onSaveInstanceState(outState);
    }

    public Bundle createBundleState(){
        Bundle state = ((MainActivity)getActivity()).createBundleState();
        state.putSerializable(STATE_CURRENT_LAYOUT,tabVisible);
        state.putBoolean(STATE_NEW_ACTIVITY,true);
        return state;

    }
}
