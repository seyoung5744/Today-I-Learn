package chap10.dsl;

import chap03.FunctionalInterfaceTest.Consumer;
import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;
import chap10.dsl.model.Trade.Type;

public class LambdaOrderBuilder {

    private final Order order = new Order(); // 빌더로 주문을 감쌈

    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder); // 주문 빌더로 전달된 람다 표현식 실행
        return builder.order; // OrderBuilder의 Consumer를 실행해 만들어진 주문을 반환
    }

    public void forCustomer(String customer) {
        order.setCustomer(customer); // 주문을 요청한 고객 설정
    }

    public void buy(Consumer<TradeBuilder> consumer) {
        trade(consumer, Type.BUY); // 주식 매수 주문을 만들도록 TradeBuilder 소비
    }

    public void sell(Consumer<TradeBuilder> consumer) {
        trade(consumer, Type.SELL); // 주식 매도 주문을 만들도록 TradeBuilder 소비
    }

    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(type);
        consumer.accept(builder); // TradeBuilder 로 전달할 람다 표현식 실행
        order.addTrade(builder.trade); // TradeBuilder 의 Consumer 를 실행해 만든 거래를 주문에 추가
    }

    public static class TradeBuilder {

        private final Trade trade = new Trade();

        public void quantity(int quantity) {
            trade.setQuantity(quantity);
        }

        public void price(double price) {
            trade.setPrice(price);
        }

        public void stock(Consumer<StockBuilder> consumer) {
            StockBuilder builder = new StockBuilder();
            consumer.accept(builder);
            trade.setStock(builder.stock);
        }
    }

    public static class StockBuilder {

        private final Stock stock = new Stock();

        public void symbol(String symbol) {
            stock.setSymbol(symbol);
        }

        public void market(String market) {
            stock.setMarket(market);
        }
    }
}
