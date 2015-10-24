package br.com.b_easy.MainActivityTests.HomeFragmentTest;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;
import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/23/2015.
 */
public class HomeFragmentTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DrawerLayout slideMenu;
    private ScrimInsetsFrameLayout mRelativeDrawer;
    private FloatingActionButton FAButton;
    private EditText et;


    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "br.com.b_easy.Activity.MainActivity";
    private static final String HOME_TAG_FRAGMENT = "HOME";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public HomeFragmentTest() throws ClassNotFoundException {
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
        solo.clickInList(2);
        solo.waitForFragmentByTag(HOME_TAG_FRAGMENT);
        assertEquals("View Fragment Task UnVisible", true, ((AppCompatActivity) solo.getCurrentActivity()).getSupportFragmentManager().findFragmentByTag(HOME_TAG_FRAGMENT).isVisible());
        FAButton = (FloatingActionButton) solo.getView(R.id.fabHomeFragment);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddValideSubject() {
        solo.waitForActivity(MainActivity.class);
        solo.clickOnView(FAButton);
        solo.waitForDialogToOpen();
        solo.clickInList(1);
        solo.waitForDialogToOpen();

        et = (EditText) solo.getView(R.id.etTitleFragmentSubjectCreate);
        assertNotNull(et);

        solo.clearEditText(et);
        solo.enterText(et, "PSI");
        solo.waitForText("PSI");
        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));

        solo.waitForActivity(MainActivity.class);
        solo.drag(0, 400, 300, 300, 10);
        solo.sleep(5000);
        assertEquals(true, solo.searchText("PSI"));
    }

    public void testAddInvalideSubject(){
        solo.waitForActivity(MainActivity.class);
        solo.clickOnView(FAButton);
        solo.waitForDialogToOpen();
        solo.clickInList(1);
        solo.waitForDialogToOpen();

        et = (EditText) solo.getView(R.id.etTitleFragmentSubjectCreate);
        assertNotNull(et);

        solo.clearEditText(et);
        solo.enterText(et, "");
        solo.waitForText("");
        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));
        solo.waitForText("Invalid Subject Name");
        solo.clickOnView(solo.getView(R.id.buttonDefaultNegative));

    }
}
