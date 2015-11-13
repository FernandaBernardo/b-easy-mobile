package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import br.com.b_easy.DataBaseModel.UserBD;

/**
 * Created by Tiago on 9/24/2015.
 */
public class UserDao extends BaseDaoImpl<UserBD,Long> {
    public UserDao(ConnectionSource cs) throws SQLException {
        super(UserBD.class);
        setConnectionSource(cs);
        initialize();
    }

    public UserBD getUserbyEmail(String Email){

        try {
            UserDao userDao = new UserDao(this.getConnectionSource());
            QueryBuilder<UserBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(UserBD.USER_EMAIL,Email);
            List<UserBD> list = taskQb.query();
            if(!list.isEmpty()) return list.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
