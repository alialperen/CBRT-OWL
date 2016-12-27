package tr.gov.cbrt.hwOwl.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestHierarchy.class, TestInference.class, TestProcess.class })
public class AllTests {

}
