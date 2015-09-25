package br.com.b_easy;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.b_easy.DataBase.DatabaseHelper;
import br.com.b_easy.DataBase.DatabaseManager;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;

/**
 * Created by Tiago on 9/20/2015.
 */
public class Util {

    public enum Task_Enum{
        DO_TO(0), DOING(1), DONE(2);
        private final int cod;
        Task_Enum(int cod){this.cod = cod;}
        public int getCod(){return this.cod;}
    }

    public static void setRecicleView(Context context,RecyclerView v, boolean divisor) {
        v.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        v.setLayoutManager(llm);
        if(divisor)
            v.addItemDecoration(new DividerItemDecoration(context, null, false, true));
    }

    public static List<Task> getListTasks(){
        return new ArrayList<Task>(){
            {
                add(new Task(1L, "Implementacao da RecycleView, Adapter, Fragments, XML Resources", "", "","Task #1", new Date()));
                add(new Task(2L, "Description #2", "", "","Task #2", new Date()));
                add(new Task(3L, "Description #3", "", "","Task #3", new Date()));
                add(new Task(4L, "Description #4", "", "","Task #4", new Date()));
                add(new Task(5L, "Description #5", "", "","Task #5", new Date()));
                add(new Task(6L, "Description #6", "", "","Task #6", new Date()));
                add(new Task(7L, "Description #7", "", "","Task #7", new Date()));
                add(new Task(8L, "Description #8", "", "","Task #8", new Date()));
            }
        };
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

    public static DatabaseHelper openBD(Context ctx){
        DatabaseManager.init(ctx);
        return DatabaseManager.getHelper();

    }

    public static void closeBD(DatabaseHelper helper){
        helper.close();
    }




}
