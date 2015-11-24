package br.com.b_easy;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.b_easy.Client.Status;
import br.com.b_easy.Client.User;
import br.com.b_easy.DAO.SubjectDao;
import br.com.b_easy.DAO.TaskDao;
import br.com.b_easy.DAO.UserDao;
import br.com.b_easy.DAO.UserSubjectDao;
import br.com.b_easy.DataBase.DatabaseHelper;
import br.com.b_easy.DataBase.DatabaseManager;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.TaskBD;
import br.com.b_easy.DataBaseModel.UserBD;


import br.com.b_easy.Client.Task;
import br.com.b_easy.Client.Subject;
import br.com.b_easy.DataBaseModel.UserSubjectBD;

import static br.com.b_easy.Client.Status.*;

/**
 * Created by Tiago on 9/20/2015.
 */
public class Util {



    public enum Task_Enum{
        DO_TO(0,"TODO"), DOING(1,"DOING"), DONE(2,"DONE");
        private final int cod;
        private final String name;
        Task_Enum(int cod,String name){this.cod = cod; this.name = name;}
        public int getCod(){return this.cod;}

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static void setRecicleView(Context context,RecyclerView v, boolean divisor) {
        v.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        v.setLayoutManager(llm);
        if(divisor)
            v.addItemDecoration(new DividerItemDecoration(context, null, false, true));
    }


    public static String toMD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            String input = s;
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static MaterialDialog buildProgressDialog(Context context, String title, String content){
        if(title == null){
            return new MaterialDialog.Builder(context)
                    .autoDismiss(false)
                    .cancelable(false)
                    .content(content)
                    .progress(true, 0)
                    .build();
        }

        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true,0)
                .autoDismiss(false)
                .cancelable(false)
                .build();
    }

    public static DatabaseHelper openBD(){

        return DatabaseManager.getHelper();

    }

    public static void closeBD(){
        DatabaseManager.getHelper().close();
    }

    public static long getUserId(){
        return 1L;
    }

