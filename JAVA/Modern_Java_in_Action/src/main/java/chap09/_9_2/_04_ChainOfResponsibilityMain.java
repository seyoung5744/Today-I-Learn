package chap09._9_2;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class _04_ChainOfResponsibilityMain {

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2); // 두 작업 처리 객체를 연결
        String result1 = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result1);

        UnaryOperator<String> headerProcessing = (String text) ->  "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    private static abstract class ProcessingObject<T> {

        protected ProcessingObject<T> successor; // 다음으로 처리할 객체 정보 유지 필드

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    private static class HeaderTextProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
    }

    private static class SpellCheckerProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }
}
