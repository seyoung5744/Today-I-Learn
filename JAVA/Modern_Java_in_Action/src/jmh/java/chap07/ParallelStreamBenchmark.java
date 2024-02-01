package chap07;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
//@Measurement(iterations = 2)
//@Warmup(iterations = 3)
public class ParallelStreamBenchmark {

    private static final long N = 10_000_000L;

//    @Benchmark
//    public long iterativeSum() {
//        long result = 0;
//        for(long i = 1L; i <= N; i++) {
//            result += i;
//        }
//        return result;
//    }
//
//    @Benchmark // 벤치마크 대상 메서드
//    public long sequentialSum() {
//        return Stream.iterate(1L, i -> i + 1)
//            .limit(N)
//            .reduce(0L, Long::sum);
//    }
//
//    @Benchmark
//    public long parallelSum() {
//        return Stream.iterate(1L, i -> i + 1)
//            .limit(N)
//            .parallel()
//            .reduce(0L, Long::sum);
//    }

    @Benchmark
    public long rangedSum() {
        return LongStream.rangeClosed(1, N)
            .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelRangedSum() {
        return LongStream.rangeClosed(1, N)
            .parallel()
            .reduce(0L, Long::sum);
    }

    @TearDown(Level.Invocation) // 매 번 벤치마크를 실행한 다음에는 카비지 컬럭터 동작 시도
    public void tearDown() {
        System.gc();
    }
}
