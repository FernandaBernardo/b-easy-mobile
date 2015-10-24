package br.com.b_easy.MainActivityTests.TaskFragmentPreConditionTest;

import android.graphics.Color;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/23/2015.
 */
public class TaskFragmentTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DrawerLayout slideMenu;
    private ScrimInsetsFrameLayout mRelativeDrawer;
    private FloatingActionButton FAButtonToDo;
    private FloatingActionButton FAButtonDoing;
    private FloatingActionButton FAButtonDone;
    private TextView tvTabToDo, tvTabDoing, tvTabDone;
    private RelativeLayout rlToDo, rlDoing, rlDone;
    private RecyclerView rvToDo, rvDoing, rvDone;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "br.com.b_easy.Activity.MainActivity";
    private static final String TASK_TAG_FRAGMENT = "TASK";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public TaskFragmentTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        slideMenu = (DrawerLayout) solo.getView(R.id.drawerLayout);
        mRelativeDrawer = (ScrimInsetsFrameLayout) solo.getView(R.id.relativeDrawer);

        solo.waitForActivity(MainActivity.class);
        solo.drag(0, 400, 300, 300, 10);
        solo.sleep(5000);
        assertEquals("Slide Menu Closed", true, slideMenu.isDrawerOpen(mRelativeDrawer));
        solo.clickInList(7);
        solo.waitForFragmentByTag(TASK_TAG_FRAGMENT);
        assertEquals("View Fragment Task UnVisible", true, ((AppCompatActivity) solo.getCurrentActivity()).getSupportFragmentManager().findFragmentByTag(TASK_TAG_FRAGMENT).isVisible());

        FAButtonToDo = (FloatingActionButton) solo.getView(R.id.fabToDoFragment);
        FAButtonDoing = (FloatingActionButton) solo.getView(R.id.fabDoingFragment);
        FAButtonDone = (FloatingActionButton) solo.getView(R.id.fabDoneFragment);
        tvTabToDo = (TextView) solo.getView(R.id.tvTabToDo);
        tvTabDoing = (TextView) solo.getView(R.id.tvTabDoing);
        tvTabDone = (TextView) solo.getView(R.id.tvTabDone);
        rlToDo = (RelativeLayout) solo.getView(R.id.rlToDoFragment);
        rlDoing = (RelativeLayout)solo.getView(R.id.rlDoingFragment);
        rlDone = (RelativeLayout) solo.getView(R.id.rlDoneFragment);
        rvToDo = (RecyclerView) solo.getView(R.id.rvToDoFragment);
        rvDoing = (RecyclerView) solo.getView(R.id.rvDoingFragment);
        rvDone = (RecyclerView) solo.getView(R.id.rvToDoneFragment);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testClickTabs(){
        solo.clickOnView(tvTabToDo);
        solo.sleep(1000);
        assertEquals(true, tvTabToDo.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.white));
        assertEquals(true, tvTabDoing.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(true, tvTabDone.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(true, rlToDo.getVisibility() == View.VISIBLE);
        assertEquals(false, rlDoing.getVisibility() == View.VISIBLE);
        assertEquals(false, rlDone.getVisibility() == View.VISIBLE);

        solo.clickOnView(tvTabDoing);
        solo.sleep(1000);
        assertEquals(true, tvTabToDo.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(true, tvTabDoing.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.white));
        assertEquals(true, tvTabDone.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(false, rlToDo.getVisibility() == View.VISIBLE);
        assertEquals(true, rlDoing.getVisibility() == View.VISIBLE);
        assertEquals(false, rlDone.getVisibility() == View.VISIBLE);

        solo.clickOnView(tvTabDone);
        solo.sleep(1000);
        assertEquals(true, tvTabToDo.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(true, tvTabDoing.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.whiteLight));
        assertEquals(true, tvTabDone.getCurrentTextColor() == solo.getCurrentActivity().getResources().getColor(R.color.white));
        assertEquals(false, rlToDo.getVisibility() == View.VISIBLE);
        assertEquals(false, rlDoing.getVisibility() == View.VISIBLE);
        assertEquals(true, rlDone.getVisibility() == View.VISIBLE);
    }

}
