package perry.betterthanlauncher.components;

import perry.betterthanlauncher.util.instances.Instance;

import javax.swing.*;
import java.awt.*;

public class InstanceListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if(value instanceof Instance) {
            Instance instance = (Instance) value;
            ImageIcon icon = (ImageIcon) instance.getIcon();

            if(icon != null) {
                setIcon(icon);
                setText(instance.getName());
            }
        }

        return c;
    }
}
