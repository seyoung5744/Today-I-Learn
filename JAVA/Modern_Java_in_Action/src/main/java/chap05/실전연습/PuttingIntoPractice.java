package chap05.실전연습;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PuttingIntoPractice {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // 질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값).
        // 정답 : [{Trader:Brian in Cambridge, year: 2011, value: 300}, {Trader:Raoul in Cambridge, year: 2011, value: 400}]
        List<Transaction> tr2011 = transactions.stream()
            .filter(transaction -> transaction.getYear() == 2011)
            .sorted(comparing(Transaction::getValue))
            .collect(toList());

        System.out.println(tr2011);
        System.out.println("======================\n");

        // 질의 2: 거래자가 근무하는 모든 고유 도시는?
        // 정답 : [Cambridge, Milan]
        List<String> cities = transactions.stream()
            .map(transaction -> transaction.getTrader().getCity())
            .distinct()
            .collect(toList());

        System.out.println(cities);
        System.out.println("======================\n");

        // 질의 3: Cambridge의 모든 거래자를 찾아 이름으로 정렬.
        // 정답 : [Trader:Alan in Cambridge, Trader:Brian in Cambridge, Trader:Raoul in Cambridge]
        List<Trader> traders = transactions.stream()
            .map(Transaction::getTrader)
            .filter(trader -> trader.getCity().equals("Cambridge"))
            .distinct()
            .sorted(comparing(Trader::getName))
            .collect(toList());

        System.out.println(traders);
        System.out.println("======================\n");

        // 질의 4: 알파벳 순으로 정렬된 모든 거래자의 이름 문자열을 반환
        // 정답 : AlanBrianMarioRaoul
        String traderStr = transactions.stream()
            .map(transaction -> transaction.getTrader().getName())
            .distinct()
            .sorted()
            .reduce("", (s1, s2) -> s1 + s2);

        System.out.println(traderStr);
        System.out.println("======================\n");

        // 질의 5: Milan에 거주하는 거래자가 있는가?
        // 정답 : true
        boolean milanBased = transactions.stream()
            .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        System.out.println(milanBased);
        System.out.println("======================\n");

        // 질의 6: Cambridge에 사는 거래자의 모든 거래내역 출력.
        // 정답 :300 1000 400 950
        transactions.stream()
            .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
            .map(Transaction::getValue)
            .forEach(System.out::println);

        System.out.println("======================\n");

        // 질의 7: 모든 거래에서 최고값은 얼마인가?
        // 정답 : 1000
        int highestValue = transactions.stream()
            .map(Transaction::getValue)
            .reduce(Integer.MIN_VALUE, Integer::max);

        System.out.println(highestValue);
        System.out.println("======================\n");

        // 가장 작은 값을 가진 거래 탐색
        Optional<Transaction> smallestTransaction = transactions.stream()
            .min(comparing(Transaction::getValue));
        // 거래가 없을 때 기본 문자열을 사용할 수 있도록 발견된 거래가 있으면 문자열로 바꾸는 꼼수를 사용함(예, the Stream is empty)
        // 정답 : {Trader:Brian in Cambridge, year: 2011, value: 300}
        System.out.println(smallestTransaction.map(String::valueOf).orElse("No transactions found"));
    }

}
