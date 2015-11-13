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
import br.com.b_easy.Model.Subject;

import br.com.b_easy.Client.Task;

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


    public static List<Subject> getListSubject(){
        return new ArrayList<Subject>(){
            {
                add(new Subject("Inteligência Artificial"));
                add(new Subject("Banco de Dados"));
                add(new Subject("Matemática Discreta"));
                add(new Subject("Sistemas Operacionais"));
                add(new Subject("Cálculo I"));
            }
        };
    }

    public static String toMD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            String input = Preferences.PASSWORD_BASE + s;
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

            return userBD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static SubjectBD fromModelSubject(Subject subject){
        try {
            SubjectDao subjectDao = new SubjectDao(Util.openBD().getConnectionSource());
            SubjectBD subjectBD = subjectDao.getSubjectByIdGlogal(subject.getId());
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
            TaskBD taskBD = taskDao.getTaskByIdGlobal(task.getId());
            if(taskBD == null)
                taskBD = new TaskBD();

            taskBD.setTitle(task.getTitle());
            taskBD.setFinalDate(task.getFinalDate());
            taskBD.setRelevance(task.getRelevance());
            taskBD.setDescription(task.getDescription());
            taskBD.setIdGlobal(task.getId());

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



}
