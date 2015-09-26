package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;

/**
 * Created by Tiago on 9/24/2015.
 */
@DatabaseTable(tableName = "task")
public class TaskBD {

    public static final String SUBJECT_ID_FIELD_NAME = "subject_id";


    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String relevance;

    @DatabaseField
    private String status;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = SUBJECT_ID_FIELD_NAME)
    private SubjectBD subject;


    public TaskBD() {
    }

    public TaskBD(long id, String title, String description, String relevance, String status, SubjectBD subject) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.relevance = relevance;
        this.status = status;
        this.subject = subject;
    }

    public TaskBD(String title, String description, String relevance, String status, SubjectBD subject) {
        this.title = title;
        this.description = description;
        this.relevance = relevance;
        this.status = status;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubjectBD getSubject() {
        return subject;
    }

    public void setSubject(SubjectBD subject) {
        this.subject = subject;
    }
}
