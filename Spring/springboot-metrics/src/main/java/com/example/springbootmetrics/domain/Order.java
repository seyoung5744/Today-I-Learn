package com.example.springbootmetrics.domain;

import java.util.Random;

public class Order {

    static Random random = new Random();
    final int productCount;
    final int totalPrice;

    public Order(int productCount, int totalPrice) {
        this.productCount = productCount;
        this.totalPrice = totalPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public static Order createRandomOrder() {
        int count = random.nextInt(3) + 1;
        int price = random.nextInt(10000) * 10 + 100;

        return new Order(count, price);
    }
}
