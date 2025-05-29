package org.example.view;

import org.example.model.entities.Customer;

/**
 * Класс для создания настраиваемого выпадающего списка для покупателей
 */
public class CustomerComboBoxItem {
    private final Customer customer;

    public CustomerComboBoxItem(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return customer.getName() + " (ID: " + customer.getId() + ")";
    }
}