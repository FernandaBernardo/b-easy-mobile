package br.com.b_easy.pojo;

import java.util.ArrayList;

/**
 * Created by Tiago on 9/30/2015.
 */
public class ListJson {
    private ArrayList<TesteJson> jsons;

    @Override
    public String toString() {
        String resp = "[ ";
        for(TesteJson aux : jsons){
            resp += aux.toString() + "\n";
        }
        resp += " ]";

        return resp;
    }
}
