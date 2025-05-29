package org.example.model.DB;

import org.example.model.entities.Purchase;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT * FROM Purchases";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, purchase.getCustomerId());
            pstmt.setInt(2, purchase.getItemId());
            pstmt.setString(3, purchase.getPurchaseDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}