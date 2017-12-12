package gui.custom_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PeerSelectButton extends JButton {

    private PeerChooser peerChooser;
    //
    private String address;

    public PeerSelectButton(String address) {
        Dimension d = new Dimension(200, 50);
        this.setPreferredSize(d);

        this.address = address;
        this.setText(this.address);
    }

    public void setPeerChooser(PeerChooser p) {
        this.peerChooser = p;
    }

    public void addActionListenerToButton() {
        this.addActionListener((ActionEvent e) -> {

        });

    }

    @Override
    public String toString() {
        return this.address;
    }


}
