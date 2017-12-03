package gui;

import javax.swing.*;

public class PeersPanel extends JPanel implements CreatableComponents {

    private JTextField ipAddressField;
    private JButton addPeerButton;

    public PeersPanel() {
        this.setLayout(null);

        this.setBounds(10, 0, 780, 540);


    }

    @Override
    public void createComponents() {

        this.ipAddressField = new JTextField();
        this.addPeerButton = new JButton("Add peer");

        this.ipAddressField.setBounds(10, 10, 250, 30);
        this.addPeerButton.setBounds(10, 50, 120, 30);

        this.add(this.ipAddressField);
        this.add(this.addPeerButton);

    }

    public JTextField getIpAddressField() {
        return ipAddressField;
    }

    public void setIpAddressField(JTextField ipAddressField) {
        this.ipAddressField = ipAddressField;
    }

    public JButton getAddPeerButton() {
        return addPeerButton;
    }

    public void setAddPeerButton(JButton addPeerButton) {
        this.addPeerButton = addPeerButton;
    }
}
