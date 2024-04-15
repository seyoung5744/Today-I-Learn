public class TestCaseTest extends TestCase {

    public TestCaseTest(String name) {
        super(name);
    }

    public void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");
        test.run();
        Assert.assertEquals(test.log, "setUp testMethod tearDown ");
    }

    public void testResult() {
        WasRun test = new WasRun("testMethod");
        TestResult result = test.run();
        Assert.assertEquals(result.getSummary(), "1 run, 0 failed");
    }

    public void testFiledResult() {
        WasRun test = new WasRun("testBrokenMethod");
        TestResult result = test.run();
        Assert.assertEquals(result.getSummary(), "1 run, 1 failed");
    }

}
