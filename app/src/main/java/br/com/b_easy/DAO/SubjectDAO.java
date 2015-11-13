package br.com.b_easy.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import br.com.b_easy.Client.Subject;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.UserSubjectBD;

/**
 * Created by Tiago on 9/24/2015.
 */

public class SubjectDao extends BaseDaoImpl<SubjectBD, Long> {
    public SubjectDao(ConnectionSource cs) throws SQLException {
        super(SubjectBD.class);
        setConnectionSource(cs);
        initialize();
    }

    public List<SubjectBD> getUserSubjects(long userId){
        UserSubjectDao userSubjectDao = null;
        try {
            userSubjectDao = new UserSubjectDao(this.getConnectionSource());
            QueryBuilder<UserSubjectBD,Long> userSubjectQb =  userSubjectDao.queryBuilder();
            QueryBuilder<SubjectBD,Long> subjectQb = this.queryBuilder();
            userSubjectQb.where().eq(UserSubjectBD.USER_ID_FIELD_NAME, userId);
            return subjectQb.join(userSubjectQb).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SubjectBD getSubjectByIdGlogal(long idGlobal){
        UserSubjectDao userSubjectDao = null;
        try {
            SubjectDao subjectDao = new SubjectDao(this.getConnectionSource());
            QueryBuilder<SubjectBD, Long> taskQb = this.queryBuilder();
            taskQb.where().eq(SubjectBD.SUBJECT_ID_GLOBAL,idGlobal);
            List<SubjectBD> list = taskQb.query();
            if(!list.isEmpty()) return list.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
