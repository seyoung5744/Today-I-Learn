public class XUnitTest {

    public static void main(String[] args) {
        // testSuite 생성
        TestSuite testSuite = new TestSuite();

        // 테스트 추가
        testSuite.add(new TestCaseTest("testTemplateMethod"));
        testSuite.add(new TestCaseTest("testResult"));
        testSuite.add(new TestCaseTest("testFailedResultFormatting"));
        testSuite.add(new TestCaseTest("testFiledResult"));
        testSuite.add(new TestCaseTest("testSuite"));

        // collecting parameter 생성
        TestResult testResult = new TestResult();

        // testSuite 에게 전달
        testSuite.run(testResult);

        // 종합 결과 출력
        System.out.println("[TEST RESULT] = " + testResult.getSummary());
    }

}
