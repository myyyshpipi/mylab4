package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JButton btnCustomers;
    private JButton btnItems;
    private JButton btnPurchases;
    private JButton btnComponents;
    private JButton btnSupplies;
    private JButton btnClearData;

    public MainView() {
        setTitle("Учет товаров и покупателей");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        btnCustomers = new JButton("Покупатели палочек");
        btnPurchases = new JButton("История покупок");
        btnItems = new JButton("Готовые палочки");
        btnComponents = new JButton("Компоненты для палочк");
        btnSupplies = new JButton("Поставка компонент для палочек");
        btnClearData = new JButton("Очистить все данные");

        add(btnCustomers);
        add(btnPurchases);
        add(btnItems);
        add(btnComponents);
        add(btnSupplies);
        add(btnClearData);
    }

    public JButton getBtnCustomers() { return btnCustomers; }
    public JButton getBtnItems() { return btnItems; }
    public JButton getBtnPurchases() { return btnPurchases; }
    public JButton getBtnComponents() { return btnComponents; }
    public JButton getBtnSupplies() { return btnSupplies; }
    public JButton getBtnClearData() { return btnClearData; }
}