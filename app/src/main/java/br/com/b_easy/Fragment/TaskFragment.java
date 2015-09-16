package br.com.b_easy.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.R;

/**
 * Created by Tiago on 9/12/2015.
 */
public class TaskFragment extends Fragment {

    private Subject subject;
    private TextView tvCardTitle;
    private MainActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

        }

        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        context = ((MainActivity)getActivity());
        subject = context.getSelectedSubject();

        tvCardTitle = (TextView) v.findViewById(R.id.tvTitleCardFragmentTask);
        tvCardTitle.setText(subject.getName());

        return v;
    }



}
