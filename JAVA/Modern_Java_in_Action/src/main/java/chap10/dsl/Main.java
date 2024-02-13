package chap10.dsl;

import static chap10.dsl.MethodChainingOrderBuilder.forCustomer;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;
import chap10.dsl.model.Trade.Type;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.plain();
        main.methodChaining();
        main.nestedFunction();
        main.lambda();
        main.mixed();
    }


    public void plain() {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);

        System.out.println("Plain:");
        System.out.println(order);
    }

    private void methodChaining() {
        Order order = forCustomer("BigBank")
            .buy(80).stock("IBM").on("NYSE").at(125.00)
            .sell(50).stock("GOOGLE").on("NASDAQ").at(375.00)
            .end();

        System.out.println("Method chaining:");
        System.out.println(order);
    }

    private void nestedFunction() {
    }

    private void lambda() {

    }

    private void mixed() {

    }

}
