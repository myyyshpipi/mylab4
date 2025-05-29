package org.example.model.entities;

/**
 * Класс для хранения информации о палочках для продажи
 */
public class Item {
    private int id;
    private String name;
    private int caseComponentId;
    private int coreComponentId;
    private double price;
    private boolean available;

    public Item(int id, String name, int caseComponentId, int coreComponentId, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.caseComponentId = caseComponentId;
        this.coreComponentId = coreComponentId;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCaseComponentId() {
        return caseComponentId;
    }

    public int getCoreComponentId() {
        return coreComponentId;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}