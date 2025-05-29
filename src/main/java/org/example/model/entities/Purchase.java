package org.example.model.entities;

public class Purchase {
    private int id;
    private int customerId;
    private int itemId;
    private String purchaseDate;

    public Purchase(int id, int customerId, int itemId, String purchaseDate) {
        this.id = id;
        this.customerId = customerId;
        this.itemId = itemId;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}