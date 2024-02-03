import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class CurrentMapTest {

    @Test
    void hashMap() {
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("a", null);
        hashMap.put(null, "a");

        assertThat(hashMap.get(null)).isEqualTo("a");
        assertThat(hashMap.get("a")).isNull();
    }

    @Test
    void hashTable() {
        Hashtable<String, String> hashtable = new Hashtable<>();

        assertThrows(NullPointerException.class, () -> hashtable.put("a", null));
        assertThrows(NullPointerException.class, () -> hashtable.put(null, "a"));
    }

    @Test
    void concurrentHashMap() {
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        assertThrows(NullPointerException.class, () -> concurrentHashMap.put("a", null));
        assertThrows(NullPointerException.class, () -> concurrentHashMap.put(null, "a"));
    }

    private static final int MAX_THREADS = 10;

    private static final HashMap<String, Integer> hashMap = new HashMap<>();
    private static final Hashtable<String, Integer> hashTable = new Hashtable<>();
    private static final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    private static final Map<String, Integer> synHashMap = Collections.synchronizedMap(new HashMap<>());

    @Test
    void concurrencyTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        for (int i = 0; i < MAX_THREADS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 10_000; j++) {
                    String key = String.valueOf(j);

                    hashMap.put(key, j);
                    hashTable.put(key, j);
                    concurrentHashMap.put(key, j);
                    synHashMap.put(key, j);
                }
            });
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hashMap size : " + hashMap.size()); // -> 10205
        assertThat(hashMap.size()).isNotEqualTo(10000);

        System.out.println("hashtable size : " + hashTable.size()); // -> 10000
        assertThat(hashTable.size()).isEqualTo(10000);

        System.out.println("concurrentHashMap size : " + concurrentHashMap.size()); // -> 10000
        assertThat(concurrentHashMap.mappingCount()).isEqualTo(10000);

        System.out.println("synHashMap size : " + synHashMap.size()); // -> 10000
        assertThat(synHashMap.size()).isEqualTo(10000);
    }

    @Test
    @RepeatedTest(5)
    void performanceTest() throws InterruptedException {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        Map<Integer, Integer> synHashMap = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        long hashtableTime = measure(hashtable);
        long synHashMapTime = measure(synHashMap);
        long concurrentHashMapTime = measure(concurrentHashMap);

        System.out.println("hashTableTime = " + hashtableTime);
        System.out.println("synHashMapTime = " + synHashMapTime);
        System.out.println("concurrentHashMapTime = " + concurrentHashMapTime);
        System.out.println();
    }

    private static long measure(Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        int count = 200_000;

        long startTime = System.nanoTime();

        for (int i = 0; i < MAX_THREADS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < count; j++) {
                    int value = ThreadLocalRandom.current().nextInt();
                    map.put(value, value);
                    map.get(value);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / count * MAX_THREADS;
    }
}
