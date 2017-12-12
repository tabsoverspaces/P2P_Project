package gui.custom_components;

import gui.CreatableComponents;
import gui.FileTransferPanel;
import udp_chat.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class PeerChooser extends JPanel implements CreatableComponents {

    private String selectedAddress;

    private FileTransferPanel ftp;

    public PeerChooser() {

        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 500, 500);
        this.setBackground(Color.RED);

    }

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String address) {
        this.selectedAddress = address;
    }

    public void initPanel(Peer peer, ActionListener l1) {

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
                b.setPeerChooser(this);

                b.addActionListenerToButton();
                b.addActionListener(l1);
                this.add(b);
            }
        }

        // add action listeners

    }

    public void setFTP(FileTransferPanel ftp) {
        this.ftp = ftp;
    }


    @Override
    public void createComponents() {

    }

}
