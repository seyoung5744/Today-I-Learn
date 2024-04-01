package appc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * http://mail.openjdk.java.net/pipermail/lambda-dev/2013-November/011516.html 참고함
 */
public class StreamForker<T> {

    private final Stream<T> stream;
    private final Map<Object, Function<Stream<T>, ?>> forks = new HashMap<>();

    public StreamForker(Stream<T> stream) {
        this.stream = stream;
    }

    public StreamForker<T> fork(Object key, Function<Stream<T>, ?> f) {
        forks.put(key, f); // 스트림에 적용할 함수 저장
        return this; // 유연하게 fork 메서드를 여러 번 호출할 수 있도록 this 반환
    }

    public Results getResults() {
        ForkingStreamConsumer<T> consumer = build();

        try {
            stream.sequential().forEach(consumer);
        } finally {
            consumer.finish();
        }
        return consumer;
    }

    private ForkingStreamConsumer<T> build() {
        List<BlockingQueue<T>> queues = new ArrayList<>(); // 각각의 연산을 저장할 큐 리스트를 생성

        // 연산 결과를 포함하는 Future를 연산을 식별할 수 있는 키에 대응시켜 맵에 저장
        HashMap<Object, Future<?>> actions = forks.entrySet().stream().reduce(
            new HashMap<Object, Future<?>>(),
            (map, e) -> {
                map.put(e.getKey(), getOperationResult(queues, e.getValue()));
                return map;
            },
            (m1, m2) -> {
                m1.putAll(m2);
                return m1;
            }
        );

        return new ForkingStreamConsumer<>(queues, actions);
    }

    private Future<?> getOperationResult(List<BlockingQueue<T>> queues, Function<Stream<T>, ?> f) {
        BlockingQueue<T> queue = new LinkedBlockingQueue<>();
        queues.add(queue); // 큐를 만들어 큐 리스트에 추가
        Spliterator<T> spliterator = new BlockingQueueSpliterator<>(queue);
        Stream<T> source = StreamSupport.stream(spliterator, false);
        return CompletableFuture.supplyAsync(() -> f.apply(source));
    }

    public static interface Results {

        public <R> R get(Object key);
    }

    // 스트림 요소를 여러 큐로 추가하는 ForkingStreamConsumer
    public static class ForkingStreamConsumer<T> implements Consumer<T>, Results {

        static final Object END_OF_STREAM = new Object();

        private final List<BlockingQueue<T>> queues;
        private final Map<Object, Future<?>> actions;

        public ForkingStreamConsumer(List<BlockingQueue<T>> queues, Map<Object, Future<?>> actions) {
            this.queues = queues;
            this.actions = actions;
        }

        @Override
        public void accept(T t) {
            queues.forEach(q -> q.add(t)); // 스트림에서 탐색한 요소를 모든 큐로 전달
        }

        @Override
        public <R> R get(Object key) {
            try {
                return ((Future<R>) actions.get(key)).get(); // 키에 대응하는 동작의 결과를 반환. Future의 계산 완료 대기
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void finish() {
            accept((T) END_OF_STREAM); // 스트림의 끝을 알리는 마지막 요소를 큐에 삽입
        }
    }

    private static class BlockingQueueSpliterator<T> implements Spliterator<T> {

        private final BlockingQueue<T> q;

        public BlockingQueueSpliterator(BlockingQueue<T> q) {
            this.q = q;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            T t;

            while (true) {
                try {
                    t = q.take();
                    break;
                } catch (InterruptedException e) {
                }
            }

            if (t != ForkingStreamConsumer.END_OF_STREAM) {
                action.accept(t);
                return true;
            }

            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }
    }
}
