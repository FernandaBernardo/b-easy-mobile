package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import br.com.b_easy.Model.Task;

/**
 * Created by Tiago on 9/24/2015.
 */

@DatabaseTable(tableName = "subject")
public class SubjectBD implements Serializable {

    public static final String SUBJECT_ID_GLOBAL = "id_global";

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField(columnName = SUBJECT_ID_GLOBAL)
    private long idGlobal;

    @ForeignCollectionField
    private ForeignCollection<TaskBD> tasks;

    public SubjectBD() {
    }

    public SubjectBD(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubjectBD(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdGlobal() {
        return idGlobal;
    }

    public void setIdGlobal(long idGlobal) {
        this.idGlobal = idGlobal;
    }

    public ForeignCollection<TaskBD> getTasks() {
        return tasks;
    }
}
