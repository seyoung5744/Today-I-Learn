package chap10.dsl;

import static chap10.dsl.MixedBuilder.buy;
import static chap10.dsl.MixedBuilder.forCustomer;
import static chap10.dsl.MixedBuilder.sell;

import chap10.dsl.model.Order;
import chap10.dsl.model.Tax;
import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {

    public static double calculate(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
        double value = order.getValue();
        if (useRegional) {
            value = Tax.regional(value);
        }
        if (useGeneral) {
            value = Tax.general(value);
        }
        if (useSurcharge) {
            value = Tax.surcharge(value);
        }
        return value;
    }

    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;

    public TaxCalculator withTaxRegional() {
        useRegional = true;
        return this;
    }

    public TaxCalculator withTaxGeneral() {
        useGeneral = true;
        return this;
    }

    public TaxCalculator withTaxSurcharge() {
        useSurcharge = true;
        return this;
    }

    public double calculate(Order order) {
        return calculate(order, useRegional, useGeneral, useSurcharge);
    }

    public DoubleUnaryOperator taxFunction = d -> d; // 주문값에 적용된 모든 세금을 계산하는 함수

    public TaxCalculator with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f); // 새로운 세금 계산 함수를 얻어서 인수로 전달된 함수와 현재 함수를 합침
        return this; // 유창하게 세금 함수가 연결될 수 있도록 결과를 반환
    }

    public double calculateF(Order order) {
        return taxFunction.applyAsDouble(order.getValue()); // 주문의 총 합에 세금 계산 함수를 적용해 최종 주문값을 계산
    }

    public static void main(String[] args) {
        Order order =
            forCustomer("BigBank",
                buy(t -> t.quantity(80)
                    .stock("IBM")
                    .on("NYSE")
                    .at(125.00)),
                sell(t -> t.quantity(50)
                    .stock("GOOGLE")
                    .on("NASDAQ")
                    .at(125.00)

                )
            );
        double value = TaxCalculator.calculate(order, true, false, true);
        System.out.printf("Boolean arguments: %.2f%n", value);

        value = new TaxCalculator().withTaxRegional()
            .withTaxSurcharge()
            .calculate(order);
        System.out.printf("Method chaining: %.2f%n", value);

        value = new TaxCalculator().with(Tax::regional)
            .with(Tax::surcharge)
            .calculateF(order);
        System.out.printf("Method references: %.2f%n", value);

        DoubleUnaryOperator f = d -> d;

        double result = f.andThen(d -> d * 2).andThen(d -> d * 3).applyAsDouble(1);
        System.out.println(result);

        result = f.applyAsDouble(1);
        System.out.println(result);

    }

}
