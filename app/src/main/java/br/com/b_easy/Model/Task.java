package br.com.b_easy.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tiago on 9/20/2015.
 */
public class Task implements Serializable{

    private long id;
    private String description;
    private String relevance;
    private String status;
    private String title;
    private Date finalDate;

    public Task(long id, String description, String relevance, String status, String title, Date finalDate) {
        this.id = id;
        this.description = description;
        this.relevance = relevance;
        this.status = status;
        this.title = title;
        this.finalDate = finalDate;
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public String getFinalDateString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(finalDate);
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
}
