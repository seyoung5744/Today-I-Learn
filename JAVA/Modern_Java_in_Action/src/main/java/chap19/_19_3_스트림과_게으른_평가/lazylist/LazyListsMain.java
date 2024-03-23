package chap19._19_3_스트림과_게으른_평가.lazylist;

public class LazyListsMain {

    public static void main(String[] args) {
        MyList<Integer> l = new MyLinkedList<>(5, new MyLinkedList<>(10, new Empty<>()));

        System.out.println(l.head());

        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println(two + " " + three + " " + four);

        System.out.println("===========");
        numbers = from(2);
        int prime_two = primes(numbers).head();
        System.out.println("===========");
        int prime_three = primes(numbers).tail().head();
        System.out.println("===========");
        int prime_five = primes(numbers).tail().tail().head();
        System.out.println(prime_two + " " + prime_three + " " + prime_five);

        // 자바는 꼬리 호출 제거 기능이 없으므로 스택오버플로가 발생할 때까지 실행됨
        // printAll(primes(from(2)));
    }

    public static LazyList<Integer> from(int n) {
        return new LazyList<Integer>(n, () -> from(n + 1));
    }

    public static MyList<Integer> primes(MyList<Integer> numbers) {
        System.out.println(numbers);
        return new LazyList<>(
            numbers.head(),
            () -> primes(
                numbers.tail()
                    .filter(n ->
                        {
                            System.out.println(n + ", " + numbers.head());
                            return n % numbers.head() != 0;
                        }
                    )
            )
        );
    }

    public static <T> void printAll(MyList<T> numbers) {
        if (numbers.isEmpty()) {
            return;
        }
        System.out.println(numbers.head());
        printAll(numbers.tail());
    }
}
