package br.com.b_easy.Model;

import java.io.Serializable;

/**
 * Created by Tiago on 9/14/2015.
 */
public class Subject implements Serializable{
    private String name;
    private long id;

    private static final long serialVersionUID = 0L;

    public Subject(String name) {
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
}
