package chap07.spliterator;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCount {

    public static final String SENTENCE =
        " Nel   mezzo del cammin  di nostra  vita "
            + "mi  ritrovai in una  selva oscura"
            + " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        System.out.println("Found " + countWords(SENTENCE) + " words");
        System.out.println("Found " + countWordsParallel(SENTENCE) + " words");
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;

        for (char c : s.toCharArray()) { // 문자열의 모든 문자를 하나씩 탐색한다.
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                // 문자를 하나씩 탐색하면서 공백 문자를 만나면 지금까지 탐색한 문자를 단어로 간주하여(공백 문자는 제외) 단어 수를 증가
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    public static int countWords(String s) {
        Stream<Character> stream = IntStream.range(0, s.length())
            .mapToObj(SENTENCE::charAt);

        return countWords(stream);
    }

    public static int countWordsParallel(String s) {
        // 문제 : 원래 문자열을 임의의 위치에서 줄로 나누다 보니 예상치 못하게 하나의 단어를 둘로 계산하는 상황 발생
        //Stream<Character> stream = IntStream.range(0, s.length())
        //    .mapToObj(SENTENCE::charAt).parallel();

        Spliterator<Character> spliterator = new WordCounterSpliterator(s);

        // StreamSupport.stream 의 두번째 인수는 병렬 스트림 생성 여부
        Stream<Character> stream = StreamSupport.stream(spliterator, true);
        return countWords(stream);
    }


    public static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(
            new WordCounter(0, true),
            WordCounter::accumulate,
            WordCounter::combine
        );

        return wordCounter.getCounter();
    }

}
