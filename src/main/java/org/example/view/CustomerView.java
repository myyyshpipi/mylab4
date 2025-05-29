package org.example.view;

import org.example.model.DB.CustomerDAO;
import org.example.model.entities.Customer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CustomerView extends JFrame {
    private JTable table;
    private CustomerTableModel tableModel;
    private CustomerDAO customerDAO;

    public CustomerView(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        setTitle("Покупатели");
        setBounds(400, 0, 600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableModel = new CustomerTableModel(customerDAO.getAllCustomers());
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Добавить покупателя");
        addButton.addActionListener(e -> showAddCustomerDialog());
        add(addButton, BorderLayout.SOUTH);
    }

    private void showAddCustomerDialog() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        Object[] message = {
                "Имя:", nameField,
                "Телефон:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить покупателя", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Customer customer = new Customer(0, nameField.getText(), phoneField.getText());
            customerDAO.addCustomer(customer);
            tableModel.updateData(customerDAO.getAllCustomers());
            tableModel.fireTableDataChanged();
        }
    }

    static class CustomerTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "Имя", "Телефон"};
        private List<Customer> customers;

        public CustomerTableModel(List<Customer> customers) {
            this.customers = customers;
        }

        public void updateData(List<Customer> newCustomers) {
            this.customers = newCustomers;
        }

        @Override
        public int getRowCount() {
            return customers.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Customer customer = customers.get(row);
            switch (col) {
                case 0: return customer.getId();
                case 1: return customer.getName();
                case 2: return customer.getPhone();
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}