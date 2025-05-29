package org.example.model.DB;

import org.example.model.entities.Purchase;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с таблиыей базы данных Purchase, где содержатся все покупки
 */
public class PurchaseDAO {
    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT * FROM Purchases";
        try (Statement s = DBConnection.getConnection().createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                purchases.add(new Purchase(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("item_id"),
                        rs.getString("purchase_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public void addPurchase(Purchase purchase) {
        String sql = "INSERT INTO Purchases(customer_id, item_id, purchase_date) VALUES(?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, purchase.getCustomerId());
            ps.setInt(2, purchase.getItemId());
            ps.setString(3, purchase.getPurchaseDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllPurchases() {
        String sql = "DELETE FROM Purchases";
        try (Statement st = DBConnection.getConnection().createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}