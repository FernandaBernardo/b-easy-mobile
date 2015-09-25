package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Tiago on 9/24/2015.
 */

@DatabaseTable(tableName = "subject")
public class SubjectBD {

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String name;

    public SubjectBD() {
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
}
