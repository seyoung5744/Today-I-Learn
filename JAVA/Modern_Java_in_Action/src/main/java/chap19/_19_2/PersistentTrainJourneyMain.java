package chap19._19_2;

public class PersistentTrainJourneyMain {

    public static void main(String[] args) {
        compareLinkAndAppend();
        System.out.println("=====================");

        TrainJourney tj1 = new TrainJourney(40, new TrainJourney(30, null));
        TrainJourney tj2 = new TrainJourney(20, new TrainJourney(50, null));

        TrainJourney appended = TrainJourney.append(tj1, tj2);
        TrainJourney.visit(appended, tj ->
            System.out.print(tj.price + " - ")
        );
        System.out.println();

        // tj1, tj2를 바꾸지 않고 새 TrainJourney를 생성
        TrainJourney appended2 = TrainJourney.append(tj1, tj2);
        TrainJourney.visit(appended2, tj ->
            System.out.print(tj.price + " - ")
        );
        System.out.println();

        // tj1은 바뀌었지만 여전히 결과에서 확인할 수 없음
        TrainJourney linked = TrainJourney.link(tj1, tj2);
        TrainJourney.visit(linked, tj ->
            System.out.print(tj.price + " - ")
        );
        System.out.println();

        // 문제 상황
        // 여기서 이 코드의 주석을 해제하면 tj2가 이미 바뀐 tj1의 끝에 추가된다. 끝없는 visit() 재귀 호출이 일어나면서 StackOverflowError가 발생함.
        /*TrainJourney linked2 = TrainJourney.link(tj1, tj2);
        TrainJourney.visit(linked2, tj -> { System.out.print(tj.price + " - "); });
        System.out.println();*/
    }


    public static void compareLinkAndAppend() {
        System.out.println("Destructive update:");
        TrainJourney firstJourney = new TrainJourney(1, null);
        TrainJourney secondJourney = new TrainJourney(2, null);
        TrainJourney xToZ = TrainJourney.link(firstJourney, secondJourney);
        System.out.printf("firstJourney (X to Y) = %s\n", firstJourney);
        System.out.printf("secondJourney (Y to Z) = %s\n", secondJourney);
        System.out.printf("X to Z = %s\n", xToZ);

        System.out.println();
        System.out.println("The functional way:");
        firstJourney = new TrainJourney(1, null);
        secondJourney = new TrainJourney(2, null);
        xToZ = TrainJourney.append(firstJourney, secondJourney);
        System.out.printf("firstJourney (X to Y) = %s\n", firstJourney);
        System.out.printf("secondJourney (Y to Z) = %s\n", secondJourney);
        System.out.printf("X to Z = %s\n", xToZ);
    }

}
