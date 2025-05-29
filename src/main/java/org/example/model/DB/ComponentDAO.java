package org.example.model.DB;

import org.example.model.entities.Component;
import org.example.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с таблицей базы данных Componet, вкоторой содержатся компоненты для производства палочек
 */
public class ComponentDAO {
    public List<Component> getAllComponents() {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM Components";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("id"),
                        rs.getString("component_type"),
                        rs.getString("material"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public void addComponent(Component component) {
        String sql = "INSERT INTO Components(component_type, material, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, component.getComponentType());
            pstmt.setString(2, component.getMaterial());
            pstmt.setInt(3, component.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Component getComponentById(int id) {
        String sql = "SELECT * FROM Components WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Component(
                        rs.getInt("id"),
                        rs.getString("component_type"),
                        rs.getString("material"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Component> getComponentsByType(String type) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM Components WHERE component_type = ?";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("id"),
                        rs.getString("component_type"),
                        rs.getString("material"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public void updateComponentQuantity(int id, int newQuantity) {
        String sql = "UPDATE Components SET quantity = ? WHERE id = ?";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}