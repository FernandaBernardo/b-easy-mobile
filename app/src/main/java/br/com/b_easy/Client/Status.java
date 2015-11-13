package br.com.b_easy.Client;

/**
 * Created by Tiago on 11/12/2015.
 */
public enum Status {
    TODO(0),
    DONE(1),
    DOING(2);

    private int status;

    private Status(int status) {
        this.status = status;
    }

    public int getType() {
        return status;
    }
}
