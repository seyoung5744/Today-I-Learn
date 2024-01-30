//package chap06.collector;
//
//import java.util.Set;
//import java.util.function.BiConsumer;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//public interface Collector <T, A, R>{
//
//    Supplier<A> supplier();
//    BiConsumer<A, T> accumulator();
//    Function<A, R> finisher();
//    BinaryOperator<A> combiner();
//    Set<Characteristics> characteristics();
//
//    enum Characteristics {
//        CONCURRENT,
//        UNORDERED,
//        IDENTITY_FINISH
//    }
//}
