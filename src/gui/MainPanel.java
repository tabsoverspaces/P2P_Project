package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel implements CreatableComponents {

    private ControlPanel controlPanel;
    private ContentLayeredPane layeredPane;

    public MainPanel() {

        this.setLayout(null);

        this.setBounds(0, 0, 815, 650);

        this.controlPanel = new ControlPanel();
        this.layeredPane = new ContentLayeredPane();
    }

    @Override
    public void createComponents() {
        this.controlPanel.createComponents();
        this.layeredPane.createComponents();

        this.add(this.controlPanel);
        this.add(this.layeredPane);

        this.addListenersToControlPanelButtons();
    }

    private void addListenersToControlPanelButtons() {
        this.controlPanel.getChatButton().addActionListener((ActionEvent e) -> {
            this.layeredPane.showChatPanel();
        });

        this.controlPanel.getPeersButton().addActionListener((ActionEvent e) -> {
            this.layeredPane.showPeersPanel();
        });
    }

}
