package pt.isec.a21220740.account;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnitRun {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AccountJUnit.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Time: " + result.getRunTime() + " ms.");
        System.out.println("Results: " + (result.getRunCount() - result.getFailureCount()) +
                " out of " + result.getRunCount() + " tests were successful.");
    }
}