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
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Model.Subject;
import br.com.b_easy.R;
import br.com.b_easy.customView.SlidingTabLayout;

/**
 * Created by Tiago on 9/12/2015.
 */
public class TaskFragment extends Fragment {

    private Subject subject;
    private TextView tvCardTitle;
    private MainActivity context;
    private ViewPager mViewPager;
    private NavigationAdapter mPagerAdapter;
    private SlidingTabLayout tabs;


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


        //tvCardTitle = (TextView) v.findViewById(R.id.tvTitleCardFragmentTask);
        //tvCardTitle.setText(subject.getName());


        mPagerAdapter = new NavigationAdapter( context.getSupportFragmentManager(), context.getResources().getStringArray(R.array.TabsTaskFragments));
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);

        tabs = (SlidingTabLayout) v.findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab, R.id.tvTab);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(mViewPager);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });


        return v;
    }

    private static class NavigationAdapter extends FragmentPagerAdapter{

        private CharSequence[] mTitles;

        public NavigationAdapter( FragmentManager fm, CharSequence[] mTitles) {
            super(fm);
            this.mTitles = mTitles;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;

            switch (position){
                case 0 : f = new ToDoFragment();
                    break;
                case 1 : f = new DoingFragment();
                    break;
                case 2 : f = new DoneFragment();
                    break;
                default : f = null;
            }

            return f;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }


    }



}
