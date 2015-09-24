package br.com.b_easy.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.Model.Task;
import br.com.b_easy.R;
import br.com.b_easy.Util;
import br.com.b_easy.customView.SlidingTabLayout;

/**
 * Created by Tiago on 9/12/2015.
 */
public class TaskFragment extends Fragment {

    private Subject subject;
    private TextView tvTabToDo, tvTabDoing, tvTabDone;
    private MainActivity context;
    private SlidingTabLayout tabs;
    private List<Task> toDoTasks, doingTasks, doneTasks;
    private Fragment[] fragments;
    private RelativeLayout rlToDo, rlDoing, rlDone;
    private RelativeLayout rlVisible;


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
        tvTabToDo = (TextView) v.findViewById(R.id.tvTabToDo);
        tvTabDoing = (TextView) v.findViewById(R.id.tvTabDoing);
        tvTabDone = (TextView) v.findViewById(R.id.tvTabDone);
        rlToDo = (RelativeLayout) v.findViewById(R.id.rlToDoFragment);
        rlDoing = (RelativeLayout)v.findViewById(R.id.rlDoingFragment);
        rlDone = (RelativeLayout) v.findViewById(R.id.rlDoneFragment);

        showFragment(rlToDo);

        tvTabToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "TODO");
                tvTabToDo.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlToDo);
            }
        });

        tvTabDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Doing");
                tvTabDoing.setTextColor(getResources().getColor(R.color.white));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabDone.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlDoing);
            }
        });

        tvTabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "Done");
                tvTabDone.setTextColor(getResources().getColor(R.color.white));
                tvTabDoing.setTextColor(getResources().getColor(R.color.whiteLight));
                tvTabToDo.setTextColor(getResources().getColor(R.color.whiteLight));
                showFragment(rlDone);
            }
        });


        //tvCardTitle = (TextView) v.findViewById(R.id.tvTitleCardFragmentTask);
        //tvCardTitle.setText(subject.getName());


//        mViewPager = (ViewPager) v.findViewById(R.id.pager);
//        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
//
//        updateFragment();

        return v;
    }

    public void showFragment(RelativeLayout rl){
        if(rlVisible != null)
            rlVisible.setVisibility(View.GONE);
        rl.setVisibility(View.VISIBLE);
        rlVisible = rl;
    }

    public void updateFragment(){
//        Log.d("UPDATE", "Calling");
//        fragments = new Fragment[3];
//        fragments[0] = new ToDoFragment();
//        fragments[1] = new DoingFragment();
//        fragments[2] = new DoneFragment();
//
//        toDoTasks = Util.getListTasks();
//        doingTasks= Util.getListTasks();
//        doneTasks = Util.getListTasks();
//
//        mPagerAdapter = new NavigationAdapter( context.getSupportFragmentManager(), context.getResources().getStringArray(R.array.TabsTaskFragments), fragments);
//        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setCurrentItem(1);
//
//
//        tabs.setCustomTabView(R.layout.custom_tab, R.id.tvTab);
//        tabs.setDistributeEvenly(true);
//        tabs.setViewPager(mViewPager);
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.accentColor);
//            }
//        });
    }

    public void deleteTask(Util.Task_Enum cod, int index){
        switch(cod){
            case DO_TO:  toDoTasks.remove(index);
                ((ToDoFragment)fragments[cod.getCod()]).updateAdapter();
                break;
            case DOING:  doingTasks.remove(index);
                ((DoingFragment)fragments[cod.getCod()]).updateAdapter();
                break;
            case DONE:  doneTasks.remove(index);
                ((DoneFragment)fragments[cod.getCod()]).updateAdapter();
                break;
        }
    }

    public void createTask(Util.Task_Enum cod, Task new_Task){
        switch(cod){
            case DO_TO:  toDoTasks.add(new_Task);
                ((ToDoFragment)fragments[cod.getCod()]).updateAdapter();
                break;
            case DOING:  doingTasks.add(new_Task);
                ((DoingFragment)fragments[cod.getCod()]).updateAdapter();
                break;
            case DONE:  doneTasks.add(new_Task);
                ((DoneFragment)fragments[cod.getCod()]).updateAdapter();
                break;
        }
    }

    public List<Task> getToDoTasks(){
        return this.toDoTasks;
    }

    public List<Task> getDoingTasks(){
        return this.doingTasks;
    }

    public List<Task> getDoneTasks(){
        return this.doneTasks;
    }

//    static class NavigationAdapter extends FragmentPagerAdapter{
//
//        private CharSequence[] mTitles;
//        private Fragment[] fragments;
//
//        public NavigationAdapter( FragmentManager fm, CharSequence[] mTitles, Fragment[] fragments) {
//            super(fm);
//            this.mTitles = mTitles;
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if(position < fragments.length)
//                return fragments[position];
//            else
//                return null;
//        }
//
//        @Override
//        public int getCount() {
//            return mTitles.length;
//        }
//
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//
//    }



}
