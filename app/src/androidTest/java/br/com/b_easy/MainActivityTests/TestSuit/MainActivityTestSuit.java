package br.com.b_easy.MainActivityTests.TestSuit;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestSuite;

import br.com.b_easy.Activity.MainActivity;
import br.com.b_easy.MainActivityTests.Test.PreConditionsTest;
import br.com.b_easy.MainActivityTests.Test.SlideMenuTest;

/**
 * Created by Tiago on 10/23/2015.
 */
public class MainActivityTestSuit extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTestSuit(Class<MainActivity> activityClass){
        super(activityClass);
    }

    public static TestSuite suite(){
        TestSuite t = new TestSuite();
        t.addTestSuite(PreConditionsTest.class);
        t.addTestSuite(SlideMenuTest.class);
        return t;
    }
}