    public static UserBD fromModelUser(User user){
        try {
            UserDao userDao = new UserDao(Util.openBD().getConnectionSource());
            UserBD userBD = userDao.getUserbyEmail(user.getEmail());
            if(userBD == null)
                userBD = new UserBD();

            userBD.setName(user.getName());
            userBD.setEmail(user.getEmail());
            userBD.setAge(user.getAge());
            userBD.setCollege(user.getCollege());
            userBD.setCourse(user.getCourse());
            userBD.setSemester(user.getSemester());
            userBD.setPictureURL(user.getPictureUrl());

            return userBD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static SubjectBD fromModelSubject(Subject subject){
        try {
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());
            SubjectBD subjectBD = null;

            if(subject.getId() != null);
                subjectBD = subjectDao.getSubjectByIdGlogal(subject.getId());

            if(subjectBD == null)
                subjectBD = new SubjectBD();

            subjectBD.setName(subject.getName());
            subjectBD.setIdGlobal(subject.getId());

            return subjectBD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TaskBD fromModelTask(Task task){
        try {
            TaskDao taskDao = new TaskDao(Util.openBD().getConnectionSource());
            TaskBD taskBD = null;
            if(task.getId() != null )
               taskBD = taskDao.getTaskByIdGlobal(task.getId());
            if(taskBD == null)
                taskBD = new TaskBD();

            taskBD.setTitle(task.getTitle());
            taskBD.setFinalDate(task.getFinalDate());
            taskBD.setRelevance(task.getRelevance());
            taskBD.setDescription(task.getDescription());
            taskBD.setIdGlobal(task.getId());
            taskBD.setSubject(fromModelSubject(task.getSubject()));

            switch (task.getStatus()){
                case TODO: taskBD.setStatus(Task_Enum.DO_TO.name);
                    break;
                case DOING: taskBD.setStatus(Task_Enum.DOING.name);
                    break;
                case DONE: taskBD.setStatus(Task_Enum.DONE.name);
                    break;
            }

            return taskBD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TaskBD fromModelTask(Task task, SubjectBD s){
        try {
            TaskDao taskDao = new TaskDao(Util.openBD().getConnectionSource());
            TaskBD taskBD = null;
            if(task.getId() != null )
                taskBD = taskDao.getTaskByIdGlobal(task.getId());
            if(taskBD == null)
                taskBD = new TaskBD();

            taskBD.setTitle(task.getTitle());
            taskBD.setFinalDate(task.getFinalDate());
            taskBD.setRelevance(task.getRelevance());
            taskBD.setDescription(task.getDescription());
            taskBD.setIdGlobal(task.getId());
            taskBD.setSubject(s);

            switch (task.getStatus()){
                case TODO: taskBD.setStatus(Task_Enum.DO_TO.name);
                    break;
                case DOING: taskBD.setStatus(Task_Enum.DOING.name);
                    break;
                case DONE: taskBD.setStatus(Task_Enum.DONE.name);
                    break;
            }

            return taskBD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Task toModelTask(TaskBD task){

        br.com.b_easy.Client.Task t = new br.com.b_easy.Client.Task();
        br.com.b_easy.Client.Subject s = new br.com.b_easy.Client.Subject();
        s.setId(task.getSubject().getIdGlobal());
        t.setSubject(s);
        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setFinalDate(task.getFinalDate());
        t.setRelevance(task.getRelevance());

        if(task.getStatus().equals(Task_Enum.DO_TO.name)) t.setStatus(TODO);
        else if(task.getStatus().equals(Task_Enum.DOING)) t.setStatus(DOING);
        else if(task.getStatus().equals(Task_Enum.DONE)) t.setStatus(DONE);

        return t;

    }

    public static void updateReferences(User user){

        try {
            UserDao userDao = new UserDao(Util.openBD().getConnectionSource());
            UserSubjectDao userSubjectDao = new UserSubjectDao(Util.openBD().getConnectionSource());
            SubjectDao  subjectDao = new SubjectDao(Util.openBD().getConnectionSource());
            TaskDao  taskDao = new TaskDao(Util.openBD().getConnectionSource());

            taskDao.deleteBuilder().delete();
            userSubjectDao.deleteBuilder().delete();
            userDao.deleteBuilder().delete();
            subjectDao.deleteBuilder().delete();

            UserBD userBD = fromModelUser(user);
            userDao.create(userBD);

            if(user.getSubjects() != null ) {
                for (Subject s : user.getSubjects()) {
                    SubjectBD subjectBD = fromModelSubject(s);
                    subjectDao.create(subjectBD);
                    userSubjectDao.create(new UserSubjectBD(userBD, subjectBD));

                    if(s.getTasks() != null) {
                        for (Task t : s.getTasks()) {
                            TaskBD taskBD = fromModelTask(t);
                            taskBD.setSubject(subjectBD);
                            taskDao.create(taskBD);
                        }
                    }

                }
            }

            Preferences.getInstance().setUser(userBD);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateReferences(SubjectBD selectedSubject, List<Task> tasks) {
        try {
            TaskDao  taskDao = new TaskDao(Util.openBD().getConnectionSource());
            for(Task t : tasks){
                TaskBD taskBD = Util.fromModelTask(t,selectedSubject);
                if(taskDao.idExists(taskBD.getId()))
                    taskDao.update(taskBD);
                else
                    taskDao.create(taskBD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(){

        User user = new User();
        user.setName("Tiago Emanuel");
        user.setEmail("t.missao@gmail.com");

        Subject s = new Subject();
        s.setName("oi");
        s.setId(1L);
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("TO DO");
        t1.setDescription("TO DO T1");
        t1.setStatus(Status.TODO);
        ArrayList<Task> at = new ArrayList<>();
        at.add(t1);
        ArrayList<Subject> as = new ArrayList<>();
        s.setTasks(at);
        as.add(s);

        Subject s2 = new Subject();
        s2.setName("oi2");
        s2.setId(2L);
        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("DOing");
        t2.setDescription("DOing T2");
        t2.setStatus(Status.DOING);
        ArrayList<Task> at2 = new ArrayList<>();
        at2.add(t2);
        s2.setTasks(at2);
        as.add(s2);

        Subject s3 = new Subject();
        s3.setName("oi3");
        s3.setId(3L);
        Task t3 = new Task();
        t3.setId(3L);
        t3.setTitle("DONE");
        t3.setDescription("DONE T3");
        t3.setStatus(Status.DONE);
        ArrayList<Task> at3 = new ArrayList<>();
        at3.add(t3);
        s3.setTasks(at3);
        as.add(s3);

        user.setSubjects(as);

        return user;
    }



}
