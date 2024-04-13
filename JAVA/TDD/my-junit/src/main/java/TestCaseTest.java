public class TestCaseTest extends TestCase {

    public TestCaseTest(String name) {
        super(name);
    }

    // 하나의 test 해야할 코드라고 생각하자.
    public void testRunning() {
        WasRun test = new WasRun("testMethod"); // 실행할 테스트 메서드의 이름 전달
        System.out.println("test.wasRun() = " + test.wasRun);
        test.run();
        System.out.println("test.wasRun() = " + test.wasRun);
    }

}
