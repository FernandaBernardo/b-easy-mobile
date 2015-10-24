package br.com.b_easy.MainActivityTests.SlideMenuTest;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Condition;
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
        solo.sleep(2000);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        slideMenu = (DrawerLayout) solo.getView(R.id.drawerLayout);
        mRelativeDrawer = (ScrimInsetsFrameLayout) solo.getView(R.id.relativeDrawer);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testSlide(){
        solo.waitForActivity(MainActivity.class);

        solo.drag(0, 400, 300, 300, 1);
        solo.waitForView(solo.getView(R.id.drawerLayout));
        solo.sleep(5000);
        assertEquals("Slide Menu Closed", true, slideMenu.isDrawerOpen(mRelativeDrawer));

        solo.drag(300, 0, 300, 300, 1);
        solo.sleep(5000);
        assertEquals("Slide Menu Open", false, slideMenu.isDrawerOpen(mRelativeDrawer));

        solo.drag(300, 300, 300, 0, 1);
        solo.sleep(2000);
        assertEquals("Slide Menu Open", false, slideMenu.isDrawerOpen(mRelativeDrawer));

        solo.drag(300, 300, 50, 300, 1);
        solo.sleep(2000);
        assertEquals("Slide Menu Open", false, slideMenu.isDrawerOpen(mRelativeDrawer));

    }

    public void testClickToggleButton(){
        solo.waitForActivity(MainActivity.class);
        solo.clickOnScreen(45F,80F);
        solo.waitForView(solo.getView(R.id.drawerLayout));
        assertEquals("Slide Menu Closed",true, slideMenu.isDrawerOpen(mRelativeDrawer));
    }

    public void testContent(){
        solo.waitForActivity(MainActivity.class);
        solo.clickOnScreen(45F,80F);
        solo.sleep(5000);
        assertEquals("Text: Home Not Found ", true, solo.searchText("Home"));
        assertEquals("Text: About Not Found ", true, solo.searchText("About"));
        assertEquals("Text: Tasks Not Found ", true, solo.searchText("Tasks"));

    }

}

