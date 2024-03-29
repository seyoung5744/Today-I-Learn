package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id; // 상품 주문을 위한 id

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
