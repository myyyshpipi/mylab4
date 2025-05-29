package org.example.view;

import org.example.model.entities.Item;

/**
 * Класс для создания настраиваемого выпадащего списка палочек для продажи
 */
public class ItemComboBoxItem {
    private final Item item;

    public ItemComboBoxItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return item.getName() + " (ID: " + item.getId() + ")";
    }
}