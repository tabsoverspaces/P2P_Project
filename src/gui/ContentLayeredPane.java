package gui;

import javax.swing.*;

public class ContentLayeredPane extends JLayeredPane implements CreatableComponents {

    private ChatPanel chatPanel;
    private PeersPanel peersPanel;

    public ContentLayeredPane() {

        this.setLayout(null);
        this.setBounds(0, 60, 815, 540);
    }

    @Override
    public void createComponents() {

        this.chatPanel = new ChatPanel();
        this.peersPanel = new PeersPanel();

        this.chatPanel.createComponents();
        this.peersPanel.createComponents();

        this.add(this.chatPanel, new Integer(0));
        this.add(this.peersPanel, new Integer(1));

        this.chatPanel.setVisible(true);
        this.peersPanel.setVisible(false);
    }

    public void showChatPanel() {
        this.setLayer(this.chatPanel, 10);
        this.setLayer(this.peersPanel, 0);

        this.chatPanel.setVisible(true);
        this.peersPanel.setVisible(false);
    }

    public void showPeersPanel() {
        this.setLayer(this.chatPanel, 0);
        this.setLayer(this.peersPanel, 10);

        this.chatPanel.setVisible(false);
        this.peersPanel.setVisible(true);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public PeersPanel getPeersPanel() {
        return peersPanel;
    }

    public void setPeersPanel(PeersPanel peersPanel) {
        this.peersPanel = peersPanel;
    }
}
