public class TestCaseTest extends TestCase {

    private TestResult result;

    public TestCaseTest(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        result = new TestResult();
    }

    public void testTemplateMethod() {
        WasRun test = new WasRun("testMethod");
        test.run(result);
        Assert.assertEquals(test.log, "setUp testMethod tearDown ");
    }

    public void testResult() {
        WasRun test = new WasRun("testMethod");
        test.run(result);
        Assert.assertEquals(result.getSummary(), "1 run, 0 failed");
    }

    public void testFiledResult() {
        WasRun test = new WasRun("testBrokenMethod");
        test.run(result);
        Assert.assertEquals(result.getSummary(), "1 run, 1 failed");
    }

    public void testFailedResultFormatting() {
        result.testStarted();
        result.testFailed();
        Assert.assertEquals(result.getSummary(), "1 run, 1 failed");
    }

    public void testSuite() {
        TestSuite suite = new TestSuite();
        suite.add(new WasRun("testMethod"));
        suite.add(new WasRun("testBrokenMethod"));
        suite.run(result);
        Assert.assertEquals(result.getSummary(), "2 run, 1 failed");
    }

}
