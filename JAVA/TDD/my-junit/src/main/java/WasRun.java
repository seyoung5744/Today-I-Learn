public class WasRun extends TestCase {

    public String log;

    public WasRun(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        this.log = "setUp ";
    }

    public void testMethod() {
        this.log = this.log + "testMethod ";
    }

    @Override
    public void tearDown() {
        this.log = this.log + "tearDown ";
    }
}
