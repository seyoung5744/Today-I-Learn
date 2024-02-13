package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;
import chap10.dsl.model.Trade.Type;

public class MethodChainingOrderBuilder {

    public final Order order = new Order(); // 빌더로 감싼 주문

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer); // 고객의 주문을 만드는 정적 팩토리 메서드
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Type.BUY, quantity); // 주식을 사는 TradeBuilder 만들기
    }

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Type.SELL, quantity); // 주식을 파는 TradeBuilder 만들기
    }

    private MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade); // 주문에 주식 추가
        return this; // 유연하게 추가 주문을 만들어 추가할 수 있도록 주문 빌더 자체를 반환
    }

    public Order end() {
        return order; // 주문 만들기를 종료하고 반환
    }

    public static class TradeBuilder {

        private final MethodChainingOrderBuilder builder;
        public final Trade trade = new Trade();

        private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
            this.builder = builder;
            trade.setType(type);
            trade.setQuantity(quantity);
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(builder, trade, symbol);
        }
    }

    public static class StockBuilder {

        private final MethodChainingOrderBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        public TradeBuilderWithStock on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return new TradeBuilderWithStock(builder, trade);
        }
    }

    public static class TradeBuilderWithStock {

        private final MethodChainingOrderBuilder builder;
        private final Trade trade;

        public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
            this.builder = builder;
            this.trade = trade;
        }

        public MethodChainingOrderBuilder at(double price) {
            trade.setPrice(price);
            return builder.addTrade(trade);
        }
    }
}

