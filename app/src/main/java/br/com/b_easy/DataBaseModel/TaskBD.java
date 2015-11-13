package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;

/**
 * Created by Tiago on 9/24/2015.
 */
@DatabaseTable(tableName = "task")
public class TaskBD implements Serializable{

    public static final String SUBJECT_ID_FIELD_NAME = "subject_id";
    public static final String TITLE_FIELD_NAME = "title";
    public static final String DESCRIPTION_ID_FIELD_NAME = "description";
    public static final String RELEVANCE_FIELD_NAME = "relevance";
    public static final String STATUS_FIELD_NAME = "status";
    public static final String ID_GLOBAL = "id_global";


    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField(columnName = ID_GLOBAL)
    private long idGlobal;

    @DatabaseField(columnName = TITLE_FIELD_NAME)
    private String title;

    @DatabaseField(columnName = DESCRIPTION_ID_FIELD_NAME)
    private String description;

    @DatabaseField(columnName = RELEVANCE_FIELD_NAME)
    private String relevance;

    @DatabaseField(columnName = STATUS_FIELD_NAME)
    private String status;

    @DatabaseField(columnName = "DATUM_LA", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date finalDate;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = SUBJECT_ID_FIELD_NAME)
    private SubjectBD subject;


    public TaskBD() {
    }

    public TaskBD(long id, String title, String description, String relevance, String status, Date finalDate, SubjectBD subject) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.relevance = relevance;
        this.status = status;
        this.finalDate = finalDate;
        this.subject = subject;
    }

    public TaskBD(String title, String description, String relevance, String status, Date finalDate, SubjectBD subject) {
        this.title = title;
        this.description = description;
        this.relevance = relevance;
        this.status = status;
        this.finalDate = finalDate;
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

    public Date getFinalDate() {
        return finalDate;
    }

    public String getFinalDateString(){
        if(finalDate == null) return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(finalDate);
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public long getIdGlobal() {
        return idGlobal;
    }

    public void setIdGlobal(long idGlobal) {
        this.idGlobal = idGlobal;
    }
}
