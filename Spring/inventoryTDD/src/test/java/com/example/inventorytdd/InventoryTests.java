package com.example.inventorytdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTests {

    @Test
    void inventory_size_test() {
        Inventory inventory = new Inventory();

        inventory.setLength(10);
        inventory.setWidth(10);
        inventory.setHeight(10);

        assertThat(inventory.getLength()).isEqualTo(10);
        assertThat(inventory.getWidth()).isEqualTo(10);
        assertThat(inventory.getHeight()).isEqualTo(10);
    }

    @Test
    void inventory_type_test() {
        Inventory inventory = new Inventory();
        inventory.setType(InventoryTypeEnum.COLD);

        assertThat(inventory.getType()).isEqualTo(InventoryTypeEnum.COLD);
    }

    @Test
    void inventory_capacity_test() {
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);

        assertThat(inventory.getCapacity()).isEqualTo(10);
    }

    @Test
    void inventory_current_test() {
        Inventory inventory = new Inventory();
        inventory.setCurrent(5);

        assertThat(inventory.getCurrent()).isEqualTo(5);
    }

}
