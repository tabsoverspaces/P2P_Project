package gui.custom_components;

import javax.swing.*;
import java.awt.*;

public class PeerSelectButton extends JButton {

    public PeerSelectButton(String address) {
        Dimension d = new Dimension(200, 50);
        this.setPreferredSize(d);

        this.setText(address);
    }


}
