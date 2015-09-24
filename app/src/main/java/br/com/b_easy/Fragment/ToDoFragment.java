package br.com.b_easy.Fragment;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Adapter.TaskAdapter;
import br.com.b_easy.Model.Task;
import br.com.b_easy.R;
import br.com.b_easy.Util;

/**
 * Created by Tiago on 9/12/2015.
 */
public class ToDoFragment extends Fragment {

    private List<Task> tasks;
    private RecyclerView rv;
    private TaskAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_to_do,container,false);
        tasks = ((MainActivity)getActivity()).getTasks(Util.Task_Enum.DO_TO);

        rv = (RecyclerView) v.findViewById(R.id.rvToDoFragment);
//        adapter = new TaskAdapter((MainActivity)getContext(), tasks, Util.Task_Enum.DO_TO);
        Util.setRecicleView(getContext(), rv, false);
        rv.setAdapter(adapter);

        return v;
    }

    public void updateAdapter(){rv.getAdapter().notifyDataSetChanged();
    }

}
