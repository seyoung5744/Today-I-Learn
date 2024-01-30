package chap06.collector;

import chap03.FunctionalInterfaceTest.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectorHarness {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ExecutorService 생성
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // CompletableFuture로 각 메소드를 비동기적으로 실행
        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(() -> execute(PartitionPrimeNumbers::partitionPrimes), executorService);
        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(() -> execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector), executorService);
        CompletableFuture<Long> future3 = CompletableFuture.supplyAsync(() -> execute(PartitionPrimeNumbers::partitionPrimesWithInlineCollector), executorService);

        // 모든 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        allOf.join();

        // 결과 출력
        System.out.println("Partitioning done in: " + future1.get() + " msecs");
        System.out.println("Partitioning done in: " + future2.get() + " msecs");
        System.out.println("Partitioning done in: " + future3.get() + " msecs");

        // ExecutorService 종료
        executorService.shutdown();

//        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimes) + " msecs");
//        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector) + " msecs");
//        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithInlineCollector) + " msecs");
    }


    public static synchronized long execute(Consumer<Integer> primePartitioner) {
        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) { // 테스트 10번 반복
            long start = System.nanoTime();
            primePartitioner.accept(1_000_000); // 백만 개의 숫자를 소수와 비소수로 분할
            long duration = (System.nanoTime() - start) / 1_000_000; // duration 을 밀리초 단위로 측정
            if (duration < fastest) {
                fastest = duration; // 가장 빨리 실행되었는지 확인한다.
            }
            System.out.println("done in " + duration);
        }
        return fastest;
    }
}
