package gui;

import udp_chat.Launcher;
import udp_chat.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


        this.addListenersToButtons();
    }

    public MainPanel getPanel() {
        return this.panel;
    }

    public void setPanel(MainPanel panel) {
        this.panel = panel;
    }

    /**
     * Method used to encapsulate all actionListener assignements that are done to the buttons
     */
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

        // action listener object for the peer choosing buttons
        /**
         * This class' sole purpose is to provide a custom actionlistener
         * for adding to the peer choosing buttons
         */
        class CustomActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.panel.getLayeredPane().getFileTransferPanel().resetViewAction();
//                MainFrame.this.mainPeer.getFileTransferHandler()
//                        .setSelectedAddress(this.toString());
//                MainFrame.this.panel.getLayeredPane().getFileTransferPanel()
//                        .getPeerAddressLabel().setText(this.getClass().toString());
            }
        }
        /**
         * The following lines of code add action listener(s) to the select peer choosers
         */
        this.panel.getLayeredPane().getFileTransferPanel().getSelectPeerButton()
                .addActionListener((ActionEvent e) -> {
                    this.panel.getLayeredPane().getFileTransferPanel().getPeerChooser()
                            .initPanel(this.mainPeer, new CustomActionListener());
                });
    }
}
