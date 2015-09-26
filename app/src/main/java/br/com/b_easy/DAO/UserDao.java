package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

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
}
