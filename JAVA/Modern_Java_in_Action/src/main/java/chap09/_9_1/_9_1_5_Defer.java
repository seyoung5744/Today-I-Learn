package chap09._9_1;

import java.util.function.Supplier;

// 참고 : https://velog.io/@tkdgus5828/%EB%9E%8C%EB%8B%A4%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%A1%B0%EA%B1%B4%EB%B6%80-%EC%97%B0%EA%B8%B0-%ED%8F%89%EA%B0%80%EC%8B%A4%ED%96%89
public class _9_1_5_Defer {

    public static String makeLog() {
        try {
            Thread.sleep(1000); // 10초 기다림 (== 10000ms)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "new Log Msg";
    }

    public static void printLogEager(final Boolean flag, final String msg) {
        if (flag) {
            System.out.println(msg);
        }
    }

    public static void printLogLazy(final Boolean flag, Supplier<String> msgSupplier) {
        if (flag) {
            System.out.println(msgSupplier.get());
        }
    }

    public static void main(String[] args) {
        Boolean flag = false;
        printLogEager(flag, makeLog());
        System.out.println("?"); // to divide eager and lazy method
        printLogLazy(flag, () -> makeLog());
    }

}
