package chap16._16_4;

import static chap16.Util.delay;
import static chap16.Util.format;
import static chap16.Util.randomDelay;

import java.util.Random;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPriceDouble(String product) {
        return calculatePrice(product);
    }

    public double getPriceRandomTimes(String product) {
        randomDelay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public String getName() {
        return name;
    }
}
