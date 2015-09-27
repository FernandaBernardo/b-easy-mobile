package br.com.b_easy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.R;

/**
 * Created by Tiago on 9/12/2015.
 */
public class HomeFragment extends Fragment {

    private FloatingActionButton fabButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        fabButton = (FloatingActionButton) v.findViewById(R.id.fabHomeFragment);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddOptions();
            }
        });

        return v;
    }

    public void createDialogAddOptions(){

        new MaterialDialog.Builder(getContext())
                .title("Selecione a Ação")
                .items( R.array.optionsFABNoSubject)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Log.d("Click", "OnSelection " + which);
                        if (which == 0) {
                            createDialogAddSubject();
                        }
                    }
                })
                .show();
    }

    public void createDialogAddSubject(){

        MaterialDialog dialog =  new MaterialDialog.Builder(getContext())
                .title("Adicionar Matéria")
                .customView(R.layout.fragment_subject_create, true)
                .positiveText("Concluir")
                .negativeText("Cancelar")
                .negativeColorRes(R.color.secondaryTextColor)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

                        boolean status = false;

                        String sName = ((EditText) dialog.findViewById(R.id.etTitleFragmentSubjectCreate)).getText().toString();

                        status = ((MainActivity)getActivity()).saveSubject(new SubjectBD(sName));

                        if (status) {
                            Log.d("DataBase", "SUCESS: Saved Subject");
                            getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                        else
                            Log.e("DataBase", "ERROR: Save Subject");

                        super.onPositive(dialog);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
    }



}
