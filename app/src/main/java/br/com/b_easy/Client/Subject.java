package br.com.b_easy.Client;

import java.util.List;

/**
 * Created by Tiago on 11/12/2015.
 */
public class Subject {

    private Long id;
    private String name;
    private String color;
    private User user;
    private List<Task> tasks;

    public int getProgress(){
        int progress = 0;
        int total = tasks.size() == 0 ? 1 : tasks.size();

        for (Task task : tasks) {
            if(task.getStatus().equals(Status.DONE)) {
                progress++;
            }
        }

        return progress/total;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
