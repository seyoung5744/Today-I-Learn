package thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class CasMainV2 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        int result = atomicInteger.incrementAndGet();
        System.out.println("result = " + result);

        int result2 = atomicInteger.incrementAndGet();
        System.out.println("result2 = " + result2);

        // incrementAndGet() 구현
        int resultValue1 = incrementAndGet(atomicInteger);
        System.out.println("resultValue1 = " + resultValue1);

        int resultValue2 = incrementAndGet(atomicInteger);
        System.out.println("resultValue2 = " + resultValue2);
    }

    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;

        do {
            // 1-1. thread1: 0
            // 2-1. thread1: 1
            getValue = atomicInteger.get();
            log("getValue: " + getValue);
            // 1-2. thread2 value -> 1
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result: " + result);
        } while (!result);

        return getValue + 1;
    }
}
