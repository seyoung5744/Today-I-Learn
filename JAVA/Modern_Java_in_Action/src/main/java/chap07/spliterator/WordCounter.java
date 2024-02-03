package chap07.spliterator;

public class WordCounter {

    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    // 반복 알고리즘처럼 accumulate 메서드는 문자열의 문자를 하나씩 탐색한다
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter, // 두 WordCounter의 counter 값을 더한다.
            wordCounter.lastSpace); // counter 값만 더할 것이므로 마지막 공백은 신경쓰지 않는다.
    }

    public int getCounter() {
        return counter;
    }
}
