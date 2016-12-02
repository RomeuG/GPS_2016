package com.example.romanpr.passwordmanager;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by romeu on 11/26/16.
 */
public class JUnitRun {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CryptoJUnit.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        
        System.out.println("Time: " + result.getRunTime() + " ms.");
        System.out.println("Results: " + (result.getRunCount() - result.getFailureCount()) +
                " out of " + result.getRunCount() + " tests were successful.");
    }
}
