package perry.betterthanlauncher.components;

import javax.swing.*;
import java.awt.*;

public class ImageListCellRenderer implements ListCellRenderer {
    /**
     * From http://java.sun.com/javase/6/docs/api/javax/swing/ListCellRenderer.html:
     * <p>
     * Return a component that has been configured to display the specified value.
     * That component's paint method is then called to "render" the cell.
     * If it is necessary to compute the dimensions of a list because the list cells do not have a fixed size,
     * this method is called to generate a component on which getPreferredSize can be invoked.
     * <p>
     * jlist - the jlist we're painting
     * value - the value returned by list.getModel().getElementAt(index).
     * cellIndex - the cell index
     * isSelected - true if the specified cell is currently selected
     * cellHasFocus - true if the cell has focus
     */
    public Component getListCellRendererComponent(JList jlist, Object value, int cellIndex, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof JPanel) {
            Component component = (Component) value;
            component.setForeground(Color.white);
            component.setBackground(isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
            return component;
        } else {
            // TODO - I get one String here when the JList is first rendered; proper way to deal with this?
            //System.out.println("Got something besides a JPanel: " + value.getClass().getCanonicalName());
            return new JLabel("???");
        }
    }
}