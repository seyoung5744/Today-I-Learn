public class TestResult {

    private int runCount;
    private int failCount;

    public void testStarted() {
        runCount += 1;
    }

    public void testFailed() {
        failCount += 1;
    }

    public String getSummary() {
        return String.format("%d run, %d failed", runCount, failCount);
    }
}
