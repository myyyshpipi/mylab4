package org.example.model.DB;

import org.example.model.entities.Supply;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO {

public List<Supply> getAllSupplies() {
    List<Supply> supplies = new ArrayList<>();
    String sql = "SELECT * FROM Supplies";
    try (Statement stmt = DBConnection.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            supplies.add(new Supply(
                    rs.getInt("id"),
                    rs.getString("supplier_name"),
                    rs.getString("supply_date"),
                    rs.getString("component_type"),
                    rs.getString("material"),
                    rs.getInt("quantity")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return supplies;
}

    public void addSupply(Supply supply) {
        String sql = "INSERT INTO Supplies(supplier_name, supply_date, component_type, material, quantity) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, supply.getSupplierName());
            pstmt.setString(2, supply.getSupplyDate());
            pstmt.setString(3, supply.getComponentType());
            pstmt.setString(4, supply.getMaterial());
            pstmt.setInt(5, supply.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}