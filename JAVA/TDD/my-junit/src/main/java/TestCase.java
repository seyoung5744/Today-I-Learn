import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestCase {

    private static final Logger logger = LoggerFactory.getLogger(TestCase.class);

    protected final String name;

    public TestCase(String name) {
        this.name = name;
    }

    public TestResult run() { // 템플릿 메소드
        TestResult result = new TestResult();
        result.testStarted();
        setUp();
        runTestCase(result);
        tearDown();
        return result;
    }

    public void setUp() {} // setUp 메소드 추가

    public void runTestCase(TestResult testResult) {
        try {
            logger.info("{} execute ", name);
            Method method = getClass().getMethod(name);
            method.invoke(this);
        } catch (Exception e) {
            testResult.testFailed();
        }
    }

    public void tearDown() {}
}
