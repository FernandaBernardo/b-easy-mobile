package br.com.b_easy.Client;

import java.util.Date;

/**
 * Created by Tiago on 11/12/2015.
 */
public class Task {

    private Long id;
    private String title;
    private String description;
    private String relevance;
    private Date finalDate;
    private Status status;
    private Subject subject;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Date getFinalDate() {
        return finalDate;
    }
    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
