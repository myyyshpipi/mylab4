package org.example.view;

import javax.swing.*;
import java.awt.*;

/**
 * Вспомогательный клаа обертка для того чтобы создавать сложные выпадающие списки для покупателей и палочек
 */
public class CustomComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {
            setText(value.toString());
        }
        return this;
    }
}