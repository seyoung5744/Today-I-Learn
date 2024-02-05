package chap09._9_1;

public class _9_1_2_RefactoringTest {

    public void test() {
        Runnable r1 = () -> {
            System.out.println(this.getClass());
        };
        r1.run();

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(this.getClass());
            }
        };
        r2.run();
    }

    public static void main(String[] args) {

        // 1.
        _9_1_2_RefactoringTest test = new _9_1_2_RefactoringTest();
        test.test();

        int a = 10;

        Runnable r1 = () -> {
//            int a = 2;
            System.out.println(a);
        };
        r1.run();

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
        r2.run();

        doSomeThing(new Task() {
            @Override
            public void execute() {
                System.out.println("Danger danger!!");
            }
        });

        /**
         * Ambiguous method call. Both
         * doSomeThing (Runnable) in Test and
         * doSomeThing (Task) in Test match
         **/
        // doSomeThing(() -> System.out.println("Danger danger!!"));
        doSomeThing((Task) () -> System.out.println("Danger danger!!"));

    }

    interface Task {

        public void execute();
    }

    public static void doSomeThing(Runnable r) {
        r.run();
    }

    public static void doSomeThing(Task a) {
        a.execute();
    }

}
