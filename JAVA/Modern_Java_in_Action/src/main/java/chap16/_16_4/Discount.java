package chap16._16_4;

import static chap16.Util.delay;
import static chap16.Util.format;

public class Discount {

    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        // 기존 가격에 할인 코드를 적용한다.
        return quote.getShopName() + " price is " + Discount.apple(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apple(double price, Discount.Code code) {
        delay(); // Discount 서비스의 응답 지연을 흉내 낸다.
        return format(price * (100 - code.percentage) / 100);
    }
}
