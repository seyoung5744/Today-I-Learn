package chap19._19_2_영속_자료구조;

import chap03.FunctionalInterfaceTest.Consumer;

public class TrainJourney {

    public int price;
    public TrainJourney onward;

    public TrainJourney(int price, TrainJourney onward) {
        this.price = price;
        this.onward = onward;
    }

    @Override
    public String toString() {
        return String.format("TrainJourney[%d] -> %s", price, onward);
    }

    public static TrainJourney link(TrainJourney a, TrainJourney b) {
        if (a == null) {
            return b;
        }

        TrainJourney t = a;
        while (t.onward != null) {
            t = t.onward;
        }
        t.onward = b;
        return a;
    }

    public static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.price, append(a.onward, b));
    }

    public static void visit(TrainJourney journey, Consumer<TrainJourney> c) {
        if(journey != null) {
            c.accept(journey);
            visit(journey.onward, c);
        }
    }
}
