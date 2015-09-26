package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

import br.com.b_easy.Model.Task;

/**
 * Created by Tiago on 9/24/2015.
 */

@DatabaseTable(tableName = "subject")
public class SubjectBD {

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String name;

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

    public ForeignCollection<TaskBD> getTasks() {
        return tasks;
    }
}
