package org.example.view;

import org.example.model.DB.ComponentDAO;
import org.example.model.DB.ItemDAO;
import org.example.model.entities.Item;
import org.example.model.entities.Component;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class ItemView extends JFrame {
    private JTable table;
    private ItemTableModel tableModel;
    private ItemDAO itemDAO;
    private ComponentDAO componentDAO;
    private ComponentView componentView;

public ItemView(ItemDAO itemDAO, ComponentDAO componentDAO, ComponentView componentView) {
    this.itemDAO = itemDAO;
    this.componentDAO = componentDAO;
    this.componentView = componentView;
    setTitle("Волшебные палочки");
    setBounds(400, 0, 600, 400);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    tableModel = new ItemTableModel(itemDAO.getAllItems(), componentDAO);
    table = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    JButton addButton = new JButton("Сделать палочку");
    addButton.addActionListener(e -> showAddItemDialog());
    add(addButton, BorderLayout.SOUTH);
}

    private void showAddItemDialog() {
        JTextField nameField = new JTextField();
        JComboBox<Component> caseComboBox = new JComboBox<>();
        JComboBox<Component> coreComboBox = new JComboBox<>();
        JTextField priceField = new JTextField();

        // Устанавливаем кастомный рендерер
        caseComboBox.setRenderer(new ComponentCellRenderer());
        coreComboBox.setRenderer(new ComponentCellRenderer());

        // Заполняем списки доступными компонентами
        for (Component comp : componentDAO.getComponentsByType("Древесина")) {
            if (comp.getQuantity() > 0) {
                caseComboBox.addItem(comp);
            }
        }
        for (Component comp : componentDAO.getComponentsByType("Сердцевина")) {
            if (comp.getQuantity() > 0) {
                coreComboBox.addItem(comp);
            }
        }

        // Если нет доступных компонентов, блокируем добавление
        if (caseComboBox.getItemCount() == 0 || coreComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Недостаточно доступных Сердцевин/Древесин для создания палочки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] message = {
                "Название:", nameField,
                "Древесина:", caseComboBox,
                "Сердцевина:", coreComboBox,
                "Цена:", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить волшебную палочку", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                Component selectedCase = (Component) caseComboBox.getSelectedItem();
                Component selectedCore = (Component) coreComboBox.getSelectedItem();
                double price = Double.parseDouble(priceField.getText());

                if (selectedCase == null || selectedCore == null) {
                    JOptionPane.showMessageDialog(this, "Выберите древесину и сердцевину.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Item item = new Item(0, name, selectedCase.getId(), selectedCore.getId(), price, true);
                itemDAO.addItem(item);

                // Уменьшаем количество компонентов
                componentDAO.updateComponentQuantity(selectedCase.getId(), selectedCase.getQuantity() - 1);
                componentDAO.updateComponentQuantity(selectedCore.getId(), selectedCore.getQuantity() - 1);

                // Обновляем таблицы
                tableModel.updateData(itemDAO.getAllItems());
                tableModel.fireTableDataChanged();

                if (componentView != null) {
                    componentView.refresh(); // Обновляем отображение компонентов
                }

                JOptionPane.showMessageDialog(this, "Палочка успешно добавлен.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Цена должна быть числом.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class ItemTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "Название палочки", "Древесина", "Сердцевина", "Цена", "Доступна для продажи"};
        private List<Item> items;
        private ComponentDAO componentDAO;

        public ItemTableModel(List<Item> items, ComponentDAO componentDAO) {
            this.items = items;
            this.componentDAO = componentDAO;
        }

        public void updateData(List<Item> newItems) {
            this.items = newItems;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Item item = items.get(row);
            switch (col) {
                case 0: return item.getId();
                case 1: return item.getName();
                case 2: return getComponentName(item.getCaseComponentId());
                case 3: return getComponentName(item.getCoreComponentId());
                case 4: return item.getPrice();
                case 5: return item.isAvailable() ? "Да" : "Нет";
                default: return null;
            }
        }

        private String getComponentName(int id) {
            Component component = componentDAO.getComponentById(id);
            if (component != null) {
                return component.getMaterial();
            }
            return "Не найден";
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    public void refresh() {
        tableModel.updateData(itemDAO.getAllItems());
        tableModel.fireTableDataChanged();
    }

}