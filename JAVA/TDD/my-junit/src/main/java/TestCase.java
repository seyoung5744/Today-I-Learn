import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestCase {

    private static final Logger logger = LoggerFactory.getLogger(TestCase.class);

    protected final String name;

    public TestCase(String name) {
        this.name = name;
    }

    public void run() { // 템플릿 메소드
        setUp();
        runTestCase();
    }

    public void setUp() {} // setUp 메소드 추가

    public void runTestCase() {
        try {
            logger.info("{} execute ", name);
            Method method = getClass().getMethod(name);
            method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
