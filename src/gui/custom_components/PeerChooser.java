package gui.custom_components;

import gui.CreatableComponents;
import udp_chat.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class PeerChooser extends JPanel implements CreatableComponents {

    private String selectedAddress;

    private JButton backButton;

    public PeerChooser() {

        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 500, 500);
        this.setBackground(Color.RED);

        this.backButton = new JButton("Back");
        this.backButton.setBounds(0, 0, 150, 30);

    }

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String address) {
        this.selectedAddress = address;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public void initPanel(Peer peer, ActionListener l) {

        // remove all prior components
        this.removeAll();

        // add back button
        this.add(this.backButton);


        // if no addresses available, add label
        if (peer.getAddresses().size() == 0) {
            this.add(new Label("No peers available") {
                @Override
                public void setBounds(Rectangle r) {
                    super.setBounds(0, 0, 100, 50);
                }
            });
            return;
        } else {
            // add a button for each address
            for (InetAddress p : peer.getAddresses()) {
                PeerSelectButton b = new PeerSelectButton(p.toString());
                b.setPeerChooser(this);
                b.addActionListenerToButton();
                b.addActionListener(l);
                this.add(b);
            }
        }

        // add action listeners

    }

    @Override
    public void createComponents() {

    }

}
