package br.com.b_easy.MainActivityTests.TestSuit;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestSuite;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.Fragment.TaskFragment;
import br.com.b_easy.MainActivityTests.HomeFragmentTest.HomeFragmentPreConditionsTest;
import br.com.b_easy.MainActivityTests.SlideMenuTest.SlideMenuPreConditionsTest;
import br.com.b_easy.MainActivityTests.SlideMenuTest.SlideMenuItemTest;
import br.com.b_easy.MainActivityTests.SlideMenuTest.SlideMenuTest;
import br.com.b_easy.MainActivityTests.TaskFragmentPreConditionTest.TaskFragmentPreConditionsTest;
import br.com.b_easy.MainActivityTests.TaskFragmentPreConditionTest.TaskFragmentTest;

/**
 * Created by Tiago on 10/23/2015.
 */
public class MainActivityTestSuit extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTestSuit(Class<MainActivity> activityClass){
        super(activityClass);
    }

    public static TestSuite suite(){
        TestSuite t = new TestSuite();
        t.addTestSuite(SlideMenuPreConditionsTest.class);
        t.addTestSuite(SlideMenuTest.class);
        t.addTestSuite(SlideMenuItemTest.class);
        t.addTestSuite(HomeFragmentPreConditionsTest.class);
        t.addTestSuite(TaskFragmentPreConditionsTest.class);
        t.addTestSuite(TaskFragmentTest.class);
        return t;
    }
}
