package org.example.view;

import org.example.model.DB.CustomerDAO;
import org.example.model.DB.ItemDAO;
import org.example.model.DB.PurchaseDAO;
import org.example.model.entities.Customer;
import org.example.model.entities.Item;
import org.example.model.entities.Purchase;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class PurchaseView extends JFrame {
    private JTable table;
    private PurchaseTableModel tableModel;
    private PurchaseDAO purchaseDAO;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private ItemView itemView;

    public PurchaseView(PurchaseDAO purchaseDAO, CustomerDAO customerDAO, ItemDAO itemDAO, ItemView itemView) {
        this.purchaseDAO = purchaseDAO;
        this.customerDAO = customerDAO;
        this.itemDAO = itemDAO;
        this.itemView = itemView;

        setTitle("Покупки");
        setBounds(400,0,600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableModel = new PurchaseTableModel(purchaseDAO.getAllPurchases(), customerDAO, itemDAO);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Добавить покупку");
        addButton.addActionListener(e -> showAddPurchaseDialog());
        add(addButton, BorderLayout.SOUTH);
    }


    private void showAddPurchaseDialog() {
        JComboBox<CustomerComboBoxItem> customerComboBox = new JComboBox<>();
        JComboBox<ItemComboBoxItem> itemComboBox = new JComboBox<>();

        // Заполняем список покупателей
        for (Customer customer : customerDAO.getAllCustomers()) {
            customerComboBox.addItem(new CustomerComboBoxItem(customer));
        }

        // Заполняем список доступных предметов
        for (Item item : itemDAO.getAllItems()) {
            if (item.isAvailable()) {
                itemComboBox.addItem(new ItemComboBoxItem(item));
            }
        }

        // Устанавливаем кастомный рендерер
        customerComboBox.setRenderer(new CustomComboBoxRenderer());
        itemComboBox.setRenderer(new CustomComboBoxRenderer());

        // Проверка на наличие доступных элементов
        if (customerComboBox.getItemCount() == 0 || itemComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Недостаточно данных для создания покупки.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Object[] message = {
                "Покупатель:", customerComboBox,
                "Предмет:", itemComboBox,
                "Дата (YYYY-MM-DD):", new JTextField()
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить покупку", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Получаем выбранные элементы
                CustomerComboBoxItem selectedCustomer = (CustomerComboBoxItem) customerComboBox.getSelectedItem();
                ItemComboBoxItem selectedItem = (ItemComboBoxItem) itemComboBox.getSelectedItem();
                JTextField dateField = (JTextField) ((Object[]) message)[5];

                // Проверяем, что все поля заполнены
                if (selectedCustomer == null || selectedItem == null || dateField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Заполните все поля.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Добавляем покупку
                Purchase purchase = new Purchase(
                        0,
                        selectedCustomer.getCustomer().getId(),
                        selectedItem.getItem().getId(),
                        dateField.getText()
                );
                purchaseDAO.addPurchase(purchase);

                // Обновляем статус предмета
                Item item = selectedItem.getItem();
                itemDAO.updateItemAvailability(item.getId(), false);

                // Обновляем таблицы
                tableModel.updateData(purchaseDAO.getAllPurchases());
                tableModel.fireTableDataChanged();

                if (itemView != null) {
                    itemView.refresh(); // Обновляем отображение предметов
                }

                JOptionPane.showMessageDialog(this, "Покупка успешно добавлена.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при добавлении покупки.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    class PurchaseTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "Покупатель", "Предмет", "Дата"};
        private List<Purchase> purchases;
        private CustomerDAO customerDAO;
        private ItemDAO itemDAO;

        public PurchaseTableModel(List<Purchase> purchases, CustomerDAO customerDAO, ItemDAO itemDAO) {
            this.purchases = purchases;
            this.customerDAO = customerDAO;
            this.itemDAO = itemDAO;
        }

        public void updateData(List<Purchase> newPurchases) {
            this.purchases = newPurchases;
        }

        @Override
        public int getRowCount() {
            return purchases.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Purchase purchase = purchases.get(row);
            switch (col) {
                case 0:
                    return purchase.getId();
                case 1:
                    return getCustomerName(purchase.getCustomerId());
                case 2:
                    return getItemName(purchase.getItemId());
                case 3:
                    return purchase.getPurchaseDate();
                default:
                    return null;
            }
        }

        private String getCustomerName(int id) {
            Customer customer = customerDAO.getCustomerById(id);
            return customer != null ? customer.getName() : "Не найден";
        }

        private String getItemName(int id) {
            Item item = itemDAO.getItemById(id);
            return item != null ? item.getName() : "Не найден";
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}