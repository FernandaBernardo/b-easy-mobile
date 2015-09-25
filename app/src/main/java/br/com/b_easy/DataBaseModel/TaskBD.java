package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Tiago on 9/24/2015.
 */
@DatabaseTable(tableName = "task")
public class TaskBD {

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String description;

    @DatabaseField
    private String relevance;

    @DatabaseField
    private String status;

    @DatabaseField
    private String title;

}
