package com.ninehackers.speedgear;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.ninehackers.speedgear.stanley.StanleyDemo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ApplicationTestCase<Application> {
    private final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
        doStanleyTest();
    }

    private void doStanleyTest(){
        Log.d(TAG, "doStanleyTest.");
        StanleyDemo stanleyDemo=new StanleyDemo();
        assertEquals(true, stanleyDemo.IsStanley());
    }

}