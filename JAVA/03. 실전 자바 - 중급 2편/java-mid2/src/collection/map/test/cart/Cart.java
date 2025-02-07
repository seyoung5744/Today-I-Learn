package collection.map.test.cart;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> productMap = new HashMap<>();

    public void add(Product product, int quantity) {
        int existingQuantity = productMap.getOrDefault(product, 0);
        productMap.put(product, quantity + existingQuantity);
    }

    public void minus(Product product, int quantity) {
        int existingQuantity = productMap.getOrDefault(product, 0);

        int newQuantity = existingQuantity - quantity;

        if (newQuantity <= 0) {
            productMap.remove(product);
            return;
        }

        productMap.put(product, newQuantity);
    }

    public void printAll() {
        System.out.println("==모든 상품 출력==");
        for (Product product : productMap.keySet()) {
            System.out.println("상품: " + product + " 수량: " + productMap.get(product));
        }
    }
}
