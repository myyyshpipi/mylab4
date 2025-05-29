package org.example.model.DB;

import org.example.model.entities.Item;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с таблицей базы данных Item, где содержится информация о всех палочках
 */
public class ItemDAO {
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM Items";
        try (Statement s = DBConnection.getConnection().createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("case_component_id"),
                        rs.getInt("core_component_id"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO Items(name, case_component_id, core_component_id, price, available) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getCaseComponentId());
            ps.setInt(3, item.getCoreComponentId());
            ps.setDouble(4, item.getPrice());
            ps.setBoolean(5, item.isAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM Items WHERE id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("case_component_id"),
                        rs.getInt("core_component_id"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateItemAvailability(int itemId, boolean available) {
        String sql = "UPDATE Items SET available = ? WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllItems() {
        String sql = "DELETE FROM Items";
        try (Statement s = DBConnection.getConnection().createStatement()) {
            s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}