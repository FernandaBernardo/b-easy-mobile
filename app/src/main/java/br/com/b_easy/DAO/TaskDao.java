package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.com.b_easy.DataBaseModel.TaskBD;

/**
 * Created by Tiago on 9/26/2015.
 */
public class TaskDao extends BaseDaoImpl<TaskBD,Long> {
    public TaskDao(ConnectionSource cs) throws SQLException {
        super(TaskBD.class);
        setConnectionSource(cs);
        initialize();
    }
}
