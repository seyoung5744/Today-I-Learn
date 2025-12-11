package com.example.inventorytdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryHelperTests {

    @Test
    void inventoryHelper_getUsableCapacity() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);
        inventory.setCurrent(5);

        int usableCapacity = inventoryHelper.getUsableCapacity(inventory);

        assertThat(usableCapacity).isEqualTo(5);
    }

    @Test
    void inventoryHelper_inbound_true() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();
        inventory.setCapacity(40);

        assertThat(inventoryHelper.inbound(inventory, 10)).isTrue();
        assertThat(inventory.getCurrent()).isEqualTo(10);
    }

    @Test
    void inventoryHelper_inbound_false() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();
        inventory.setCapacity(10);

        assertThat(inventoryHelper.inbound(inventory, 30)).isFalse();
        assertThat(inventory.getCurrent()).isEqualTo(0);
    }

    @Test
    void inventoryHelper_getInboundable_true() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();

        inventory.setCapacity(10);
        inventory.setCurrent(5);

        assertThat(inventoryHelper.getInboundable(inventory, 3)).isTrue();
    }

    @Test
    void inventoryHelper_getInboundable_false() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();

        inventory.setCapacity(10);
        inventory.setCurrent(8);

        assertThat(inventoryHelper.getInboundable(inventory, 3)).isFalse();
    }

    @Test
    void inventoryHelper_newInventory() {
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = inventoryHelper.createInventory();

        assertThat(inventory).isNotNull();
    }

    @Test
    void inventoryHelper_setInventory() {

    }
}
