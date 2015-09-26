package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.com.b_easy.DataBaseModel.UserSubjectBD;

/**
 * Created by Tiago on 9/26/2015.
 */
public class UserSubjectDao extends BaseDaoImpl<UserSubjectBD,Long> {
    public UserSubjectDao(ConnectionSource cs) throws SQLException {
        super(UserSubjectBD.class);
        setConnectionSource(cs);
        initialize();
    }
}
