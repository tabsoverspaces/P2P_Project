package gui;

import udp_classes.Peer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private MainPanel panel;

    private Peer mainPeer;

    public MainFrame() {

        this.setLayout(null);
        this.setSize(new Dimension(815, 650));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.panel = new MainPanel();
        this.panel.createComponents();


        this.add(this.panel);
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }

    public Peer getMainPeer() {
        return this.mainPeer;
    }
}
