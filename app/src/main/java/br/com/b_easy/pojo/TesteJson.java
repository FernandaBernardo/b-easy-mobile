package br.com.b_easy.pojo;

/**
 * Created by Tiago on 9/30/2015.
 */
public class TesteJson {

    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String BODY = "body";


    private int userId;
    private String title;
    private String body;

    public TesteJson() {
    }

    public TesteJson(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "UserID : " + this.userId + "\n"
                + "Title : " + this.title + "\n"
                + "Dody : " + this.body + "\n";
    }
}
