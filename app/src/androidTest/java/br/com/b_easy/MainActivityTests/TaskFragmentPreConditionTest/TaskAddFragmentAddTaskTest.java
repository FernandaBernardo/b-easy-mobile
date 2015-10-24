package br.com.b_easy.MainActivityTests.TaskFragmentPreConditionTest;

import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.robotium.solo.Solo;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.R;

/**
 * Created by Tiago on 10/24/2015.
 */
public class TaskAddFragmentAddTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DrawerLayout slideMenu;
    private ScrimInsetsFrameLayout mRelativeDrawer;
    private FloatingActionButton FAButtonToDo;
    private FloatingActionButton FAButtonDoing;
    private FloatingActionButton FAButtonDone;
    private TextView tvTabToDo, tvTabDoing, tvTabDone;
    private EditText title,desc,rel;


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
    public TaskAddFragmentAddTaskTest() throws ClassNotFoundException {
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


    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddValideTask() {
        solo.waitForActivity(MainActivity.class);
        solo.clickOnView(tvTabToDo);
        solo.clickOnView(FAButtonToDo);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "Entrega Final PSI");
        solo.waitForText("Entrega Final PSI");

        solo.clearEditText(desc);
        solo.enterText(desc, "Entrega do Projeto Final e Apresentacao");
        solo.waitForText("Entrega do Projeto Final e Apresentacao");

        solo.clearEditText(rel);
        solo.enterText(rel, "0");
        solo.waitForText("0");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));

        solo.waitForDialogToClose();
        assertEquals(true, solo.searchText("Entrega Final PSI"));
        assertEquals(true, solo.searchText("Entrega do Projeto Final e Apresentacao"));


        solo.clickOnView(tvTabDoing);
        solo.clickOnView(FAButtonDoing);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "Apresentacao II");
        solo.waitForText("Apresentacao II");

        solo.clearEditText(desc);
        solo.enterText(desc, "Apresentacao do Projeto de PSI, segunda parte");
        solo.waitForText("Apresentacao do Projeto de PSI, segunda parte");

        solo.clearEditText(rel);
        solo.enterText(rel, "0");
        solo.waitForText("0");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));

        solo.waitForDialogToClose();
        assertEquals(true, solo.searchText("Apresentacao II"));
        assertEquals(true, solo.searchText("Apresentacao do Projeto de PSI, segunda parte"));


        solo.clickOnView(tvTabDone);
        solo.clickOnView(FAButtonDone);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "Apresentacao I");
        solo.waitForText("Apresentacao I");

        solo.clearEditText(desc);
        solo.enterText(desc, "Apresentacao do Projeto de PSI, primeira parte");
        solo.waitForText("Apresentacao do Projeto de PSI, primeira parte");

        solo.clearEditText(rel);
        solo.enterText(rel, "0");
        solo.waitForText("0");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));

        solo.waitForDialogToClose();
        assertEquals(true, solo.searchText("Apresentacao I"));
        assertEquals(true, solo.searchText("Apresentacao do Projeto de PSI, primeira parte"));


    }

    public void testAddInvalidTask() {
        solo.waitForActivity(MainActivity.class);
        solo.clickOnView(tvTabToDo);
        solo.clickOnView(FAButtonToDo);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "");
        solo.waitForText("");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));
        solo.waitForText("Invalid Task Name");
        solo.clickOnView(solo.getView(R.id.buttonDefaultNegative));



        solo.clickOnView(tvTabDoing);
        solo.clickOnView(FAButtonDoing);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "");
        solo.waitForText("");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));
        solo.waitForText("Invalid Task Name");
        solo.clickOnView(solo.getView(R.id.buttonDefaultNegative));


        solo.clickOnView(tvTabDone);
        solo.clickOnView(FAButtonDone);
        solo.waitForDialogToOpen();
        solo.clickInList(2);
        solo.waitForDialogToOpen();

        title = (EditText) solo.getView(R.id.etTitleFragmentTaskCreate);
        desc = (EditText) solo.getView(R.id.etDescritionFragmentTaskCreate);
        rel = (EditText) solo.getView(R.id.etRelevanciaFragmentTaskCreate);

        assertNotNull(title);
        assertNotNull(desc);
        assertNotNull(rel);

        solo.clearEditText(title);
        solo.enterText(title, "");
        solo.waitForText("");

        solo.clickOnView(solo.getView(R.id.buttonDefaultPositive));
        solo.waitForText("Invalid Task Name");
        solo.clickOnView(solo.getView(R.id.buttonDefaultNegative));
    }


}
