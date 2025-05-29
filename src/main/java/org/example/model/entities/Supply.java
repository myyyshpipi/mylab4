package org.example.model.entities;

public class Supply {
    private int id;
    private String supplierName;
    private String supplyDate;
    private String componentType;
    private String material;
    private int quantity;

    public Supply(int id, String supplierName, String supplyDate, String componentType, String material, int quantity) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplyDate = supplyDate;
        this.componentType = componentType;
        this.material = material;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplyDate() {
        return supplyDate;
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