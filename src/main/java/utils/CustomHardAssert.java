package utils;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

/**
 * A sophisticated extension of TestNG's Hard Assertion.
 * It immediately halts the test on failure but guarantees a screenshot is captured before stopping.
 */
public class CustomHardAssert extends Assertion {

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        // Take a screenshot at the EXACT millisecond this hard assertion fails
        ScreenshotUtil.takeScreenshotAndAttachToAllure();
        
        // Pass the failure back to TestNG to immediately halt the test
        super.onAssertFailure(assertCommand, ex);
    }
}
