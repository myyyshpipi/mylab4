package org.example.controller;

import org.example.model.DB.*;
import org.example.model.DBConnection;
import org.example.view.*;

import javax.swing.*;
import java.sql.SQLException;


public class Controller {
    // Объекты для базы
    private CustomerDAO customerDAO;
    private ComponentDAO componentDAO;
    private ItemDAO itemDAO;
    private PurchaseDAO purchaseDAO;
    private SupplyDAO supplyDAO;

    // Объекты для экранов
    private MainView mainView;
    private ComponentView componentView;
    private ItemView itemView;
    private PurchaseView purchaseView;
    private CustomerView customerView;
    private SupplyView supplyView;

    public Controller() {
        // db
        customerDAO = new CustomerDAO();
        componentDAO = new ComponentDAO();
        itemDAO = new ItemDAO();
        purchaseDAO = new PurchaseDAO();
        supplyDAO = new SupplyDAO();


        // view
        mainView = new MainView();
        componentView = new ComponentView(componentDAO);
        itemView = new ItemView(itemDAO, componentDAO, componentView);
        purchaseView = new PurchaseView(purchaseDAO, customerDAO, itemDAO, itemView);
        customerView = new CustomerView(customerDAO);
        supplyView = new SupplyView(supplyDAO, componentView);


        // listners
        mainView.getBtnPurchases().addActionListener(e -> purchaseView.setVisible(true));
        mainView.getBtnCustomers().addActionListener(e -> customerView.setVisible(true));
        mainView.getBtnComponents().addActionListener(e -> componentView.setVisible(true));
        mainView.getBtnItems().addActionListener(e -> itemView.setVisible(true));
        mainView.getBtnSupplies().addActionListener(e -> supplyView.setVisible(true));

        // Листнер для кнопки очистки данных
        mainView.getBtnClearData().addActionListener(e -> confirmAndClearData());

        mainView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DBConnection.closeConnection();
                System.exit(0);
            }
        });

        mainView.setVisible(true);
    }

    private void confirmAndClearData() {
        int confirm = JOptionPane.showConfirmDialog(mainView, "Точно удаляем все данные?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clearDatabase();
            refreshAllViews();
        }
    }

    private void clearDatabase() {
        purchaseDAO.deleteAllPurchases();
        itemDAO.deleteAllItems();
        supplyDAO.deleteAllSupplies();
        componentDAO.deleteAllComponents();
        customerDAO.deleteAllCustomers();
    }

    private void refreshAllViews() {
        if (customerView != null) customerView.refresh();
        if (itemView != null) itemView.refresh();
        if (componentView != null) componentView.refresh();
        if (purchaseView != null) purchaseView.refresh();
        if (supplyView != null) supplyView.refresh();
    }

    public static void main(String[] args) {
        new Controller();
    }
}