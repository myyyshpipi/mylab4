package org.example.model.entities;

public class Component {
    private int id;
    private String componentType;
    private String material;
    private int quantity;

    public Component(int id, String componentType, String material, int quantity) {
        this.id = id;
        this.componentType = componentType;
        this.material = material;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getMaterial() {
        return material;
    }

    public int getQuantity() {
        return quantity;
    }
}