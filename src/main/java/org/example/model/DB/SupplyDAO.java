package org.example.model.DB;

import org.example.model.entities.Supply;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы таблицей базы данных Supply, где содержится информация по поставкам
 */
public class SupplyDAO {

public List<Supply> getAllSupplies() {
    List<Supply> supplies = new ArrayList<>();
    String sql = "SELECT * FROM Supplies";
    try (Statement st = DBConnection.getConnection().createStatement();
         ResultSet rs = st.executeQuery(sql)) {
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
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, supply.getSupplierName());
            ps.setString(2, supply.getSupplyDate());
            ps.setString(3, supply.getComponentType());
            ps.setString(4, supply.getMaterial());
            ps.setInt(5, supply.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllSupplies() {
        String sql = "DELETE FROM Supplies";
        try (Statement st = DBConnection.getConnection().createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}