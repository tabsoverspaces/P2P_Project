package gui.custom_components;

import gui.CreatableComponents;
import udp_chat.Peer;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class PeerChooser extends JPanel implements CreatableComponents {

    public PeerChooser() {

        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 500, 500);
        this.setBackground(Color.RED);


    }

    public void initPanel(Peer peer) {

        // remove all prior components
        this.removeAll();

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
                this.add(b);
            }
        }
    }


    @Override
    public void createComponents() {

    }
}
