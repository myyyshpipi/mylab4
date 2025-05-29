package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Класс обертка для того, чтобы отображать в выпадающих списках материал компонентам
 */
public class ComponentCellRenderer extends DefaultListCellRenderer {

    @Override
    public java.awt.Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof org.example.model.entities.Component) {
            setText(((org.example.model.entities.Component) value).getMaterial());
        } else {
            setText("Неизвестный материал");
        }
        if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        return this;
    }
}