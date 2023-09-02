package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 평균 시간 측정. 결과 파일에 Score로 표시
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 시간 단위 설정
@Fork(5) // 벤치 마크 수행 횟수. 횟수가 늘어날수록 정밀도는 높아지지만 오래걸림
@Warmup(iterations = 3)
public class RandomArrayBenchmarkTest {

    private static final Random random = new Random();
    private static final int arraySize = 100_000; //배열의 길이
    private static final int strLen = 6; // 한 문자열의 길이

    private String[] strs;
    private List<String> listStrs;
    private List<String> linkedStrs;
    private String target;

    @Setup
    public void setup(){
        strs = RandomArray.makeRandomStrings(strLen, arraySize); //배열의 길이와 문자열 길이를 입력받아, 랜덤한 문자열 배열 생성
        // ArrayList 객체로 복사 (new ArrayList 로 생성하지 않을 경우 copy하지 않고 참조만 하여 Array가 캐시 해놓은 것을 쓰기 때문에 성능비교가 제대로 안됨.
        listStrs = new ArrayList<>(Arrays.asList(strs));
        linkedStrs = new LinkedList<>(Arrays.asList(strs)); // LinkedList 객체로 복사
        target = strs[random.nextInt(arraySize)];
    }

    @Benchmark // 벤치마크 대상
    public void measureInArray() {
        int i = 0;
        while (!strs[i].equals(target)) {
            i++;
        }
    }

    @Benchmark // 벤치마크 대상
    public void measureInArrayList() {
        // listStrs.indexOf(target); // indexOf의 인자를 체크하는 비용이 추가로 발생하므로 get으로 체크
        int i = 0;
        while (!listStrs.get(i).equals(target)) {
            i++;
        }
    }

    @Benchmark // 벤치마크 대상
    public void measureInLinkedList() {
        // Array, ArrayList 처럼 get으로 체크하는 경우 매번 첫 노드부터 탐색하므로 indexOf 사용
        linkedStrs.indexOf(target);
    }
}
