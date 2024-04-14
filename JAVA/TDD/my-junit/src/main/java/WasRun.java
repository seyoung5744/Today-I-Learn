public class WasRun extends TestCase {

    public boolean wasRun;
    public boolean wasSetUp;

    public WasRun(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        this.wasSetUp = true;
    }

    public void testMethod() {
        this.wasRun = true;
    }

}
