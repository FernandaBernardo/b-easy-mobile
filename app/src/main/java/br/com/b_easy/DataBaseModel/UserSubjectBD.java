package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Tiago on 9/24/2015.
 */

@DatabaseTable(tableName = "user_subject")
public class UserSubjectBD {

    public final static String USER_ID_FIELD_NAME = "user_id";
    public final static String SUBJECT_ID_FIELD_NAME = "subject_id";

    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField(foreign = true, columnName = USER_ID_FIELD_NAME)
    UserBD user;

    @DatabaseField(foreign = true, columnName = SUBJECT_ID_FIELD_NAME)
    SubjectBD subject;

    public UserSubjectBD() {
    }

    public UserSubjectBD(long id, UserBD user, SubjectBD subject) {
        this.id = id;
        this.user = user;
        this.subject = subject;
    }

    public UserSubjectBD(UserBD user, SubjectBD subject) {
        this.user = user;
        this.subject = subject;
    }
}
