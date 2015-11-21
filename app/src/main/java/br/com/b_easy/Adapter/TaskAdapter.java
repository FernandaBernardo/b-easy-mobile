package br.com.b_easy.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.DataBaseModel.TaskBD;
import br.com.b_easy.Fragment.TaskFragment;
import br.com.b_easy.Model.Task;
import br.com.b_easy.R;
import br.com.b_easy.Util;

/**
 * Created by Tiago on 9/20/2015.
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private TaskFragment fragment;
    private List<TaskBD> mTasks;
    private Util.Task_Enum mCod;

    public TaskAdapter(Context mContext, List<TaskBD> mTasks, Util.Task_Enum mCod, TaskFragment fragment) {
        this.mContext = mContext;
        this.mTasks = mTasks;
        this.mCod = mCod;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof TaskViewHolder){
            final TaskViewHolder taskHolder = (TaskViewHolder) holder;
            TaskBD aux = mTasks.get(position);
            taskHolder.tvTitle.setText(aux.getTitle());
            taskHolder.tvDescription.setText(aux.getDescription());
            taskHolder.tvFinalDate.setText(aux.getFinalDateString());

            taskHolder.ivNext.setVisibility(View.VISIBLE);
            taskHolder.ivBack.setVisibility(View.VISIBLE);

            if(mCod == Util.Task_Enum.DO_TO)
                taskHolder.ivBack.setVisibility(View.GONE);
            if(mCod == Util.Task_Enum.DONE)
                taskHolder.ivNext.setVisibility(View.GONE);


            taskHolder.ivNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean success = false;

                    Toast.makeText(mContext, "Click on Next", Toast.LENGTH_SHORT).show();
                    if(mCod == Util.Task_Enum.DO_TO)
                      fragment.createTask(mCod,Util.Task_Enum.DOING, mTasks.get(position));

                    else if(mCod == Util.Task_Enum.DOING)
                       fragment.createTask(mCod,Util.Task_Enum.DONE, mTasks.get(position));

                    Log.d("DataBase", "Move: " + (success ? "SUCCESS" : "FAIL"));
                }
            });

            taskHolder.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean success = false;

                    Toast.makeText(mContext, "Click on Back", Toast.LENGTH_SHORT).show();
                    if(mCod == Util.Task_Enum.DONE)
                        fragment.createTask(mCod,Util.Task_Enum.DOING, mTasks.get(position));

                    else if(mCod == Util.Task_Enum.DOING)
                        fragment.createTask(mCod,Util.Task_Enum.DO_TO,mTasks.get(position));

                    Log.d("DataBase", "Move: " + (success ? "SUCCESS" : "FAIL"));

                }
            });

            taskHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Click on Edit", Toast.LENGTH_SHORT).show();
                    fragment.createDialogUpdateTask(mCod,position);
                }
            });

            taskHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Click on Delete", Toast.LENGTH_SHORT).show();
                    boolean success = fragment.deleteTask(mCod, position);
                    Log.d("DataBase", "Delete: " + ( success ? "SUCCESS" : "FAIL"));
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvTitle, tvDescription, tvFinalDate;
        protected ImageView ivNext, ivBack, ivEdit, ivDelete;
        protected CardView card;
        protected View v;

        public TaskViewHolder(View v){
            super(v);
            this.v = v;
            this.tvTitle = (TextView) v.findViewById(R.id.tvTitleItemTask);
            this.tvDescription = (TextView) v.findViewById(R.id.tvSubtitleItemTask);
            this.tvFinalDate = (TextView) v.findViewById(R.id.tvFinalDateItemTask);
            this.ivNext = (ImageView) v.findViewById(R.id.ivNextItemTask);
            this.ivBack = (ImageView) v.findViewById(R.id.ivBackItemTask);
            this.ivEdit = (ImageView) v.findViewById(R.id.ivEditItemTask);
            this.ivDelete = (ImageView) v.findViewById(R.id.ivDeleteItemTask);
            this.card = (CardView) v.findViewById(R.id.cardItemTask);
        }
    }

}
