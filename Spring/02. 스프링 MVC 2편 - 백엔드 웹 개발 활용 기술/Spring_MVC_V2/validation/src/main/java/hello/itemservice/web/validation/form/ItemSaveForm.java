package hello.itemservice.web.validation.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

//@Data
//@Setter
@Getter
public class ItemSaveForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public ItemSaveForm() {
        System.out.println("ItemSaveForm.ItemSaveForm");
    }

//    public ItemSaveForm(String itemName, Integer price, Integer quantity) {
//        System.out.println("ItemSaveForm.ItemSaveForm(String, Integer, Integer)");
//        this.itemName = itemName;
//        this.price = price;
//        this.quantity = quantity;
//    }

//    public void setItemName(String itemName) {
//        System.out.println("ItemSaveForm.setItemName");
//        this.itemName = itemName;
//    }
//
//    public void setPrice(Integer price) {
//        System.out.println("ItemSaveForm.setPrice");
//        this.price = price;
//    }
//
//    public void setQuantity(Integer quantity) {
//        System.out.println("ItemSaveForm.setQuantity");
//        this.quantity = quantity;
//    }
}
