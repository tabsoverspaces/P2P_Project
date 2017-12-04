package gui;

import udp_classes.Launcher;
import udp_classes.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;

public class MainFrame extends JFrame {

    private MainPanel panel;
    private Peer mainPeer;

    public MainFrame(Peer peer) {

        this.mainPeer = peer;

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

        // add listeners
//        this.addListenersToButtons();


        this.addListenersToButtons();
    }

    public MainPanel getPanel() {
        return this.panel;
    }

    public void setPanel(MainPanel panel) {
        this.panel = panel;
    }

//    public Peer getMainPeer() {
//        return this.mainPeer;
//    }
//
//    public void setMainPeer(Peer mainPeer) {
//        this.mainPeer = mainPeer;
//    }

    public void addListenersToButtons() {
        this.panel.getLayeredPane().getPeersPanel().getAddPeerButton()
                .addActionListener((ActionEvent e) -> {

                    // get input string
                    JTextField field = MainFrame.this.panel.getLayeredPane().getPeersPanel()
                            .getIpAddressField();
                    String input = field.getText();

                    // check input string
                    if (!(Launcher.fv.verifyIPAddress(input))) {
                        MainFrame.this.panel.clearFields();// empty the text field if nothing
                        return; // return
                    } else { // continue if true
                        // do stuff
                        this.mainPeer.addPeer(input);

                    }

                });

        this.panel.getLayeredPane().getChatPanel().getSendButton()
                .addActionListener((ActionEvent e) -> {

                    // get text
                    String text = MainFrame.this.panel.getLayeredPane().getChatPanel().getTextField().getText();
                    this.mainPeer.sendMessage(text);

                });
    }

    @Deprecated
    private Integer findRandomOpenPort() {
        try (ServerSocket socket = new ServerSocket(0);) {
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void print() {
        System.out.println("SUP BITCH MAIN FRAME LAST METHOD");
    }
}
