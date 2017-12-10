package gui;

import javax.swing.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel implements CreatableComponents {

    private JButton chatButton;
    private JButton peersButton;
    private JButton fileTransferButton;

    private ArrayList<JButton> buttonList;

    public ControlPanel() {

        this.setLayout(null);
        this.setBounds(0, 0, 800, 50);

        this.buttonList = new ArrayList<>();
    }

    public JButton getFileTransferButton() {
        return fileTransferButton;
    }

    public void setFileTransferButton(JButton fileTransferButton) {
        this.fileTransferButton = fileTransferButton;
    }

    @Override
    public void createComponents() {

        this.chatButton = new JButton("Chat");
        this.peersButton = new JButton("Peers");
        this.fileTransferButton = new JButton("File transfer");

        this.buttonList.add(this.chatButton);
        this.buttonList.add(this.peersButton);
        this.buttonList.add(this.fileTransferButton);

        this.setButtonBounds();
        this.addButtons();

        // add action listeners to the buttons
        // here...

        this.setVisible(true);
    }

    /**
     * Method used to more programatically set the bounds of the buttons
     * that are contained in the control panel
     * Rather than doing it by hand, we do it....only half by hand..
     */
    private void setButtonBounds() {
        int startx = 10;
        int spacing = 20;

        int y = 10;

        int buttonWidth = 150;
        int buttonHeight = 30;

        // counts how many buttons have already been placed
        int count = 0;

        for (JButton b : this.buttonList) {
            int x = startx + (count * (spacing + buttonWidth));
            b.setBounds(x, y, buttonWidth, buttonHeight);
            count++;
        }

    }

    /**
     * Method used to add the buttons to the control panel
     * without having to do it manually
     */
    private void addButtons() {
        for (JButton b : this.buttonList) {
            this.add(b);
        }
    }

    public JButton getChatButton() {
        return this.chatButton;
    }

    public JButton getPeersButton() {
        return this.peersButton;
    }
}
