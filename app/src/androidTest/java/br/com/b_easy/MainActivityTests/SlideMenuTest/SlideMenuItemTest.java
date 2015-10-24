package br.com.b_easy.MainActivityTests.SlideMenuTest;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/23/2015.
 */
public class SlideMenuItemTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DrawerLayout slideMenu;
    private ScrimInsetsFrameLayout mRelativeDrawer;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "br.com.b_easy.Activity.MainActivity";
    private static final String TASK_TAG_FRAGMENT = "TASK";
    private static final String HOME_TAG_FRAGMENT = "HOME";
    private static final String ABOUT_TAG_FRAGMENT = "ABOUT";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public SlideMenuItemTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        slideMenu = (DrawerLayout) solo.getView(R.id.drawerLayout);
        mRelativeDrawer = (ScrimInsetsFrameLayout) solo.getView(R.id.relativeDrawer);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testItemSubjectListClick(){

        solo.waitForActivity(MainActivity.class);

        solo.drag(0, 400, 300, 300, 10);
        solo.sleep(5000);
        assertEquals("Slide Menu Closed", true, slideMenu.isDrawerOpen(mRelativeDrawer));
        Log.d("textView", solo.clickInList(8).get(0).getText().toString());
        solo.waitForFragmentByTag(TASK_TAG_FRAGMENT);
        assertEquals("View Fragment Task UnVisible", true, ((AppCompatActivity) solo.getCurrentActivity()).getSupportFragmentManager().findFragmentByTag(TASK_TAG_FRAGMENT).isVisible());
        assertEquals(true, solo.searchText("Subject #5"));
        assertEquals(false, solo.searchText("B-Easy"));

    }

    public void testItemHomeSubjectListClick(){
        solo.waitForActivity(MainActivity.class);
        solo.drag(0, 400, 300, 300, 10);
        solo.sleep(5000);
        assertEquals("Slide Menu Closed", true, slideMenu.isDrawerOpen(mRelativeDrawer));
        Log.d("textView", solo.clickInList(2).get(0).getText().toString());
        solo.waitForFragmentByTag(HOME_TAG_FRAGMENT);
        assertEquals("View Fragment Task UnVisible", true, ((AppCompatActivity) solo.getCurrentActivity()).getSupportFragmentManager().findFragmentByTag(HOME_TAG_FRAGMENT).isVisible());
        assertEquals(true, solo.searchText("B-Easy"));
    }

    public void testItemAboutListClick(){
        /*solo.waitForActivity(MainActivity.class);
        solo.drag(0, 400, 300, 300, 10);
        solo.sleep(3000);
        assertEquals("Slide Menu Closed", true, slideMenu.isDrawerOpen(mRelativeDrawer));
        Log.d("textView", solo.clickInList(10).get(0).getText().toString());
        solo.waitForFragmentByTag(ABOUT_TAG_FRAGMENT);
        assertEquals("View Fragment Task UnVisible", true, ((AppCompatActivity) solo.getCurrentActivity()).getSupportFragmentManager().findFragmentByTag(ABOUT_TAG_FRAGMENT).isVisible());
        assertEquals(true,solo.searchText("B-Easy"));*/
    }

    public void testItemPerfilListClick(){

    }

}
