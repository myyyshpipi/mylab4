package org.example.view;

import org.example.model.DB.SupplyDAO;
import org.example.model.entities.Supply;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class SupplyView extends JFrame {
    private JTable table;
    private SupplyTableModel tableModel;
    private SupplyDAO supplyDAO;
    private ComponentView componentView;

    public SupplyView(SupplyDAO supplyDAO, ComponentView componentView) {
        this.supplyDAO = supplyDAO;
        this.componentView = componentView;

        setTitle("Поставки");
        setBounds(400, 0,600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableModel = new SupplyTableModel(supplyDAO.getAllSupplies());
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Поставка компонент");
        addButton.addActionListener(e -> showAddSupplyDialog());
        add(addButton, BorderLayout.SOUTH);
    }

private void showAddSupplyDialog() {
    JTextField supplierField = new JTextField();
    JTextField dateField = new JTextField();
    String[] componentTypes = {"Древесина", "Сердцевина"};
    JComboBox<String> typeCombo = new JComboBox<>(componentTypes);
    JTextField materialField = new JTextField();
    JTextField quantityField = new JTextField();

    Object[] message = {
            "Поставщик:", supplierField,
            "Дата (YYYY-MM-DD):", dateField,
            "Тип компонента:", typeCombo,
            "Материал:", materialField,
            "Количество:", quantityField
    };

    int option = JOptionPane.showConfirmDialog(this, message, "Добавить поставку", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            String supplier = supplierField.getText();
            String date = dateField.getText();
            String componentType = (String) typeCombo.getSelectedItem();
            String material = materialField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            Supply supply = new Supply(0, supplier, date, componentType, material, quantity);
            supplyDAO.addSupply(supply);

            // Обновляем таблицу поставок
            tableModel.updateData(supplyDAO.getAllSupplies());
            tableModel.fireTableDataChanged();

            // Обновляем таблицу компонентов
            if (componentView != null) {
                componentView.refresh(); // Вызываем обновление данных
            }

            JOptionPane.showMessageDialog(this, "Поставка успешно добавлена.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Количество должно быть числом.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    static class SupplyTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "Поставщик", "Дата", "Тип", "Материал", "Количество"};
        private List<Supply> supplies;

        public SupplyTableModel(List<Supply> supplies) {
            this.supplies = supplies;
        }

        public void updateData(List<Supply> newSupplies) {
            this.supplies = newSupplies;
        }



        @Override
        public int getRowCount() {
            return supplies.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Supply supply = supplies.get(row);
            switch (col) {
                case 0: return supply.getId();
                case 1: return supply.getSupplierName();
                case 2: return supply.getSupplyDate();
                case 3: return supply.getComponentType();
                case 4: return supply.getMaterial();
                case 5: return supply.getQuantity();
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }


    }
}