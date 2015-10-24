package br.com.b_easy.MainActivityTests.SlideMenuTest;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/23/2015.
 */
public class SlideMenuPreConditionsTest extends ActivityInstrumentationTestCase2 {
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
    public SlideMenuPreConditionsTest() throws ClassNotFoundException {
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

    public void testPreconditions(){
        solo.waitForActivity(MainActivity.class);
        assertNotNull("SlideMenu is Null", slideMenu);
        assertNotNull("RelativeDrawer is Null", mRelativeDrawer);
    }

}

