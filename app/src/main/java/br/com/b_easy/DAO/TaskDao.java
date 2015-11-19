package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.TaskBD;
import br.com.b_easy.DataBaseModel.UserSubjectBD;
import br.com.b_easy.Util;

/**
 * Created by Tiago on 9/26/2015.
 */
public class TaskDao extends BaseDaoImpl<TaskBD,Long> {
    public TaskDao(ConnectionSource cs) throws SQLException {
        super(TaskBD.class);
        setConnectionSource(cs);
        initialize();
    }

    public List<TaskBD> getTasksToDoBySubject(Long subjectId){

        try {
            SubjectDao subjectDao = new SubjectDao(this.getConnectionSource());
            QueryBuilder<TaskBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(TaskBD.SUBJECT_ID_FIELD_NAME,subjectId)
                .and()
                .eq(TaskBD.STATUS_FIELD_NAME, Util.Task_Enum.DO_TO.toString());
            return taskQb.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TaskBD> getTasksDoingBySubject(Long subjectId){

        try {
            SubjectDao subjectDao = new SubjectDao(this.getConnectionSource());
            QueryBuilder<TaskBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(TaskBD.SUBJECT_ID_FIELD_NAME,subjectId)
                    .and()
                    .eq(TaskBD.STATUS_FIELD_NAME, Util.Task_Enum.DOING.toString());
            return taskQb.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TaskBD> getTasksDoneBySubject(Long subjectId){

        try {
            SubjectDao subjectDao = new SubjectDao(this.getConnectionSource());
            QueryBuilder<TaskBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(TaskBD.SUBJECT_ID_FIELD_NAME,subjectId)
                    .and()
                    .eq(TaskBD.STATUS_FIELD_NAME, Util.Task_Enum.DONE.toString());
            return taskQb.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TaskBD getTaskByIdGlobal(Long idGlobal){
        if(idGlobal == null) return null;
        try {
            TaskDao taskDao = new TaskDao(this.getConnectionSource());
            QueryBuilder<TaskBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(TaskBD.ID_GLOBAL, idGlobal);
            List<TaskBD> list = taskQb.query();
            if(!list.isEmpty()) return list.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
