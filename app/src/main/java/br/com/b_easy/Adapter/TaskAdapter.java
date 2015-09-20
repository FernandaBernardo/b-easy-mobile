package br.com.b_easy.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.b_easy.Model.Task;
import br.com.b_easy.R;

/**
 * Created by Tiago on 9/20/2015.
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Task> mTasks;

    public TaskAdapter(Context mContext, List<Task> mTasks) {
        this.mContext = mContext;
        this.mTasks = mTasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TaskViewHolder){
            TaskViewHolder taskHolder = (TaskViewHolder) holder;
            Task aux = mTasks.get(position);
            taskHolder.tvTitle.setText(aux.getTitle());
            taskHolder.tvDescription.setText(aux.getDescription());
            taskHolder.tvFinalDate.setText(aux.getFinalDateString());
        }

    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvTitle, tvDescription, tvFinalDate;

        public TaskViewHolder(View v){
            super(v);
            this.tvTitle = (TextView) v.findViewById(R.id.tvTitleItemTask);
            this.tvDescription = (TextView) v.findViewById(R.id.tvSubtitleItemTask);
            this.tvFinalDate = (TextView) v.findViewById(R.id.tvFinalDateItemTask);
        }
    }

}
