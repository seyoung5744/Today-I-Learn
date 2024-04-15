import java.util.ArrayList;
import java.util.List;

public class TestSuite {

    List<TestCase> tests = new ArrayList<>();

    public void add(TestCase testCase) {
        tests.add(testCase);
    }

    public void run(TestResult testResult) {
        tests.forEach(test -> test.run(testResult));
    }
}
