package br.com.b_easy.MainActivityTests.Test;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/23/2015.
 */
public class SlideMenuTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DrawerLayout slideMenu;
    private ScrimInsetsFrameLayout mRelativeDrawer;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "br.com.b_easy.Activity.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public SlideMenuTest() throws ClassNotFoundException {
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
        //solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testSlide(){

        solo.drag(0, 300, 300, 300, 10);
        solo.sleep(1000);
        assertEquals("Slide Menu Closed", slideMenu.isDrawerOpen(mRelativeDrawer), true);

        solo.drag(300, 0, 300, 300, 10);
        solo.sleep(1000);
        assertEquals("Slide Menu Open", slideMenu.isDrawerOpen(mRelativeDrawer), false);

        solo.drag(300, 300, 300, 0, 10);
        solo.sleep(1000);
        assertEquals("Slide Menu Open", slideMenu.isDrawerOpen(mRelativeDrawer), false);

        solo.drag(300, 300, 50, 300, 10);
        solo.sleep(1000);
        assertEquals("Slide Menu Open", slideMenu.isDrawerOpen(mRelativeDrawer), false);

    }

    public void testContent(){

        assertEquals("Text: Home Not Found ", true, solo.searchText("Home"));
        assertEquals("Text: About Not Found ", true, solo.searchText("About"));
        assertEquals("Text: Tasks Not Found ", true, solo.searchText("Tasks"));

    }

}

