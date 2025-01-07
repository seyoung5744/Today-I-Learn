package access.ex;

public class ShoppingCart {

    private Item[] items;
    private int itemCount;

    public ShoppingCart() {
        this.items = new Item[10];
    }

    public void addItem(Item item) {
        if (itemCount >= items.length) {
            System.out.println("장바구니가 가득 찼습니다.");
            return;
        }
        items[itemCount] = item;
        itemCount++;
    }


    public void displayItems() {
        System.out.println("장바구니 상품 출력");

        for (int i = 0; i < itemCount; i++) {
            System.out.println("상품명: " + items[i].getName() + ", 합계: " + items[i].getTotalAmount());
        }

        System.out.println("전체 가격 합: " + calculateTotalPrice());
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;

        for (int i = 0; i < itemCount; i++) {
            totalPrice += items[i].getTotalAmount();
        }

        return totalPrice;
    }
}
