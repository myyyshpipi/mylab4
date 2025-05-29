package org.example.view;

import org.example.model.DB.ComponentDAO;
import org.example.model.entities.Component;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class ComponentView extends JFrame {
    private JTable table;
    private ComponentTableModel tableModel;
    private ComponentDAO componentDAO;

public ComponentView(ComponentDAO componentDAO) {
    this.componentDAO = componentDAO;
    setTitle("Компоненты");
    setBounds(400, 0,600, 400);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    tableModel = new ComponentTableModel(componentDAO.getAllComponents());
    table = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    //JButton addButton = new JButton("Добавить компонент");
    //addButton.addActionListener(e -> showAddComponentDialog());
    //add(addButton, BorderLayout.SOUTH);
}

    public void refresh() {
        tableModel.updateData(componentDAO.getAllComponents());
        tableModel.fireTableDataChanged();
    }

    private void showAddComponentDialog() {
        String[] componentTypes = {"Древесина", "Сердцевина"};
        JComboBox<String> typeCombo = new JComboBox<>(componentTypes);
        JTextField materialField = new JTextField();
        JTextField quantityField = new JTextField();

        Object[] message = {
                "Тип компонента:", typeCombo,
                "Материал:", materialField,
                "Количество:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить компонент.", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String componentType = (String) typeCombo.getSelectedItem();
                String material = materialField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                Component component = new Component(0, componentType, material, quantity);
                componentDAO.addComponent(component);
                tableModel.updateData(componentDAO.getAllComponents());
                tableModel.fireTableDataChanged();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Количество должно быть числом.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class ComponentTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "Тип", "Материал", "Количество"};
        private List<Component> components;

        public ComponentTableModel(List<Component> components) {
            this.components = components;
        }

        public void updateData(List<Component> newComponents) {
            this.components = newComponents;
        }

        @Override
        public int getRowCount() {
            return components.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Component component = components.get(row);
            switch (col) {
                case 0: return component.getId();
                case 1: return component.getComponentType();
                case 2: return component.getMaterial();
                case 3: return component.getQuantity();
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }


}